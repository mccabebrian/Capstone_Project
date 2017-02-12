package brianmccabe.coffeenow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.adapters.CafeListAdapter;
import brianmccabe.coffeenow.models.Results;

public class CafeListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    ListView list;
    CafeListAdapter cafeListAdapter;

    public static final String SHOP_NAME_KEY = "shopName";

    public CafeListFragment() {
    }

    @SuppressWarnings("unused")
    public static CafeListFragment newInstance(int columnCount) {
        CafeListFragment fragment = new CafeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafelist_list, container, false);
        final Results[] results = (Results[]) getArguments().getSerializable("name");

        list = (ListView) view.findViewById(R.id.list);
        cafeListAdapter = new CafeListAdapter(results, getContext());
        list.setAdapter(cafeListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CoffeeMenuActivity.class);
                intent.putExtra(SHOP_NAME_KEY, results[position].getName());
                startActivity(intent);
            }
        });
        return view;
    }
}
