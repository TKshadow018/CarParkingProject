package a3labgo.tusar.carparkingproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tusar on 7/1/2018.
 */

public interface Api1 {
    String BASE_URL = "http://tusar.ml/"; //address of your localhost
    @GET("http://tusar.ml/json/regjson.php")
    Call<List<Model>> getModel(@Query("name") String a,@Query("email") String b, @Query("password") String c);
}
