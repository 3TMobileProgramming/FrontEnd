package com.example.capstonedesign;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface NoticeService {
    @GET("posts")
    Call<List<Notice>> getAllNotices();

    @GET("posts")
    Call<List<Notice>> searchNotices(
            @Query("q") String query
    );
}
