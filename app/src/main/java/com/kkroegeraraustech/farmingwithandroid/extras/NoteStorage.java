package com.kkroegeraraustech.farmingwithandroid.extras;

/**
 * Created by Ken Heron Systems on 6/5/2015.
 */
public class NoteStorage {

    public enum enumSeason{
        FALL,
        SPRING,
        SUMMER,
        WINTER;
    }
    private int mYear;
    private enumSeason mSeason;
    private int mMonth;
    private int mDay;
    private String mNote;

    //default constructor
    public NoteStorage() {
        this.mYear = 2015;
        this.mMonth = 6;
        this.mDay = 5;
        this.mSeason = enumSeason.SUMMER;
    }

    //overloaded constructor
    public NoteStorage(int year, int month, int day, enumSeason season)
    {
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mSeason = season;
    }

    public void setDate(int year, int month, int day, enumSeason season) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mSeason = season;
    }

    public void getDate(int year, int month, int day, enumSeason season) {
        year = this.mYear;
        month = this.mMonth;
        day = this.mDay;
        season = mSeason;
    }
}
