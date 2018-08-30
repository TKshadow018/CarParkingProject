package a3labgo.tusar.carparkingproject;
import com.google.gson.annotations.SerializedName;

public class Model {
    private int id;
    private String name;
    private String email;
    private String password;
    private int balance;

    public Model(int id, String name, String email, String password, int balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }
}
