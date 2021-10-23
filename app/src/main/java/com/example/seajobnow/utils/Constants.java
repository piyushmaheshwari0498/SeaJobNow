package com.example.seajobnow.utils;

public class Constants {
     public static final String SHARED_PREFRENCE_NAME = "AppData";
     public static final String IS_LOGGED_IN = "login";
     public static final int RESPONSE_CODE = 200;// http response code
     public static final int RESPONSE_CODE1 = 201;// http response code
     public static final int RESPONSE_CODE_UNAUTHORISED = 203;// http response code
     public static final int RESPONSE_STATUS = 1;//server response code
     public static final int RESPONSE_ERROR = 500;


     public interface INTENT_KEYS {
          public  String KEY_COMPANY_ID = "company_id";
          public  String KEY_ACCOUNT_PERSON_NAME = "account_person_name";
          public  String KEY_COMPANY_NAME = "company_name";
          public  String KEY_COMPANY_CODE = "company_code";
          public  String KEY_ACCOUNT_STATUS = "company_status";
          public  String KEY_CITY_ID = "city_id";
          public  String KEY_STATE_ID = "state_id";
          public  String KEY_COUNTRY_ID = "country_id";
     }
}
