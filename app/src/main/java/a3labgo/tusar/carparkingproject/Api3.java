package a3labgo.tusar.carparkingproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tusar on 6/24/2018.
 */

public interface Api3 {
    String BASE_URL = "http://tusar.ml/"; //address of your localhost
    @GET("http://tusar.ml/json/returnparking2.php")
    Call<List<Model2>> getModel(@Query("name") String name);
}
