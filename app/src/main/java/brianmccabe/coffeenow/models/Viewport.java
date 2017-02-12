package brianmccabe.coffeenow.models;

import java.io.Serializable;

/**
 * Created by brian on 29/01/2017.
 */

public class Viewport implements Serializable {
    private Southwest southwest;

    private Northeast northeast;

    public Southwest getSouthwest ()
    {
        return southwest;
    }

    public void setSouthwest (Southwest southwest)
    {
        this.southwest = southwest;
    }

    public Northeast getNortheast ()
    {
        return northeast;
    }

    public void setNortheast (Northeast northeast)
    {
        this.northeast = northeast;
    }

}
