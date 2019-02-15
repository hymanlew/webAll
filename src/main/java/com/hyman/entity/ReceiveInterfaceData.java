package com.hyman.entity;

public class ReceiveInterfaceData {

    private String name;
    private String num;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReceiveInterfaceData{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
