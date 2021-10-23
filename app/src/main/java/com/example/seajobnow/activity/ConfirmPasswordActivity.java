package com.example.seajobnow.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.seajobnow.ApiEntity.RetrofitBuilder;
import com.example.seajobnow.ApiEntity.response.ConfirmPwdResponse;
import com.example.seajobnow.Apinterface.ApiConnection;
import com.example.seajobnow.LoginActivity;
import com.example.seajobnow.R;
import com.example.seajobnow.databinding.ActivityConfirmPasswordBinding;
import com.example.seajobnow.databinding.ActivityVerifyBinding;
import com.example.seajobnow.utils.Custom_Toast;
import com.example.seajobnow.utils.InternetConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPasswordActivity extends AppCompatActivity {
    Button btnsave;
    String newpwd, confirmpwd, company_email;
    InternetConnection appUtils;
    ActivityConfirmPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(Html.fromHtml("<font color='#FFFFFF'><small>Confirm Password</small></font>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appUtils = new InternetConnection();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
//        }

        btnsave = findViewById(R.id.btnsave);

        binding.passwordInputLayout.setEndIconVisible(false);
        binding.confrimPasswordInputLayout.setEndIconVisible(false);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!appUtils.isConnected(ConfirmPasswordActivity.this)) {
                        Custom_Toast.warning(ConfirmPasswordActivity.this, getString(R.string.no_internet));
                    } else if (validate()) {
                        getConfirmpwd();
                    }

                } catch (Exception e) {
                    Log.e("loginError", e.getLocalizedMessage());
                }
            }
        });

        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.passwordInputLayout.setEndIconVisible(!Objects.requireNonNull(binding.etPassword.getText()).toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.confrimPasswordInputLayout.setEndIconVisible(!Objects.requireNonNull(binding.etConfirmPassword.getText()).toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                binding.passwordInputLayout.setError("");

            }
        });

        binding.confrimPasswordInputLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                binding.etConfirmPassword.setError("");

            }
        });
    }

    private void getConfirmpwd() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<ConfirmPwdResponse> call = apiInterface.getConfirmpwdDetails(getHashMap());
        call.enqueue(new Callback<ConfirmPwdResponse>() {
            @Override
            public void onResponse(Call<ConfirmPwdResponse> call, Response<ConfirmPwdResponse> response) {
                if (response.code() == 200 && response.message().equals(    "OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        String msg = response.body().getMessage();
                        Log.d("OTP",msg);
                        Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ConfirmPasswordActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ConfirmPasswordActivity.this, "Some Thing Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ConfirmPwdResponse> call, Throwable t) {

            }
        });
    }

    private boolean validate() {
        company_email = binding.editTextAccount.getText().toString();
        newpwd = Objects.requireNonNull(binding.etPassword.getText()).toString();
        confirmpwd = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();

        if (company_email.isEmpty()) {
            Custom_Toast.warning(getApplicationContext(), "Please Enter Email Id");
            return false;
        } else if (newpwd.isEmpty()) {
//            Custom_Toast.error(getApplicationContext(), "Invalid details.Please Re-Enter the New password");
            binding.passwordInputLayout.setError("Please Enter the New password");
            return false;
        } else if (confirmpwd.isEmpty()) {
            binding.confrimPasswordInputLayout.setError("Please Enter the Confirm password");
//            Custom_Toast.error(getApplicationContext(), "Invalid details.Please Re-Enter the Confirm password");
            return false;
        }
        else if (!newpwd.equals(confirmpwd)) {
            binding.confrimPasswordInputLayout.setError("Confirm password should match");
//            Custom_Toast.error(getApplicationContext(), "Invalid details.Please Re-Enter the Confirm password");
            return false;
        }
        return true;
    }

    private HashMap<String, Object> getHashMap() {
        company_email = binding.editTextAccount.getText().toString();
        newpwd = Objects.requireNonNull(binding.etPassword.getText()).toString();
        confirmpwd = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("comp_email", company_email);
        map.put("comp_password", newpwd);
        map.put("comp_confirm_password", confirmpwd);
        return map;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // System.out.println("Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //   System.out.println("Potrait");
        }
    }
}
