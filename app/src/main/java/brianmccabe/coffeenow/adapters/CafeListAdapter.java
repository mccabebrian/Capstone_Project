package brianmccabe.coffeenow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.models.Results;

/**
 * Created by brian on 04/02/2017.
 */

public class CafeListAdapter extends ArrayAdapter<Results> implements View.OnClickListener{

    private Results[] results;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
    }

    public CafeListAdapter(Results[] results, Context context) {
        super(context, R.layout.fragment_cafelist, results);
        this.results = results;
        this.mContext=context;

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
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.txtName.setText(dataModel.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
