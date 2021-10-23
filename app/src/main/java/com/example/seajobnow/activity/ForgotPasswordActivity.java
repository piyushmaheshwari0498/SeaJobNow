package com.example.seajobnow.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.seajobnow.ApiEntity.RetrofitBuilder;
import com.example.seajobnow.ApiEntity.response.ForgotPasswordResponse;
import com.example.seajobnow.Apinterface.ApiConnection;
import com.example.seajobnow.R;
import com.example.seajobnow.databinding.ActivityForgotPasswordBinding;
import com.example.seajobnow.databinding.ActivityLoginBinding;
import com.example.seajobnow.utils.Constants;
import com.example.seajobnow.utils.Custom_Toast;
import com.example.seajobnow.utils.InternetConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordActivity extends AppCompatActivity {
    String compnayCode;
    ActivityForgotPasswordBinding binding;
    InternetConnection appUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(Html.fromHtml("<font color='#FFFFFF'><small>Forgot Password</small></font>"));
        //setTitle(Html.fromHtml("<small>Forgot Password</small>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
//        }

        appUtils = new InternetConnection();

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

        binding.etcompanyCode.setFilters(new InputFilter[] { toUpperCaseFilter });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("btn");
                try {
                    if (!appUtils.isConnected(ForgotPasswordActivity.this)) {
                        Custom_Toast.warning(ForgotPasswordActivity.this, getString(R.string.no_internet));
                    } else if (!validate()) {
                        return;
                    }
                    forgetpassword();
                } catch (Exception e) {
                    Log.e("loginError", e.getMessage());
                }
            }
        });
        binding.etcompanyCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                binding.companyCodeInputLayout.setError("");

            }
        });
    }

    private void forgetpassword() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<ForgotPasswordResponse> call = apiInterface.getForgotpwdDetails(getHashMap());
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 &&
                            response.body().getStatusMessage().equals("Success")) {
                        String otp = response.body().getOtp();
                        String msg = response.body().getMessage()+"   otp : "+otp;
                        System.out.println("msg : "+msg);
                        Toast.makeText(getApplicationContext(), otp, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, VerifyActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("comp_code", compnayCode);
                        startActivity(intent);
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
                            Log.d("error",message);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            if (binding.etcompanyCode.getText().toString().equals("")) {
                                binding.companyCodeInputLayout.setError("Please Enter Company Code");
                            } else if (response.code() == 404 | response.code() == 0) {
                                //Mobile Number Condition
                                binding.companyCodeInputLayout.setError("Please Enter Correct Company Code");
                            } else {
                                binding.companyCodeInputLayout.setError("");
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Some Thing Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

            }
        });
    }

    private boolean validate() {
        String MobilePattern = "[0-9]{10}";
        compnayCode = binding.etcompanyCode.getText().toString();
        if (compnayCode.isEmpty()) {
//            Custom_Toast.error(getApplicationContext(), "Invalid Mobile Number.Please Re-Enter the Mobile Number");
            binding.companyCodeInputLayout.setError("Please Enter Company Code");
            return false;
        } else {
            binding.companyCodeInputLayout.setError("");
            return true;
        }
    }

    private HashMap<String, Object> getHashMap() {
        compnayCode = binding.etcompanyCode.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("comp_code", compnayCode);
        System.out.println("compnayCode" + compnayCode);
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