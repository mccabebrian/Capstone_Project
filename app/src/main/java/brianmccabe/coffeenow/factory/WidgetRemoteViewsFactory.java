package brianmccabe.coffeenow.factory;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.data.CoffeeNowContentProvider;
import brianmccabe.coffeenow.data.DatabaseHandler;
import brianmccabe.coffeenow.models.Coffee;

/**
 * Created by brian on 12/02/2017.
 */

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private int appWidgetId;

    private List<Coffee> widgetList = new ArrayList<>();
    private DatabaseHandler db;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        db = new DatabaseHandler(this.context);

    }

    private void updateWidgetListView() {
        List<Coffee> coffeeList =new ArrayList<>();

        String URL = CoffeeNowContentProvider.URL;

        Uri coffee = Uri.parse(URL);
        Cursor c = context.getContentResolver().query(coffee, null, null, null, context.getString(R.string.content_provider_name));
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    byte[] bytes;
                    String name = c.getString(c.getColumnIndex(CoffeeNowContentProvider.name));

                    bytes = c.getBlob(c.getColumnIndex(CoffeeNowContentProvider.icon));

                    String price = c.getString(c.getColumnIndex(CoffeeNowContentProvider.price));

                    Coffee coffee1 = new Coffee(name, bytes, price);
                    coffeeList.add(coffee1);
                } while (c.moveToNext());
            }

            c.close();
        }


        this.widgetList = coffeeList;
    }

    @Override
    public int getCount() {
        return widgetList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.listview_row_item);

        if (widgetList.size() == 0) {
            remoteView.setTextViewText(R.id.coffee_item, context.getString(R.string.no_favorites_text));
            return remoteView;
        }

        remoteView.setTextViewText(R.id.coffee_item, widgetList.get(position).getName());

        return remoteView;
    }

    @Override
    public int getViewTypeCount() {
        return widgetList.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onCreate() {
        updateWidgetListView();
    }

    @Override
    public void onDataSetChanged() {
        updateWidgetListView();
    }

    @Override
    public void onDestroy() {
        widgetList.clear();
        db.close();
    }
}