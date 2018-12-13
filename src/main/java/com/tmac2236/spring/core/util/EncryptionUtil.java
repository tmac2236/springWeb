package com.tmac2236.spring.core.util;

import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionUtil {
    private static Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA";
    private static final String SHA256 = "SHA-256";
    private static final String SHA512 = "SHA-512";

    public static String toHexString(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public byte[] parseHexString(String hexString) {
        return DatatypeConverter.parseHexBinary(hexString);
    }

    private static String encrypt(String algorithm, String words) {
        if (StringUtils.isBlank(algorithm)) {
            return "";
        } else {
            try {
                MessageDigest e = MessageDigest.getInstance(algorithm);
                byte[] bytes = e.digest(words.getBytes("UTF-8"));
                return toHexString(bytes);
            } catch (Exception arg3) {
                logger.error("error in " + algorithm + " encrypt", arg3);
                return "";
            }
        }
    }

    public static String encryptMD5(String words) throws Exception {
        return encrypt("MD5", words);
    }

    public static String encryptSHA1(String words) throws Exception {
        return encrypt("SHA", words);
    }

    public static String encryptSHA256(String words) throws Exception {
        return encrypt("SHA-256", words);
    }

    public static String encryptSHA512(String words) {
        return encrypt("SHA-512", words);
    }

    public String encryptAES(String key, String initVector, String value) {
        try {
            IvParameterSpec e = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(1, skeySpec, e);
            byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception arg7) {
            logger.error("error in encryptAES", arg7);
            return null;
        }
    }

    public String decryptAES(String key, String initVector, String encrypted) {
        try {
            encrypted = encrypted.replace(" ", "+");
            IvParameterSpec e = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, skeySpec, e);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original, "UTF-8");
        } catch (Exception arg7) {
            logger.error("error in decryptAES", arg7);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(encryptSHA512("ck#Yj(admin"));
        System.out.println(encryptSHA512("ck#Yj(guest"));
    }
}