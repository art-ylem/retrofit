package com.example.javalesson.retrofit;

import android.text.Editable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("public-api/v1.4/events")
    Observable<Events> getPosts(@Query("page_size") int pageSize);

    @GET("public-api/v1.4/events")
    Observable<InfoPost> getPostInformationById(@Query("event_id") String id);


}
