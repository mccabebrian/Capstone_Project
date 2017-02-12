package brianmccabe.coffeenow.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.data.DatabaseHandler;
import brianmccabe.coffeenow.models.Coffee;
/**
 * Created by brian on 11/02/2017.
 */

public class MenuItemAdapter extends BaseAdapter {

    List<Coffee> coffeeList;
    Context context;
    private static LayoutInflater inflater=null;

    public MenuItemAdapter(Context context, List<Coffee> list) {
        this.coffeeList = list;
        this.context=context;
        inflater = ( LayoutInflater )context.
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
        titleText =(TextView) rowView.findViewById(R.id.coffee_name);
        coffeeImage =(ImageView) rowView.findViewById(R.id.coffee_img);
        favoritesButton = (Button) rowView.findViewById(R.id.fav_button);
        addToCartButton = (Button) rowView.findViewById(R.id.cart_button);

        final DatabaseHandler db = new DatabaseHandler(context);

        titleText.setText(coffeeList.get(position).getName());
        coffeeImage.setImageBitmap(bmp);

        if(db.getCoffee(coffeeList.get(position).getName()) != null) {
            favoritesButton.setVisibility(View.GONE);
            return rowView;
        }

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addCoffee(coffeeList.get(position));
                favoritesButton.setVisibility(View.GONE);
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addToCart(coffeeList.get(position));
                addToCartButton.setVisibility(View.GONE);
            }
        });

        if(db.getCoffeeFromCart(coffeeList.get(position).getName()) != null) {
            addToCartButton.setEnabled(false);
            return rowView;
        }

        return rowView;
    }
}
