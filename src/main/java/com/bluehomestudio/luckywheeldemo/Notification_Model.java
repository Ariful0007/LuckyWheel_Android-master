package com.bluehomestudio.luckywheeldemo;
public class Notification_Model {
    String username,feedback;

    public Notification_Model() {
    }

    public Notification_Model(String username, String feedback) {
        this.username = username;
        this.feedback = feedback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
