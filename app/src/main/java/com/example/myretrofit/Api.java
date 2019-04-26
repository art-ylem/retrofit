package com.example.myretrofit;

import com.example.myretrofit.events.Events;
import com.example.myretrofit.postInformation.InfoPost;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("public-api/v1.4/events")
    Observable<Events> getPosts(@Query("page_size") int pageSize, @Query("fields") String fields);

    @GET("public-api/v1.4/events/{alias}")
    Observable<InfoPost> getPostInformationById(@Path("alias") String alias);


}
