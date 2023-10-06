package com.bankManagement.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
public class TransactionsModel {
    public String _id;
    public String senderId;
    public String senderName;
    public String remarks;
    public String receiverId;
    public String receiverIFSC;
    public String receiverName;
    public boolean loanApproved;
    public int amount;
    public String paymentMethod;
    public String time;

    public TransactionsModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverIFSC() {
        return receiverIFSC;
    }

    public void setReceiverIFSC(String receiverIFSC) {
        this.receiverIFSC = receiverIFSC;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public boolean isLoanApproved() {
        return loanApproved;
    }

    public void setLoanApproved(boolean loanApproved) {
        this.loanApproved = loanApproved;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "TransactionsModel{" +
                "_id='" + _id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiverIFSC='" + receiverIFSC + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
