/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;
import Model.Employee;
import Model.Product;

/**
 *
 * @author Acer
 */
public class EmployeeDAO {

    public boolean insert(Employee employee) {
        String sql = "insert into Employee(EmplID, Name, Position, DOB, Gender, Address, Phone, Salary, Email) values(?,?,?,?,?,?,?,?,?)";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, employee.getEmplID());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setString(4, employee.getDOB());
            preparedStatement.setString(5, employee.getGender());
            preparedStatement.setString(6, employee.getAddress());
            preparedStatement.setString(7, employee.getPhone());
            preparedStatement.setString(8, employee.getSalary());
            preparedStatement.setString(9, employee.getEmail());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(Employee employee) throws Exception {
        String sql = "update Employee set Name = ?, Position = ?, DOB = ?, Gender = ?, Address = ?, Phone = ?, Salary = ?, Email = ? where EmplID = ?";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(9, employee.getEmplID());
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPosition());
            preparedStatement.setString(3, employee.getDOB());
            preparedStatement.setString(4, employee.getGender());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setString(6, employee.getPhone());
            preparedStatement.setString(7, employee.getSalary());
            preparedStatement.setString(8, employee.getEmail());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean delete(Employee employee) throws Exception {
        String sql = "delete from Employee where EmplID = ?";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, employee.getEmplID());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public Employee findByEmplID(String emplID) throws Exception {
        String sql = "select * from Employee where EmplID = ?";
        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, emplID);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmplID(resultSet.getString("EmplID"));
                    employee.setName(resultSet.getString("Name"));
                    employee.setPosition(resultSet.getString("Position"));
                    employee.setDOB(resultSet.getString("DOB"));
                    employee.setGender(resultSet.getString("Gender"));
                    employee.setAddress(resultSet.getString("Address"));
                    employee.setPhone(resultSet.getString("Phone"));
                    employee.setSalary(resultSet.getString("Salary"));
                    employee.setEmail(resultSet.getString("Email"));

                    return employee;
                }
            }
            return null;
        }
    }
    
    public Employee findByName(String name) throws Exception {
        String sql = "select * from Employee where Name = ?";
        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmplID(resultSet.getString("EmplID"));
                    employee.setName(resultSet.getString("Name"));
                    employee.setPosition(resultSet.getString("Position"));
                    employee.setDOB(resultSet.getString("DOB"));
                    employee.setGender(resultSet.getString("Gender"));
                    employee.setAddress(resultSet.getString("Address"));
                    employee.setPhone(resultSet.getString("Phone"));
                    employee.setSalary(resultSet.getString("Salary"));
                    employee.setEmail(resultSet.getString("Email"));

                    return employee;
                }
            }
            return null;
        }
    }

    public List<Employee> findAll() throws Exception {
        String sql = "Select * from Employee";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                List<Employee> list = new ArrayList<>();
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmplID(resultSet.getString("EmplID"));
                    employee.setName(resultSet.getString("Name"));
                    employee.setPosition(resultSet.getString("Position"));
                    employee.setDOB(resultSet.getString("DOB"));
                    employee.setGender(resultSet.getString("Gender"));
                    employee.setAddress(resultSet.getString("Address"));
                    employee.setPhone(resultSet.getString("Phone"));
                    employee.setSalary(resultSet.getString("Salary"));
                    employee.setEmail(resultSet.getString("Email"));
                    
                    list.add(employee);

                }
                return list;
            }
        }

    }
}
