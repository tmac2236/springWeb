package com.tmac2236.spring.core.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmac2236.spring.core.util.UserUtil;
import com.tmac2236.web.auth.dao.ACLDao;
import com.tmac2236.web.auth.dao.UserDao;
import com.tmac2236.web.auth.entity.ACL;
import com.tmac2236.web.auth.entity.ACLLocation;
import com.tmac2236.web.auth.entity.User;

@Controller
public class ViewController {
    
    private static Logger logger = LoggerFactory.getLogger(ViewController.class);
    
    @Autowired
    ACLDao aclDao;
    @Autowired
    UserDao userDao;
    
    @RequestMapping(value = { "/main" })
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        HttpSession session = request.getSession();
        User user = UserUtil.getUser(request);
        String userId = user.getUserId();
        List<ACL> ACLList = aclDao.findACLByUserId(userId);
        
        List<ACLLocation> resourceIdList = new ArrayList<ACLLocation>();
        JSONArray jSONArray = new JSONArray();
        for(ACL acl : ACLList) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("resourceId", acl.getACLLocation().getResourceId());
            jSONObject.put("lat",String.valueOf(acl.getACLLocation().getLat()));
            jSONObject.put("lng",String.valueOf(acl.getACLLocation().getLng()));
            jSONArray.put(jSONObject);
            //resourceIdList.add(acl.getACLLocation());
        }
        session.setAttribute("resourceIdList", jSONArray);
        
        return new ModelAndView("/main");
    }

    @RequestMapping(value = { "/profile" })
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        HttpSession session = request.getSession();
        List<User> userList= userDao.findAll();
        
        session.setAttribute("userList", userList);
        
        return new ModelAndView("/profile/profile");
    }
}
