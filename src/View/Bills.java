/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillDAO;
import Controller.ClockThread;
import Controller.DatabaseConnection;
import Controller.Validator;
import Model.Bill;
import Model.Employee;
import Model.Product;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class Bills extends javax.swing.JFrame {

    /**
     * Creates new form Bill
     */
    BillDAO billDAO = new BillDAO();
    Bill bill = new Bill();
    Product p = new Product();
    private ArrayList<Product> listProduct = new ArrayList<>();
    private DefaultTableModel modelDetailsBill, modelMtBill;
    private ClockThread clockThread;
    private int num1, num2;

    public Bills() {
        initComponents();
        initBill();
        DateTime();
        initTable();
        defaultBills();
        initTableMtBills();
        loadDataBillsToTable();
        defaultMtBills();

    }

    public void initBill() {
        this.setTitle("Bill");
        this.setExtendedState(this.MAXIMIZED_BOTH);
        billDAO.fillCbbProductsId(cbbProductsId);
        billDAO.fillCbbEmpl(cbbEmployee);
        billDAO.fillCbbEmpl(cbbMtEmplId);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void DateTime() {
        ClockThread clockThread = new ClockThread(lblDateTime);
        clockThread.start();

    }

    private void initTable() {
        modelDetailsBill = (DefaultTableModel) tabListDetailsBill.getModel();
        //set column table
        modelDetailsBill.setColumnIdentifiers(new Object[]{"Products ID", "Quantity", "Unit Price", "Price"});
        tabListDetailsBill.setModel(modelDetailsBill);
        tabListDetailsBill.setDefaultEditor(Object.class, null);
        tabListDetailsBill.getTableHeader().setDefaultRenderer((new HeaderColor()));
        tabListDetailsBill.setBackground(new Color(255, 255, 255));
        tabListDetailsBill.setRowHeight(25);
        tabListDetailsBill.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tabListDetailsBill.getSelectedRow() >= 0) {
                    cbbProductsId.setSelectedItem(tabListDetailsBill.getValueAt(tabListDetailsBill.getSelectedRow(), 0) + "");
                }
            }
        });
    }

    private void loadDataDetailsBill() {
        try {
            BillDAO billDAO = new BillDAO();
            ArrayList<Bill> listBill = billDAO.getListProducts(txtBillId);
            modelDetailsBill.setRowCount(0);
            for (Bill bill : listBill) {
                modelDetailsBill.addRow(new Object[]{
                    bill.getProductId(), bill.getQuantityProductsBill(),
                    bill.getUnitPrice(), bill.getProductPrice()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printBill() {
        String link = "H:\\Project\\ProjectJavaSem2\\K&Technology\\src\\View\\JasperReport\\reportBills.jrxml";
        try {
            JasperReport jr = JasperCompileManager.compileReport(link);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseConnection.openConnection());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Management Bill
    private void loadDataBillsToTable() {
        try {
            BillDAO billDAO = new BillDAO();
            ArrayList<Bill> listMtBill = billDAO.getListBill();
            modelMtBill.setRowCount(0);
            //put the data in row
            for (Bill bill : listMtBill) {
                modelMtBill.addRow(new Object[]{
                    bill.getBillId(), bill.getBillPrice(), bill.getDateTime(),
                    bill.getEmplId(), bill.getGuestName()
                });
            }
            modelMtBill.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTableMtBills() {
        modelMtBill = (DefaultTableModel) tabMtBill.getModel();
        modelMtBill.setColumnIdentifiers(new Object[]{"Bill ID", "Bill Price", "Date Time", "Employee ID", "Guest Name"});
        tabMtBill.setModel(modelMtBill);
        tabMtBill.getTableHeader().setDefaultRenderer((new HeaderColor()));
        tabMtBill.setBackground(new Color(255, 255, 255));
        tabMtBill.setRowHeight(25);
    }

    //desgin UI 
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

    private void defaultBills() {
        txtBillId.setEnabled(false);
        txtGuestId.setEnabled(false);
        txtGuestAddress.setEnabled(false);
        txtGuestName.setEnabled(false);
        txtGuestPhone.setEnabled(false);
        txtGuestMail.setEnabled(false);
        cbbProductsId.setEnabled(false);
        txtQuantity.setEnabled(false);
        cbbEmployee.setEnabled(false);
        lblPayment.setEnabled(false);
        lblAddProducts.setEnabled(false);
        lblDelete.setEnabled(false);
        lblSave.setEnabled(false);
        txtGuestId.setText("");
        txtGuestAddress.setText("");
        txtGuestName.setText("");
        txtGuestPhone.setText("");
        txtGuestMail.setText("");
        txtQuantity.setText("");

    }

    private void guestSetEnable() {
        txtGuestId.setEnabled(true);
        txtGuestAddress.setEnabled(true);
        txtGuestName.setEnabled(true);
        txtGuestPhone.setEnabled(true);
        txtGuestMail.setEnabled(true);
    }

    private void guestSetEnableFalse() {
        txtGuestId.setEnabled(false);
        txtGuestAddress.setEnabled(false);
        txtGuestName.setEnabled(false);
        txtGuestPhone.setEnabled(false);
        txtGuestMail.setEnabled(false);
    }

    private void defaultMtBills() {
        txtMtBillId.setText("");
        txtMtBillId.setEnabled(false);
        txtMtGuestId.setText("");
        txtMtGuestId.setEnabled(false);
        txtMtGuestName.setText("");
        txtMtGuestName.setEnabled(false);
        cbbMtEmplId.setEnabled(false);
        lblMtSave.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new jPanelGradient();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new jPanelGradient();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        cbbProductsId = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblUnitPrice = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblQuantityProducts = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtGuestId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGuestName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtGuestAddress = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtGuestPhone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtGuestMail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBillId = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbbEmployee = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblEmplName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabListDetailsBill = new javax.swing.JTable();
        lblPayment = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblNewBill = new javax.swing.JLabel();
        lblAddProducts = new javax.swing.JLabel();
        lblDelete = new javax.swing.JLabel();
        lblSave = new javax.swing.JLabel();
        lblReturn = new javax.swing.JLabel();
        jPanel3 = new jPanelGradient();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMtGuestId = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMtGuestName = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMtBillId = new javax.swing.JTextField();
        cbbMtEmplId = new javax.swing.JComboBox<>();
        lblMtEmplName = new javax.swing.JLabel();
        lblMtBillPrice = new javax.swing.JLabel();
        lblEdit = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabMtBill = new javax.swing.JTable();
        lblMtSave = new javax.swing.JLabel();
        lblReturn1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtMtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblExit = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(186, 79, 84));

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBackground(new java.awt.Color(186, 79, 84));

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        jLabel24.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Products ID");

        cbbProductsId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbProductsIdActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Quantity In Stock:");

        txtQuantity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtQuantityMouseExited(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuantityKeyReleased(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Unit Price");

        jLabel27.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Price");

        lblUnitPrice.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblUnitPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblUnitPrice.setText("abc");

        jLabel29.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Quantity ");

        lblQuantityProducts.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblQuantityProducts.setForeground(new java.awt.Color(255, 255, 255));
        lblQuantityProducts.setText("abc");

        jLabel1.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Name");

        lblProductName.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblProductName.setForeground(new java.awt.Color(255, 255, 255));

        lblPrice.setBackground(new java.awt.Color(255, 255, 255));
        lblPrice.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblPrice.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbProductsId, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblQuantityProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(lblUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(cbbProductsId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(lblUnitPrice)
                    .addComponent(lblQuantityProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(lblPrice)))
                .addGap(11, 11, 11))
        );

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        jLabel5.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Guest ID");

        jLabel6.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Guest Name");

        jLabel7.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Address");

        jLabel8.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Phone");

        jLabel9.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Email");

        jLabel2.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bill ID");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGuestId, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGuestAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGuestName)
                    .addComponent(txtGuestPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addComponent(txtGuestMail))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGuestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtGuestPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGuestId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtGuestMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGuestAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Employee");

        cbbEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbEmployeeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Date Time :");

        lblDateTime.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblDateTime.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name");

        lblEmplName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEmplName.setForeground(new java.awt.Color(255, 255, 255));
        lblEmplName.setText("abc");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lblEmplName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblDateTime))
                .addContainerGap())
        );

        tabListDetailsBill.setModel(new javax.swing.table.DefaultTableModel(
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
        tabListDetailsBill.setRowHeight(25);
        tabListDetailsBill.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tabListDetailsBillFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(tabListDetailsBill);

        lblPayment.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblPayment.setForeground(new java.awt.Color(255, 255, 255));
        lblPayment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print.png"))); // NOI18N
        lblPayment.setText("Payment");
        lblPayment.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblPaymentMousePressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Total :");

        lblTotal.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));

        lblNewBill.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblNewBill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        lblNewBill.setText("New Bill");
        lblNewBill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNewBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNewBillMousePressed(evt);
            }
        });

        lblAddProducts.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblAddProducts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add-friend.png"))); // NOI18N
        lblAddProducts.setText("Add Products");
        lblAddProducts.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblAddProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAddProductsMousePressed(evt);
            }
        });

        lblDelete.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-user.png"))); // NOI18N
        lblDelete.setText("Deleted");
        lblDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblDeleteMousePressed(evt);
            }
        });

        lblSave.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        lblSave.setText("Save");
        lblSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSaveMousePressed(evt);
            }
        });

        lblReturn.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        lblReturn.setText("Return");
        lblReturn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblReturnMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddProducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(lblNewBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(lblAddProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblSave, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal))
                .addGap(18, 18, 18)
                .addComponent(lblPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bill", jPanel2);

        jPanel3.setBackground(new java.awt.Color(186, 79, 84));

        jPanel8.setBackground(new java.awt.Color(153, 153, 153));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Employee ID");

        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Guest ID");

        jLabel17.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Employee Name");

        jLabel18.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Guest Name");

        jLabel19.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Bill Price");

        jLabel4.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Bill ID");

        cbbMtEmplId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMtEmplIdActionPerformed(evt);
            }
        });

        lblMtEmplName.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblMtEmplName.setForeground(new java.awt.Color(255, 255, 255));

        lblMtBillPrice.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblMtBillPrice.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMtBillId)
                            .addComponent(cbbMtEmplId, 0, 287, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(lblMtEmplName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMtGuestId, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addComponent(txtMtGuestName)
                    .addComponent(lblMtBillPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtMtGuestId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtMtGuestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbMtEmplId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(lblMtEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMtBillPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        lblEdit.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        lblEdit.setText("Edit Guest");
        lblEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblEditMousePressed(evt);
            }
        });

        tabMtBill.setModel(new javax.swing.table.DefaultTableModel(
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
        tabMtBill.setRowHeight(25);
        tabMtBill.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tabMtBill.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tabMtBillFocusLost(evt);
            }
        });
        tabMtBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMtBillMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabMtBill);

        lblMtSave.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblMtSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMtSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        lblMtSave.setText("Save");
        lblMtSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblMtSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMtSaveMousePressed(evt);
            }
        });

        lblReturn1.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblReturn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReturn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        lblReturn1.setText("Return");
        lblReturn1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblReturn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblReturn1MousePressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel20.setText("Find By Bill ID");

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMtSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblReturn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(484, 484, 484)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(471, 471, 471)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMtSave, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblReturn1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Management Bill", jPanel3);

        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Sylfaen", 1, 36)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("K&Technology");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExit)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbProductsIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbProductsIdActionPerformed
        p = billDAO.setLableProducts(cbbProductsId.getSelectedItem().toString());
        if (p != null) {
            lblUnitPrice.setText(String.valueOf(p.getProductPrice()));
            lblQuantityProducts.setText(String.valueOf(p.getProductQuantity()));
            lblProductName.setText(String.valueOf(p.getProductName()));
            if (txtQuantity.getText().trim().isEmpty()) {
                num1 = 0;
                num2 = Integer.parseInt(lblUnitPrice.getText());
                long result = billDAO.mul(num1, num2);
                lblPrice.setText("" + result);
            } else {
                num1 = Integer.parseInt(txtQuantity.getText());
                num2 = Integer.parseInt(lblUnitPrice.getText());
                long result = billDAO.mul(num1, num2);
                lblPrice.setText("" + result);
            }
        }
    }//GEN-LAST:event_cbbProductsIdActionPerformed

    private void cbbEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbEmployeeActionPerformed
        Employee empl = new Employee();
        empl = billDAO.setLabaleEmployee(cbbEmployee.getSelectedItem().toString());
        if (empl != null) {
            lblEmplName.setText(String.valueOf(empl.getName()));
        }
    }//GEN-LAST:event_cbbEmployeeActionPerformed

    private void lblNewBillMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNewBillMousePressed
        try {
            if (lblNewBill.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To New Bill", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    guestSetEnable();
                    lblNewBill.setEnabled(false);
                    lblAddProducts.setEnabled(false);
                    lblDelete.setEnabled(false);
                    lblPayment.setEnabled(false);
                    lblSave.setEnabled(true);
                    cbbEmployee.setEnabled(true);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String dateTime = sdf.format(date);
                    bill.setDateTime(dateTime);
                    if (billDAO.insertBill(bill)) {
                        billDAO.getBillId(txtBillId);
                        JOptionPane.showMessageDialog(null, "New Bill");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblNewBillMousePressed

    private void lblSaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSaveMousePressed
        try {
            if (lblSave.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Save", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    StringBuilder sb = new StringBuilder();
                    //condition
                    Validator.checkName(txtGuestName, sb, "Please Enter Guest Name",
                            "Guest Name: Please Enter Alphabet And Limited 30 characters");
                    Validator.checkEmpty(txtGuestId, sb, "Please Enter Guest ID");
                    Validator.checkEmpty(txtGuestAddress, sb, "Please Enter Guest Address");
                    Validator.checkPhone(txtGuestPhone, sb);
                    Validator.checkMail(txtGuestMail, sb);
                    if (sb.length() > 0) {
                        JOptionPane.showMessageDialog(this, sb.toString(), "Invalid Data", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    bill.setGuestId(txtGuestId.getText());
                    bill.setGuestName(txtGuestName.getText());
                    bill.setGuestAddress(txtGuestAddress.getText());
                    bill.setGuestPhone(txtGuestPhone.getText());
                    bill.setGuestEmail(txtGuestMail.getText());
                    if (billDAO.insertGuest(bill)) {
                        //JOptionPane.showMessageDialog(null, "Save Guest Succesfull");
                        bill.setBillId(txtBillId.getText());
                        bill.setEmplId(cbbEmployee.getSelectedItem().toString());
                        if (billDAO.updateBill(bill)) {
                            cbbProductsId.setEnabled(true);
                            txtQuantity.setEnabled(true);
                            lblAddProducts.setEnabled(true);
                            lblDelete.setEnabled(true);
                            lblSave.setEnabled(false);
                            cbbEmployee.setEnabled(false);
                            guestSetEnableFalse();
                            JOptionPane.showMessageDialog(null, "Save Guest Succesfull");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Save Guest Failed");
                    }
                }
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblSaveMousePressed

    private void txtQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyReleased
        try {
            if (txtQuantity.getText().trim().isEmpty()) {
                num1 = 0;
                num2 = Integer.parseInt(lblUnitPrice.getText());
                long result = billDAO.mul(num1, num2);
                lblPrice.setText("" + result);
            } else {
                num1 = Integer.parseInt(txtQuantity.getText());
                num2 = Integer.parseInt(lblUnitPrice.getText());
                long result = billDAO.mul(num1, num2);
                lblPrice.setText("" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtQuantityKeyReleased

    private void lblAddProductsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddProductsMousePressed
        try {
            if (lblAddProducts.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Save", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    bill.setBillId(txtBillId.getText());
                    bill.setProductId(cbbProductsId.getSelectedItem().toString());
                    bill.setQuantityProductsBill(Integer.parseInt(txtQuantity.getText()));
                    bill.setUnitPrice(Integer.parseInt(lblUnitPrice.getText()));
                    bill.setProductPrice(Integer.parseInt(lblPrice.getText()));
                    StringBuilder sb = new StringBuilder();
                    Validator.checkNumber(txtQuantity, sb);
                    if (sb.length() > 0) {
                        JOptionPane.showMessageDialog(this, sb.toString(), "Invalid Data", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int a = Integer.parseInt(txtQuantity.getText());
                    int b = Integer.parseInt(lblQuantityProducts.getText());
                    if (a > b) {
                        JOptionPane.showMessageDialog(this, "Quantity of products should not be greater than quantity in stock");
                        return;
                    }
                    if (billDAO.insertProductsBill(bill)) {
                        loadDataDetailsBill();
                        lblPayment.setEnabled(true);
                        billDAO.setTotalPrice(txtBillId, lblTotal);
                        txtQuantity.setText("");
                        JOptionPane.showMessageDialog(null, "Save Products Succesfull");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot Add Duplicate Product ID In Bill", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_lblAddProductsMousePressed

    private void txtQuantityMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQuantityMouseExited
        try {
            if (txtQuantity.isEnabled()) {
                StringBuilder sb = new StringBuilder();
                Validator.checkNumber(txtQuantity, sb);
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(this, sb.toString(), "Invalid", JOptionPane.ERROR_MESSAGE);
                    txtQuantity.setText("");
                    return;
                }
                if (txtQuantity.getText().trim().isEmpty()) {
                    num1 = 0;
                    num2 = Integer.parseInt(lblUnitPrice.getText());
                    long result = billDAO.mul(num1, num2);
                    lblPrice.setText("" + result);
                } else {
                    num1 = Integer.parseInt(txtQuantity.getText());
                    num2 = Integer.parseInt(lblUnitPrice.getText());
                    long result = billDAO.mul(num1, num2);
                    lblPrice.setText("" + result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtQuantityMouseExited

    private void lblDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMousePressed
        try {
            if (lblDelete.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Delete", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (billDAO.delete(txtBillId.getText(), cbbProductsId.getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(this, "Deleted Product Success");
                        loadDataDetailsBill();
                        billDAO.setTotalPrice(txtBillId, lblTotal);
                    } else {
                        JOptionPane.showMessageDialog(this, "deleted Product Failed");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblDeleteMousePressed

    private void lblPaymentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPaymentMousePressed
        try {
            if (lblPayment.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want Payment?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    bill.setBillId(txtBillId.getText());
                    bill.setBillPrice(Integer.parseInt(lblTotal.getText()));
                    bill.setEmplId(String.valueOf(cbbEmployee.getSelectedItem()));
                    billDAO.updateQuantityProduct(txtBillId);
                    billDAO.updateBillPrice(bill);
                    defaultBills();
                    modelDetailsBill.setRowCount(0);
                    printBill();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblPaymentMousePressed

    private void tabListDetailsBillFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabListDetailsBillFocusLost
        tabListDetailsBill.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabListDetailsBillFocusLost

    private void lblReturnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReturnMousePressed
        defaultBills();
    }//GEN-LAST:event_lblReturnMousePressed

    private void lblEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMousePressed
        if (JOptionPane.showConfirmDialog(this, "Do You Want To Edit", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            txtMtGuestName.setEnabled(true);
            lblMtSave.setEnabled(true);
        }
    }//GEN-LAST:event_lblEditMousePressed

    private void lblMtSaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMtSaveMousePressed
        try {
            if (lblMtSave.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Save", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    bill.setGuestId(txtMtGuestId.getText());
                    bill.setGuestName(txtMtGuestName.getText());
                    billDAO.editGuestNameOfBill(bill);
                    loadDataBillsToTable();
                    defaultMtBills();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblMtSaveMousePressed

    private void lblReturn1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReturn1MousePressed

        if (JOptionPane.showConfirmDialog(this, "Do You Want To Return", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            defaultMtBills();
            loadDataBillsToTable();
        }
    }//GEN-LAST:event_lblReturn1MousePressed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            String billId = txtMtSearch.getText();
            ArrayList<Bill> listMtBill = new ArrayList<>();
            if (billId.trim().isEmpty()) {
                loadDataBillsToTable();
            } else {
                listMtBill = billDAO.findByIdMtBill(billId);
                modelMtBill.setRowCount(0);
                for (Bill bill : listMtBill) {
                    modelMtBill.addRow(new Object[]{
                        bill.getBillId(), bill.getBillPrice(), bill.getDateTime(),
                        bill.getEmplId(), bill.getGuestName()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tabMtBillFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabMtBillFocusLost
        tabMtBill.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabMtBillFocusLost

    private void tabMtBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMtBillMouseClicked
        try {
            int row = tabMtBill.getSelectedRow();
            if (row >= 0) {
                String id = (String) tabMtBill.getValueAt(row, 0);
                bill = billDAO.findById(id);
                if (bill != null) {
                    txtMtBillId.setText(bill.getBillId());
                    lblMtBillPrice.setText(String.valueOf(bill.getBillPrice()));
                    txtMtGuestId.setText(bill.getGuestId());
                    txtMtGuestName.setText(bill.getGuestName());
                    cbbMtEmplId.setSelectedItem(bill.getEmplId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tabMtBillMouseClicked

    private void cbbMtEmplIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMtEmplIdActionPerformed
        Employee empl = new Employee();
        empl = billDAO.setLabaleEmployee(cbbMtEmplId.getSelectedItem().toString());
        if (empl != null) {
            lblMtEmplName.setText(String.valueOf(empl.getName()));
        }
    }//GEN-LAST:event_cbbMtEmplIdActionPerformed

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        this.dispose();
        MainClient mn = new MainClient();
        mn.setVisible(true);
    }//GEN-LAST:event_lblExitMouseClicked

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
            java.util.logging.Logger.getLogger(Bills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bills.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bills().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbbEmployee;
    private javax.swing.JComboBox<String> cbbMtEmplId;
    public javax.swing.JComboBox<String> cbbProductsId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAddProducts;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblEmplName;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMtBillPrice;
    private javax.swing.JLabel lblMtEmplName;
    private javax.swing.JLabel lblMtSave;
    private javax.swing.JLabel lblNewBill;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblQuantityProducts;
    private javax.swing.JLabel lblReturn;
    private javax.swing.JLabel lblReturn1;
    private javax.swing.JLabel lblSave;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUnitPrice;
    private javax.swing.JTable tabListDetailsBill;
    private javax.swing.JTable tabMtBill;
    private javax.swing.JTextField txtBillId;
    private javax.swing.JTextField txtGuestAddress;
    private javax.swing.JTextField txtGuestId;
    private javax.swing.JTextField txtGuestMail;
    private javax.swing.JTextField txtGuestName;
    private javax.swing.JTextField txtGuestPhone;
    private javax.swing.JTextField txtMtBillId;
    private javax.swing.JTextField txtMtGuestId;
    private javax.swing.JTextField txtMtGuestName;
    private javax.swing.JTextField txtMtSearch;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
