package co.pichak.interview.bahiraei.countertask.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bigoloo on 4/6/15.
 */
public class CounterTaskPreference {

    private final static String currentPrefStr = "CURRENT";
    private final static String logListPrefStr = "LIST";
    private static Context mContext;
    private final SharedPreferences.Editor prefsEditor;
    private SharedPreferences applicationPref;
    private static CounterTaskPreference currentCountPref, logListPref;

    private CounterTaskPreference(Context context, String name) {
        this.applicationPref = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        this.prefsEditor = applicationPref.edit();
    }

    public static CounterTaskPreference getCurrentCountPref(Context context) {
        mContext=context;
        if (currentCountPref == null) {

            currentCountPref = new CounterTaskPreference(context, currentPrefStr);
        }

        return currentCountPref;
    }

    public static CounterTaskPreference getLogListPref(Context context) {
        mContext=context;
        if (logListPref == null) {
            logListPref = new CounterTaskPreference(context, logListPrefStr);
        }
        return logListPref;
    }

    public int getCurrent(){
        return getCurrentCountPref(mContext).applicationPref.getInt(currentPrefStr,0);

    }

    public void saveCurrent( int current) {
        getCurrentCountPref(mContext).prefsEditor.putInt(currentPrefStr, current);
        prefsEditor.commit();
    }

    public void setCountToLog( int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        getLogListPref(mContext).prefsEditor.putInt(currentDateandTime, count);
        prefsEditor.commit();
    }

    public Map getAllLogCount() {


        Map<String, Integer> temp =(HashMap) getLogListPref(mContext).applicationPref.getAll();
        return( new TreeMap<String, Integer>(temp)).descendingMap();

    }

}

