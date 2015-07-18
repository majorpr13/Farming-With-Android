package com.kkroegeraraustech.farmingwithandroid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Child;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Parent;

import java.util.ArrayList;

/*
 * Created by Ken Heron Systems on 7/15/2015.
 */
/**
 * A Custom adapter to create Parent view (Used grouprow.xml) and Child View((Used childrow.xml).
 */
public class Review_ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ExpandableListItems_Parent> groups;

    public Review_ExpandableListAdapter(Context context, ArrayList<ExpandableListItems_Parent> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ExpandableListItems_Child> chList = groups.get(groupPosition).getChildren();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ExpandableListItems_Child child = (ExpandableListItems_Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fieldreview_childrows, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text1);
        //ImageView iv = (ImageView) convertView.findViewById(R.id.flag);

        tv.setText(child.getName().toString());
        //iv.setImageResource(child.getImage());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ExpandableListItems_Child> chList = groups.get(groupPosition).getChildren();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ExpandableListItems_Parent group = (ExpandableListItems_Parent) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.fieldreview_grouprows, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text_fieldname);
        tv.setText(group.getName().toString());
        //tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}