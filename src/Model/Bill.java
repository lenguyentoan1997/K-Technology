/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class Bill {

    String billId, productId, dateTime, emplId, guestId, guestName, guestAddress, guestPhone, guestEmail;
    int unitPrice, productPrice, productQuantity, billPrice,quantityProductsBill,billsId;

    public Bill() {
    }

    public Bill(String billId, String productId, String dateTime, String emplId, String guestId, String guestName, String guestAddress, String guestPhone, String guestEmail, int unitPrice, int productPrice, int productQuantity, int billPrice, int quantityProductsBill, int billsId) {
        this.billId = billId;
        this.productId = productId;
        this.dateTime = dateTime;
        this.emplId = emplId;
        this.guestId = guestId;
        this.guestName = guestName;
        this.guestAddress = guestAddress;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
        this.unitPrice = unitPrice;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.billPrice = billPrice;
        this.quantityProductsBill = quantityProductsBill;
        this.billsId = billsId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEmplId() {
        return emplId;
    }

    public void setEmplId(String emplId) {
        this.emplId = emplId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestAddress() {
        return guestAddress;
    }

    public void setGuestAddress(String guestAddress) {
        this.guestAddress = guestAddress;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(int billPrice) {
        this.billPrice = billPrice;
    }

    public int getQuantityProductsBill() {
        return quantityProductsBill;
    }

    public void setQuantityProductsBill(int quantityProductsBill) {
        this.quantityProductsBill = quantityProductsBill;
    }

    public int getBillsId() {
        return billsId;
    }

    public void setBillsId(int billsId) {
        this.billsId = billsId;
    }

   
  

}
