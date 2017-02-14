package brianmccabe.coffeenow.ui;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
import brianmccabe.coffeenow.ui.ShoppingCart;


public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    GridView gv;
    TextView tv;
    FloatingActionButton cartButton;
    private static final int LOADER_ID = 1;

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



        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        // Inflate the layout for this fragment

        tv = (TextView) view.findViewById(R.id.no_fav_text);
        gv = (GridView) view.findViewById(R.id.gridview);

        cartButton = (FloatingActionButton) view.findViewById(R.id.cart_button);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        return view;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String URL = CoffeeNowContentProvider.URL;

        Uri coffee = Uri.parse(URL);

        return new android.support.v4.content.CursorLoader(getContext(), coffee,
                null, null, null, "name");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor c) {
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

        if (coffees.size() != 0) {

            tv.setVisibility(View.GONE);


            cartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ShoppingCart.class);
                    startActivity(intent);
                }
            });

            gv.setVisibility(View.VISIBLE);
            gv.setAdapter(new MenuItemAdapter(getContext(), coffees));
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }
}