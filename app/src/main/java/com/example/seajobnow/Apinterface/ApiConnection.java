package com.example.seajobnow.Apinterface;

import com.example.seajobnow.ApiEntity.response.ConfirmPwdResponse;
import com.example.seajobnow.ApiEntity.response.ForgotPasswordResponse;
import com.example.seajobnow.ApiEntity.response.LoginResponse;
import com.example.seajobnow.ApiEntity.response.PostSpinnerResponse;
import com.example.seajobnow.ApiEntity.response.RegisterResponse;
import com.example.seajobnow.ApiEntity.response.SpinnerResponse;
import com.example.seajobnow.ApiEntity.response.VerifyResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiConnection {

    //Company Login
    @POST("company/login")
    Call<LoginResponse> getLoginDetails(@Body HashMap<String, Object> loginResponse);

    //Company Forgot Password
    @POST("company/company_forget_password")
    Call<ForgotPasswordResponse> getForgotpwdDetails(@Body HashMap<String, Object> forgotpwdResponse);

    //Verify Password
    @POST("company/company_verify_otp")
    Call<VerifyResponse> getVerifyOtpDetails(@Body HashMap<String, Object> otpResponse);

    //Change Password
    @POST("company/company_confirmpassword")
    Call<ConfirmPwdResponse> getConfirmpwdDetails(@Body HashMap<String, Object> forgotpwdResponse);

    //Spinner data which is fetch in City {} State {} Country {}
    @GET("company/company_registration_assets")
    Call<SpinnerResponse> getSpinner();

    //Spinner data which is fetching POst Job Department{} Rank{} etc.
    @GET("company/company_jobPost_assets")
    Call<PostSpinnerResponse> getPostSpinner();

    //Company Register
    @POST("company/company_registration")
    Call<RegisterResponse> putRegisterDetails(@Body HashMap<String, Object> registerResponse);
}
