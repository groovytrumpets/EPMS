/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author nguye
 */
public class PassCheck {

    public static void main(String[] args) {
        System.out.println(isStrongPassword("a"));
    }

    public static boolean isStrongPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
        return password.matches(regex);
    }
    public static String generateSecurePassword(int length) {
        if (length < 8) throw new IllegalArgumentException("Password must be at least 8 characters");

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{};:,.<>?";
        String all = upper + lower + digits + special;

        SecureRandom random = new SecureRandom();
        List<Character> chars = new ArrayList<>();

        // Ít nhất 1 ký tự mỗi loại
        chars.add(upper.charAt(random.nextInt(upper.length())));
        chars.add(lower.charAt(random.nextInt(lower.length())));
        chars.add(digits.charAt(random.nextInt(digits.length())));
        chars.add(special.charAt(random.nextInt(special.length())));

        // Thêm ký tự ngẫu nhiên còn lại
        for (int i = 4; i < length; i++) {
            chars.add(all.charAt(random.nextInt(all.length())));
        }

        // Trộn thứ tự ký tự
        Collections.shuffle(chars);

        // Ghép thành chuỗi
        StringBuilder password = new StringBuilder();
        for (char c : chars) password.append(c);

        return password.toString();
    }
}
