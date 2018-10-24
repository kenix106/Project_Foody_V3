package cl.evilgenius.project_foody_v3.Model;

public class User {
    private String Name, Pass, Phone;

    public User() {
    }

    public User(String name, String pass ) {
        Name = name;
        Pass = pass;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
