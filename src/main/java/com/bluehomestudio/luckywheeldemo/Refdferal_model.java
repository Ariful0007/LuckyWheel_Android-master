package com.bluehomestudio.luckywheeldemo;

public class Refdferal_model {
    String refername,refername_email,user_id,user_name,uuid,
            user_email,time;

    public Refdferal_model() {
    }

    public String getRefername() {
        return refername;
    }

    public void setRefername(String refername) {
        this.refername = refername;
    }

    public String getRefername_email() {
        return refername_email;
    }

    public void setRefername_email(String refername_email) {
        this.refername_email = refername_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Refdferal_model(String refername, String refername_email,
                           String user_id, String user_name, String uuid, String user_email, String time) {
        this.refername = refername;
        this.refername_email = refername_email;
        this.user_id = user_id;
        this.user_name = user_name;
        this.uuid = uuid;
        this.user_email = user_email;
        this.time = time;
    }
}
