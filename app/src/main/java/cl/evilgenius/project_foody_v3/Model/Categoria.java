package cl.evilgenius.project_foody_v3.Model;

import android.media.Image;

public class Categoria {

    private String nombre;
    private String pic;

    public Categoria() {
    }

    public Categoria(String nombre, String pic) {
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
