package cl.evilgenius.project_foody_v3.Model;

import java.util.List;

public class Pedido {
    //Request

    private String phone;
    private String nombre;
    private String address;
    private String total;
    private String status = "0";
    private List<Order> foods; //lista de pedidos


    public Pedido() {
    }




  /*  public Pedido(String phone, String nombre, String address, String total, List<Order> foods) {
        this.phone = phone;
        this.nombre = nombre;
       this.address = address;
        this.total = total;
        this.foods = foods;
      this.status = "0"; //por defecto vamos a dejar 0: pedido, 1: enviando, 2: enviado


    }*/

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

 public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pedido(String phone, String nombre, String address, String total, String status, List<Order> foods) {
        this.phone = phone;
        this.nombre = nombre;
        this.address = address;
        this.total = total;
        this.status = status;
        this.foods = foods;
    }
}
