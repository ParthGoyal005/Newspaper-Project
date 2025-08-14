package com.example.newspaperproject.customerboard;

public class CustomerBean {
    String mobile,cname,email,address,dos,area,hawker,paper,price,status;

    public CustomerBean(String mobile, String cname, String email, String address, String dos, String area, String hawker, String paper, String price, String status) {
        this.mobile = mobile;
        this.cname = cname;
        this.email = email;
        this.address = address;
        this.dos = dos;
        this.area = area;
        this.hawker = hawker;
        this.paper = paper;
        this.price = price;
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHawker() {
        return hawker;
    }

    public void setHawker(String hawker) {
        this.hawker = hawker;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
