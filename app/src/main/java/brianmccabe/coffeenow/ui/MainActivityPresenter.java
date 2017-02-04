package brianmccabe.coffeenow.ui;

import android.location.Location;

import brianmccabe.coffeenow.models.Results;

/**
 * Created by brian on 28/01/2017.
 */

public interface MainActivityPresenter {
    void onLocationChanged(Location location);
    void onReceivedResults(Results[] results);
    void showLoader();
    void hideLoader();
}
