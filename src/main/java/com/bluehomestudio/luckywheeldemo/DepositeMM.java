package com.bluehomestudio.luckywheeldemo;

public class DepositeMM {
    String email,uuid,amount,number,userid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public DepositeMM(String email, String uuid, String amount, String number, String userid) {
        this.email = email;
        this.uuid = uuid;
        this.amount = amount;
        this.number = number;
        this.userid = userid;
    }

    public DepositeMM() {
    }
}
