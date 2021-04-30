package com.example.myphotoapp.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService{
    @FormUrlEncoded
    @POST("/login")
    fun requestLogin(
            @Field("name") userid:String,
            @Field("phone_number") userpw:String

    ) : Call<Login>
}