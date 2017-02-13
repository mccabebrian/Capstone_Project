package brianmccabe.coffeenow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.models.Opening_hours;
import brianmccabe.coffeenow.models.Results;
import brianmccabe.coffeenow.ui.CoffeeMenuActivity;

/**
 * Created by brian on 04/02/2017.
 */

public class CafeListAdapter extends ArrayAdapter<Results> implements View.OnClickListener {

    private Results[] results;
    Context mContext;
    TextView title;
    TextView locationText;
    ImageView cafeImage;
    RatingBar ratingBar;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
    }

    public CafeListAdapter(Results[] results, Context context) {
        super(context, R.layout.fragment_cafelist, results);
        this.results = results;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Results dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_cafelist, parent, false);
            title = (TextView) convertView.findViewById(R.id.cafe_shop_title);
            locationText = (TextView) convertView.findViewById(R.id.location_text);
            cafeImage = (ImageView) convertView.findViewById(R.id.cafe_img);
            ratingBar = (RatingBar) convertView.findViewById(R.id.rating);
            convertView.setTag(viewHolder);
        }

        title.setText(dataModel.getName());
        locationText.setText(dataModel.getVicinity());

        Picasso.with(convertView.getContext())
                .load(dataModel.getIcon())
                .into(cafeImage);

        String rating = dataModel.getRating();
        if (rating == null) {
            ratingBar.setVisibility(View.GONE);
        } else {
            ratingBar.setRating(Float.parseFloat(dataModel.getRating()));
        }
        return convertView;
    }
}
