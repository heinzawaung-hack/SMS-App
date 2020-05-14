package sample.TransactionDB;

import java.sql.Date;

public class Transactions {
    private int id;
    private String type;
    private String productNameID;
    private int productID;
    private int quantity;
    private String remark;
    private String dateTime;

    public Transactions(int id, String type, String productNameID, int quantity, String remark, String dateTime) {
        this.id = id;
        this.type = type;
        this.productNameID = productNameID;
        this.quantity = quantity;
        this.remark = remark;
        this.dateTime = dateTime;
    }

    public Transactions(int id, String type, int productID, int quantity, String remark, String dateTime) {
        this.id = id;
        this.type = type;
        this.productID = productID;
        this.quantity = quantity;
        this.remark = remark;
        this.dateTime = dateTime;
    }

    public Transactions(String type, int productID, int quantity, String remark) {
        this.type = type;
        this.productID = productID;
        this.quantity = quantity;
        this.remark = remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRemark() {
        return remark;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setProductNameID(String productNameID) {
        this.productNameID = productNameID;
    }

    public String getProductNameID() {
        return productNameID;
    }
}
