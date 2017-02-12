package brianmccabe.coffeenow.models;


import java.io.Serializable;

/**
 * Created by brian on 29/01/2017.
 */

public class Geometry implements Serializable {
    private Viewport viewport;

    private Location location;

    public Viewport getViewport ()
    {
        return viewport;
    }

    public void setViewport (Viewport viewport)
    {
        this.viewport = viewport;
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }
}
