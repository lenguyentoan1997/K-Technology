/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.toedter.calendar.JDateChooser;
import javax.swing.JTextField;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class Validator {

    public static boolean checkEmpty(JTextField field, StringBuilder sb, String msg) {
        boolean check = true;
        if (field.getText().trim().isEmpty()) {
            sb.append(msg).append("\n");
            check = false;
        }
        return check;
    }


    public static boolean checkQuantity(JTextField field, StringBuilder sb) {
        boolean check = true;
        if (!checkEmpty(field, sb, "Please Enter Product Quantity ")) {
            return false;
        } else {
            String quantity = field.getText();
            String reg = "^(\\d){0,8}(\\d)$";
            if (quantity.length() > 0) {
                if (!quantity.matches(reg)) {
                    sb.append("Product Quantity: Please Enter Number And Limited to 10 Numbers \n");
                    check = false;
                }
            }
        }
        return check;
    }

    public static boolean checkPrice(JTextField field, StringBuilder sb) {
        boolean check = true;
        if (!checkEmpty(field, sb, "Please Enter Product Price ")) {
            return false;
        } else {
            String price = field.getText();
            String reg = "^(\\d){0,4}(\\d)$";
            if (price.length() > 0) {
                if (!price.matches(reg)) {
                    sb.append("Product Price: Please Enter Number And Limited to 6 Numbers \n");
                    check = false;
                }
            }
        }
        return check;
    }

    public static boolean checkPhone(JTextField field, StringBuilder sb) {
        boolean check = true;
        if (!checkEmpty(field, sb, "Please Enter Phone")) {
            return false;
        } else {
            String phone = field.getText();
            String reg = "^(\\d){0,20}(\\d)$";
            if (phone.length() > 0) {
                if (!phone.matches(reg)) {
                    sb.append("Producer Phone: Please Enter Number And Limited To 22 Numbers \n");
                    check = false;
                }
            }
        }
        return check;
    }

    public static boolean checkName(JTextField field, StringBuilder sb, String msg, String message) {
        boolean check = true;
        if (!checkEmpty(field, sb, msg)) {
            return false;
        } else {
            String name = field.getText();
            String reg = "^([a-z A-Z]){0,30}$";
            if (name.length() > 0) {
                if (!name.matches(reg)) {
                    sb.append(message + "\n");
                    check = false;
                }
            }
        }
        return check;
    }

    public static boolean checkMail(JTextField field, StringBuilder sb) {
        boolean check = true;
        String mail = field.getText();
        String reg = "^(.*)(@)(.*)$";
        if (mail.length() > 0) {
            if (mail == "no") {
                return true;
            }
            if (!mail.matches(reg)) {
                sb.append("Mail:Plase Enter Mail And Must Have '@'");
                check = false;
            }
        }
        return false;
    }

    public static boolean checkNumber(JTextField field, StringBuilder sb) {
        boolean check = true;
        if (!checkEmpty(field, sb, "Please Enter Number")) {
            return false;
        } else {
            String phone = field.getText();
            String reg = "^(\\d){0,20}(\\d)$";
            if (phone.length() > 0) {
                if (!phone.matches(reg)) {
                    sb.append("Number: Please Enter Number And Limited To 22 Numbers \n");
                    check = false;
                }
            }
        }
        return check;
    }

}
