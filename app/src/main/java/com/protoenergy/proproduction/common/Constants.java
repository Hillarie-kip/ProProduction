package com.protoenergy.proproduction.common;

public class Constants {
    // web service url constants
    public class URLs {
        public static final String BASE_URL = "http://sync.protoenergy.com/api/";
        public static final String LOGIN = BASE_URL +"User/LoginProduction";
        public static final String GET_ORDERNUMBERS = BASE_URL +"production/ordernumbers";
       /* public static final String URL_UPLOADMAKER = BASE_URL +"production/qrmaker";
        public static final String URL_UPDATECHECKER = BASE_URL +"production/editqr";*/

        public static final String URL_UPLOADMAKERV2 = BASE_URL +"production/qrmakerv2";
        public static final String URL_UPDATECHECKERV2 = BASE_URL +"production/editqrv2";
        public static final String URL_VALIDATEQRV2 = BASE_URL +"production/validateqrv2?ProductionOrder=";


    }

    // webservice key constants
    public class Params {
        public static final String KEY_STATUS = "success";
        public static final String KEY_MESSAGE = "message";

        public static final String OBJ = "UserLoginData";
        public static final String USERID = "UserID";
        public static final String FNAME = "FullName";
        public static final String AREANAME = "AreaName";
        public static final String AREAID = "AreaID";
        public static final String WORKTYPE = "WorkType";
        public static final String WORKID= "WorkID";
        public static final String PHONE = "PhoneNumber";
        public static final String PASSWORD = "Password";


    }
}

