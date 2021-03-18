/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Product;
import View.Products;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JComboBox;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class ProductDAO {

    private Connection connection;
    ArrayList<Product> listProduct = new ArrayList<>();
    private Products managementProducts;

    public ProductDAO() {
        try {
            //connection sql
            connection = DatabaseConnection.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insert(Product p) {
        String sql = "INSERT INTO dbo.Products(productid,name,quantity,warranty,price,producername,images)"
                + "VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, p.getProductId());
            ps.setString(2, p.getProductName());
            ps.setInt(3, p.getProductQuantity());
            ps.setString(4, p.getProductWarranty());
            ps.setInt(5, p.getProductPrice());
            ps.setString(6, p.getProducerName());
            //data img
            if (p.getProductImage() != null) {
                Blob img = new SerialBlob(p.getProductImage());
                ps.setBlob(7, img);
            } else {
                Blob img = null;
                ps.setBlob(7, img);
            }
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Product p) {
        String sql = "update dbo.Products set name=?,quantity=?,warranty=?,price=?,producername=?,images=? where productid=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(7, p.getProductId());
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getProductQuantity());
            ps.setString(3, p.getProductWarranty());
            ps.setInt(4, p.getProductPrice());
            ps.setString(5, p.getProducerName());
            //data img
            if (p.getProductImage() != null) {
                Blob img = new SerialBlob(p.getProductImage());
                ps.setBlob(6, img);
                return ps.executeUpdate() > 0;
            } else {
                Blob img = null;
                ps.setBlob(6, img);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String productid) {
        String sql = "delete from dbo.Products where productid=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Product> getListProduct(String sql) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //set data in table
            while (rs.next()) {
                Product p = createProduct(rs);
                //add data in arraylist(listProduct)
                listProduct.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProduct;
    }

    public Product findById(String productid) {
        String sql = "select * from dbo.Products where productId=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = createProduct(rs);
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Product> findByName(String name) {
        String sql = "select * from dbo.Products where name like'%" + name + "%'";
        ArrayList<Product> listProducts = new ArrayList<Product>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = createProduct(rs);
                listProducts.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProducts;
    }

    private Product createProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getString("productid"));
        p.setProductName(rs.getString("name"));
        p.setProductQuantity(rs.getInt("quantity"));
        p.setProductWarranty(rs.getString("warranty"));
        p.setProductPrice(rs.getInt("price"));
        p.setProducerName(rs.getString("producername"));
        Blob blob = rs.getBlob("images");
        if (blob != null) {
            p.setProductImage(blob.getBytes(1, (int) blob.length()));
        }
        return p;
    }

    public void fillComboBox(JComboBox comboBox) {
        try {
            String sql = "select * from dbo.Producer";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("producername");
                comboBox.addItem(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
