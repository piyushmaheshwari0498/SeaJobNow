package com.example.seajobnow.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seajobnow.ApiEntity.RetrofitBuilder;
import com.example.seajobnow.ApiEntity.response.VerifyResponse;
import com.example.seajobnow.Apinterface.ApiConnection;
import com.example.seajobnow.LoginActivity;
import com.example.seajobnow.R;
import com.example.seajobnow.databinding.ActivityVerifyBinding;
import com.example.seajobnow.utils.Custom_Toast;
import com.example.seajobnow.utils.InternetConnection;
import com.example.seajobnow.utils.PatternClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {
//    EditText etOtpNumber,etAccountID;

    InternetConnection appUtils;
    String otpnumber;
    String intent_otp;
    String companyCode;
    ActivityVerifyBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(Html.fromHtml("<font color='#FFFFFF'><small>Verify OTP</small></font>"));
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
//        }

        Intent receiveIntent = getIntent();
        intent_otp = receiveIntent.getStringExtra("otp");
        companyCode = receiveIntent.getStringExtra("comp_code");
        otpnumber = binding.otpView.getOTP();
        appUtils = new InternetConnection();

        binding.subtext.setText(getString(R.string.otp_text) + "\n" + PatternClass.protectEmailAddress("maheshwaripiyush99@gmail.com")
                + "\n" + PatternClass.maskEmailAddress("maheshwaripiyush99@gmail.com"));

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!appUtils.isConnected(VerifyActivity.this)) {
                        Custom_Toast.warning(VerifyActivity.this, getString(R.string.no_internet));
                    } else if (validate()) {
                        getVerifyDetails();
                    }

                } catch (Exception e) {
                    Log.e("loginError", e.getMessage());
                }
            }
        });
    }

    private void getVerifyDetails() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<VerifyResponse> call = apiInterface.getVerifyOtpDetails(getHashMap());
        call.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        String msg = response.body().getMessage();
                        Custom_Toast.success(VerifyActivity.this, msg);
                        Intent i = new Intent(VerifyActivity.this, ConfirmPasswordActivity.class);
                        startActivity(i);
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            Custom_Toast.warning(VerifyActivity.this, message);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(VerifyActivity.this, "Some Thing Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                Custom_Toast.info(VerifyActivity.this,
                        getString(R.string.connection_failure));
            }
        });
    }

    private boolean validate() {
        otpnumber = binding.otpView.getOTP();
        if (otpnumber.isEmpty()) {
            Custom_Toast.info(getApplicationContext(), "Please Enter OTP Number");
            return false;
        }
        return true;
    }

    private HashMap<String, Object> getHashMap() {
        otpnumber = binding.otpView.getOTP();
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("otp", otpnumber);
        map.put("comp_code", companyCode);
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
