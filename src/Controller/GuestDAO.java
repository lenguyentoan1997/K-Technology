/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;
import Model.Guest;

/**
 *
 * @author Acer
 */
public class GuestDAO {

    public boolean insert(Guest guest) throws Exception {
        String sql = "insert into Guest(GuestID, Name, Address, Phone, Email) values(?,?,?,?,?)";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, guest.getGuestID());
            preparedStatement.setString(2, guest.getName());
            preparedStatement.setString(3, guest.getAddress());
            preparedStatement.setString(4, guest.getPhone());
            preparedStatement.setString(5, guest.getEmail());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean update(Guest guest) throws Exception {
        String sql = "update Guest set Name = ?, Address = ?, Phone = ?, Email = ? where GuestID = ?";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(5, guest.getGuestID());
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setString(2, guest.getAddress());
            preparedStatement.setString(3, guest.getPhone());
            preparedStatement.setString(4, guest.getEmail());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean delete(Guest guest) throws Exception {
        String sql = "delete from Guest where GuestID = ?";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, guest.getGuestID());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public Guest findByGuestID(String guestID) throws Exception {
        String sql = "select * from Guest where GuestID = ?";
        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, guestID);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    Guest guest = new Guest();
                    guest.setGuestID(resultSet.getString("GuestID"));
                    guest.setName(resultSet.getString("Name"));
                    guest.setAddress(resultSet.getString("Address"));
                    guest.setPhone(resultSet.getString("Phone"));
                    guest.setEmail(resultSet.getString("Email"));

                    return guest;
                }
            }
            return null;
        }
    }

    public List<Guest> findAll() throws Exception {
        String sql = "Select * from Guest";

        try (
                Connection connection = (Connection) DatabaseConnection.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                List<Guest> list = new ArrayList<>();
                while (resultSet.next()) {
                    Guest guest = new Guest();
                    guest.setGuestID(resultSet.getString("GuestID"));
                    guest.setName(resultSet.getString("Name"));
                    guest.setAddress(resultSet.getString("Address"));
                    guest.setPhone(resultSet.getString("Phone"));
                    guest.setEmail(resultSet.getString("Email"));

                    list.add(guest);

                }
                return list;
            }
        }

    }

}
