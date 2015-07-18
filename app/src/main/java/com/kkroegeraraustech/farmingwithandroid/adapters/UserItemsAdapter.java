package com.kkroegeraraustech.farmingwithandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Child;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Parent;

import java.security.acl.Group;
import java.util.ArrayList;

public class UserItemsAdapter extends BaseExpandableListAdapter {

    private Activity mContext;
    private ArrayList<ExpandableListItems_Parent>  mGroups;
    private LayoutInflater mInflater;

    public UserItemsAdapter(Activity context, ArrayList<ExpandableListItems_Parent> groups) {
        mContext = context;
        mGroups = groups;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).getChildrenSize();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).getChild(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.useritems_grouplayout, null);
        }

        // Get the group item
        ExpandableListItems_Parent group = (ExpandableListItems_Parent) getGroup(groupPosition);

        // Set group name
        TextView textView = (TextView) convertView.findViewById(R.id.textView1);
        textView.setText(group.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.useritems_rowlayout, null);
        }

        // Get child name
        ExpandableListItems_Child tmpChild = (ExpandableListItems_Child) getChild(groupPosition, childPosition);

        String children = tmpChild.getName();

        // Set child name
        TextView text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
 
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, children, Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}