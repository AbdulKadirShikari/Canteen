package com.canteenautomation.canteeen.Services;

/**
 * Created by sohel on 4/5/2017.
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String readyToPlay = "readyToPlay";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    public static final String CANCEL_REQUEST = "cancel_request";
    public static final String GAME_START = "game_start";
    public static final String Table_Expaire = "table_expaire";
    public static final String QuizRequest = "QuizRequest";
    public static final String QuizResult = "QuizResult";
    public static final String MultiPlayer = "multiPlayer_notification";
    public static final String gameStartByHost = "gameStartByHost";
    public static final String MultiPlayer_defender_Accept = "multiPlayer_depender_acceptnotification";
}