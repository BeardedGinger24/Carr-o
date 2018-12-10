package com.example.carr_o.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carr_o.HomeAdapter;
import com.example.carr_o.MainActivity;
import com.example.carr_o.R;
import com.example.carr_o.model.VINDecode;
import com.example.carr_o.utilities.NetworkUtils;
import com.example.carr_o.utilities.JsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeAdapter.ListItemClickListener {

    HomeAdapter mHomeAdapter;
    RecyclerView mCarInfo;
    ArrayList<VINDecode> carInfo;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mCarInfo = view.findViewById(R.id.recycler_view);
        carInfo = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(carInfo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mCarInfo.setLayoutManager(layoutManager);

        mCarInfo.setHasFixedSize(true);

//        makeNewsSearchQuery();

//        fetchStoreItems();

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

//    private void makeNewsSearchQuery(){
//        URL newsSearchUrl = NetworkUtils.buildUrl();
//        new VINQueryTask().execute(newsSearchUrl);
//    }
//
//    public class VINQueryTask extends AsyncTask<URL, Void, String>{
//       @Override
//       protected void onPreExecute() {
//           super.onPreExecute();
//       }
//
//       @Override
//       protected String doInBackground(URL... urls) {
//           URL searchUrl = urls[0];
//           String newsSearchResults = null;
//           try {
//               newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//           return newsSearchResults;
//       }
//
//       @Override
//       protected void onPostExecute(String newsSearchResults) {
//           super.onPostExecute(newsSearchResults);
//           carInfo = JsonUtils.parseJson(newsSearchResults);
//
//           mHomeAdapter = new HomeAdapter( carInfo);
//           mCarInfo.setAdapter(mHomeAdapter);
//           //String test = articles.get(0).getAuthor();
//           //mNewsJsonResults.setText(test);
//       }
//   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
