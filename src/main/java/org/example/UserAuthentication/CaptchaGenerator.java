package org.example.UserAuthentication;

public class CaptchaGenerator {
    /**
     *This will generate captcha
     *Taken reference from GEEKSFORGEEKS
     * @param n
     * @return It is returning generated captch
     */
    public static String generateCaptcha(int n){
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String captcha = "";
        while (n-- > 0){
            int index = (int)(Math.random() * 62);
            captcha += chrs.charAt(index);
        }
        return captcha;
    }

    public static boolean checkCaptcha(String captcha, String userCaptcha){
        return captcha.equals(userCaptcha);
    }
}
