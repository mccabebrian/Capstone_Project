package brianmccabe.coffeenow.models;

import java.io.Serializable;

/**
 * Created by brian on 29/01/2017.
 */

public class Opening_hours implements Serializable {
    private String open_now;

    private String[] weekday_text;

    public String getOpen_now ()
    {
        return open_now;
    }

    public void setOpen_now (String open_now)
    {
        this.open_now = open_now;
    }

    public String[] getWeekday_text ()
    {
        return weekday_text;
    }

    public void setWeekday_text (String[] weekday_text)
    {
        this.weekday_text = weekday_text;
    }
}
