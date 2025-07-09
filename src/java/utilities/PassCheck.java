/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

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
}
