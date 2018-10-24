package cl.evilgenius.project_foody_v3.Model;

public class Food {


  private String   Description,Discount,MenuId,Name,Price,Image;

    public Food() {
    }

    public Food(String description, String discount, String menuId, String name, String price, String image) {
        Description = description;
        Discount = discount;
        MenuId = menuId;
        Name = name;
        Price = price;
        this.Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescripcion(String descripcion) {
        Description = descripcion;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
