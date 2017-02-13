package brianmccabe.coffeenow.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.adapters.MenuItemAdapter;
import brianmccabe.coffeenow.data.CoffeeNowContentProvider;
import brianmccabe.coffeenow.data.DatabaseHandler;
import brianmccabe.coffeenow.models.Coffee;


public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gv;
        TextView tv;
        FloatingActionButton cartButton;

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        // Inflate the layout for this fragment
        DatabaseHandler db = new DatabaseHandler(getContext());


        String URL = CoffeeNowContentProvider.URL;

        Uri coffee = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(coffee, null, null, null, getContext().getString(R.string.content_provider_name));
        List<Coffee> coffees = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    byte[] bytes;
                    String name = c.getString(c.getColumnIndex(CoffeeNowContentProvider.name));

                    bytes = c.getBlob(c.getColumnIndex(CoffeeNowContentProvider.icon));

                    String price = c.getString(c.getColumnIndex(CoffeeNowContentProvider.price));

                    Coffee coffee1 = new Coffee(name, bytes, price);
                    coffees.add(coffee1);
                } while (c.moveToNext());
            }

            c.close();
        }

        if (coffees.size() == 0) {
            return view;
        }
        tv = (TextView) view.findViewById(R.id.no_fav_text);
        gv = (GridView) view.findViewById(R.id.gridview);
        tv.setVisibility(View.GONE);
        cartButton = (FloatingActionButton) view.findViewById(R.id.cart_button);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShoppingCart.class);
                startActivity(intent);
            }
        });

        gv.setVisibility(View.VISIBLE);
        gv.setAdapter(new MenuItemAdapter(getContext(), coffees));
        return view;
    }
}
