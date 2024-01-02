package com.canteenautomation.canteeen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canteenautomation.canteeen.database.datahelper.UserDataHelper;
import com.canteenautomation.canteeen.database.model.UserModel;
import com.canteenautomation.canteeen.sohel.Const;
import com.canteenautomation.canteeen.sohel.S;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginSignUpActivity extends AppCompatActivity {
    String userName;
    String mobileNo;
    String emailId;
    String password;
    String LoginMobileno;
    String LoginPAssword;
    String cpassword;

    EditText login_password_et, login_mobile_no_et;
    Button login_login_btn, Login_signup_btn;
    RelativeLayout login_RelativeLayout, signUp_RelativeLayout;
    EditText signup_username_et, signup_phoneno_et,
            signup_email_et, signup_password_et,
            signup_confirmpass_et;
    Button signup_register_btn, signup_login_btn;
    String USER_ID, userPhone, UserLoginEmail, loginuserName;
    private String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        if (UserDataHelper.getInstance().getList().size() > 0) {
            S.I_clear(LoginSignUpActivity.this, ItemListActivity.class,null);

        }
        String tokenString=   FirebaseInstanceId.getInstance().getToken();
        Token=tokenString;
        Log.e("HELLO","Again::"+tokenString);
        //Login
        login_password_et = findViewById(R.id.login_password_et);
        login_mobile_no_et = findViewById(R.id.login_mobile_no_et);
        login_login_btn = findViewById(R.id.login_login_btn);
        Login_signup_btn = findViewById(R.id.Login_signup_btn);
        login_RelativeLayout = findViewById(R.id.login_RelativeLayout);
        signUp_RelativeLayout = findViewById(R.id.signUp_RelativeLayout);

        Login_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_RelativeLayout.setVisibility(View.GONE);
                signUp_RelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        login_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginMobileno = login_mobile_no_et.getText().toString();
                LoginPAssword = login_password_et.getText().toString();
                S.E("ln listener Vaildation" + LoginMobileno + ":::" + LoginPAssword);
                CheakLoginValidation();
            }
        });
        //signUp

        signup_username_et = findViewById(R.id.signup_username_et);
        signup_phoneno_et = findViewById(R.id.signup_phoneno_et);
        signup_email_et = findViewById(R.id.signup_email_et);
        signup_password_et = findViewById(R.id.signup_password_et);
        signup_confirmpass_et = findViewById(R.id.signup_confirmpass_et);
        signup_register_btn = findViewById(R.id.signup_register_btn);
        signup_login_btn = findViewById(R.id.signup_login_btn);
        signup_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_RelativeLayout.setVisibility(View.VISIBLE);
                signUp_RelativeLayout.setVisibility(View.GONE);
            }
        });

        signup_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = signup_username_et.getText().toString();
                mobileNo = signup_phoneno_et.getText().toString();
                emailId = signup_email_et.getText().toString();
                password = signup_password_et.getText().toString();
                cpassword = signup_confirmpass_et.getText().toString();
                CheakSignUpValidation();
            }
        });
    }

    boolean isEmail(EditText text) {

        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);

    }

    private void CheakSignUpValidation() {

        if (!isEmpty(signup_username_et)) {
            if (!isEmpty(signup_phoneno_et) && (signup_phoneno_et.length() == 10)) {
                if (!isEmpty(signup_email_et) && isEmail(signup_email_et)) {
                    if (!isEmpty(signup_password_et) && signup_password_et.length() >= 6) {
                        if (!isEmpty(signup_confirmpass_et) && password.equals(cpassword)) {
                            SignUP();
                        } else {
                            signup_confirmpass_et.setError("password doesn't match");
                        }
                    } else {
                        signup_password_et.setError("You must have 8 characters in your password");
                    }

                } else {
                    signup_email_et.setError("Please enter vaild Email");
                }
            } else {
                signup_phoneno_et.setError("This Field Is Empty");
            }

        } else {
            signup_username_et.setError("This Field Is Empty");
        }
    }

    private void SignUP() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LoginSignUp ", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200 && jsonObject.getString("message").equals("success")) {
                        Toast.makeText(LoginSignUpActivity.this, "SignUp Successfuly. Please LogIn..", Toast.LENGTH_SHORT).show();
                        signUp_RelativeLayout.setVisibility(View.GONE);
                        login_RelativeLayout.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(LoginSignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginSignUpActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                Log.e("SignUpActivity ", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", userName);
                params.put("userPhone", mobileNo);
                params.put("userEmail", emailId);
                params.put("password", password);
                Log.e("SignUP", "getParams: " + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginSignUpActivity.this);
        requestQueue.add(stringRequest);

    }

    private void CheakLoginValidation() {


        if (!isEmpty(login_mobile_no_et) && (login_mobile_no_et.length() == 10)) {
            if (login_password_et.length() >= 6) {
                Login();
            } else {
                login_password_et.setError("Please Enter Vaild password");
            }

        } else {
            login_mobile_no_et.setError("Please Enter vaild Mobile Number");
        }

    }

    private void Login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LoginSignUp ", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200 && jsonObject.getString("message").equals("success")) {
                        JSONObject userData = jsonObject.getJSONObject("data");
                        USER_ID = userData.getString("user_id");
                        loginuserName = userData.getString("userName");
                        userPhone = userData.getString("userPhone");
                        UserLoginEmail = userData.getString("userEmail");
                        UserModel userModel = new UserModel();
                        userModel.setUserId(USER_ID);
                        userModel.setUserName(userName);
                        userModel.setUserPhone(userPhone);
                        userModel.setUserEmail(UserLoginEmail);
                        userModel.setUserEmail(Token);

                        S.E("USER_ID::" + USER_ID);
                        S.E("userName::" + loginuserName);
                        S.E("userPhone::" + userPhone);
                        S.E("UserLoginEmail::" + UserLoginEmail);

                        UserDataHelper.getInstance().insertData(userModel);
                        S.I_clear(LoginSignUpActivity.this, ItemListActivity.class,null);
                      //  Intent intent = new Intent(LoginSignUpActivity.this, ItemListActivity.class);
                        //startActivity(intent);
                    } else {
                        Toast.makeText(LoginSignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SignUpActivity ", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", LoginMobileno);
                params.put("password", LoginPAssword);
                params.put("fcm_id", Token);
                Log.e("LoginSignUp", "getParams: " + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginSignUpActivity.this);
        requestQueue.add(stringRequest);

    }
}
