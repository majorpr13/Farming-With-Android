package com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support;

/**
 * Created by Ken Heron Systems on 6/22/2015.
 */
public class ExpandableListItems_Child {

    private String name = "DEFAULT";
    private String text1 = "DEFAULT";
    private String text2 = "DEFAULT";

    public void setAll(String Name, String Text1, String Text2){
        this.name = Name;
        this.text1 = Text1;
        this.text2 = Text2;
    }

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

}
