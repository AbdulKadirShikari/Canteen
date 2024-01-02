package com.canteenautomation.canteeen.sohel;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.canteenautomation.canteeen.sohel.AppController;


public class SavedData {
    private static final String START_KM = "startKm";
    private static final String VALUE_RUN = "valuerun";
    private static final String VALUE_EXPENSESS = "valueExpensess";
    private static final String EXPENSESS_TYPE = "expensessType";
    private static final String FINISH_KM = "finishKm";
    private static final String PAYMENT_METHOD = "paymentMethod";
    private static final String IS_LOGIN = "isLogin";
    private static final String USER_EMAIL = "userEmail";
    private static final String SERVICE_AVAILABLE_STATUS = "serviceAvailableStatus";
    private static final String WALLETE_BALANCE = "walleteBalance";
    private static final String VERIFY_STATUS = "verifyStatus";
    private static final String OCCASION_STATUS = "occasionStatus";
    private static final String ADMIN_USER_ID = "adminUserId";
    private static final String MESSAGEDATA = "message";
    private static final String FCMNOTIFICATIONREQUEST = "yesNo";
    private static final String FCMNOTIFICATIONREQUESTGAMESTART = "GameStart";
    private static final String FCMNOTIFICATIONREQUESTId = "fcmid";
    private static final String UPDATEPROFILECHECK = "updatedprofile";


    private static final String QuizContinueCheck = "QuizContinueCheck";
    private static final String time = "time";
    private static final String topicId = "topicId";
    private static final String quiz_id = "quiz_id";
    private static final String NOTIFICATION_JSON = "notificationJson";
    private static final String NOTIFICATION_Status = "notificationJson_Status";
    private static final String COINS = "coins";
    private static final String ALL_COINS = "all_coins";
    private static final String AvalableBalance = "AvalableBalance";
    private static final String TWO_GROP_ID = "two_player_grop_id";
    private static final String TWO_Player_User_ID = "user_id";
    private static final String THREE_Player_User_ID = "users_id";
    private static final String CheckReadyToPlay = "checkReadyToPlay";
    private static final String CATEGORY_IMAGE = "";
    /*Order ID 146381161*/
    private static final String USERTYPE = "user_type";
    private static final String TOKEN = "token_id";
    private static final String LOGIN = "log_in";
    static SharedPreferences prefs;

    public static boolean getCheckQuetionaryOn() {
        return getInstance().getBoolean(QuizContinueCheck, false);
    }

    public static void saveCheckQuestionary(boolean startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(QuizContinueCheck, startKm);
        editor.apply();
    }

    public static String getNotificationStatus() {
        return getInstance().getString(NOTIFICATION_Status, "");
    }

