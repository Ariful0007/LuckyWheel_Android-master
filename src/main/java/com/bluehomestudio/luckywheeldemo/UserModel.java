package com.bluehomestudio.luckywheeldemo;

public class UserModel {
String name,image,number,email,pass,username;

    public String getName() {
        return name;
    }

    public UserModel(String name, String image, String number, String email, String pass, String username) {
        this.name = name;
        this.image = image;
        this.number = number;
        this.email = email;
        this.pass = pass;
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserModel() {
    }
}
