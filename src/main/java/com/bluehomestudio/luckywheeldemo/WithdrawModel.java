package com.bluehomestudio.luckywheeldemo;

public class WithdrawModel {
    String email,uuid,amount,gname,date,time,status,given_number;

    public WithdrawModel() {
    }

    public WithdrawModel(String email, String uuid,
                         String amount, String gname, String date, String time, String status, String given_number) {
        this.email = email;
        this.uuid = uuid;
        this.amount = amount;
        this.gname = gname;
        this.date = date;
        this.time = time;
        this.status = status;
        this.given_number = given_number;
    }

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

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGiven_number() {
        return given_number;
    }

    public void setGiven_number(String given_number) {
        this.given_number = given_number;
    }
}
