/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Acer
 */
public class Guest {

    private String GuestID;
    private String Name;
    private String Address;
    private String Phone;
    private String Email;

    public Guest(){
    
    }
    
    public void setGuestID(String GuestID) {
        this.GuestID = GuestID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGuestID() {
        return GuestID;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public Guest(String GuestID, String Name, String Address, String Phone, String Email) {
        this.GuestID = GuestID;
        this.Name = Name;
        this.Address = Address;
        this.Phone = Phone;
        this.Email = Email;
    }
}
