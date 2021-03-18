/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Model.Account;
import Model.Employee;
import View.Accounts;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Acer
 */
public class AccountDAO {

    public static Account userLogin;
    private Account account;

    private Connection connection;
    Employee empl = new Employee();
    private ArrayList<Account> listAccout = new ArrayList<>();

    public AccountDAO() {
        try {
            connection = DatabaseConnection.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Account> getListAccounts() {
        String sql = "Select * from Account";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setUserName(resultSet.getString("UserName"));
                account.setDecentralization(resultSet.getString("Decentralization"));
                account.setEmplID(resultSet.getString("EmplID"));
                listAccout.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAccout;

    }

    public void fillCbbEmpl(JComboBox cbb) {
        String sql = "select * from dbo.employee";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                cbb.addItem(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Employee setLblEmplId(String name) {
        String sql = "select emplId from employee where name=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                empl.setEmplID(rs.getString("emplId"));
                return empl;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee setLblEmplName(JComboBox cbb, String emplId) {
        String sql = "select name from employee where emplId=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, emplId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                empl.setName(rs.getString("name"));
                cbb.setSelectedItem(empl.getName());
                return empl;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findAll(String userName) {
        String sql = "select * from dbo.account where username =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setUserName(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDecentralization(rs.getString("decentralization"));
                account.setEmplID(rs.getString("emplId"));
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Account account) {
        if (account.getDecentralization() == "admin") {
            String sql = "insert into Account(UserName, Password, Decentralization) values(?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getUserName());
                preparedStatement.setString(2, encrypt(account.getPassword()));
                preparedStatement.setString(3, account.getDecentralization());
                return preparedStatement.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String sql1 = "insert into Account(UserName, Password, Decentralization, EmplID) values(?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, account.getUserName());
                preparedStatement.setString(2, encrypt(account.getPassword()));
                preparedStatement.setString(3, account.getDecentralization());
                preparedStatement.setString(4, account.getEmplID());
                return preparedStatement.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean update(Account account) {
        String sql = "update Account set password = ? where username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  encrypt(account.getPassword()));
            preparedStatement.setString(2, account.getUserName());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean delete(Account account) {
        String sql = "delete from Account where username = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, account.getUserName());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Account> findByUserName(String userName) throws Exception {
        String sql = "select * from dbo.account where username like'%" + userName + "%'";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    Account account = new Account();
                    account.setUserName(resultSet.getString("UserName"));
                    account.setPassword(resultSet.getString("Password"));
                    account.setDecentralization(resultSet.getString("Decentralization"));
                    account.setEmplID(resultSet.getString("EmplID"));
                    listAccout.add(account);
                }
            }
            return listAccout;
        }
    }

    public Account checkLogin(String userNamee, String password) {
        String sql = "select * from account where username=? and password=?";
        try {
            Connection con = DatabaseConnection.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userNamee);
            ps.setString(2, encrypt(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account us = new Account();
                us.setUserName(userNamee);
                us.setDecentralization(rs.getString("Decentralization"));
                return us;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//encrypt MD5

    public static String encrypt(String srcText) {
        try {

            String enrText;
            MessageDigest msd = MessageDigest.getInstance("MD5");
            byte[] srcTextBytes = srcText.getBytes("UTF-8");
            byte[] enrTextBytes = msd.digest(srcTextBytes);
            BigInteger bigInt = new BigInteger(1, enrTextBytes);
            enrText = bigInt.toString(16);
            return enrText;
        } catch (Exception e) {
        }
        return null;
    }

}
