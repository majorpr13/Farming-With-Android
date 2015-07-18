package com.kkroegeraraustech.farmingwithandroid.helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Ken Heron Systems on 6/26/2015.
 * This helper class holds date information of the constructor
 */
public class DateHelper {

    TimeZone mTimeZone;
    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 0;
    private int mHour = 0;
    private int mMin = 0;
    private int mSec = 0;
    private int mWeek = 0;
    private GregorianCalendar mDateStorage = new GregorianCalendar();

    void DateHelper() {
        updateCurrentTime();
    }

    void updateCurrentTime() {
        mDateStorage.setGregorianChange(mDateStorage.getTime());
        mYear = mDateStorage.get(Calendar.YEAR);
        mWeek = mDateStorage.get(Calendar.WEEK_OF_YEAR);
        mMonth = mDateStorage.get(Calendar.MONTH);
        mDay = mDateStorage.get(Calendar.DAY_OF_MONTH);
        mHour = mDateStorage.get(Calendar.HOUR);
        mMin = mDateStorage.get(Calendar.MINUTE);
        mSec = mDateStorage.get(Calendar.SECOND);
        mTimeZone = mDateStorage.getTimeZone();
    }

    Date getDateStructure(){
        Date tmpVar = mDateStorage.getTime();
        return(tmpVar);
    }

    int getYear() {
        int tmpVar = mYear;
        return(tmpVar);
    }

    int getWeek() {
        int tmpVar = mWeek;
        return(tmpVar);
    }

    int getMonth() {
        int tmpVar = mMonth;
        return(tmpVar);
    }

    int getDay() {
        int tmpVar = mDay;
        return(tmpVar);
    }

    int getHour() {
        int tmpVar = mHour;
        return(tmpVar);
    }

    int getMin() {
        int tmpVar = mMin;
        return(tmpVar);
    }

    int getSec() {
        int tmpVar = mSec;
        return(tmpVar);
    }



}
