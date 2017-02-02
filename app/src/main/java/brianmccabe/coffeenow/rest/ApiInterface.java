package brianmccabe.coffeenow.rest;

import brianmccabe.coffeenow.models.PlacesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by brian on 29/01/2017.
 */

public interface ApiInterface {
    @GET("json")
    Call<PlacesResponse> getNearbyPlaces(@Query("location") String location, @Query("radius") String radius,
                                         @Query("type") String type, @Query("key") String key);
}
