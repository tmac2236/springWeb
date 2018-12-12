package com.journaldev.web.auth.ctrl;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.journaldev.spring.core.dto.AjaxDto;
import com.journaldev.spring.core.util.EncryptionUtil;
import com.journaldev.spring.core.util.RSAUtil;
import com.journaldev.spring.core.util.UserUtil;
import com.journaldev.web.auth.dao.UserDao;
import com.journaldev.web.auth.dto.UserDto;
import com.journaldev.web.auth.entity.User;
import com.journaldev.web.auth.service.ConfigureAclOfUser;

@Controller
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private ConfigureAclOfUser configureAclOfUser;

    // 登入
    @RequestMapping({ "/login" })
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        boolean isValidate = false;
        String account = "";
        String password = "";
        String jsEncrypt = request.getParameter("jsEncrypt");
        if (StringUtils.isNotBlank(jsEncrypt)) {
            String user = RSAUtil.decryptFromBase64(jsEncrypt);
            if (StringUtils.isNotBlank(user)) {
                String[] cacheUser = user.split(" ");
                if (cacheUser.length == 2) {
                    account = cacheUser[0];
                    password = cacheUser[1];
                    if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)) {
                        isValidate = true;
                    }
                }
            }

            if (isValidate) {
                User user1 = UserUtil.authLogin(account, password);
                if (user1 != null) {
                    User cacheUser1 = UserUtil.getCacheUser(account);
                    User oldSession = UserUtil.getUser(request);
                    HttpSession session;

                    if (oldSession != null) {
                        if (!account.equals(oldSession.getAccount())) {
                            UserUtil.removeUserCache(oldSession);
                        } else if (cacheUser1 != null) {

                            return new ModelAndView(new RedirectView("main"));
                        }
                    }

                    session = request.getSession();
                    user1.setPassword("");
                    session.setAttribute("User", user1);
                    UserUtil.setUserCache(user1);

                    return new ModelAndView(new RedirectView("main"));
                }
            }
            return new ModelAndView("../../login", "msg", "帳號或密碼錯誤");
        } else {
            return new ModelAndView("../../login");
        }
    }

    // 登出
    @RequestMapping({ "/logout" })
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            User user = (User) session.getAttribute("User");
            if (user != null) {
                UserUtil.removeUserCache(user);
            }
        }

        session.removeAttribute("User");
        session.removeAttribute("resourceIdList");
        session.invalidate();
        return new ModelAndView("../../login");
    }

    @ResponseBody
    @RequestMapping(value = { "/user/delete" }, method = { RequestMethod.POST })
    public AjaxDto deleteUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("userId") String userId) {
        
        AjaxDto dto = new AjaxDto();
        User sessionData = UserUtil.getUser(request);
        if (sessionData == null) {
            dto.setAction("logout");
            return dto;
        } else {
            if (StringUtils.isNotBlank(userId)) {
                try {
                    this.userDao.delete(userId);
                    logger.info("刪除帳號 userId : " + userId + "成功");
                    if (this.userDao.findOne(userId) == null) {
                        dto.setStatusOK();
                        return dto;
                    }
                } catch (Exception arg8) {
                    logger.info("刪除帳號 userId : " + userId + "失敗", arg8);
                }
            }

            dto.setMessage("刪除帳號 userId : " + userId + " , 請重新整理頁面後再試一次!");
            return dto;
        }
    }
    
    @ResponseBody
    @RequestMapping(value = { "/user/edit" }, method = { RequestMethod.POST })
    public AjaxDto getUserEdit(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto userDto) {
        AjaxDto dto = new AjaxDto();
        
        String userId =userDto.getUserId();
        User dbUser = userDao.findOneByUserId(userId);
        if(userDao.findOneByAccount(userDto.getAccount()) != null && !dbUser.getAccount().equals(userDto.getAccount())){
            dto.setMessage("帳號已有其他人使用");
            return dto;
        }
        User userData = userDao.findOne(userDto.getUserId());

        if (userData == null) {
            dto.setAction("logout");
            return dto;
        } else {
            boolean roleHasChanged = (userData.getRole()).equals(userDto.getRole());

            userData.setAccount(userDto.getAccount());
            userData.setAddress(userDto.getAddress());
            userData.setMail(userDto.getMail());
            userData.setName(userDto.getName());
            String keyInPassword = userDto.getPassword().trim();
            if (keyInPassword.equals("") || keyInPassword == null) {
                // 如果輸入空字串沿用舊密碼
            } else {
                String decodeBse64 = RSAUtil.decryptFromBase64(keyInPassword);
                String encryptedPassword = EncryptionUtil.encryptSHA512("ck#Yj(" + decodeBse64.trim());
                userData.setPassword(encryptedPassword);
            }

            String keyInRole = userDto.getRole();
            if (keyInRole == null) {
                // 如果null沿用舊權限
            } else {
                userData.setRole(keyInRole);
            }
            userData.setPhone(userDto.getPhone());
            if (this.userDao.save(userData) != null) {
                logger.info(" 帳號 :" + userData.getAccount() + "修改成功 : " + userData.getRole());
                if (roleHasChanged) {
                    configureAclOfUser.initialzeAclSetting(Arrays.asList(userData));
                    logger.info(" 帳號 :" + userData.getAccount() + "權限修改成 " + userData.getRole() + "ACL 初始化完成.......");
                }
                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute("User");
                String sessionUserId = sessionUser.getUserId();
                //本人修改自己
                if(userData.getUserId().equals(sessionUserId)){
                    session.setAttribute("User", userData);
                    UserUtil.setUserCache(userData);
                }

                dto.setData(userDto);
                dto.setStatusOK();
                return dto;
            }

        }
        return dto;
    }
    
    @ResponseBody
    @RequestMapping(value = { "/user/add" }, method = { RequestMethod.POST })
    public AjaxDto getUserAdd(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto userDto) {
        AjaxDto dto = new AjaxDto();

        if (userDao.findOneByAccount(userDto.getAccount()) != null) {
            dto.setMessage("帳號已有其他人使用");
            return dto;
        } else {
            User addUser = new User();
            addUser.setAccount(userDto.getAccount());
            addUser.setName(userDto.getName());
            addUser.setPhone(userDto.getPhone());
            addUser.setAddress(userDto.getAddress());
            addUser.setMail(userDto.getMail());
            addUser.setRole(userDto.getRole());
            
            String keyInPassword = userDto.getPassword().trim();
            String decodeBse64 = RSAUtil.decryptFromBase64(keyInPassword);
            String encryptedPassword = EncryptionUtil.encryptSHA512("ck#Yj(" + decodeBse64.trim());
            addUser.setPassword(encryptedPassword);
            
            //To get UserId
            User user = this.userDao.save(addUser);
            if (user != null) {
                logger.info(" 帳號 :" + user.getAccount() + "新增成功  , Role: " + user.getRole());
                configureAclOfUser.initialzeAclSetting(Arrays.asList(user));
                logger.info(" 帳號 :" + user.getAccount() + "權限為 " + user.getRole() + "ACL 初始化完成.......");
                
                userDto.setUserId(user.getUserId());
                dto.setData(userDto);
                dto.setStatusOK();
                return dto;
            }

        }

        return dto;

    }

}