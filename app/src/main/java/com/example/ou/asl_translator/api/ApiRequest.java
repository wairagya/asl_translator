package com.example.ou.asl_translator.api;

import com.example.ou.asl_translator.model.ResponseApiDict;
import com.example.ou.asl_translator.model.ResponseApiLearnModel;
import com.example.ou.asl_translator.model.ResponseApiModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("index.php")
    Call<ResponseApiModel> checker (@Field("phares") String phares);
    @FormUrlEncoded
    @POST("learn/index.php")
    Call<ResponseApiLearnModel> checker2 (@Field("phares") String phares);
    @FormUrlEncoded
    @POST("dict.php")
//    Call<ArrayList<String>> dict (@Field("param") String param);
    Call<ResponseApiDict> dict (@Field("param") String param);
}
