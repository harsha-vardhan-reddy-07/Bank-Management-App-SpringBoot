package com.bankManagement.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "deposits")
public class DepositModel {

    public String _id;
    public String depositName;
    public String depositType;
    public String customerId;
    public String customerName;
    public String nomineeName;
    public int nomineeAge;
    public int duration;
    public int amount;
    public String createdDate;
    public String matureDate;

    public DepositModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDepositName() {
        return depositName;
    }

    public void setDepositName(String depositName) {
        this.depositName = depositName;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public int getNomineeAge() {
        return nomineeAge;
    }

    public void setNomineeAge(int nomineeAge) {
        this.nomineeAge = nomineeAge;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdData) {
        this.createdDate = createdData;
    }

    public String getMatureDate() {
        return matureDate;
    }

    public void setMatureDate(String matureDate) {
        this.matureDate = matureDate;
    }

    @Override
    public String toString() {
        return "DepositModel{" +
                "_id='" + _id + '\'' +
                ", depositName='" + depositName + '\'' +
                ", depositType='" + depositType + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", nomineeName='" + nomineeName + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", matureDate='" + matureDate + '\'' +
                '}';
    }
}