    public static void saveNotificationStatus(String notificationJson_Status) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(NOTIFICATION_Status, notificationJson_Status);
        editor.apply();
    }

    public static String getNotificationJson() {
        return getInstance().getString(NOTIFICATION_JSON, null);
    }

    public static void saveNotificationJson(String notificationJson) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(NOTIFICATION_JSON, notificationJson);
        editor.apply();
    }

    public static String getQuiz_id() {
        return getInstance().getString(quiz_id, "");
    }

    public static void saveQuizeId(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(quiz_id, startKm);
        editor.apply();
    }

    public static String gettime() {
        return getInstance().getString(time, "");
    }

    public static void savetime(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(time, startKm);
        editor.apply();
    }

    public static String gettopicId() {
        return getInstance().getString(topicId, "");
    }

    public static void savetopicId(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(topicId, startKm);
        editor.apply();
    }

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
        }
        return prefs;
    }

    public static String getExpensessType() {
        return getInstance().getString(EXPENSESS_TYPE, null);
    }

    public static void saveExpensessType(String ExpensessType) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(EXPENSESS_TYPE, ExpensessType);
        editor.apply();
    }

    public static String getUserEmail() {
        return getInstance().getString(USER_EMAIL, null);
    }

    public static void saveUserEmail(String userEmail) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USER_EMAIL, userEmail);
        editor.apply();
    }

    public static String getStartKm() {
        return getInstance().getString(START_KM, null);
    }

    public static void saveStartKm(String startKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(START_KM, startKm);
        editor.apply();
    }

    public static String getValueRun() {
        return getInstance().getString(VALUE_RUN, null);
    }

    public static void saveValueRun(String ValueRun) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(VALUE_RUN, ValueRun);
        editor.apply();
    }

    public static String getValueExpensess() {
        return getInstance().getString(VALUE_EXPENSESS, null);
    }

    public static void saveValueExpensess(String ValueExpensess) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(VALUE_EXPENSESS, ValueExpensess);
        editor.apply();
    }

    public static String getFinishKm() {
        return getInstance().getString(FINISH_KM, null);
    }

    public static void saveFinishKm(String FinishKm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FINISH_KM, FinishKm);
        editor.apply();
    }

    public static String getPaymentMethod() {
        return getInstance().getString(PAYMENT_METHOD, null);
    }

    public static void savePaymentMethod(String PaymentMethod) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(PAYMENT_METHOD, PaymentMethod);
        editor.apply();
    }

    public static String getwalleteBalance() {
        return getInstance().getString(WALLETE_BALANCE, null);
    }

    public static void savewalleteBalance(String walleteBalance) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(WALLETE_BALANCE, walleteBalance);
        editor.apply();
    }

    public static String getServiceAvailableStatus() {
        return getInstance().getString(SERVICE_AVAILABLE_STATUS, null);
    }

    public static void saveServiceAvailableStatus(String serviceAvailableStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(SERVICE_AVAILABLE_STATUS, serviceAvailableStatus);
        editor.apply();
    }

    public static String getverifyStatus() {
        return getInstance().getString(VERIFY_STATUS, null);
    }

    public static void saveverifyStatus(String verifyStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(VERIFY_STATUS, verifyStatus);
        editor.apply();
    }

    public static String getOccasionStatus() {
        return getInstance().getString(OCCASION_STATUS, null);
    }

    public static void saveOccasionStatus(String occasionStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(OCCASION_STATUS, occasionStatus);
        editor.apply();
    }

    public static String getAdminUserId() {
        return getInstance().getString(ADMIN_USER_ID, null);
    }

    public static void saveAdminUserId(String occasionStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(ADMIN_USER_ID, occasionStatus);
        editor.apply();
    }

    public static void clear() {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.clear();
        editor.apply();
    }

    public static String getNotificationMessage() {
        return getInstance().getString(MESSAGEDATA, null);
    }

    public static void saveNotification(String occasionStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(MESSAGEDATA, occasionStatus);
        editor.apply();
    }

    public static String getNotificationRequest() {
        return getInstance().getString(FCMNOTIFICATIONREQUEST, null);
    }

    public static void saveNotificationRequest(String occasionStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FCMNOTIFICATIONREQUEST, occasionStatus);
        editor.apply();
    }

    public static String getNotificationRequestForGameStart() {
        return getInstance().getString(FCMNOTIFICATIONREQUESTGAMESTART, "false");
    }

    public static void saveNotificationRequestGameStart(String occasionStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FCMNOTIFICATIONREQUESTGAMESTART, occasionStatus);
        editor.apply();
    }

    public static String getUserTpye() {
        return getInstance().getString(USERTYPE, null);
    }

    public static void saveUserType(String user_type) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USERTYPE, user_type);
        editor.apply();
    }

    public static String getToken() {
        return getInstance().getString(TOKEN, null);
    }

    public static void saveToken(String user_type) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TOKEN, user_type);
        editor.apply();
    }

    public static String getIsLogin() {
        return getInstance().getString(LOGIN, "");
    }

    public static void saveLogin(String user_type) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(LOGIN, user_type);
        editor.apply();
    }

    public static String getCoins() {
        return getInstance().getString(COINS, "");
    }

    public static void saveCoins(String coin) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(COINS, coin);
        editor.apply();

    }

    public static String getTotalCoins() {
        return getInstance().getString(ALL_COINS, "");
    }

    public static void saveTotalCoins(String coin) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(ALL_COINS, coin);
        editor.apply();

    }

    public static int getAvalableBalance() {
        return getInstance().getInt(AvalableBalance, 0);
    }

    public static void saveAvalableBalance(int bal) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putInt(AvalableBalance, bal);
        editor.apply();

    }

    public static String getTwoPlayerGroupID() {
        return getInstance().getString(TWO_GROP_ID, "");
    }

    public static void saveTwoPlayerGroupID(String coin) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TWO_GROP_ID, coin);
        editor.apply();

    }

    public static String getTwoPlayerUserID() {
        return getInstance().getString(TWO_Player_User_ID, "");
    }

    public static void saveTwoPlayerUserID(String coin) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TWO_Player_User_ID, coin);
        editor.apply();
        // editor.clear();

    }

    public static String getThreeOfFirstPlayerUserID() {
        return getInstance().getString(THREE_Player_User_ID, "");
    }

    public static void saveThreeOfFirstPlayerUserID(String UID) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(THREE_Player_User_ID, UID);
        editor.apply();
        // editor.clear();
    }

    public static String getFcmTokenID() {
        return getInstance().getString(FCMNOTIFICATIONREQUESTId, "");
    }

    public static void saveFcmTokenID(String token) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FCMNOTIFICATIONREQUESTId, token);
        editor.apply();
    }

    public static String getCategoryImage() {
        return getInstance().getString(CATEGORY_IMAGE, "");
    }

    public static void saveCategoryImage(String image) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CATEGORY_IMAGE, image);
        editor.apply();
    }

    public static String getProfileUpdate() {
        return getInstance().getString(UPDATEPROFILECHECK, "");
    }

    public static void saveProfileUpdate(String check) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(UPDATEPROFILECHECK, check);
        editor.apply();
    }

    public static String readyToPlayStore() {
        return getInstance().getString(CheckReadyToPlay, "");
    }

    public static void saveReadyToPlay(String check) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CheckReadyToPlay, check);
        editor.apply();
    }


}