package com.example.seajobnow;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
    String account_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        //Intialisation
        appSharedPreference = AppSharedPreference.getAppSharedPreference(this);
        internetConnection = new InternetConnection();


        textfieldBehaviour();

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

        checkIsloggedIn();
    }
    public boolean validateCompanyCode() {
        String indos = activityLoginBinding.etCompanyCode.getText().toString().trim();

        if (indos.isEmpty()) {
            activityLoginBinding.companyCodeInputLayout.setError("Please Enter Company Code");
            return false;
        } else {
            activityLoginBinding.companyCodeInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validatePassword() {
        String pass = activityLoginBinding.etPassword.getText().toString().trim();
        if (pass.isEmpty()) {
            activityLoginBinding.passwordInputLayout.setError("Please Enter Password");
            return false;
        } else {
            activityLoginBinding.passwordInputLayout.setErrorEnabled(false);
            return true;
        }
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
                    } else {
                        Custom_Toast.warning(LoginActivity.this,
                                getResources().getString(R.string.something_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Custom_Toast.warning(LoginActivity.this, t.getMessage());

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
                activityLoginBinding.companyCodeInputLayout.setError("");

            }
        });

        activityLoginBinding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                activityLoginBinding.passwordInputLayout.setError("");

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