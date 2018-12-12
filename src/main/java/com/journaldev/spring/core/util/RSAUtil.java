package com.journaldev.spring.core.util;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtil {
    private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);
    public static final String ALGORITHM = "RSA";
    public static final String PADDING = "RSA/NONE/PKCS1PADDING";
    public static final String PROVIDER = "BC";
    public static PrivateKey defaultPrivateKey;
    public static PublicKey defaultPublicKey;

    public static byte[] encrypt(String text, PublicKey publicKey) {
        if (text == null) {
            logger.warn("error in RSAUtil encrypt, text is null");
            return null;
        } else {
            byte[] encrypted = null;

            try {
                Cipher e = Cipher.getInstance("RSA/NONE/PKCS1PADDING", "BC");
                e.init(1, publicKey);
                encrypted = e.doFinal(text.getBytes("UTF-8"));
            } catch (Exception arg3) {
                logger.warn("error in RSAUtil encrypt!", arg3);
            }

            return encrypted;
        }
    }

    public static String encryptToBase64(String text, PublicKey publicKey) {
        byte[] encrypted = encrypt(text, publicKey);
        return encrypted != null ? Base64.getEncoder().encodeToString(encrypted) : null;
    }

    public static String encryptToBase64(String text) {
        return encryptToBase64(text, defaultPublicKey);
    }

    public static String decrypt(byte[] encrypted, PrivateKey privateKey) {
        try {
            Cipher e = Cipher.getInstance("RSA/NONE/PKCS1PADDING", "BC");
            e.init(2, privateKey);
            byte[] dectypted = e.doFinal(encrypted);
            return new String(dectypted, "UTF-8");
        } catch (Exception arg3) {
            logger.warn("error in RSAUtil decrypt!", arg3);
            return null;
        }
    }

    public static String decryptFromBase64(String encryptedText, PrivateKey privateKey) {
        if (encryptedText == null) {
            logger.warn("error in RSAUtil decryptFromBase64, encryptedText is null");
            return null;
        } else {
            encryptedText = encryptedText.replace(" ", "+");
            byte[] encrypted = Base64.getDecoder().decode(encryptedText);
            return encrypted != null ? decrypt(encrypted, privateKey) : null;
        }
    }

    public static String decryptFromBase64(String encryptedText) {
        return decryptFromBase64(encryptedText, defaultPrivateKey);
    }

    public static void main(String[] args) {
        String originalText = "12345678901234567890123456789012";
        String encryptedText = encryptToBase64(originalText);
        String plainText = decryptFromBase64(encryptedText);
        System.out.println("Original=12345678901234567890123456789012");
        System.out.println("Encrypted=" + encryptedText);
        System.out.println("Decrypted=" + plainText);
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
        String privateKeyBase64 = "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";
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        String publicKeyBase64 = "MIIFXTCCA0WgAwIBAgIJAKXZk7eH6dwOMA0GCSqGSIb3DQEBCwUAMEUxCzAJBgNVBAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQwHhcNMTcwMTEwMDE0ODIxWhcNMTcwMjA5MDE0ODIxWjBFMQswCQYDVQQGEwJBVTETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA65kUsman55f0KUsumm2gidkpk09lPdqgNvv1hvJbdAqlyP2MS/d/9T9NjLtAPqPdl9ZtP+60Fc4Vknz83iYdjHWifP39opAS/QFBcI2u51DDzrgKduWPRz7f1uCReq6dgt9LOyk1sDFsrkDmnQ6T/s+zNR7qr0y5CXxPJtc14zAkZsBjQRbi4XCLtAqvOYGsaCd/biB3BCwwvLDPaFA+IfpObQ8kQaKY2VE3M6YNaxafKDq7imDB2ARmDgXMURWhH54B27ekqSmMLEtg8LF7aXBWDTtUXPenmeFIsU+Fjisxf3c5VzNRnKtsK0T+nNH95U9ogvHvVY53fL4lx8JaZ6a4ExL0QaT1yIrbR+7v0KuV9GaWd29+RDi4QPmlwBq/NGspm4c8RWyNBLe/z4YzhYWXxhV80kLm1yDDAMt/0a6DBasNsqX7GlBxg/8SjpTGwplOu+eBxO8ng3moG8WLIzECUkRWvf9V2/5WBslYY/VyQ9/paU5eWUxqAbDiUgAW0oMuT7QkW107ylci5y5wzgxRkB4pBATlW8uezC9hxwBL6ls1VdOCYE6uJ+56qzARE3srJtF+WFm7g8Clqy63VYzToUSVqR010QBuERdL+SmgGVD8+b7Ka/jpwtu84Hx9SKouVEENG/XCYdfgPsjSWElV/KUWJKALwnEKHyOj8gkCAwEAAaNQME4wHQYDVR0OBBYEFMZX4M9p6JQOIs3qHnPGbht+YLf4MB8GA1UdIwQYMBaAFMZX4M9p6JQOIs3qHnPGbht+YLf4MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggIBAJ1wemZ0/zOr0Kd92OgEmjkoNhVP2X5TqyV3OvIVrjltUphXvfqn3BlJ+OjFtX3kIpSDORuyLcrcIzWYtGI1gVAmKa6SeGaIJl8XrNggfErq0gpktwMMqJ218O5zNZd1hSd7zshvp3N+GPG6ZVXrveH1KLjj5++T7ha2vVfCbsb4VEoxaIUQbe1SHhuHA+x/kbXVWSVmEkxE9V5n0RIMu0f4dWcRk5sm5KgtD0Gl6qynN8xBc2mdqzLseXz73WENWL0yCSnuBhvTPxU5LAXxiGIonPZJwkQ+u59J6cMAbySXT/Cfolll8NLnMUiyb9qY03ifr7DRjq8DslyXtFFnab5d1ssQaq0ovfDzS7bsX1biVmDx6CV73axa1A9GqGbGxtIlZwLdLlUI/7Y6oIG4LtT1/3EVOrUcZW4J+ze3ipiNuTlTNYRNzP87adIJ8NMVgL7N1WFxEe5Y3slcJXtuYoK1ew/90BgJEeXDMS9rKPOIz0uTUqTot9YnH5pIWFagJYS5Wl6C0vrkOtBPMPH2W15ucNlvhhl2brXLeAqwBpbI7cU/ITM4kR8JxwnQ1hM8UwxTT9yrMlqu+jywT+m1jGzdR04HiRqHmJ7JvGuFAggcD8j8xPYgk/+UYONY4OY/zCv2JYYd+dPWtnfEMzmjGhKP4xJG3k2cduMQDJ531Zf2";
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        try {
            KeyFactory e = KeyFactory.getInstance("RSA", "BC");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            defaultPrivateKey = e.generatePrivate(privateKeySpec);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(publicKeyBytes));
            defaultPublicKey = certificate.getPublicKey();
        } catch (Exception arg7) {
            logger.warn("error in RSAUtil, 預設金鑰錯誤!", arg7);
        }

    }
}
