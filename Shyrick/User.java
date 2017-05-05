package Shyrick;

public class User {

    private   int id;
    private String login;
    private String firstName;
    private String lastName;
    private boolean isAdmin;

    public User(int id, String login, String firstName, String lastName, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }


    @Override
    public String toString() {
        String str = isAdmin?"Админ \t":"Клиент \t";
        return str +
                "id - " + id +
                ",\tлогин - " + login +
                ",\tимя - " + firstName +
                ",\tфамилия - " + lastName;
    }

    public String userToStringForWrite() {
        return id + " " + login + " " + firstName + " " +  lastName + " " + isAdmin ;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
