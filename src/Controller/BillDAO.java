/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bill;
import Model.Employee;
import Model.Product;
import View.Bills;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class BillDAO {

    private Connection connection;
    private Bills bills;
    private Bill bill;
    ArrayList<Bill> listBill = new ArrayList<>();
    private ArrayList<Product> listProduct = new ArrayList<>();
    Product p = new Product();

    public BillDAO() {

        try {
            connection = DatabaseConnection.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set combobox Employee from sql
    public void fillCbbEmpl(JComboBox comboBox) {
        try {
            String sql = "select * from dbo.employee";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String emplId = rs.getString("emplid");
                comboBox.addItem(emplId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //label shows text set Employee name of EmployeeId
    public Employee setLabaleEmployee(String employeeId) {
        try {
            String sql = "select * from dbo.employee where emplId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, employeeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee empl = new Employee();
                empl.setName(rs.getString("name"));
                return empl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //set combobox products from sql
    public void fillCbbProductsId(JComboBox comboBox) {
        try {
            String sql = "select * from dbo.products";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productsId = rs.getString("productId");
                comboBox.addItem(productsId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //label shows text set price and name of productid
    public Product setLableProducts(String productid) {
        try {
            String sql = "SELECT * FROM dbo.products WHERE productid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setProductQuantity(rs.getInt("quantity"));
                p.setProductPrice(rs.getInt("price"));
                p.setProductName(rs.getString("name"));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertBill(Bill bill) {
        String sql = "insert into dbo.bill(prefix,Date) values('B',?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bill.getDateTime());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //display textfield get bill Id
    public void getBillId(JTextField txt) {
        try {
            String sql = "SELECT top 1 * FROM bill ORDER BY BillId DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String txtBillId = rs.getString("billid");
                txt.setText(txtBillId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertGuest(Bill bill) {
        String sql = "insert into dbo.guest(guestid,name,address,phone,email)"
                + "values(?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bill.getGuestId());
            ps.setString(2, bill.getGuestName());
            ps.setString(3, bill.getGuestAddress());
            ps.setString(4, bill.getGuestPhone());
            ps.setString(5, bill.getGuestEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBill(Bill bill) {
        String sql = "update dbo.bill set emplid=?,guestid=? where billid=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(3, bill.getBillId());
            ps.setString(1, bill.getEmplId());
            ps.setString(2, bill.getGuestId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Product setLableProductss(String productid) {
        try {
            String sql = "SELECT * FROM dbo.products WHERE productid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setProductQuantity(rs.getInt("quantity"));
                p.setProductPrice(rs.getInt("price"));
                p.setProductName(rs.getString("name"));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setProductsPrice(JTextField txt) {
        try {
            String sql = "SELECT top 1 * FROM bill ORDER BY BillId DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String txtBillId = rs.getString("billid");
                txt.setText(txtBillId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int mul(int a, int b) {
        return a * b;
    }

    public boolean insertProductsBill(Bill bill) {
        String sql = "insert into dbo.DetailsBill(billId,productId,quantity,unitPrice,productPrice)"
                + " values(?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bill.getBillId());
            ps.setString(2, bill.getProductId());
            ps.setInt(3, bill.getQuantityProductsBill());
            ps.setInt(4, bill.getUnitPrice());
            ps.setInt(5, bill.getProductPrice());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Bill> getListProducts(JTextField txt) {
        String billId = txt.getText();
        String sql = "select * from dbo.detailsbill where billid ='" + billId + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();;
                bill.setProductId(rs.getString("productId"));
                bill.setQuantityProductsBill(rs.getInt("quantity"));
                bill.setUnitPrice(rs.getInt("unitPrice"));
                bill.setProductPrice(rs.getInt("productPrice"));
                listBill.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBill;
    }

    public boolean delete(String billId, String productId) {
        String sql = "delete from dbo.detailsbill where billId=? and productId=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, billId);
            ps.setString(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setTotalPrice(JTextField txt, JLabel label) {
        String billId = txt.getText();
        String sql = "select SUM(ProductPrice)as 'BillPrice' from dbo.detailsBill where BillID = '" + billId + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int txtBillPrice = rs.getInt("BillPrice");
                label.setText("" + txtBillPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateBillPrice(Bill bill) {
        String sql = "update dbo.bill set billprice=? where billid=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(2, bill.getBillId());
            ps.setInt(1, bill.getBillPrice());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateQuantityProduct(JTextField txt) {
        String sql = "UPDATE Products SET products.Quantity = Products.Quantity - "
                + "DetailsBill.Quantity FROM Products INNER JOIN DetailsBill ON "
                + "Products.ProductID = DetailsBill.ProductID where DetailsBill.BillID= '"
                + txt.getText() + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Management Bill
    public ArrayList<Bill> getListBill() {
        String sql = "select * from bill inner join guest on bill.GuestID =  Guest.GuestID";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("billId"));
                bill.setBillPrice(rs.getInt("billPrice"));
                bill.setDateTime(rs.getString("date"));
                bill.setEmplId(rs.getString("emplId"));
                bill.setGuestId(rs.getString("guestId"));
                bill.setGuestName(rs.getString("name"));
                listBill.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBill;
    }

    public Bill findById(String billId) {
        String sql = "select * from bill inner join guest on bill.GuestID =  Guest.GuestID where bill.BillID =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, billId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("billId"));
                bill.setBillPrice(rs.getInt("billPrice"));
                bill.setDateTime(rs.getString("date"));
                bill.setEmplId(rs.getString("emplId"));
                bill.setGuestId(rs.getString("guestId"));
                bill.setGuestName(rs.getString("name"));
                return bill;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editGuestNameOfBill(Bill bill) {
        String sql = "update dbo.guest set name=? where GuestID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bill.getGuestName());
            ps.setString(2, bill.getGuestId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public ArrayList<Bill> findByIdMtBill(String billId) {
        String sql = "select * from bill inner join guest on bill.GuestID =  Guest.GuestID where bill.BillID =?";
        ArrayList<Bill> listMtBill = new ArrayList<Bill>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, billId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("billId"));
                bill.setBillPrice(rs.getInt("billPrice"));
                bill.setDateTime(rs.getString("date"));
                bill.setEmplId(rs.getString("emplId"));
                bill.setGuestId(rs.getString("guestId"));
                bill.setGuestName(rs.getString("name"));
                listMtBill.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMtBill;
    }
}
