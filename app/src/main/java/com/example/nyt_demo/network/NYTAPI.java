package com.example.nyt_demo.network;


import com.example.nyt_demo.model.NYTModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class NYTAPI {

        public static String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/mostviewed/";

    public NYTAPI(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static AuthService authService = null;

    public static AuthService getAuthService() {

        if (authService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            authService = retrofit.create(AuthService.class);
        }

        return authService;
    }


    public interface AuthService {

        /*ALL USER */
        //https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=aRfsXFcJFAGH8SFNb7V8UGM4Iz3kCaEP&q=Sacramento

//        @GET("?{api-key}/{q}")
//        Call<Object> getArticles(@Query("api-key") String email);
//        Call<Object> getArticles(@Path("api-key") String api_key, @Path("q") String search);


        // url=https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=aRfsXFcJFAGH8SFNb7V8UGM4Iz3kCaEP
        /*@Header("Authorization") String token,*/
        @GET("{section}/{period}") //
        Call<NYTModel> getArticles1(
                @Path("section") String section,
                @Path("period") String period,
                @Query("api-key") String api_key
        );
    }
}
