/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author admin
 */
public class Employee {
    private String EmplID;
    private String Name;
    private String Position;
    private String DOB;
    private String Gender;
    private String Address;
    private String Phone;
    private String Salary;
    private String Email;

    public Employee() {
       
    }


    public void setEmplID(String EmplID) {
        this.EmplID = EmplID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
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

    public void setSalary(String Salary) {
        this.Salary = Salary;
    }

    public String getEmplID() {
        return EmplID;
    }

    public String getName() {
        return Name;
    }

    public String getPosition() {
        return Position;
    }

    public String getDOB() {
        return DOB;
    }

    public String getGender() {
        return Gender;
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

    public String getSalary() {
        return Salary;
    }

    public Employee(String EmplID, String Name, String Position, String DOB, String Gender, String Address, String Phone, String Email, String Salary) {
        this.EmplID = EmplID;
        this.Name = Name;
        this.Position = Position;
        this.DOB = DOB;
        this.Gender = Gender;
        this.Address = Address;
        this.Phone = Phone;
        this.Salary = Salary;
        this.Email = Email;
    }


}
