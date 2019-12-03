package com.protoenergy.proproduction.common;

public class Constants {
    // web service url constants
    public class URLs {
        //public static final String BASE_URL = "http://sync.protoenergy.com/api/";
        public static final String BASE_URL = "http://192.168.100.15/protoapi/api/";

        public static final String LOGIN = BASE_URL +"production/login";

        public static final String GET_ORDERNUMBERS = BASE_URL +"production/ordernumbers";
        public static final String URL_UPLOADMAKER = BASE_URL +"production/qrmaker";
        public static final String URL_UPLOADCHECKER = BASE_URL +"production/qrchecker";
        public static final String URL_GETCHECKER = BASE_URL +"production/validateqr";
        public static final String URL_UPDATECHECKER = BASE_URL +"production/editqr";

    }

    // webservice key constants
    public class Params {
        public static final String KEY_STATUS = "success";
        public static final String KEY_MESSAGE = "message";

        public static final String OBJ = "ProductionLoginData";
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

