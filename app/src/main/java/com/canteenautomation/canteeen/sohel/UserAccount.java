package com.canteenautomation.canteeen.sohel;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by Sohel on 10/4/2016.
 */

public class UserAccount {
    //for EditText Refrance
    public static EditText EditTextPointer;
    public static String errorMessage;
    private EditText userName, password;
    private Context mCont;

    public UserAccount(Context mCont, EditText un, EditText pw) {
        this.userName = un;
        this.password = pw;
        this.mCont = mCont;
        isLoginInit(userName, password);
    }

    private static void isLoginInit(EditText userName, EditText password) {
        int maxLength = 10;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        //this is for userName
        userName.setHint("Enter Email / Contact No");
        userName.setSingleLine(true);
        userName.setMaxLines(1);
        //userName.setFilters(fArray);
        //this is for setDrawable left
        // userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_outline_white_24dp, 0, 0, 0);

        //this is for password
        password.setHint("Enter Passwrod");
        password.setSingleLine(true);
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setMaxLines(1);
        password.setFilters(fArray);
        //this is for setDrawable left
        // password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white_24dp, 0, 0, 0);
    }

    public static boolean isEmailValid(EditText tv) {
        //add your own logic
        if (TextUtils.isEmpty(tv.getText())) {
            EditTextPointer = tv;
            errorMessage = "This field can't be empty.!";
            return false;
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(tv.getText()).matches()) {
                return true;
            } else {
                EditTextPointer = tv;
                errorMessage = "Invalid Email Id";
                return false;
            }
        }
    }

    public static boolean isPasswordValid(EditText tv) {
        //add your own logic
        if (tv.getText().toString().length() >= 6) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Greater than 6 char";
            return false;
        }
    }

    public static boolean isPhoneNumberLength(EditText tv) {
        //add your own logic
        if (tv.getText().toString().length() == 10) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Enter 10 digits number";
            return false;
        }
    }

    public static final boolean isValidPhoneNumber(EditText tv) {
        if (tv.getText() == null || TextUtils.isEmpty(tv.getText())) {
            return false;
        } else {
            if (android.util.Patterns.PHONE.matcher(tv.getText()).matches()) {
                return true;
            } else {
                EditTextPointer = tv;
                errorMessage = "Invalid Mobile No.";
                return false;
            }
        }
    }

    public static boolean isEmpty(EditText... arg) {
        for (int i = 0; i < arg.length; i++) {
            if (arg[i].getText().length() <= 0) {
                EditTextPointer = arg[i];
                EditTextPointer.requestFocus();
                return false;
            }

        }
        return true;
    }

    /*public static boolean isSpinnerSelected(LabelledSpinner... arg) {
        for (int i = 0; i < arg.length; i++) {
            if (arg[i].getSpinner().getSelectedItemPosition() <= 0) {
                return false;
            }

        }
        return true;
    }*/

    /**
     * This class uses the AccountManager to get the primary email address of the
     * current user.
     */
    public static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(context, accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(Context cx, AccountManager accountManager) {
        if (ActivityCompat.checkSelfPermission(cx, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    public boolean loginViaLocal(String vUserName, String vPassword) {
        //Check for empty values
        if (isEmpty(userName, password)) {
            // if you are login via email please use below commented line.
            //if (isPasswordValid(password) && isEmailValid(userName))
            if (isPasswordValid(password)) {
                //Takeing Data from EditText in localvariable
                String uName, pwd;
                uName = userName.getText().toString().trim();
                pwd = password.getText().toString().trim();
                if (uName.equals(vUserName) && pwd.equals(vPassword)) {
                    return true;
                } else {
                    S.E("Invalid information");

                    return false;
                }
            } else {
                if (EditTextPointer != null) {
                    EditTextPointer.setError(errorMessage);
                }
            }
        } else {
            if (EditTextPointer != null) {
                EditTextPointer.setError("This Field Is Empty");
            }
        }
        return false;
    }

    public String getDeviceID(Context cx) {
        TelephonyManager tManager = (TelephonyManager) cx.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(mCont, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return tManager.getDeviceId();
    }
}