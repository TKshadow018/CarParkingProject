package a3labgo.tusar.carparkingproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tusar on 7/4/2018.
 */

public interface Api6 {
    String BASE_URL = "http://tusar.ml/"; //address of your localhost
    @GET("http://tusar.ml/json/opengate.php")//have to change model
    Call<List<Model3>> getModel(@Query("id") int id);

}