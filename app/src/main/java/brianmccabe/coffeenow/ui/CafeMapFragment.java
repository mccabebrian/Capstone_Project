package brianmccabe.coffeenow.ui;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.models.Results;

import static brianmccabe.coffeenow.ui.CafeListFragment.SHOP_NAME_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CafeMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CafeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CafeMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    MapView mapView;
    GoogleMap map;

    private OnFragmentInteractionListener mListener;

    public CafeMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CafeMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CafeMapFragment newInstance(String param1, String param2) {
        CafeMapFragment fragment = new CafeMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cafe_map, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        Results[] results = (Results[]) getArguments().getSerializable("name");
        for(int i =0; i<results.length; i++) {
            if(results[i].getGeometry() == null || results[i].getGeometry().getLocation() == null) {
                return;
            }
            googleMap.setOnInfoWindowClickListener(this);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Float.parseFloat(results[i].getGeometry().getLocation().getLat()),
                            Float.parseFloat(results[i].getGeometry().getLocation().getLng())))
                    .title(results[i].getName())
                    .snippet(results[i].getName()));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String name = marker.getTitle();
        Intent intent = new Intent(getContext(), CoffeeMenuActivity.class);
        intent.putExtra(SHOP_NAME_KEY, name);
        startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
