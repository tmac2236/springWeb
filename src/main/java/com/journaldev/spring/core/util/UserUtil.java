package com.journaldev.spring.core.util;

import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import com.journaldev.web.auth.entity.User;
import com.journaldev.web.auth.dao.UserDao;
import com.journaldev.spring.core.util.ContextUtil;

public class UserUtil {
    private static final String salt = "ck#Yj(";
    private static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap();
    private static final String[] HEADERS_TO_TRY = new String[]{"X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    public static User authLogin(String account, String password) {
        UserDao userDao = (UserDao) ContextUtil.getBean(UserDao.class);
        User user = ((UserDao) ContextUtil.getBean(UserDao.class)).findOneByAccount(account);
        return user != null && StringUtils.isNotBlank(password)
                && EncryptionUtil.encryptSHA512("ck#Yj(" + password).equals(user.getPassword()) ? user : null;
    }

    public static String encryptPassword(String password) {
        return EncryptionUtil.encryptSHA512("ck#Yj(" + password);
    }

    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session != null ? (User) request.getSession().getAttribute("User") : null;
    }

    public static boolean hasAnyRoles(User user, String roles) {
        StringTokenizer tokenizer = new StringTokenizer(roles, ",");

        String role;
        do {
            if (!tokenizer.hasMoreElements()) {
                return false;
            }

            role = tokenizer.nextToken();
        } while (!StringUtils.equalsIgnoreCase(role, user.getRole()));

        return true;
    }

    public static User getCacheUser(String account) {
        return (User) userMap.get(account);
    }

    public static void setUserCache(User user) {
        userMap.put(user.getAccount(), user);
    }

    public static void removeUserCache(User user) {
        if (user != null) {
            User oldUser = getCacheUser(user.getAccount());
            //if (oldUser != null && oldUser.getUUID().equals(user.getUUID())) {
                userMap.remove(user.getAccount());
            //}
        }

    }

    public static String getIpAddr(HttpServletRequest request) {
        String[] arg0 = HEADERS_TO_TRY;
        int arg1 = arg0.length;

        for (int arg2 = 0; arg2 < arg1; ++arg2) {
            String header = arg0[arg2];
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}