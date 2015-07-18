package com.kkroegeraraustech.farmingwithandroid.extras.Soil_Review_Support;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Child;

import java.util.ArrayList;

/**
 * Created by Ken Heron Systems on 6/22/2015.
 */
public class ExpandableListItems_Parent_SoilOverall {

    private String name = "DEFAULT";
    private String text1 = "DEFAULT";
    private String text2 = "DEFAULT";
    private String checkedtype = "DEFAULT";
    private boolean checked = false;

    // ArrayList to store child objects
    private ArrayList<ExpandableListItems_Child> children = new ArrayList<ExpandableListItems_Child>();

    //get and set of the group
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getText1()
    {
        return text1;
    }

    public void setText1(String text1)
    {
        this.text1 = text1;
    }

    public String getText2()
    {
        return text2;
    }

    public void setText2(String text2)
    {
        this.text2 = text2;
    }


    //get and set of the children
    public String getNameChildren(int childPosition)
    {
        ExpandableListItems_Child tempChild = children.get(childPosition);
        return(tempChild.getName());
    }

    public void setNameChildren(String name,int childPosition)
    {
        ExpandableListItems_Child tempChild = children.get(childPosition);
        tempChild.setName(name);
        children.set(childPosition,tempChild);
    }

    public String getText1Children(int childPosition)
    {
        ExpandableListItems_Child tempChild = children.get(childPosition);
        return(tempChild.getText1());
    }

    public void setText1Children(String text1,int childPosition)
    {
        ExpandableListItems_Child tempChild = children.get(childPosition);
        tempChild.setText1(text1);
        children.set(childPosition,tempChild);
    }

    public String getText2Children(int childPosition)
    {
        ExpandableListItems_Child tempChild = children.get(childPosition);
        return(tempChild.getText2());
    }

    public void setText2Children(String text2,int childPosition)
    {
        ExpandableListItems_Child tempChild = children.get(childPosition);
        tempChild.setText2(text2);
        children.set(childPosition,tempChild);
    }




    public String getCheckedType()
    {
        return checkedtype;
    }

    public void setCheckedType(String checkedtype)
    {
        this.checkedtype = checkedtype;
    }

    public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    // ArrayList to store child objects
    public ArrayList<ExpandableListItems_Child> getChildren()
    {
        return children;
    }


    public ExpandableListItems_Child getChild(int position) {
        return(this.children.get(position));
    }

    public void setChildren(ArrayList<ExpandableListItems_Child> children)
    {
        this.children = children;
    }

    public void addChild(ExpandableListItems_Child childItem){
        this.children.add(childItem);
    }

    public int getChildrenSize(){
        return(this.children.size());
    }
}
