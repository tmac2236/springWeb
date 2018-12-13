package com.tmac2236.web.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tmac2236.web.auth.dao.ACLDao;
import com.tmac2236.web.auth.dao.ACLLocationDao;
import com.tmac2236.web.auth.dao.UserDao;
import com.tmac2236.web.auth.entity.ACL;
import com.tmac2236.web.auth.entity.ACLLocation;
import com.tmac2236.web.auth.entity.User;

/**
 * Cofiguring the Acl of User Service
 */
@Service
public class ConfigureAclOfUser {
    
    private static Logger logger = LoggerFactory.getLogger(ConfigureAclOfUser.class);
    
    @Autowired
    ACLDao aCLDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ACLLocationDao aCLLocationDao;
    //@PostConstruct
    public void exec() {
        logger.info("[START] MannualSetACL exec()");
        List<User> userList = userDao.findAll();
        System.out.println(initialzeAclSetting(userList));
        logger.info("[END] MannualSetACL exec()");
    }
    
    

    /**
     * 自動建置每個user依照權限設定初始化的ACL
     */
    public List<ACL> initialzeAclSetting(List<User> userList) {
        List<String> iconStrList = new ArrayList<String>();
        iconStrList.add("avi0");
        iconStrList.add("avi1");
        iconStrList.add("avi2");
        iconStrList.add("avi3");

        iconStrList.add("cms0");
        iconStrList.add("cms1");
        iconStrList.add("cms2");
        iconStrList.add("cms3");

        iconStrList.add("etag0");
        iconStrList.add("etag1");
        iconStrList.add("etag2");
        iconStrList.add("etag3");

        iconStrList.add("sig0");
        iconStrList.add("sig1");
        iconStrList.add("sig2");
        iconStrList.add("sig3");

        iconStrList.add("vd0");
        iconStrList.add("vd1");
        iconStrList.add("vd2");
        iconStrList.add("vd3");
        
        
        for (User user : userList) {
            String roleId = user.getRole();
            String userId = user.getUserId();
            
            List<ACL> aclList = new ArrayList<ACL>();
            for (int i = 0; i < iconStrList.size(); i++) {
                
                String iconName = iconStrList.get(i);
                String lastChar = iconName.substring(iconName.length() - 1);
                
                //admin:3  user:2   guest:1   all
                if (Integer.valueOf(roleId) >= Integer.valueOf(lastChar)) {
                    ACL acl = new ACL();
                    acl.setRoleId(roleId);
                    acl.setUserId(userId);
                    ACLLocation aCLLocation = aCLLocationDao.findACLLocationByResourceId(iconName);
                    acl.setACLLocation(aCLLocation);
                    
                    aclList.add(acl);
                }
            }
            if (!aclList.isEmpty()) {
                aCLDao.save(aclList);
                return aclList;
            }
        }
        // if the result contains no elements return a arraylist.
        return new ArrayList<ACL>()  ;
    }
}
