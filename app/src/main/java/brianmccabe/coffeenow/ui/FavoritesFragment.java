package brianmccabe.coffeenow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.adapters.MenuItemAdapter;
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
        List<Coffee> coffees = db.getAllCoffees();

        if(coffees.size() == 0){
            return view;
        }
        tv = (TextView) view.findViewById(R.id.no_fav_text);
        gv=(GridView) view.findViewById(R.id.gridview);
            tv.setVisibility(View.GONE);
        cartButton =(FloatingActionButton) view.findViewById(R.id.cart_button);

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
