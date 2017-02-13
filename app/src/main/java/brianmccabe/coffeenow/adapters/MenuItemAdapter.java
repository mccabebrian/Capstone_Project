package brianmccabe.coffeenow.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.data.CoffeeNowContentProvider;
import brianmccabe.coffeenow.data.DatabaseHandler;
import brianmccabe.coffeenow.models.Coffee;
import retrofit2.http.Url;

/**
 * Created by brian on 11/02/2017.
 */

public class MenuItemAdapter extends BaseAdapter {

    List<Coffee> coffeeList;
    Context context;
    private static LayoutInflater inflater = null;

    public MenuItemAdapter(Context context, List<Coffee> list) {
        this.coffeeList = list;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return coffeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        TextView titleText;
        ImageView coffeeImage;
        final Button favoritesButton;
        final Button addToCartButton;

        byte[] image = coffeeList.get(position).getCoffeeImage();

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

        rowView = inflater.inflate(R.layout.grid_item, null);
        titleText = (TextView) rowView.findViewById(R.id.coffee_name);
        coffeeImage = (ImageView) rowView.findViewById(R.id.coffee_img);
        favoritesButton = (Button) rowView.findViewById(R.id.fav_button);
        addToCartButton = (Button) rowView.findViewById(R.id.cart_button);

        final DatabaseHandler db = new DatabaseHandler(context);

        titleText.setText(coffeeList.get(position).getName());
        coffeeImage.setImageBitmap(bmp);


        String URL = CoffeeNowContentProvider.URL;

        Uri coffee = Uri.parse(URL);
        Cursor c = context.getContentResolver().query(coffee, null, null, null, context.getString(R.string.content_provider_name));
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex(CoffeeNowContentProvider.name));
                    if(coffeeList.get(position).getName().equals(name)) {
                        return rowView;
                    }
                } while (c.moveToNext());
            }

            c.close();
        }

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] image = coffeeList.get(position).getCoffeeImage();

                    ContentValues values = new ContentValues();
                    values.put(CoffeeNowContentProvider.name, coffeeList.get(position).getName());
                    values.put(CoffeeNowContentProvider.icon, image);
                    values.put(CoffeeNowContentProvider.price, coffeeList.get(position).getPrice());
                    context.getContentResolver().insert(CoffeeNowContentProvider.CONTENT_URI, values);
                    favoritesButton.setVisibility(View.GONE);

            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] image = coffeeList.get(position).getCoffeeImage();

                ContentValues values = new ContentValues();
                values.put(CoffeeNowContentProvider.name, coffeeList.get(position).getName());
                values.put(CoffeeNowContentProvider.icon, image);
                values.put(CoffeeNowContentProvider.price, coffeeList.get(position).getPrice());
                context.getContentResolver().insert(CoffeeNowContentProvider.CONTENT_URI_CART, values);
            }
        });

        String URLCart = CoffeeNowContentProvider.URL_CART;

        Uri coffeeCart = Uri.parse(URLCart);
        Cursor cursor = context.getContentResolver().query(coffeeCart, null, null, null, context.getString(R.string.content_provider_name));
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(CoffeeNowContentProvider.name));
                    if(coffeeList.get(position).getName().equals(name)) {
                        addToCartButton.setEnabled(false);
                        return rowView;
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return rowView;
    }
}
