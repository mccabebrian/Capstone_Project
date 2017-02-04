package brianmccabe.coffeenow.ui;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import brianmccabe.coffeenow.location.LocationTracker;
import brianmccabe.coffeenow.models.PlacesResponse;
import brianmccabe.coffeenow.models.Results;
import brianmccabe.coffeenow.rest.ApiClient;
import brianmccabe.coffeenow.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by brian on 28/01/2017.
 */

class MainActivityPresenterImpl {
    private MainActivityPresenter mainActivityPresenter;

    void init(Context context) {
        mainActivityPresenter = (MainActivity) context;
        mainActivityPresenter.showLoader();
        LocationTracker locationTracker = new LocationTracker(context);
        locationTracker.getLocation();

    }

    public void retrievePlaces(Location location) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String coordinates = location.getLatitude() + "," + location.getLongitude();
        Call<PlacesResponse> call = apiService.getNearbyPlaces(coordinates, "2500", "cafe", "AIzaSyCC7-iGM8WNtePjBF_FMjEHi1YV7O5pClw");
        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse>call, Response<PlacesResponse> response) {
                Results[] places = response.body().getResults();
                mainActivityPresenter.onReceivedResults(places);
                mainActivityPresenter.hideLoader();
            }

            @Override
            public void onFailure(Call<PlacesResponse>call, Throwable t) {
                Log.e(TAG, t.toString());
                mainActivityPresenter.hideLoader();
            }
        });
    }
}
