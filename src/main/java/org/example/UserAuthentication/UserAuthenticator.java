package org.example.UserAuthentication;

import org.example.UserAuthentication.CaptchaGenerator;
import org.example.UserAuthentication.HashPassword;

import java.io.*;
import java.util.Scanner;

public class UserAuthenticator {
    public static String userName;

    private final String filepath = "src/main/java/org/example/UserAuthentication/registereduser.txt";

    /**
     *User register
     * @param sc
     */
    public void registerUser(Scanner sc) {
        System.out.println("Register Yourself");
        sc.nextLine();

        System.out.println("Enter Your Name");
        String name = sc.nextLine();
        System.out.println("Enter Password");
        String password = sc.nextLine();

        String captcha = CaptchaGenerator.generateCaptcha(9);
        System.out.println("CAPTCHA: " + captcha);
        System.out.println("Enter above CAPTCHA: ");
        String userCaptcha = sc.nextLine();
        if (CaptchaGenerator.checkCaptcha(captcha, userCaptcha)) {
            System.out.println("User Registered");
            String hashedPassword = HashPassword.hash(password);
            try (FileWriter fw = new FileWriter(filepath, true)) {
                fw.write(name + "?" + hashedPassword + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Enter Captcha Correctly");
        }
    }

    /**
     *User Login
     * @param sc
     * @return
     */
    public boolean loginUser(Scanner sc) {
        System.out.println("Login Now");
        sc.nextLine();
        System.out.println("Enter Your Name");
        String loginname = sc.nextLine();
        userName = loginname;
        System.out.println("Enter Your Password");
        String loginpassword = sc.nextLine();
        String hashedLoginPassword = HashPassword.hash(loginpassword);

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split("\\?");
                if (credentials.length == 2 && credentials[0].equals(loginname) && credentials[1].equals(hashedLoginPassword)) {
                    System.out.println("Login Successful");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Invalid Login Credentials");
        return false;
    }
}
