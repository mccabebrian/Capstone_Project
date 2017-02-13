package brianmccabe.coffeenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.models.temp;

/**
 * Created by brian on 12/02/2017.
 */

public class CartItemAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    List<temp> coffees;

    public CartItemAdapter(Context context, List<temp> coffeeList) {
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.coffees = coffeeList;
    }

    @Override
    public int getCount() {
        return coffees.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.cart_list_item, null);
        TextView tv = (TextView) rowView.findViewById(R.id.coffee_name);

        tv.setText(coffees.get(position).getName());

        return rowView;
    }
}
