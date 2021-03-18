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
public class Account {
    private String UserName;
    private String Password;
    private String Decentralization;
    private String EmplID;

    public Account() {

    }

    public Account(String UserName, String Password, String Decentralization, String EmplID){
        this.UserName = UserName;
        this.Password = Password;
        this.Decentralization = Decentralization;
        this.EmplID = EmplID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmplID() {
        return EmplID;
    }

    public String getPassword() {
        return Password;
    }

    public String getDecentralization() {
        return Decentralization;
    }

    public void setUserName(String Username) {
        this.UserName = Username;
    }

    public void setEmplID(String EmplID) {
        this.EmplID = EmplID;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setDecentralization(String Decentralization) {
        this.Decentralization = Decentralization;
    }
}
