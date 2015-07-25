package com.kkroegeraraustech.farmingwithandroid.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.extras.Field_Review_Support.ExpandableListItems_Child_Fields;
import com.kkroegeraraustech.farmingwithandroid.extras.Field_Review_Support.ExpandableListItems_Parent_Fields;

import java.util.ArrayList;

/**
 * Created by Ken Heron Systems on 7/19/2015.
 */
public class Adapter_FieldExpandableList extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ExpandableListItems_Parent_Fields> groups;

    public Adapter_FieldExpandableList(Context context, ArrayList<ExpandableListItems_Parent_Fields> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ExpandableListItems_Child_Fields> chList = groups.get(groupPosition).getChildren();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ExpandableListItems_Child_Fields child = (ExpandableListItems_Child_Fields) getChild(groupPosition, childPosition);
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

        ArrayList<ExpandableListItems_Child_Fields> chList = groups.get(groupPosition).getChildren();
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
        ExpandableListItems_Parent_Fields group = (ExpandableListItems_Parent_Fields) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.fieldreview_grouprows, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text_fieldname);
        tv.setText(group.getName().toString());
        tv.setTextSize(18);
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

    /******************* Checkbox Checked Change Listener ********************/

    private final class CheckUpdateListener implements CompoundButton.OnCheckedChangeListener
    {
        private final ExpandableListItems_Parent_Fields parent;

        private CheckUpdateListener(ExpandableListItems_Parent_Fields parent)
        {
            this.parent = parent;
        }
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            Log.i("onCheckedChanged", "isChecked: " + isChecked);
            parent.setChecked(isChecked);
            final Boolean checked = parent.isChecked();
        }
    }
    /***********************************************************************/
}
