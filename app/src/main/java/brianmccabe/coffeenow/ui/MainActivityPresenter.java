package brianmccabe.coffeenow.ui;

import android.location.Location;

/**
 * Created by brian on 28/01/2017.
 */

public interface MainActivityPresenter {
    void onLocationChanged(Location location);
}
