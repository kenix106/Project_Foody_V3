package cl.evilgenius.project_foody_v3.backdoor_test;

public class backDoor_Blog {

    private String nombre;
    private String pic;

    public backDoor_Blog() {
    }

    public backDoor_Blog(String nombre, String pic) {
        this.nombre = nombre;
        this.pic = pic;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
