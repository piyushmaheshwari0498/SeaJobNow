package com.example.seajobnow;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seajobnow.ApiEntity.RetrofitBuilder;
import com.example.seajobnow.ApiEntity.request.LoginRequest;
import com.example.seajobnow.ApiEntity.response.LoginResponse;
import com.example.seajobnow.Apinterface.ApiConnection;
import com.example.seajobnow.activity.ForgotPasswordActivity;
import com.example.seajobnow.databinding.ActivityLoginBinding;
import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;
import com.example.seajobnow.utils.Custom_Toast;
import com.example.seajobnow.utils.InternetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    InternetConnection internetConnection;
    AppSharedPreference appSharedPreference;

    List<LoginRequest> loginRequestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        //Intialisation
        appSharedPreference = AppSharedPreference.getAppSharedPreference(this);
        internetConnection = new InternetConnection();


        textfieldBehaviour();


        InputFilter toUpperCaseFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = start; i < end; i++) {
                    Character character = source.charAt(i);
                    character = Character.toUpperCase(character); // THIS IS UPPER CASING
                    stringBuilder.append(character);

                }
                return stringBuilder.toString();
            }

        };

        activityLoginBinding.etCompanyCode.setFilters(new InputFilter[] { toUpperCaseFilter });

        activityLoginBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internetConnection.isConnected(LoginActivity.this)) {
                    Custom_Toast.warning(LoginActivity.this, getString(R.string.no_internet));
                } else {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });

        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internetConnection.isConnected(LoginActivity.this)) {
                    Custom_Toast.warning(LoginActivity.this, getString(R.string.no_internet));
                } else {
                    if (!validateCompanyCode() | !validatePassword()) {
                        return;
                    }
                    checkLoginDetails();
                }
            }
        });

        activityLoginBinding.tvForgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        checkIsloggedIn();
    }
    public boolean validateCompanyCode() {
        String indos = activityLoginBinding.etCompanyCode.getText().toString().trim();

        if (indos.isEmpty()) {
            activityLoginBinding.companyCodeInputLayout.setError(getString(R.string.enter_companycode));
            return false;
        } else {
            activityLoginBinding.companyCodeInputLayout.setError(null);
            return true;
        }

    }

    public boolean validatePassword() {
        String pass = activityLoginBinding.etPassword.getText().toString().trim();
        if (pass.isEmpty()) {
            activityLoginBinding.passwordInputLayout.setError(getString(R.string.enter_password));

        } else {
            activityLoginBinding.passwordInputLayout.setError(null);
            return true;
        }
        return false;
    }

    public void checkIsloggedIn() {
        if (appSharedPreference.getBooleanValue(Constants.IS_LOGGED_IN)) {
            Intent mIntent = new Intent(this, MainActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
            finish();
        }
    }

    private void checkLoginDetails() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<LoginResponse> call = apiInterface.getLoginDetails(getHashMap());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.code() == 200 && response.message().equals("OK")) {
                    // System.out.println("success" + response.body().getStatusMessage());
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        loginRequestList = response.body().getData();
                        if (!loginRequestList.isEmpty()) {
                            // TODO: 19-08-2020 Saving Data to SharedPreference
                            appSharedPreference.putStringValue(Constants.INTENT_KEYS.KEY_COMPANY_ID, loginRequestList.get(0).getCompId());
                            appSharedPreference.putStringValue(Constants.INTENT_KEYS.KEY_COMPANY_CODE, loginRequestList.get(0).getCompCode());
                            appSharedPreference.putStringValue(Constants.INTENT_KEYS.KEY_COMPANY_NAME, loginRequestList.get(0).getCompName());
                            appSharedPreference.putBooleanValue(Constants.IS_LOGGED_IN, true);

                            Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mIntent);

                        }
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String errormessage = jsonObject.getString("message");
                            System.out.println("msg" + errormessage);
                            Log.d("error message", errormessage);
                            if (errormessage.toLowerCase().contains("password")) {
                                activityLoginBinding.passwordInputLayout.setError(getString(R.string.valid_password));
                            } else if (errormessage.toLowerCase().contains("indos")) {
                                activityLoginBinding.companyCodeInputLayout.setError(getString(R.string.valid_companycode));
                            } else {
                                Custom_Toast.warning(LoginActivity.this,
                                        getResources().getString(R.string.contact_admin));
                            }

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } /*else {
                        Custom_Toast.info(LoginActivity.this,
                                "Failed to connect with Server");
                    }*/
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Custom_Toast.info(LoginActivity.this,
                        getString(R.string.connection_failure));

            }
        });
    }

    private HashMap<String, Object> getHashMap() {
        String company_code = activityLoginBinding.etCompanyCode.getText().toString().toLowerCase();
        String password = activityLoginBinding.etPassword.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("comp_code", company_code);
        map.put("comp_password", password);
        map.put("comp_status", "1");
        return map;
    }

    public void textfieldBehaviour() {
        activityLoginBinding.passwordInputLayout.setEndIconVisible(!activityLoginBinding.etPassword.getText().toString().equals(""));

        activityLoginBinding.etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //do here your stuff f
                    if (!internetConnection.isConnected(LoginActivity.this)) {
                        Custom_Toast.warning(LoginActivity.this, getString(R.string.no_internet));
                    } else {
                        checkLoginDetails();
                    }
                    return true;
                }
                return false;
            }
        });

        activityLoginBinding.etCompanyCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                activityLoginBinding.companyCodeInputLayout.setError(null);

            }
        });

        activityLoginBinding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                activityLoginBinding.passwordInputLayout.setError(null);

            }
        });

        activityLoginBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activityLoginBinding.passwordInputLayout.setEndIconVisible(!activityLoginBinding.etPassword.getText().toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}