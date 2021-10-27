package com.example.seajobnow;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.seajobnow.ApiEntity.RetrofitBuilder;
import com.example.seajobnow.ApiEntity.request.CityRequest;
import com.example.seajobnow.ApiEntity.request.CountryRequest;
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.ApiEntity.request.SpinnerDataRequest;
import com.example.seajobnow.ApiEntity.request.StateRequest;
import com.example.seajobnow.ApiEntity.response.RegisterResponse;
import com.example.seajobnow.ApiEntity.response.SpinnerResponse;
import com.example.seajobnow.Apinterface.ApiConnection;
import com.example.seajobnow.actions.ShowSnackbar;
import com.example.seajobnow.adapters.CityAdapter;
import com.example.seajobnow.adapters.CountryAdapter;
import com.example.seajobnow.adapters.StateAdapter;
import com.example.seajobnow.databinding.ActivityRegisterBinding;
import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;
import com.example.seajobnow.utils.InternetConnection;
import com.example.seajobnow.utils.PatternClass;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;

    String selectedCityId = "", selectedStateId = "", selectedCountryId = "";
    String cityname, statename, countryname;
    String selectedCityName, selectedStateName, selectedCountryName;

    CityAdapter customCityAdapter2;
    StateAdapter stateAdapter2;
    CountryAdapter countryAdapter2;
    InternetConnection internetConnection;
    AppSharedPreference appSharedPreference;
    private List<CityRequest> cityRequestList;
    private List<StateRequest> stateRequestList;
    private List<CountryRequest> countryRequestList;

    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mDay, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
        internetConnection = new InternetConnection();
        appSharedPreference = AppSharedPreference.getAppSharedPreference(this);

        if (!internetConnection.isConnected(getApplicationContext())) {
            new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot(), getString(R.string.no_internet));
        } else {
            getSpinnerData();
        }
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        showDatePicker();

        activityRegisterBinding.btnNextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internetConnection.isConnected(getApplicationContext())) {
                    new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot(), getString(R.string.no_internet));
                }
                else {
                    showBottomSheetDialog();
                }
                //if this condition returns false it will show error and registration won't be done.
               /* else if (!validateCompanyName() | !validateRPSLID() | !validateRPSLDATE() | !validatePersonName() | !validateEmail()
                        | !validateMobile() | !validateWebsite())
                    *//*| !validateAddress() | !validatePincode() | !validateCity()
                        | !validateState() | !validateCountry()*//* {
                    return;
                }
                //if this condition returns true it will proceed to registration.
                addRegisterDetails();*/
            }
        });

        activityRegisterBinding.textLoginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void showDatePicker() {
        activityRegisterBinding.etrpslDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int newMonth = monthOfYear + 1;
                                String monthObtained = newMonth < 10 ? "0" + newMonth : String.valueOf(newMonth);
                                String dayObtained = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String displayDate = dayObtained + "-" + monthObtained + "-" + year;
                                activityRegisterBinding.etrpslDate.setText(displayDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
            }
        });
    }

    private boolean validateCity() {
        if (selectedCityId.isEmpty()) {
            activityRegisterBinding.inputspnCity.setError(getString(R.string.valid_city));
            return false;
        } else {
            activityRegisterBinding.inputspnCity.setError(null);
            return true;
        }
    }

    private boolean validateState() {
        if (selectedStateId.isEmpty()) {
            activityRegisterBinding.inputspnState.setError(getString(R.string.valid_state));
            return false;
        } else {
            activityRegisterBinding.inputspnState.setError(null);
            return true;
        }
    }

    private boolean validateCountry() {
        if (selectedCountryId.isEmpty()) {
            activityRegisterBinding.inputspnCountry.setError(getString(R.string.valid_country));
            return false;
        } else {
            activityRegisterBinding.inputspnCountry.setError(null);
            return true;
        }
    }

    private boolean validateCompanyName() {
        String company_name = activityRegisterBinding.etCompanyName.getText().toString();
        if (company_name.isEmpty()) {
            activityRegisterBinding.companyNameLayout.setError(getString(R.string.valid_company_name));
            return false;
        } else {
            activityRegisterBinding.companyNameLayout.setError(null);
            return true;
        }
    }

    private boolean validateRPSLID() {
        String rpsl = activityRegisterBinding.etrpslId.getText().toString();
        if (rpsl.isEmpty()) {
            activityRegisterBinding.rpslLayout.setError(getString(R.string.valid_rpsl));
            return false;
        } else {
            activityRegisterBinding.rpslLayout.setError(null);
            return true;
        }
    }

    private boolean validateRPSLDATE() {
        String rpsl_date = activityRegisterBinding.etrpslDate.getText().toString();
        if (rpsl_date.isEmpty()) {
            activityRegisterBinding.rpslDateLayout.setError(getString(R.string.valid_rpsl_date));
            return false;
        } else {
            activityRegisterBinding.rpslDateLayout.setError(null);
            return true;
        }
    }

    private boolean validatePersonName() {
        String cp_name = activityRegisterBinding.etName.getText().toString();
        if (cp_name.isEmpty()) {
            activityRegisterBinding.contactNameLayout.setError(getString(R.string.valid_contact_person));
            return false;
        } else {
            activityRegisterBinding.contactNameLayout.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        String address = activityRegisterBinding.etAddress.getText().toString();
        if (address.isEmpty()) {
            activityRegisterBinding.addressLayout.setError(getString(R.string.valid_address));
            return false;
        } else {
            activityRegisterBinding.addressLayout.setError(null);
            return true;
        }
    }

    private boolean validatePincode() {
        String pincode = activityRegisterBinding.etPincode.getText().toString();
        if (pincode.isEmpty()) {
            activityRegisterBinding.pincodeLayout.setError(getString(R.string.valid_pincode));
            return false;
        } else {
            activityRegisterBinding.pincodeLayout.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {

        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = activityRegisterBinding.etEmailID.getText().toString();
        if (email.isEmpty()) {
            activityRegisterBinding.emailLayout.setError(getString(R.string.error_email));
            return false;
        } else if (!email.matches(checkEmail)) {
            activityRegisterBinding.emailLayout.setError(getString(R.string.valid_email));
            return false;
        } else {
            activityRegisterBinding.emailLayout.setError(null);
            return true;
        }
    }

    private boolean validateMobile() {

        String mobileno = activityRegisterBinding.etMobileNo.getText().toString();

        if (activityRegisterBinding.etMobileNo.getText().toString().isEmpty()) {
            activityRegisterBinding.mobileLayout.setError(getString(R.string.error_mobile));
            return false;
        } else if (!PatternClass.isValidPhone(mobileno)) {
            activityRegisterBinding.mobileLayout.setError(getString(R.string.valid_mobile));
            return false;
        } else {
            activityRegisterBinding.mobileLayout.setError(null);
            return true;
        }
    }

    private boolean validateWebsite() {

        String website = activityRegisterBinding.etwebsite.getText().toString();

        if (activityRegisterBinding.etwebsite.getText().toString().isEmpty()) {
            activityRegisterBinding.websiteLayout.setError(getString(R.string.error_website));
            return false;
        } else {
            activityRegisterBinding.websiteLayout.setError(null);
            return true;
        }
    }

    public void getSpinnerData() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<SpinnerResponse> call = apiInterface.getSpinner();
        call.enqueue(new Callback<SpinnerResponse>() {
            @Override
            public void onResponse(Call<SpinnerResponse> call, Response<SpinnerResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        SpinnerDataRequest registerData = response.body().getData();

                        //City Data
                        cityRequestList = registerData.getCity();
                        customCityAdapter2 = new CityAdapter(RegisterActivity.this, R.layout.custom_spinner_item, cityRequestList);
                        activityRegisterBinding.spnCity.setThreshold(1);
                        activityRegisterBinding.spnCity.setAdapter(customCityAdapter2);


                        activityRegisterBinding.spnCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                CityRequest cityRequest = (CityRequest) adapterView.getItemAtPosition(pos);
                                selectedCityId = cityRequest.getCityId();
                                selectedCityName = cityRequest.getCityName();
                                Log.e("cityId", selectedCityId);
                                Log.e("cityName", selectedCityName);
                            }
                        });


                        //State Data

                        stateRequestList = registerData.getState();
                        stateAdapter2 = new StateAdapter(RegisterActivity.this, R.layout.custom_spinner_item, stateRequestList);
                        activityRegisterBinding.spnState.setThreshold(1);
                        activityRegisterBinding.spnState.setAdapter(stateAdapter2);

                        activityRegisterBinding.spnState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                StateRequest stateRequest = (StateRequest) adapterView.getItemAtPosition(pos);
                                selectedStateId = stateRequest.getStateId();
                                selectedStateName = stateRequest.getStateName();
                                Log.e("stateId", selectedStateId);
                                Log.e("stateName", selectedStateName);
                            }
                        });

                        //Country Data
                        countryRequestList = registerData.getCountry();

                        countryAdapter2 = new CountryAdapter(RegisterActivity.this, R.layout.custom_spinner_item, countryRequestList);
                        activityRegisterBinding.spnCountry.setThreshold(1);
                        activityRegisterBinding.spnCountry.setAdapter(countryAdapter2);
                        activityRegisterBinding.spnCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                CountryRequest countryRequest = (CountryRequest) adapterView.getItemAtPosition(pos);
                                selectedCountryId = countryRequest.getCountryId();
                                selectedCountryName = countryRequest.getCountryName();
                                Log.e("countryId", selectedCountryId);
                                Log.e("countryName", selectedCountryName);
                            }
                        });
                    } else {
                        if (response.body().getStatusCode() == 0) {
                            if (response.body().getStatusMessage().equals("Fail")) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.wrong_details), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SpinnerResponse> call, Throwable t) {

            }
        });
    }

    private void addRegisterDetails() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<RegisterResponse> call = apiInterface.putRegisterDetails(getHashMap());
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    System.out.println("success" + response.body().getStatusMessage());
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        String msg = response.body().getMessage();
                        // Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                        new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot(), msg);
                        finish();
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot(), message);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
//                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                        new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot(), getResources().getString(R.string.something_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot(), t.getMessage());
            }
        });
    }

    private HashMap<String, Object> getHashMap() {
        String companyname = activityRegisterBinding.etCompanyName.getText().toString();
        String name = activityRegisterBinding.etName.getText().toString();
        String email = activityRegisterBinding.etEmailID.getText().toString();
        String mobileno = activityRegisterBinding.etMobileNo.getText().toString();
        String address = activityRegisterBinding.etAddress.getText().toString();
        String pincode = activityRegisterBinding.etPincode.getText().toString();
        String website = activityRegisterBinding.etwebsite.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("companyname", companyname);
        map.put("name", name);
        map.put("email", email);
        map.put("address", address);
        map.put("pincode", pincode);
        map.put("mobile", mobileno);
        map.put("comp_web_url", website);
        map.put("cityname", selectedCityId);
        map.put("statename", selectedStateId);
        map.put("countryname", selectedCountryId);
        map.put("comp_position", "1");
        return map;
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.verify_registration_bottom_sheet);

        MaterialButton logout_yes = bottomSheetDialog.findViewById(R.id.btnSubmit);
        OtpTextView emailOtpTextView = bottomSheetDialog.findViewById(R.id.email_otp_view);
        OtpTextView mobileOtpTextView = bottomSheetDialog.findViewById(R.id.mobile_otp_view);
        TextView emailTextView = bottomSheetDialog.findViewById(R.id.emailsubtext);
        TextView mobileTextView = bottomSheetDialog.findViewById(R.id.mobilesubtext);

        bottomSheetDialog.getBehavior().setHideable(false);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        bottomSheetDialog.show();

        String subText = getString(R.string.otp_text);
        emailTextView.setText(subText + "\n"+"maheshwaripiyush@gmail.com");
        mobileTextView.setText(subText + "\n"+"+91 7021507178");

        logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

}