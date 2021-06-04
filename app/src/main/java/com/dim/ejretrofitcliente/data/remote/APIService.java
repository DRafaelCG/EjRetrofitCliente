package com.dim.ejretrofitcliente.data.remote;

import com.dim.ejretrofitcliente.data.model.Post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {
    @POST("/posts")
    @FormUrlEncoded
    /* Utiliza RxJava*/
    //Observable<Post>
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);
}
