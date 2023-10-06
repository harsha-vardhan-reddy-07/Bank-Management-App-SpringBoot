package com.bankManagement.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "users")
public class UserModel {

    @Getter
    public String _id;
    public String username;


    public String email;
    public String Password;

    public String userType;
    public int balance;
    public String homeBranch;
    public String IFSCCode;

    public UserModel() {
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setHomeBranch(String homeBranch) {
        this.homeBranch = homeBranch;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "_id='" + _id + '\'' +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", Password='" + Password + '\'' +
                ", userType='" + userType + '\'' +
                ", homeBranch='" + homeBranch + '\'' +
                ", IFSCCode='" + IFSCCode + '\'' +
                '}';
    }

}
