/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DatabaseConnection;
import Controller.ImageHelper;
import Controller.ProducerDAO;
import Controller.ProductDAO;
import Controller.Validator;
import Model.Producer;
import Model.Product;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.*;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class Products extends javax.swing.JFrame {

    /**
     * Creates new form Products
     */
    private DefaultTableModel model, producerModel;
    Product p = new Product();
    Producer producer = new Producer();
    ProductDAO productDAO = new ProductDAO();
    ProducerDAO producerDAO = new ProducerDAO();
    EditProducts editProducts = new EditProducts(this, true);
    private byte[] productImage;
    private ArrayList<Product> listProduct = new ArrayList<>();
    private ArrayList<Producer> listProducer = new ArrayList<>();
    private Connection connection;

    public Products() {
        initComponents();
        initJFrameProducts();
        initTable();
        loadDataToTable();
        initTableProducer();
        loadDataToTableProducer();

    }

    public void initJFrameProducts() {
        this.setTitle("Management Products");
        this.setExtendedState(this.MAXIMIZED_BOTH);
        defaultLabel();
        productDAO.fillComboBox(cbbProducerName);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initTable() {
        model = (DefaultTableModel) tabProducts.getModel();
        //set column table
        model.setColumnIdentifiers(new Object[]{"ID", "Name", "Quantity", "Warranty", "Price", "Producer Name",
            "Images"});
        tabProducts.setModel(model);
        tabProducts.setDefaultEditor(Object.class, null);
        tabProducts.getTableHeader().setDefaultRenderer((new HeaderColor()));
        tabProducts.setBackground(new Color(255, 255, 255));
        tabProducts.setRowHeight(25);
    }

    private void loadDataToTable() {
        try {
            ProductDAO proDAO = new ProductDAO();
            ArrayList<Product> listProduct = proDAO.getListProduct("select * from Products");
            model.setRowCount(0);
            //put the data in row
            for (Product p : listProduct) {
                addRow(p);
            }
            model.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addRow(Product p) {
        model.addRow(new Object[]{
            p.getProductId(), p.getProductName(), p.getProductQuantity(),
            p.getProductWarranty(), p.getProductPrice(), p.getProducerName(),
            p.getProductImage()
        });
    }

    private void initTableProducer() {
        producerModel = (DefaultTableModel) tabListProducer.getModel();
        producerModel.setColumnIdentifiers(new Object[]{"Name", "Type", "Address", "Phone"});
        tabListProducer.setModel(producerModel);
        tabListProducer.setDefaultEditor(Object.class, null);
        tabListProducer.getTableHeader().setDefaultRenderer((new HeaderColor()));
        tabListProducer.setBackground(new Color(255, 255, 255));
        tabListProducer.setRowHeight(25);

    }

    private void loadDataToTableProducer() {
        try {
            ProducerDAO producerDAO = new ProducerDAO();
            ArrayList<Producer> listProducer = producerDAO.getListProducer("select producername,type,address,phone from producer");
            producerModel.setRowCount(0);
            for (Producer producer : listProducer) {
                producerModel.addRow(new Object[]{
                    producer.getProducerName(), producer.getProducerType(),
                    producer.getProducerAdd(), producer.getProducerPhone()

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//DESGIN UI 

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

    public void defaultLabel() {
        lblChooseFile.setEnabled(false);
        txtProductId.setEnabled(false);
        txtProductName.setEnabled(false);
        txtProductQuantity.setEnabled(false);
        txtProductWarranty.setEnabled(false);
        txtProductPrice.setEnabled(false);
        cbbProducerName.setEnabled(false);
        lblAdd.setEnabled(true);
        lblDeleted.setEnabled(true);
        lblEdit.setEnabled(true);
        lblSave.setEnabled(true);
        tabProducts.setEnabled(true);
        txtProducerName.setEnabled(false);
        txtProducerType.setEnabled(false);
        txtProducerAddress.setEnabled(false);
        txtProducerPhone.setEnabled(false);
        tabListProducer.setEnabled(true);
        tabProducts.setEnabled(true);
        lblProducerAdd.setEnabled(true);
        lblProducerEdit.setEnabled(true);
        lblProducerSave.setEnabled(false);

    }

    public void enterTxt() {
        txtProductId.setEnabled(true);
        txtProductName.setEnabled(true);
        txtProductQuantity.setEnabled(true);
        txtProductWarranty.setEnabled(true);
        txtProductPrice.setEnabled(true);
        cbbProducerName.setEnabled(true);
        lblEdit.setEnabled(false);
        lblChooseFile.setEnabled(true);
        lblDeleted.setEnabled(false);
        lblAdd.setEnabled(false);
        productImage = null;
        //display default img
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/profile-user (1).png"));
        lblProductImages.setIcon(icon);
        tabProducts.setEnabled(false);
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
        jPanel2 = new jPanelGradient();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new jPanelGradient();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabProducts = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtProductQuantity = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtProductWarranty = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtProductPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbbProducerName = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        radbtnQuantity = new javax.swing.JRadioButton();
        rdbtnPrice = new javax.swing.JRadioButton();
        lblSort = new javax.swing.JLabel();
        lblProductImages = new javax.swing.JLabel();
        lblChooseFile = new javax.swing.JLabel();
        lblAdd = new javax.swing.JLabel();
        lblEdit = new javax.swing.JLabel();
        lblDeleted = new javax.swing.JLabel();
        lblReturn = new javax.swing.JLabel();
        lblPrint = new javax.swing.JLabel();
        lblSave = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jPanel9 = new jPanelGradient();
        jPanel3 = new jPanelGradient();
        jLabel7 = new javax.swing.JLabel();
        txtProducerName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtProducerType = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtProducerAddress = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtProducerPhone = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabListProducer = new javax.swing.JTable();
        lblProducerAdd = new javax.swing.JLabel();
        lblProducerEdit = new javax.swing.JLabel();
        lblProducerReturn = new javax.swing.JLabel();
        lblProducerDeleted = new javax.swing.JLabel();
        lblProducerPrint = new javax.swing.JLabel();
        lblProducerSave = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtFindProducer = new javax.swing.JTextField();
        btnProducerSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(186, 79, 84));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Sylfaen", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("                 K&Technology");

        tabProducts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabProducts.setModel(new javax.swing.table.DefaultTableModel(
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
        tabProducts.setRowHeight(25);
        tabProducts.setSelectionBackground(new java.awt.Color(128, 128, 128));
        tabProducts.setShowVerticalLines(false);
        tabProducts.getTableHeader().setReorderingAllowed(false);
        tabProducts.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tabProductsFocusLost(evt);
            }
        });
        tabProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabProducts);

        jLabel3.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product ID");

        jLabel4.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Name");

        jLabel5.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Quantity");

        jLabel16.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel16.setText("Warranty");

        jLabel17.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel17.setText("Price");

        jLabel6.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Producer Name");

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        radbtnQuantity.setForeground(new java.awt.Color(255, 255, 255));
        radbtnQuantity.setText("Quantity");

        rdbtnPrice.setForeground(new java.awt.Color(255, 255, 255));
        rdbtnPrice.setSelected(true);
        rdbtnPrice.setText("Price");

        lblSort.setBackground(new java.awt.Color(0, 153, 153));
        lblSort.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblSort.setForeground(new java.awt.Color(255, 255, 255));
        lblSort.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSort.setText("Sort");
        lblSort.setBorder(new javax.swing.border.SoftBevelBorder(0));
        lblSort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSortMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblSort, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbtnPrice)
                .addGap(18, 18, 18)
                .addComponent(radbtnQuantity)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSort)
                    .addComponent(rdbtnPrice)
                    .addComponent(radbtnQuantity))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblProductImages.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductImages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/profile-user (1).png"))); // NOI18N

        lblChooseFile.setBackground(new java.awt.Color(186, 79, 84));
        lblChooseFile.setFont(new java.awt.Font("Sylfaen", 0, 11)); // NOI18N
        lblChooseFile.setForeground(new java.awt.Color(255, 255, 255));
        lblChooseFile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChooseFile.setText("Choose File");
        lblChooseFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblChooseFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblChooseFileMousePressed(evt);
            }
        });

        lblAdd.setBackground(new java.awt.Color(186, 79, 84));
        lblAdd.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        lblAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add-friend.png"))); // NOI18N
        lblAdd.setText("Add");
        lblAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAddMousePressed(evt);
            }
        });

        lblEdit.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        lblEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        lblEdit.setText("Edit");
        lblEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblEditMousePressed(evt);
            }
        });

        lblDeleted.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        lblDeleted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDeleted.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-user.png"))); // NOI18N
        lblDeleted.setText("Deleted");
        lblDeleted.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDeleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblDeletedMousePressed(evt);
            }
        });

        lblReturn.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        lblReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        lblReturn.setText("Return");
        lblReturn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        lblReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblReturnMousePressed(evt);
            }
        });

        lblPrint.setBackground(new java.awt.Color(255, 255, 255));
        lblPrint.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        lblPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print.png"))); // NOI18N
        lblPrint.setText("Print");
        lblPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblPrintMousePressed(evt);
            }
        });

        lblSave.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        lblSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        lblSave.setText("Save");
        lblSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSaveMousePressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel18.setText("Find By Name");

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                            .addComponent(txtProductQuantity)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbbProducerName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtProductWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(96, 96, 96)
                        .addComponent(lblProductImages, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                            .addComponent(lblSave, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDeleted, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(lblReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(526, 526, 526))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(754, 754, 754))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDeleted, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSave, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addComponent(txtProductWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtProductQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(20, 20, 20)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbProducerName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6))
                        .addComponent(lblProductImages, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Products Mangement", jPanel1);

        jPanel9.setBackground(new java.awt.Color(186, 79, 84));

        jPanel3.setBackground(new java.awt.Color(186, 79, 84));

        jLabel7.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel7.setText("Producer Name");

        jLabel8.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel8.setText("Type");

        jLabel9.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel9.setText("Address");

        jLabel13.setFont(new java.awt.Font("Sylfaen", 1, 16)); // NOI18N
        jLabel13.setText("  Phone");

        tabListProducer.setModel(new javax.swing.table.DefaultTableModel(
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
        tabListProducer.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tabListProducer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabListProducerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabListProducer);

        lblProducerAdd.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblProducerAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducerAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add-friend.png"))); // NOI18N
        lblProducerAdd.setText("Add");
        lblProducerAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProducerAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProducerAddMousePressed(evt);
            }
        });

        lblProducerEdit.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblProducerEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducerEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        lblProducerEdit.setText("Edit");
        lblProducerEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProducerEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProducerEditMousePressed(evt);
            }
        });

        lblProducerReturn.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblProducerReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducerReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        lblProducerReturn.setText("Return");
        lblProducerReturn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProducerReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProducerReturnMousePressed(evt);
            }
        });

        lblProducerDeleted.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblProducerDeleted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducerDeleted.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-user.png"))); // NOI18N
        lblProducerDeleted.setText("Deleted");
        lblProducerDeleted.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProducerDeleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProducerDeletedMousePressed(evt);
            }
        });

        lblProducerPrint.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblProducerPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducerPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print.png"))); // NOI18N
        lblProducerPrint.setText("Print");
        lblProducerPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProducerPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProducerPrintMousePressed(evt);
            }
        });

        lblProducerSave.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        lblProducerSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducerSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        lblProducerSave.setText("Save");
        lblProducerSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProducerSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProducerSaveMousePressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Find By Name");

        btnProducerSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnProducerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProducerSearchActionPerformed(evt);
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
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProducerName, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProducerType, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProducerAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                        .addGap(142, 142, 142)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProducerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(550, 550, 550)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFindProducer, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProducerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblProducerAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblProducerEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblProducerReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblProducerDeleted, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblProducerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblProducerPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtProducerName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtProducerType, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtProducerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtProducerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProducerAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProducerEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProducerReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProducerDeleted, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProducerPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProducerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProducerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(txtFindProducer, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Producer", jPanel9);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addComponent(jTabbedPane2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void clear() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtProductQuantity.setText("");
        txtProductWarranty.setText("");
        txtProductPrice.setText("");
        txtProducerName.setText("");
        txtProducerType.setText("");
        txtProducerAddress.setText("");
        txtProducerPhone.setText("");
        txtSearch.setText("");
        txtFindProducer.setText("");
    }
    private void lblAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddMousePressed

        try {
            if (lblAdd.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Add Product A New?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    clear();
                    enterTxt();
                }
            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_lblAddMousePressed

    private void lblSaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSaveMousePressed
        try {
            if (lblSave.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You To Save", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    defaultLabel();
                    StringBuilder sb = new StringBuilder();
                    //condition JtextField
                    Validator.checkEmpty(txtProductId, sb, "Please Enter Product ID");
                    Validator.checkEmpty(txtProductName, sb, "Please Enter Product Name");
                    Validator.checkQuantity(txtProductQuantity, sb);
                    Validator.checkEmpty(txtProductWarranty, sb, "Please Enter Product Warranty");
                    Validator.checkPrice(txtProductPrice, sb);
                    if (sb.length() > 0) {
                        JOptionPane.showMessageDialog(this, sb.toString(), "Invaid Data", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //get data from the text field
                    p.setProductId(txtProductId.getText());
                    p.setProductName(txtProductName.getText());
                    p.setProductQuantity(Integer.parseInt(txtProductQuantity.getText()));
                    p.setProductWarranty(txtProductWarranty.getText());
                    p.setProductPrice(Integer.parseInt(txtProductPrice.getText()));
                    p.setProducerName(cbbProducerName.getSelectedItem().toString());
                    p.setProductImage(productImage);
                    if (productDAO.insert(p)) {
                        JOptionPane.showMessageDialog(null, "Save Product Successful");
                        clear();
                        loadDataToTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot Add Duplicate Products ID");
                        enterTxt();
                    }
                }
            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblSaveMousePressed

    private void lblEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMousePressed

        if (lblEdit.isEnabled()) {
            editProducts.setVisible(true);
            loadDataToTable();
        }
        return;
    }//GEN-LAST:event_lblEditMousePressed

    private void tabProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProductsMouseClicked
        try {
            int row = tabProducts.getSelectedRow();
            if (row >= 0) {
                String id = (String) tabProducts.getValueAt(row, 0);
                p = productDAO.findById(id);
                if (p != null) {
                    txtProductId.setText(p.getProductId());
                    txtProductName.setText(p.getProductName());
                    txtProductQuantity.setText(String.valueOf(p.getProductQuantity()));
                    txtProductWarranty.setText(p.getProductWarranty());
                    txtProductPrice.setText(String.valueOf(p.getProductPrice()));
                    cbbProducerName.setSelectedItem(p.getProducerName());
                    if (p.getProductImage() != null) {
                        Image img = ImageHelper.CreateImageFromByteArray(p.getProductImage(), "jpg");
                        lblProductImages.setIcon(new ImageIcon(img));
                        productImage = p.getProductImage();
                    } else {
                        //display default img
                        productImage = p.getProductImage();
                        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/profile-user (1).png"));
                        lblProductImages.setIcon(icon);
                    }
                }
                if (p != null) {
                    editProducts.txtProductId.setText(p.getProductId());
                    editProducts.txtProductName.setText(p.getProductName());
                    editProducts.txtProductQuantity.setText(String.valueOf(p.getProductQuantity()));
                    editProducts.txtProductWarranty.setText(p.getProductWarranty());
                    editProducts.txtProductPrice.setText(String.valueOf(p.getProductPrice()));
                    editProducts.cbbProducerName.setSelectedItem(p.getProducerName());
                    if (p.getProductImage() != null) {
                        Image img = ImageHelper.CreateImageFromByteArray(p.getProductImage(), "jpg");
                        editProducts.lblProductImages.setIcon(new ImageIcon(img));
                        productImage = p.getProductImage();
                    } else {
                        productImage = p.getProductImage();
                        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/profile-user (1).png"));
                        editProducts.lblProductImages.setIcon(icon);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_tabProductsMouseClicked

    private void lblDeletedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeletedMousePressed
        try {
            if (lblDeleted.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Deleted?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (productDAO.delete(txtProductId.getText())) {
                        JOptionPane.showMessageDialog(this, "Deleted Product Successful");
                        loadDataToTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Deleted Product Failed");
                    }
                }
            }
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblDeletedMousePressed

    private void tabProductsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabProductsFocusLost
        tabProducts.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabProductsFocusLost

    private void lblReturnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReturnMousePressed

        if (JOptionPane.showConfirmDialog(this, "Do You Want To Revert The Functionality To Default", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            defaultLabel();
            loadDataToTable();
            clear();
        }
    }//GEN-LAST:event_lblReturnMousePressed

    private void lblPrintMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPrintMousePressed
        String link = "H:\\Project\\ProjectJavaSem2\\K&Technology\\src\\View\\JasperReport\\reportProducts.jrxml";
        try {
            //created JasperReport
            JasperReport jr = JasperCompileManager.compileReport(link);
            //created JasperPrint
            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseConnection.openConnection());
            //created JasperViewer
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_lblPrintMousePressed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            String name = txtSearch.getText();
            if (name.trim().isEmpty()) {
                loadDataToTable();
            } else {
                listProduct = productDAO.findByName(name);
                model.setRowCount(0);
                for (Product p : listProduct) {
                    addRow(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void lblProducerAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProducerAddMousePressed
        try {
            if (lblProducerAdd.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Add A New Producer", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    lblProducerEdit.setEnabled(true);
                    lblProducerAdd.setEnabled(false);
                    lblProducerSave.setEnabled(true);
                    txtProducerName.setEnabled(true);
                    txtProducerType.setEnabled(true);
                    txtProducerAddress.setEnabled(true);
                    txtProducerPhone.setEnabled(true);
                    tabListProducer.setEnabled(false);
                    clear();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblProducerAddMousePressed

    private void lblProducerSaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProducerSaveMousePressed

        try {
            if (lblProducerSave.isEnabled()) {
                if (lblProducerEdit.isEnabled()) {
                    if (JOptionPane.showConfirmDialog(this, "Do You To Add", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        StringBuilder sb = new StringBuilder();
                        //condition JtextField
                        Validator.checkEmpty(txtProducerName, sb, "Please Enter Producer Name");
                        Validator.checkEmpty(txtProducerType, sb, "Please Enter Producer Type");
                        Validator.checkEmpty(txtProducerAddress, sb, "Please Enter Producer Address");
                        Validator.checkPhone(txtProducerPhone, sb);
                        if (sb.length() > 0) {
                            JOptionPane.showMessageDialog(this, sb.toString(), "Invaid Data", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        //get data from the text field
                        producer.setProducerName(txtProducerName.getText());
                        producer.setProducerType(txtProducerType.getText());
                        producer.setProducerAdd(txtProducerAddress.getText());
                        producer.setProducerPhone(txtProducerPhone.getText());
                        if (producerDAO.insertProducer(producer)) {
                            JOptionPane.showMessageDialog(null, "Save Producer Successful");
                            clear();
                            loadDataToTableProducer();
                            defaultLabel();
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "Save Producer Failed");
                        }
                    }
                }
                if (lblProducerAdd.isEnabled()) {
                    producer.setProducerName(txtProducerName.getText());
                    producer.setProducerType(txtProducerType.getText());
                    producer.setProducerAdd(txtProducerAddress.getText());
                    producer.setProducerPhone(txtProducerPhone.getText());
                    if (JOptionPane.showConfirmDialog(this, "Do You Want To Save", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        StringBuilder sb = new StringBuilder();
                        Validator.checkEmpty(txtProducerName, sb, "Please Enter Producer Name");
                        Validator.checkEmpty(txtProducerType, sb, "Please Enter Product Type");
                        Validator.checkEmpty(txtProducerAddress, sb, "Please Enter Product Address");
                        Validator.checkPhone(txtProducerPhone, sb);;
                        if (sb.length() > 0) {
                            JOptionPane.showMessageDialog(this, sb.toString(), "Invaid Data", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (producerDAO.update(producer)) {
                            JOptionPane.showMessageDialog(this, "Edit Producer Successful");
                            clear();
                            loadDataToTableProducer();
                            defaultLabel();
                            return;
                        } else {
                            JOptionPane.showMessageDialog(this, "Edit Producer Failed");
                        }
                    }
                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_lblProducerSaveMousePressed

    private void tabListProducerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabListProducerMouseClicked
        try {
            int row = tabListProducer.getSelectedRow();
            if (row >= 0) {
                String name = (String) tabListProducer.getValueAt(row, 0);
//                int rowNumber = (Integer) tabListProducer.getValueAt(row, 0);
                producer = producerDAO.findByName(name);
                if (producer != null) {
                    txtProducerName.setText(producer.getProducerName());
                    txtProducerType.setText(producer.getProducerType());
                    txtProducerAddress.setText(producer.getProducerAdd());
                    txtProducerPhone.setText(producer.getProducerPhone());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tabListProducerMouseClicked

    private void lblProducerEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProducerEditMousePressed
        try {
            if (lblProducerEdit.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Edit Producer", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    lblProducerAdd.setEnabled(true);
                    txtProducerName.setEnabled(false);
                    txtProducerType.setEnabled(true);
                    txtProducerAddress.setEnabled(true);
                    txtProducerPhone.setEnabled(true);
                    lblProducerEdit.setEnabled(false);
                    lblProducerSave.setEnabled(true);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblProducerEditMousePressed

    private void lblProducerReturnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProducerReturnMousePressed

        if (JOptionPane.showConfirmDialog(this, "Do You Want To Restart ", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            loadDataToTableProducer();
            defaultLabel();
            clear();
        }
    }//GEN-LAST:event_lblProducerReturnMousePressed

    private void lblProducerDeletedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProducerDeletedMousePressed
        try {
            if (lblProducerDeleted.isEnabled()) {
                if (JOptionPane.showConfirmDialog(this, "Do You Want To Deleted?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (producerDAO.delete(txtProducerName.getText())) {
                        JOptionPane.showMessageDialog(this, "Deleted Product Successful");
                        loadDataToTableProducer();
                    } else {
                        JOptionPane.showMessageDialog(this, "Deleted Product Failed");
                    }
                }
            }
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblProducerDeletedMousePressed

    private void lblProducerPrintMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProducerPrintMousePressed
        String link = "H:\\Project\\ProjectJavaSem2\\K&Technology\\src\\View\\JasperReport\\reportProducer.jrxml";
        try {
            //created JasperReport
            JasperReport jr = JasperCompileManager.compileReport(link);
            //created JasperPrint
            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseConnection.openConnection());
            //created JasperViewer
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblProducerPrintMousePressed

    private void btnProducerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProducerSearchActionPerformed
        try {
            String name = txtFindProducer.getText();
            if (name.trim().isEmpty()) {
                loadDataToTableProducer();
            } else {
                listProducer = producerDAO.findByNamee(name);
                producerModel.setRowCount(0);
                for (Producer producer : listProducer) {
                    producerModel.addRow(new Object[]{producer.getProducerName(), producer.getProducerType(),
                        producer.getProducerAdd(), producer.getProducerPhone()});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnProducerSearchActionPerformed

    private void lblSortMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSortMousePressed
        if (rdbtnPrice.isSelected()) {
            sortBySelection("select * from dbo.Products order by price");
        } else {
            sortBySelection("select * from dbo.Products order by quantity");
        }
    }//GEN-LAST:event_lblSortMousePressed

    private void lblChooseFileMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChooseFileMousePressed
        if (lblChooseFile.isEnabled()) {
            //create object chooser
            JFileChooser chooser = new JFileChooser();
            //create filter only for users to choose file(.jpg)
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return true;
                    } else {
                        return file.getName().toLowerCase().endsWith(".jpg");
                    }
                }

                @Override
                public String getDescription() {
                    return "Image File(*.jpg)";
                }
            });
            if (chooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) {
                return;
            }
            File file = chooser.getSelectedFile();
            try {
                //create object ImageIcon from path
                ImageIcon icon = new ImageIcon(file.getPath());
                //custome size image original covert getImage
                Image img = ImageHelper.resize(icon.getImage(), 267, 267);
                ImageIcon resizedIcon = new ImageIcon(img);
                lblProductImages.setIcon(resizedIcon);
                //converts selected images to array bytes
                productImage = ImageHelper.toByteArray(img, "jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_lblChooseFileMousePressed

    private void sortBySelection(String sql) {
        try {
            ProductDAO proDAO = new ProductDAO();
            ArrayList<Product> listProduct = proDAO.getListProduct(sql);
            model.setRowCount(0);
            //put the data in row
            for (Product p : listProduct) {
                addRow(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            java.util.logging.Logger.getLogger(Products.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Products.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Products.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Products.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProducerSearch;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbProducerName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblChooseFile;
    private javax.swing.JLabel lblDeleted;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblPrint;
    private javax.swing.JLabel lblProducerAdd;
    private javax.swing.JLabel lblProducerDeleted;
    private javax.swing.JLabel lblProducerEdit;
    private javax.swing.JLabel lblProducerPrint;
    private javax.swing.JLabel lblProducerReturn;
    private javax.swing.JLabel lblProducerSave;
    private javax.swing.JLabel lblProductImages;
    private javax.swing.JLabel lblReturn;
    private javax.swing.JLabel lblSave;
    private javax.swing.JLabel lblSort;
    private javax.swing.JRadioButton radbtnQuantity;
    private javax.swing.JRadioButton rdbtnPrice;
    private javax.swing.JTable tabListProducer;
    private javax.swing.JTable tabProducts;
    private javax.swing.JTextField txtFindProducer;
    private javax.swing.JTextField txtProducerAddress;
    private javax.swing.JTextField txtProducerName;
    private javax.swing.JTextField txtProducerPhone;
    private javax.swing.JTextField txtProducerType;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtProductQuantity;
    private javax.swing.JTextField txtProductWarranty;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
