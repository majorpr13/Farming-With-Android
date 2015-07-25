package com.kkroegeraraustech.farmingwithandroid.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.activities.ActivityReviewField;
import com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper.SQLHelper_Fields;
import com.kkroegeraraustech.farmingwithandroid.helpers.FieldHelper;

/**
 * Created by Ken Heron Systems on 7/14/2015.
 */
public class NewFieldFragment extends Fragment implements View.OnClickListener {


    private static String TAB_NAME = "FIELD DESCRIPTION";

    public String getTabName(){
        return (TAB_NAME);
    }

    private static View mLayout;
    Intent mIntent;
    private OnFragmentInteractionListener mListener;
    Spinner mSpinnerFieldType;
    private FieldHelper mProposedField = new FieldHelper();
    private FragmentActivity mContext;

    private EditText mTextFieldName;

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
        mContext = (FragmentActivity)activity;
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
        mLayout  = inflater.inflate(R.layout.fragment_new_field, container, false);
        View layout = mLayout;

        mSpinnerFieldType = (Spinner)mLayout.findViewById(R.id.spinner_FieldType);

        ArrayAdapter<String> adapterCropType = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,mProposedField.valuesCrop){
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTextColor(Color.BLACK);
                v.setBackgroundColor(Color.WHITE);
                v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                v.setTextSize(35);
                return v;
            }

            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTextColor(Color.BLACK);
                v.setBackgroundColor(Color.WHITE);
                v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                v.setTextSize(35);
                return v;
            }
        };

// Specify the layout to use when the list of choices appears
        adapterCropType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        mSpinnerFieldType.setAdapter(adapterCropType);

        mTextFieldName = (EditText)layout.findViewById(R.id.textNewFieldName);
        mTextFieldName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpString = s.toString();
                //TODO Check field validity here
                mProposedField.setFieldName(tmpString);
            }
        });


        Button button_SAVE = (Button)layout.findViewById(R.id.button_FieldNew_Save);
        Button button_BACK = (Button)layout.findViewById(R.id.button_FieldNew_Back);
        button_SAVE.setOnClickListener(this);
        button_BACK.setOnClickListener(this);

        return layout;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Log.d("View ID", Integer.toString(v.getId()));
        switch(v.getId()) {
            case R.id.button_FieldNew_Save:
                //saveButtonProcess();
                break;
        }
    }

    private void saveButtonProcess(){
        //TODO Check if Field already exists
        SQLHelper_Fields db = new SQLHelper_Fields(this.getActivity());
        db.addField(mProposedField);
        mIntent = new Intent(getActivity().getApplicationContext(), ActivityReviewField.class);
        startActivity(mIntent);

    }

}
