package sample.model;

public class User {
    private String name;
    private String surname;
    private String email;
    private String password;
    private int roll;
    private int id;
    public static User currentUser;

    public User(String email, int id, int roll ) {
        this.email = email;
        this.id = id;
        this.roll = roll;
    }

    public User(int id, String email, String password, int roll) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roll = roll;
    }

    public User(String name, String email, String surname){
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
