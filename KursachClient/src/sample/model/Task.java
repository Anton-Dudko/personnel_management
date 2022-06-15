package sample.model;

public class Task {
    private String title;
    private String email;
    private int id;

    public String getTitle() {
        return title;
    }

    public Task(int id, String email, String title) {
        this.title = title;
        this.email = email;
        this.id = id;
    }

    public Task(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
