package a3labgo.tusar.carparkingproject;
import com.google.gson.annotations.SerializedName;

public class Model2 {
    private int id;
    private String name;
    private int totalspot;
    private int availablespot;
    private int price;
    private int locationid;
    private float latitude;
    private float longitude;

    public Model2(int id, String name, int totalspot, int availablespot, int price, int locationid, float latitude,float longitude) {
        this.id = id;
        this.name = name;
        this.totalspot = totalspot;
        this.availablespot = availablespot;
        this.price = price;
        this.locationid = locationid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalspot() {
        return totalspot;
    }

    public int getAvailablespot() {
        return availablespot;
    }

    public int getPrice() {
        return price;
    }

    public int getLocationid() {
        return locationid;
    }

    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude;}
}
