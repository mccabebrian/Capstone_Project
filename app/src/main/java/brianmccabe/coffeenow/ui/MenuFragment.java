package brianmccabe.coffeenow.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.adapters.MenuItemAdapter;
import brianmccabe.coffeenow.models.temp;

public class MenuFragment extends Fragment {
    GridView gv;

    private static final String AMERICANO_KEY = "Americano";
    private static final String CAPPUCCINO_KEY = "cappuccino";
    private static final String MOCHA_KEY = "mocha";
    private static final String ESPRESSO_KEY = "espresso";
    private static final String MACHIATTO_KEY = "machiatto";
    private static final String LATE_KEY = "late";
    private static final String BREVE_KEY = "breve";

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        FloatingActionButton cartButton;

        List<temp> coffees = new ArrayList<>();
        coffees.add(new temp(AMERICANO_KEY, getByteFromDrawable(R.drawable.americano), "1.20"));
        coffees.add(new temp(MACHIATTO_KEY, getByteFromDrawable(R.drawable.machiatto), "2.20"));
        coffees.add(new temp(LATE_KEY, getByteFromDrawable(R.drawable.late), "2.30"));
        coffees.add(new temp(ESPRESSO_KEY, getByteFromDrawable(R.drawable.espresso), "1.70"));
        coffees.add(new temp(MOCHA_KEY, getByteFromDrawable(R.drawable.mocha), "1.90"));
        coffees.add(new temp(CAPPUCCINO_KEY, getByteFromDrawable(R.drawable.cappuccino), "2.80"));
        coffees.add(new temp(BREVE_KEY, getByteFromDrawable(R.drawable.breve), "3.20"));

        gv = (GridView) view.findViewById(R.id.gridview);
        cartButton = (FloatingActionButton) view.findViewById(R.id.cart_button);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShoppingCart.class);
                startActivity(intent);
            }
        });
        gv.setAdapter(new MenuItemAdapter(getContext(), coffees));
        return view;
    }

    private byte[] getByteFromDrawable(int drawable) {
        byte[] byteArray;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

        return byteArray;
    }
}
