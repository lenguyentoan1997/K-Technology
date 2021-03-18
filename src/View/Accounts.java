/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AccountDAO;
import Controller.Validator;
import Model.Account;
import Model.Employee;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class Accounts extends javax.swing.JFrame {

    /**
     * Creates new form Accounts
     */
    private AccountDAO accountDAO = new AccountDAO();
    private Employee empl = new Employee();
    private Account account = new Account();
    private DefaultTableModel jTableModel;
    private ArrayList<Account> listAccount = new ArrayList<>();

    public Accounts() {
        initComponents();
        initJframeAccounts();
        initTable();
        loadDataToTable();
        setDefaultJframe();
    }

    public void initJframeAccounts() {
        this.setTitle("Management Accounts");
        this.setExtendedState(MAXIMIZED_BOTH);
        accountDAO.fillCbbEmpl(cbbEmplName);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

    private void initTable() {
        jTableModel = (DefaultTableModel) tabAccount.getModel();
        jTableModel.setColumnIdentifiers(new Object[]{"USER NAME", "PASSWORD", "DECENTRALIZATION", "EMPLOYEE ID"});
        tabAccount.setDefaultEditor(Object.class, null);
        tabAccount.getTableHeader().setDefaultRenderer((new HeaderColor()));
        tabAccount.setBackground(new Color(255, 255, 255));
        tabAccount.setRowHeight(25);

    }

    private void loadDataToTable() {
        try {
            AccountDAO accountDAO = new AccountDAO();
            ArrayList<Account> listAccount = accountDAO.getListAccounts();
            jTableModel.setRowCount(0);
            for (Account account : listAccount) {
                account.setPassword("**************");
                jTableModel.addRow(new Object[]{account.getUserName(),
                    account.getPassword(), account.getDecentralization(), account.getEmplID()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextIsEmpty() {
        txtUserName.setText("");
        txtPassword.setText("");
        lblEmplId.setText("NULL");
        cbbEmplName.setSelectedItem("NULL");
    }

    //color background gradient
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

    public void setDefaultJframe() {
        txtUserName.setEnabled(false);
        txtPassword.setEnabled(false);
        cbbEmplName.setEnabled(false);
        radAdmin.setEnabled(false);
        radEmpl.setEnabled(false);
        tabAccount.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new jPanelGradient();
        jLabel3 = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        cbbEmplName = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        radAdmin = new javax.swing.JRadioButton();
        radEmpl = new javax.swing.JRadioButton();
        lblAdd = new javax.swing.JLabel();
        lblReset = new javax.swing.JLabel();
        lblEdit = new javax.swing.JLabel();
        lblDeltete = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabAccount = new javax.swing.JTable();
        lblSave = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblEmplId = new javax.swing.JLabel();
        boxShowPass = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Sylfaen", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("K&Technology");

        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("USER NAME");

        jLabel4.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("PASSWORD");

        jLabel6.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("EMLOYEE ID");

        cbbEmplName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NULL" }));
        cbbEmplName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbEmplNameActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("DECENTRALIZATION");

        buttonGroup1.add(radAdmin);
        radAdmin.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        radAdmin.setForeground(new java.awt.Color(255, 255, 255));
        radAdmin.setText("admin");
        radAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radAdminActionPerformed(evt);
            }
        });

        buttonGroup1.add(radEmpl);
        radEmpl.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        radEmpl.setForeground(new java.awt.Color(255, 255, 255));
        radEmpl.setSelected(true);
        radEmpl.setText("employee");
        radEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radEmplActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radAdmin)
                .addGap(18, 18, 18)
                .addComponent(radEmpl)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radAdmin)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radEmpl))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        lblAdd.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdd.setText("ADD");
        lblAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddMouseClicked(evt);
            }
        });

        lblReset.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblReset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReset.setText("RESET");
        lblReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResetMouseClicked(evt);
            }
        });

        lblEdit.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEdit.setText("Change Password");
        lblEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });

        lblDeltete.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblDeltete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDeltete.setText("DELETE");
        lblDeltete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDeltete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDelteteMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel18.setText("FIND BY USER NAME");

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tabAccount.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        tabAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabAccount.setGridColor(new java.awt.Color(0, 0, 0));
        tabAccount.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabAccount.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tabAccount.setShowVerticalLines(false);
        tabAccount.getTableHeader().setReorderingAllowed(false);
        tabAccount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tabAccountFocusLost(evt);
            }
        });
        tabAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabAccountMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabAccount);

        lblSave.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSave.setText("SAVE");
        lblSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSaveMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("EMLOYEE NAME");

        lblEmplId.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblEmplId.setText("NULL");

        boxShowPass.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        boxShowPass.setForeground(new java.awt.Color(102, 102, 102));
        boxShowPass.setText("Show Password");
        boxShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxShowPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxShowPass)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEmplId, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbbEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(262, 262, 262)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDeltete, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(77, 77, 77)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblReset, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSave, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(218, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(533, 533, 533)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblExit)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReset, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(lblDeltete, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(boxShowPass)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSave, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmplId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbEmplNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbEmplNameActionPerformed
        empl = accountDAO.setLblEmplId(cbbEmplName.getSelectedItem().toString());
        if (empl != null) {
            lblEmplId.setText(String.valueOf(empl.getEmplID()));
        } else {
            lblEmplId.setText("NULL");
        }
    }//GEN-LAST:event_cbbEmplNameActionPerformed

    private void tabAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabAccountMouseClicked
        try {
            int row = tabAccount.getSelectedRow();
            if (row >= 0) {
                String userName = (String) tabAccount.getValueAt(row, 0);
                account = accountDAO.findAll(userName);
                if (account != null) {
                    txtUserName.setText(account.getUserName());
                    txtPassword.setText(account.getPassword());
                    if (account.getDecentralization().trim().equals("admin")) {
                        lblEmplId.setText("NULL");
                        radAdmin.setSelected(true);
                        cbbEmplName.setSelectedItem("NULL");
                    } else {
                        lblEmplId.setText(account.getEmplID());
                        radEmpl.setSelected(true);
                        accountDAO.setLblEmplName(cbbEmplName, lblEmplId.getText());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tabAccountMouseClicked

    private void boxShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxShowPassActionPerformed

       
            if (boxShowPass.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('*');
            }
        

    }//GEN-LAST:event_boxShowPassActionPerformed

    private void tabAccountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabAccountFocusLost
        tabAccount.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabAccountFocusLost

    private void lblAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddMouseClicked
        try {
            if (JOptionPane.showConfirmDialog(this, "Do You Want To Add Account", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                setTextIsEmpty();
                lblAdd.setEnabled(false);
                txtUserName.setEnabled(true);
                txtPassword.setEnabled(true);
                cbbEmplName.setEnabled(true);
                radAdmin.setEnabled(true);
                radEmpl.setEnabled(true);
                tabAccount.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblAddMouseClicked

    private void lblSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSaveMouseClicked
        try {
            if (lblSave.isEnabled()) {
                StringBuilder sb = new StringBuilder();
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Add Account", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (lblEdit.isEnabled()) {

                        Validator.checkEmpty(txtUserName, sb, "Please Enter User Name");
                        Validator.checkEmpty(txtPassword, sb, "Please Enter Password");
                        if (sb.length() > 0) {
                            JOptionPane.showMessageDialog(this, sb.toString(), "Invaid Data", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        account.setUserName(txtUserName.getText());
                        account.setPassword(txtPassword.getText());
                        if (radAdmin.isSelected()) {
                            account.setDecentralization(radAdmin.getText());
                        } else {
                            account.setDecentralization(radEmpl.getText());
                        }
                        account.setEmplID(lblEmplId.getText());
                        if (accountDAO.insert(account)) {
                            JOptionPane.showMessageDialog(this, "Save Account New Successful");
                            loadDataToTable();
                            setDefaultJframe();
                        } else {
                            JOptionPane.showMessageDialog(this, "Save Account New Error");
                        }
                    }
                    if (lblAdd.isEnabled()) {
                        Validator.checkEmpty(txtPassword, sb, "Please Enter Password");
                        if (sb.length() > 0) {
                            JOptionPane.showMessageDialog(this, sb.toString(), "Invaid Data", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        account.setPassword(txtPassword.getText());
                        account.setUserName(txtUserName.getText());
                        if (accountDAO.update(account)) {
                            JOptionPane.showMessageDialog(this, "Change Password Successful");
                            loadDataToTable();
                            setDefaultJframe();
                        } else {
                            JOptionPane.showMessageDialog(this, "Change Password Error");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblSaveMouseClicked

    private void radAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radAdminActionPerformed
        cbbEmplName.setEnabled(false);
        cbbEmplName.setSelectedItem("NULL");
    }//GEN-LAST:event_radAdminActionPerformed

    private void radEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radEmplActionPerformed
        cbbEmplName.setEnabled(true);
    }//GEN-LAST:event_radEmplActionPerformed

    private void lblDelteteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDelteteMouseClicked
        try {
            if (lblDeltete.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Delete", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    account.setUserName(txtUserName.getText());
                    if (accountDAO.delete(account)) {
                        JOptionPane.showMessageDialog(this, "Delete Account Successful");
                        loadDataToTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Delete Account Error");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblDelteteMouseClicked

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        if (JOptionPane.showConfirmDialog(this, "Do You Want To Change Password", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            lblEdit.setEnabled(false);
            txtPassword.setEnabled(true);
        }
    }//GEN-LAST:event_lblEditMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        this.dispose();
        MainClient main = new MainClient();
        main.setVisible(true);
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {

            String name = txtSearch.getText();
            if (name.trim().isEmpty()) {
                loadDataToTable();
            } else {
                listAccount = accountDAO.findByUserName(name);

                for (Account a : listAccount) {
                    a.setPassword("**************");
                    jTableModel.setRowCount(0);
                    jTableModel.addRow(new Object[]{
                        a.getUserName(), a.getPassword(), a.getDecentralization(),
                        a.getEmplID()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void lblResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResetMouseClicked
        setDefaultJframe();
    }//GEN-LAST:event_lblResetMouseClicked

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
            java.util.logging.Logger.getLogger(Accounts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Accounts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Accounts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Accounts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Accounts().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxShowPass;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbEmplName;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblDeltete;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblEmplId;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblReset;
    private javax.swing.JLabel lblSave;
    public javax.swing.JRadioButton radAdmin;
    private javax.swing.JRadioButton radEmpl;
    private javax.swing.JTable tabAccount;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}