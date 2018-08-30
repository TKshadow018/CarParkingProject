package a3labgo.tusar.carparkingproject;

import java.util.List;
import a3labgo.tusar.carparkingproject.Model;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tusar on 11/12/2017.
 */

public interface Api {
    String BASE_URL = "http://tusar.ml/"; //address of your localhost
    @GET("http://tusar.ml/json/loginjson.php")
    Call<List<Model>> getModel(@Query("email") String em,@Query("password") String ps);
}
