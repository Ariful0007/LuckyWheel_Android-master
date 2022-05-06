package com.bluehomestudio.luckywheeldemo;

public class MethodeModel {
    String name,minwithdraw,hint,image,c_price,c_name,uuid,agents;

    public MethodeModel() {
    }

    public MethodeModel(String name, String minwithdraw,
                        String hint, String image, String c_price, String c_name, String uuid, String agents) {
        this.name = name;
        this.minwithdraw = minwithdraw;
        this.hint = hint;
        this.image = image;
        this.c_price = c_price;
        this.c_name = c_name;
        this.uuid = uuid;
        this.agents = agents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinwithdraw() {
        return minwithdraw;
    }

    public void setMinwithdraw(String minwithdraw) {
        this.minwithdraw = minwithdraw;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getC_price() {
        return c_price;
    }

    public void setC_price(String c_price) {
        this.c_price = c_price;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAgents() {
        return agents;
    }

    public void setAgents(String agents) {
        this.agents = agents;
    }
}
