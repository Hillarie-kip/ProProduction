package com.protoenergy.proproduction.user;
/**
 * Created by hillarie on 12/7/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import static com.protoenergy.proproduction.common.Constants.Params.AREAID;
import static com.protoenergy.proproduction.common.Constants.Params.AREANAME;
import static com.protoenergy.proproduction.common.Constants.Params.USERID;
import static com.protoenergy.proproduction.common.Constants.Params.FNAME;
import static com.protoenergy.proproduction.common.Constants.Params.PHONE;
import static com.protoenergy.proproduction.common.Constants.Params.WORKID;
import static com.protoenergy.proproduction.common.Constants.Params.WORKTYPE;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("driverInfo", Context.MODE_PRIVATE);
        this.context = context;
    }


    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }


    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
        edit.clear();

    }

    public void putUserID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(USERID, loginorout);
        edit.commit();
    }
    public String getUserID() {
        return app_prefs.getString(USERID, "");

    }


    public void putAreaName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(AREANAME, loginorout);
        edit.commit();
    }
    public String getAreaName() {
        return app_prefs.getString(AREANAME, "");
    }
    public void putAreaID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(AREAID, loginorout);
        edit.commit();
    }
    public String getAreaID() {
        return app_prefs.getString(AREAID, "");
    }


    public void putFullName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(FNAME, loginorout);
        edit.commit();
    }
    public String getFullName() {
        return app_prefs.getString(FNAME, "");
    }

    public void putPhone(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PHONE, loginorout);
        edit.commit();
    }
    public String getPHONE() {
        return app_prefs.getString(PHONE, "");
    }

    public void putWorkType(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(WORKTYPE, loginorout);
        edit.commit();
    }
    public String getWorkType() {
        return app_prefs.getString(WORKTYPE, "");

    }

    public void putWorkID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(WORKID, loginorout);
        edit.commit();
    }
    public String getWorkID() {
        return app_prefs.getString(WORKID, "");

    }




}
