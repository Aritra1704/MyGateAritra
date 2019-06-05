package com.arpaul.mygate_aritra.constants;

public class Constant {

    private Constant(){}

    private static volatile Constant INSTANCE = null;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1002;
    private static final String DATE_TIME_FORMAT_UTC_MILIS   = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final int PERM_READ_PHONE_STATE           = 1101;
    private static final int PERM_READ_EXTERNAL_STORAGE      = 1102;
    private static final int PERM_CAMERA                     = 1106;

    public static Constant getINSTANCE() {
        if(INSTANCE == null)
            INSTANCE = new Constant();
        return INSTANCE;
    }

    public static int getCaptureImageActivityRequestCode() {
        return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;
    }

    public static String getDateTimeFormatUtcMilis() {
        return DATE_TIME_FORMAT_UTC_MILIS;
    }

    public static int getPermReadPhoneState() {
        return PERM_READ_PHONE_STATE;
    }

    public static int getPermReadExternalStorage() {
        return PERM_READ_EXTERNAL_STORAGE;
    }

    public static int getPermCamera() {
        return PERM_CAMERA;
    }
}
