package com.meng.configuration.entity;

/**
 * 地址+端口号
 */
public class AddressNode {
    private String address;
    private String port;

    public AddressNode(String address, String port) {
        this.address = address;
        this.port = port;
    }

    public AddressNode() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
