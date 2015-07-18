package com.kkroegeraraustech.farmingwithandroid.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkroegeraraustech.farmingwithandroid.R;

/**
 * Created by Ken Heron Systems on 7/14/2015.
 */
public class NewFieldFragment extends Fragment {
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
    public static NewFieldFragment newInstance(String param1, String param2) {
        NewFieldFragment fragment = new NewFieldFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static NewFieldFragment getInstance(int position) {
        NewFieldFragment fragmentDummy = new NewFieldFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentDummy.setArguments(args);
        return fragmentDummy;
    }

    public NewFieldFragment() {
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
        View layout = inflater.inflate(R.layout.activity_intro_screen, container, false);
        /*
        textView = (TextView) layout.findViewById(R.id.position);
        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText("The Page Selected Is " + bundle.getInt("position"));
            mCompassImage = (ImageView)layout.findViewById(R.id.compassPointer);
            setDirection(45.0f);
        }
        */
        return layout;
    }

/**
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
    //public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    //  public void onFragmentInteraction(Uri uri);
    //}

}
