package com.akkru.user.akkru.api.service;

import com.akkru.user.akkru.api.model.EditUser;
import com.akkru.user.akkru.api.model.EditUserResponse;
import com.akkru.user.akkru.api.model.ExpenseResponse;
import com.akkru.user.akkru.api.model.IncomeResponse;
import com.akkru.user.akkru.api.model.Login2;
import com.akkru.user.akkru.api.model.SetExpense.SetExpenseRespons;
import com.akkru.user.akkru.api.model.SetIncomeResponse;
import com.akkru.user.akkru.api.model.User;
import com.akkru.user.akkru.api.model.UserData;
import com.akkru.user.akkru.api.model.UserResponse;
import com.akkru.user.akkru.api.model.reward.RewardResponse;

import java.util.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserClient {
    @POST("user/login")
    Call<User> login(@Body Login2 login);

    @POST("v1/user/create")
    Call<UserData> createUser(@Body UserData userdata);


    @GET("v1/user")
    Call<UserResponse> userinfo(@Header("Authorization") String token);

    @PUT("v1/user")
    @FormUrlEncoded
    Call<EditUserResponse> editUser(@Header("Authorization") String Token,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("password_confirmation") String password_confirmation);

    @GET("v1/income")
    Call<IncomeResponse> getIncome(@Header("Authorization") String Token);

    @GET("v1/expense")
    Call<ExpenseResponse> getExpense(@Header("Authorization") String Token);

    @POST("v1/income/create")
    @FormUrlEncoded
    Call<SetIncomeResponse> setIncome(@Header("Authorization") String Token, @Field("name") String name,
                                      @Field("total") String total, @Field("category") String category);


    @Multipart
    @POST("v1/expense/create")
    Call<SetExpenseRespons> setExpense(@Header("Authorization") String Token,
                                       @Part ("name") String name,
                                       @Part MultipartBody.Part file,
                                       @Part ("merchant") String merchant,
                                       @Part("category_id") int Category);

    @GET("v1/reward")
    Call<RewardResponse> getRewards();

}
