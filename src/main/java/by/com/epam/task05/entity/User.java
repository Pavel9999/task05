package by.com.epam.task05.entity;

public class User
{
    int id;
    String first_name;
    String second_name;
    String last_name;
    String full_name;
    String email;
    String password;
    UserRole role;

    public User()
    {

    }

    public User(String first_name, String second_name, String last_name, String email, String password)
    {
        this.first_name = first_name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name()
    {
        full_name = last_name + " " + first_name + " " + second_name;
        return full_name;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
