package cl.evilgenius.project_foody_v3.Model;

public class Order {
    private String ProductID;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Discount;
    private String NumTable;



    public Order(String productID, String productName, String quantity, String price, String discount, String numTable) {

        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
        NumTable = numTable;
    }

  /*  public Order(String foodId, String name, String number, String price, String discount) {
    }*/

    public Order() {
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
    public String getNumTable() {
        return NumTable;
    }

    public void setNumTable(String numTable) {
        NumTable = numTable;
    }
}
