package com.kkroegeraraustech.farmingwithandroid.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.activities.ActivityGuidance;
import com.kkroegeraraustech.farmingwithandroid.activities.ActivityNewField;
import com.kkroegeraraustech.farmingwithandroid.activities.ActivityReviewField;
import com.kkroegeraraustech.farmingwithandroid.adapters.Review_ExpandableListAdapter;
import com.kkroegeraraustech.farmingwithandroid.adapters.UserItemsAdapter;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Child;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Parent;

import java.util.ArrayList;

/**
 * Created by Ken Heron Systems on 6/6/2015.
 * This class handles the main launch window
 */
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainLaunchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainLaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainLaunchFragment extends Fragment implements View.OnClickListener {

    private View layout;
    Intent mIntent;
    UserItemsAdapter mAdapterListView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainLaunchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainLaunchFragment newInstance(String param1, String param2) {
        MainLaunchFragment fragment = new MainLaunchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainLaunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("View ID","On Create View");
        layout = inflater.inflate(R.layout.fragment_useritems,container,false);

        /*
        layout = inflater.inflate(R.layout.fragment_useritems,container,false);
        ExpandableListView listTemp = (ExpandableListView)layout.findViewById(R.id.listView);
        ArrayList<ExpandableListItems_Parent> groups = prepareData();
        Review_ExpandableListAdapter adapterUserItems = new Review_ExpandableListAdapter(getActivity(), groups);
        listTemp.setAdapter(adapterUserItems);
        */
        //for main launch screen
        //layout = inflater.inflate(R.layout.main_intro_screen, container, false);

        /*
        //Handle the buttons on the screen
        Button button_addITEM = (Button)layout.findViewById(R.id.button_addITEM);
        Button button_revITEM = (Button)layout.findViewById(R.id.button_revITEM);
        Button button_SYNC = (Button)layout.findViewById(R.id.button_SYNC);
        Button button_CAMERA = (Button)layout.findViewById(R.id.button_CAMERA);
        Button button_GUIDANCE = (Button)layout.findViewById(R.id.button_GUIDANCE);

        button_addITEM.setOnClickListener(this);
        button_revITEM.setOnClickListener(this);
        button_SYNC.setOnClickListener(this);
        button_CAMERA.setOnClickListener(this);
        button_GUIDANCE.setOnClickListener(this);
        */
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableListView listTemp = (ExpandableListView)layout.findViewById(R.id.listView);
        ArrayList<ExpandableListItems_Parent> groups = prepareData();
        Review_ExpandableListAdapter adapterUserItems = new Review_ExpandableListAdapter(getActivity(), groups);
        listTemp.setAdapter(adapterUserItems);
        listTemp.setGroupIndicator(null);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Log.d("View ID",Integer.toString(v.getId()));
        switch(v.getId()) {
            case R.id.button_addITEM:
                mIntent = new Intent(getActivity().getApplicationContext(), ActivityNewField.class);
                startActivity(mIntent);
                break;
            case R.id.button_revITEM:
                mIntent = new Intent(getActivity().getApplicationContext(), ActivityReviewField.class);
                startActivity(mIntent);
                break;
            case R.id.button_SYNC:
                break;
            case R.id.button_CAMERA:
                break;
            case R.id.button_GUIDANCE:
                mIntent = new Intent(getActivity().getApplicationContext(), ActivityGuidance.class);
                startActivity(mIntent);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public ArrayList<ExpandableListItems_Parent> prepareData() {

        ExpandableListItems_Parent group1 = new ExpandableListItems_Parent();
        group1.setName("John Doe");
        ExpandableListItems_Child tmpChild = new ExpandableListItems_Child();
        tmpChild.setName("john.doe@gmail.com");
        group1.addChild(tmpChild);
        group1.addChild(tmpChild);
        group1.setText1("Text One");

        ExpandableListItems_Parent group2 = new ExpandableListItems_Parent();
        group2.setName("Julio Boes");
        tmpChild = new ExpandableListItems_Child();
        tmpChild.setName("julio.boes@gmail.com");
        group2.addChild(tmpChild);
        group2.addChild(tmpChild);


        ExpandableListItems_Parent group3 = new ExpandableListItems_Parent();
        group3.setName("Ron Osmun");
        tmpChild = new ExpandableListItems_Child();
        tmpChild.setName("ron.osmun@gmail.com");
        group3.addChild(tmpChild);
        group3.addChild(tmpChild);

        ExpandableListItems_Parent group4 = new ExpandableListItems_Parent();
        group4.setName("Angelica Tebbs");
        tmpChild = new ExpandableListItems_Child();
        tmpChild.setName("angelica.tebbs@gmail.com");
        group4.addChild(tmpChild);
        group4.addChild(tmpChild);

        ExpandableListItems_Parent group5 = new ExpandableListItems_Parent();
        group5.setName("Temika Benning");
        tmpChild = new ExpandableListItems_Child();
        tmpChild.setName("temika.benning@gmail.com");
        group5.addChild(tmpChild);
        group5.addChild(tmpChild);


        ArrayList<ExpandableListItems_Parent> groups = new ArrayList<ExpandableListItems_Parent>();
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);
        groups.add(group5);

        return groups;
    }

}
