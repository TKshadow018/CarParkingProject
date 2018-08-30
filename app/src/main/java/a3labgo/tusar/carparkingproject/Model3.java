package a3labgo.tusar.carparkingproject;

/**
 * Created by Tusar on 6/25/2018.
 */

public class Model3 {
    private int id;
    private int userid;
    private int parkingid;
    private String starttime;
    private int currentstate;
    private int confirmcode;

    public Model3(int id, int userid, int parkingid, String starttime, int currentstate, int confirmcode) {
        this.id = id;
        this.userid = userid;
        this.parkingid = parkingid;
        this.starttime = starttime;
        this.currentstate = currentstate;
        this.confirmcode = confirmcode;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public int getParkingid() {
        return parkingid;
    }

    public String getStarttime() {
        return starttime;
    }

    public int getCurrentstate() {
        return currentstate;
    }

    public int getConfirmcode() {
        return confirmcode;
    }


}
