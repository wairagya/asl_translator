package com.example.ou.asl_translator.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    //public static final String base_url="http://10.0.2.2/asl/";
    //public static final String base_url="http://asl.wahyupermadi.id/";
    public static final String base_url="http://192.168.1.9/asl/";
    private static Retrofit retro = null;

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit getClient(){
        if (retro==null){
            retro = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retro;
    }

    public static ApiRequest getRequestService(){
        return getClient().create(ApiRequest.class);
    }
}
