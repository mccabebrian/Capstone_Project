package brianmccabe.coffeenow.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import brianmccabe.coffeenow.factory.WidgetRemoteViewsFactory;

/**
 * Created by brian on 12/02/2017.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}