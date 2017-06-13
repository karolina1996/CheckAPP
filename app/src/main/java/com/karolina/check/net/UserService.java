package com.karolina.check.net;

import com.karolina.check.models.SimpleResponse;
import com.karolina.check.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by RicardoAndrés on 13/06/2017.
 */

public interface UserService {

    @POST("users/login")
    Call<SimpleResponse> login(@Body User user);

    @POST("users/signin")
    Call<SimpleResponse> signin(@Body User user);

}
