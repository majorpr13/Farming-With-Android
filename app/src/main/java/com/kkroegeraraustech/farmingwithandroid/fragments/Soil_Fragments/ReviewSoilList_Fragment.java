package com.kkroegeraraustech.farmingwithandroid.fragments.Soil_Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.activities.ActivityNewField;
import com.kkroegeraraustech.farmingwithandroid.activities.MainActivity;
import com.kkroegeraraustech.farmingwithandroid.adapters.Adapter_FieldExpandableList;
import com.kkroegeraraustech.farmingwithandroid.adapters.Adapter_SoilsExpandableList;
import com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper.SQLHelper_Fields;
import com.kkroegeraraustech.farmingwithandroid.extras.Field_Review_Support.ExpandableListItems_Parent_Fields;
import com.kkroegeraraustech.farmingwithandroid.helpers.FieldHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken Heron Systems on 7/22/2015.
 */
public class ReviewSoilList_Fragment extends Fragment implements View.OnClickListener {

    private static View mLayout;
    Intent mIntent;
    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textView;

    private float mCurrentDegree = 0f;
    private ImageView mCompassImage;
    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DummyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewSoilList_Fragment newInstance(String param1, String param2) {
        ReviewSoilList_Fragment fragment = new ReviewSoilList_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ReviewSoilList_Fragment getInstance(int position) {
        ReviewSoilList_Fragment fragmentDummy = new ReviewSoilList_Fragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentDummy.setArguments(args);
        return fragmentDummy;
    }

    public ReviewSoilList_Fragment() {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayout  = inflater.inflate(R.layout.fragment_review_field, container, false);
        View layout = mLayout;

        Button button_ADDNEW = (Button)layout.findViewById(R.id.button_FieldReview_Add);
        Button button_BACK = (Button)layout.findViewById(R.id.button_FieldReview_Back);

        button_ADDNEW.setOnClickListener(this);
        button_BACK.setOnClickListener(this);

        return layout;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableListView listTemp = (ExpandableListView)mLayout.findViewById(R.id.expandableListView_fields);
        ArrayList<ExpandableListItems_Parent_Fields> groups = prepareData();
        Adapter_SoilsExpandableList adapterUserItems = new Adapter_SoilsExpandableList(getActivity(), groups);
        listTemp.setAdapter(adapterUserItems);
        listTemp.setGroupIndicator(null);

    }

    @Override
    public void onClick(final View v) {
        Log.d("View ID", Integer.toString(v.getId()));
        switch(v.getId()) {
            case R.id.button_FieldReview_Back:
                Log.d("ON CLICK", Integer.toString(v.getId()));
                mIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.button_FieldReview_Add:
                mIntent = new Intent(getActivity().getApplicationContext(), ActivityNewField.class);
                startActivity(mIntent);
                break;
        }
    }

    public ArrayList<ExpandableListItems_Parent_Fields> prepareData() {

        ArrayList<ExpandableListItems_Parent_Fields> groups = new ArrayList<ExpandableListItems_Parent_Fields>();


        ExpandableListItems_Parent_Fields group1;
        SQLHelper_Fields db = new SQLHelper_Fields(this.getActivity());
        List<FieldHelper> contacts = db.getAllFields();

        for (FieldHelper cn : contacts) {
            group1 = new ExpandableListItems_Parent_Fields();
            group1.setName(cn.getFieldName());
            groups.add(group1);
        }

        /*
        ExpandableListItems_Parent_Fields group1 = new ExpandableListItems_Parent_Fields();
        group1.setName("John Doe");

        ExpandableListItems_Parent_Fields group2 = new ExpandableListItems_Parent_Fields();
        group2.setName("Julio Boes");
        groups.add(group1);
        groups.add(group2);

        */

        return groups;
    }

}
