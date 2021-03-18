/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Producer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class ProducerDAO {

    private Connection connection;
    ArrayList<Producer> listProducer = new ArrayList<>();

    public ProducerDAO() {
        try {
            connection = DatabaseConnection.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Producer> getListProducer(String sql) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producer producer = new Producer();
                producer.setProducerName(rs.getString("producername"));
                producer.setProducerType(rs.getString("type"));
                producer.setProducerAdd(rs.getString("address"));
                producer.setProducerPhone(rs.getString("phone"));
                listProducer.add(producer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProducer;
    }

    public boolean insertProducer(Producer producer) {
        String sql = "INSERT INTO dbo.Producer(producername,type,address,phone)"
                + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, producer.getProducerName());
            ps.setString(2, producer.getProducerType());
            ps.setString(3, producer.getProducerAdd());
            ps.setString(4, producer.getProducerPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProducer(Producer producer) {
        String sql = "update dbo.Producer set producername=?,type=?,address=?,phone=? where numberrow=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(5, producer.getProducerNumberRow());
            ps.setString(1, producer.getProducerName());
            ps.setString(2, producer.getProducerType());
            ps.setString(3, producer.getProducerAdd());
            ps.setString(4, producer.getProducerPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Producer findByName(String producerName) {
        String sql = "select producername,type,address,phone from producer where producername=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, producerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Producer producer = new Producer();
                producer.setProducerName(rs.getString("producername"));
                producer.setProducerType(rs.getString("type"));
                producer.setProducerAdd(rs.getString("address"));
                producer.setProducerPhone(rs.getString("phone"));
                return producer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Producer producer) {
        String sql = "update producer set type=?,address=?,phone=? where producername=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(4, producer.getProducerName());
            ps.setString(1, producer.getProducerType());
            ps.setString(2, producer.getProducerAdd());
            ps.setString(3, producer.getProducerPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String producername) {
        String sql = "delete from dbo.Producer where producername=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, producername);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Producer> findByNamee(String name) {
        ArrayList<Producer> listProducerr = new ArrayList<Producer>();
        String sql = "select * from dbo.Producer where producername like'%" + name + "%'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producer producer = new Producer();
                producer.setProducerName(rs.getString("producername"));
                producer.setProducerType(rs.getString("type"));
                producer.setProducerAdd(rs.getString("address"));
                producer.setProducerPhone(rs.getString("phone"));
                listProducerr.add(producer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProducerr;
    }
}
