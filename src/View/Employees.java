/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DatabaseConnection;
import Controller.EmployeeDAO;
import Controller.Validator;
import Model.Employee;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import static org.apache.commons.beanutils.WrapDynaClass.clear;

/**
 *
 * @author admin
 */
public class Employees extends javax.swing.JFrame {

    Employee employee = new Employee();
    EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employees() throws Exception {

        initComponents();
        showEmployeeInJTable();
        defaultLabel();
        this.setTitle("K&Technogoly");
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tableDisplayList.setDefaultEditor(Object.class, null);
        tableDisplayList.getTableHeader().setDefaultRenderer((new HeaderColor()));
        tableDisplayList.setBackground(new Color(255, 255, 255));
        tableDisplayList.setRowHeight(25);

    }

    public Connection getConnection() {
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:8899;databaseName=K&Technology;username=group1;password=group1";
            Connection connection = DriverManager.getConnection(url);
            return connection;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public ArrayList<Employee> employeeList() throws Exception {
        ArrayList<Employee> employeeList = new ArrayList<>();
        Connection connection = DatabaseConnection.openConnection();
        try {
            String query = "Select * from Employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Employee employee;
            while (resultSet.next()) {
                employee = new Employee(resultSet.getString("EmplID"), resultSet.getString("Name"), resultSet.getString("Position"), resultSet.getString("DOB"), resultSet.getString("Gender"), resultSet.getString("Address"), resultSet.getString("Phone"), resultSet.getString("Salary"), resultSet.getString("Email"));
                employeeList.add(employee);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return employeeList;
    }

    public void showEmployeeInJTable() throws Exception {
        ArrayList<Employee> list = employeeList();
        DefaultTableModel model = (DefaultTableModel) tableDisplayList.getModel();
        Object[] row = new Object[9];
        model.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getEmplID();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getPosition();
            row[3] = list.get(i).getDOB();
            row[4] = list.get(i).getGender();
            row[5] = list.get(i).getAddress();
            row[6] = list.get(i).getPhone();
            row[7] = list.get(i).getSalary();
            row[8] = list.get(i).getEmail();
            model.addRow(row);
        }
    }

    public void executedSQLQuery(String query) throws Exception {
        Connection connection = DatabaseConnection.openConnection();
        try {
            Statement statement = connection.createStatement();
            if (statement.executeUpdate(query) == 1) {
                DefaultTableModel defaultTableModel = (DefaultTableModel) tableDisplayList.getModel();
                defaultTableModel.setRowCount(0);
                showEmployeeInJTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //desgin UI
    class jPanelGradient extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            Color color1 = new Color(185, 211, 238);
            Color color2 = new Color(86, 180, 211);
            GradientPaint gp = new GradientPaint(0, 0, color1, 180, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public class HeaderColor extends DefaultTableCellRenderer {

        public HeaderColor() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setBackground(new Color(128, 128, 128));
            setForeground(new Color(255, 255, 255));
            return this;
        }

    }

    public void defaultLabel() {
        txtId.setEnabled(false);
        txtName.setEnabled(false);
        txtPosition.setEnabled(false);
        txtDob.setEnabled(false);
        txtGender.setEnabled(false);
        txtAddress.setEnabled(false);
        txtPhoneNumber.setEnabled(false);
        txtSalary.setEnabled(false);
        txtEmail.setEnabled(false);
    }

    public void enterTxt() {
        txtId.setEnabled(true);
        txtName.setEnabled(true);
        txtPosition.setEnabled(true);
        txtDob.setEnabled(true);
        txtGender.setEnabled(true);
        txtAddress.setEnabled(true);
        txtPhoneNumber.setEnabled(true);
        txtSalary.setEnabled(true);
        txtEmail.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new jPanelGradient();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtPosition = new javax.swing.JTextField();
        txtDob = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtGender = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSalary = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDisplayList = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JLabel();
        btnSave = new javax.swing.JLabel();
        btnEdit = new javax.swing.JLabel();
        btnPrint = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JLabel();
        btnReset = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(186, 79, 84));
        jPanel1.setPreferredSize(new java.awt.Dimension(3000, 1002));

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("NAME");

        jLabel2.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("POSITION");

        jLabel4.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("GENDER");

        jLabel5.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("DOB");

        jLabel6.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("ADDRESS");

        jLabel7.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("EMAIL");

        jLabel8.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("PHONE");

        jLabel9.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("SALARY");

        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel12MousePressed(evt);
            }
        });

        tableDisplayList.setAutoCreateRowSorter(true);
        tableDisplayList.setForeground(new java.awt.Color(74, 31, 61));
        tableDisplayList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "POSITION", "DAY OF BIRTH", "GENDER", "ADDRESS", "PHONE NUMBER", "SALARY", "EMAIL"
            }
        ));
        tableDisplayList.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tableDisplayList.setRowHeight(25);
        tableDisplayList.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tableDisplayList.setShowVerticalLines(false);
        tableDisplayList.getTableHeader().setReorderingAllowed(false);
        tableDisplayList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDisplayListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDisplayList);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnDelete.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDelete.setText("DELETE");
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSave.setText("SAVE");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEdit.setText("EDIT");
        btnEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEditMousePressed(evt);
            }
        });

        btnPrint.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPrint.setText("PRINT");
        btnPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setText("ADD");
        btnAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnReset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReset.setText("RESET");
        btnReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel18.setText("Find By ID");

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Sylfaen", 1, 36)); // NOI18N
        jLabel11.setText("                                                                           K&Technology");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtDob, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(87, 87, 87))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(472, 472, 472))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addGap(82, 82, 82)))
                                .addComponent(jLabel16)
                                .addGap(81, 81, 81)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(146, 146, 146)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch)))))
                .addGap(623, 623, 623)
                .addComponent(jLabel12)
                .addGap(88, 88, 88))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1818, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(35, 35, 35)
                .addComponent(jLabel13)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDob, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1830, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MousePressed
        System.exit(0);
    }//GEN-LAST:event_jLabel12MousePressed

    private void tableDisplayListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDisplayListMouseClicked
        int i = tableDisplayList.getSelectedRow();
        TableModel tableModel = tableDisplayList.getModel();
        txtId.setText(tableModel.getValueAt(i, 0).toString());
        txtName.setText(tableModel.getValueAt(i, 1).toString());
        txtPosition.setText(tableModel.getValueAt(i, 2).toString());
        txtDob.setText(tableModel.getValueAt(i, 3).toString());
        txtGender.setText(tableModel.getValueAt(i, 4).toString());
        txtAddress.setText(tableModel.getValueAt(i, 5).toString());
        txtPhoneNumber.setText(tableModel.getValueAt(i, 6).toString());
        txtEmail.setText(tableModel.getValueAt(i, 7).toString());
        txtSalary.setText(tableModel.getValueAt(i, 8).toString());
    }//GEN-LAST:event_tableDisplayListMouseClicked

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        txtId.setText("");
        txtName.setText("");
        txtPosition.setText("");
        txtDob.setText("");
        txtGender.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
    }//GEN-LAST:event_btnResetMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked

        enterTxt();
        txtId.setText("");
        txtName.setText("");
        txtPosition.setText("");
        txtDob.setText("");
        txtGender.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
        btnAdd.setEnabled(false);
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        try {
            if (btnDelete.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Deleted?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    employee.setEmplID(txtId.getText());
                    if (employeeDAO.delete(employee)) {
                        JOptionPane.showMessageDialog(this, "Deleted Product Successful");
                        showEmployeeInJTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Deleted Product Failed");
                    }
                }
            }
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void btnEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMousePressed

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        enterTxt();
        txtId.setEnabled(false);
        btnEdit.setEnabled(false);
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        Icon iconError = new javax.swing.ImageIcon("C:\\Users\\admin\\Desktop\\K&Technology\\K&Technology\\src\\Images\\cancel.png");
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            Employee employeefindID = employeeDAO.findByEmplID(txtSearch.getText());

            if (employeefindID != null) {
                txtId.setText(employeefindID.getEmplID());
                txtName.setText(employeefindID.getName());
                txtPosition.setText(employeefindID.getPosition());
                txtDob.setText(employeefindID.getDOB());
                txtGender.setText(employeefindID.getGender());
                txtAddress.setText(employeefindID.getAddress());
                txtPhoneNumber.setText(employeefindID.getPhone());
                txtEmail.setText(employeefindID.getEmail());
                txtSalary.setText(employeefindID.getSalary());
            } else {
                JOptionPane.showMessageDialog(this, "Don't have the ID !", "ERROR", WIDTH, iconError);
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        try {
            if (btnSave.isEnabled()) {
                if (btnEdit.isEnabled()) {
                    if (JOptionPane.showConfirmDialog(this, "Do You Want To Add ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        StringBuilder sb = new StringBuilder();
                        //condition JtextField
                        Validator.checkEmpty(txtId, sb, "Please Enter Employee's ID !");
                        Validator.checkEmpty(txtName, sb, "Please Enter Employee's Name !");
                        Validator.checkEmpty(txtPosition, sb, "Please Enter Employee's Position !");
                        Validator.checkEmpty(txtDob, sb, "Please Enter Employee's Day Of Birth !");
                        Validator.checkEmpty(txtGender, sb, "Please Enter Employee's Gender !");
                        Validator.checkEmpty(txtAddress, sb, "Please Enter Employee's Address !");
                        Validator.checkEmpty(txtPhoneNumber, sb, "Please Enter Employee's Phone Number !");
                        Validator.checkEmpty(txtSalary, sb, "Please Enter Employee's Salary !");
                        Validator.checkEmpty(txtEmail, sb, "Please Enter Employee's Email !");
                        //get data from the text field
                        employee.setEmplID(txtId.getText());
                        employee.setName(txtName.getText());
                        employee.setPosition(txtPosition.getText());
                        employee.setDOB(txtDob.getText());
                        employee.setGender(txtGender.getText());
                        employee.setAddress(txtAddress.getText());
                        employee.setPhone(txtPhoneNumber.getText());
                        employee.setSalary(txtGender.getText());
                        employee.setEmail(txtEmail.getText());
                        if (employeeDAO.insert(employee)) {
                            JOptionPane.showMessageDialog(null, "Save Producer Successful");
                            showEmployeeInJTable();
                            btnAdd.setEnabled(true);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "Save Producer Failed");
                        }
                    }
                }
                if (btnAdd.isEnabled()) {
                    employee.setEmplID(txtId.getText());
                    employee.setName(txtName.getText());
                    employee.setPosition(txtPosition.getText());
                    employee.setDOB(txtDob.getText());
                    employee.setGender(txtGender.getText());
                    employee.setAddress(txtAddress.getText());
                    employee.setPhone(txtPhoneNumber.getText());
                    employee.setSalary(txtGender.getText());
                    employee.setEmail(txtEmail.getText());
                    if (JOptionPane.showConfirmDialog(this, "Do You Want To Save ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        StringBuilder sb = new StringBuilder();
                        Validator.checkEmpty(txtName, sb, "Please Enter Employee's Name !");
                        Validator.checkEmpty(txtPosition, sb, "Please Enter Employee's Position !");
                        Validator.checkEmpty(txtDob, sb, "Please Enter Employee's Day Of Birth !");
                        Validator.checkEmpty(txtGender, sb, "Please Enter Employee's Gender !");
                        Validator.checkEmpty(txtAddress, sb, "Please Enter Employee's Address !");
                        Validator.checkEmpty(txtPhoneNumber, sb, "Please Enter Employee's Phone Number !");
                        Validator.checkEmpty(txtSalary, sb, "Please Enter Employee's Salary !");
                        Validator.checkEmpty(txtEmail, sb, "Please Enter Employee's Email !");
                        if (sb.length() > 0) {
                            JOptionPane.showMessageDialog(this, sb.toString(), "Invaid Data", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (employeeDAO.update(employee)) {
                            JOptionPane.showMessageDialog(this, "Edit Employee Successful");
                            clear();
                            showEmployeeInJTable();
                            btnEdit.setEnabled(true);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(this, "Edit Employee Failed");
                        }
                    }
                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Employees.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employees.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employees.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employees.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Employees().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Employees.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnEdit;
    private javax.swing.JLabel btnPrint;
    private javax.swing.JLabel btnReset;
    private javax.swing.JLabel btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableDisplayList;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDob;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGender;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
