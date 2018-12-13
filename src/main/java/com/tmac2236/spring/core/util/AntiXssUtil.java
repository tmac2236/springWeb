package com.tmac2236.spring.core.util;


import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiXssUtil {
    private static Logger logger = LoggerFactory.getLogger(AntiXssUtil.class);
    private static Policy policy;
    private static AntiSamy antiSamy;

    public static String getCleanHTML(String input) {
        String output = "";

        try {
            if (input != null) {
                output = antiSamy.scan(input, policy).getCleanHTML();
            }
        } catch (Exception arg2) {
            logger.info("AntiXss Scan Error", arg2);
        }

        return output;
    }

    public static String getTrimCleanHTML(String input) {
        return StringUtils.trim(getCleanHTML(input));
    }

    public static String getTrimCleanText(String input) {
        return Pattern.compile("<[^>]*>", 32).matcher(input).replaceAll("").trim();
    }

    public static void main(String[] args) {
        String href = "http://www.google.com\nasd<br>aaa";
        String script = "<script>alert(\'你被script攻擊了\')</script>過濾script";
        String imgLink = "<img src=\'www.google.com/123.gif\'/>過濾圖片";
        String hrefLink = "<a href=\'www.google.com\'>過濾連結</a>";
        String string = "一般文字...";
        System.out.println(getTrimCleanText(href));
        System.out.println(getCleanHTML(script));
        System.out.println(getCleanHTML(imgLink));
        System.out.println(getCleanHTML(hrefLink));
        System.out.println(getCleanHTML(string));
    }

    static {
        try {
            policy = Policy.getInstance(
                    Thread.currentThread().getContextClassLoader().getResource("antisamy-slashdot-1.4.4.xml"));
            antiSamy = new AntiSamy();
            logger.info("AntiXssUtil 初始化成功");
        } catch (PolicyException arg0) {
            logger.error("AntiXssUtil 初始化失敗", arg0);
        }

    }
}
