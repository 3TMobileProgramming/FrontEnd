package com.example.capstonedesign;
import com.example.capstonedesign.models.ChatRequest;
import com.example.capstonedesign.models.ChatResponse;
import com.example.capstonedesign.models.Notice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NoticeService {
    /*@GET("notices")
    Call<List<Notice>> getAllNotices();

    @GET("notices/search")
    Call<List<Notice>> getSearchNotices(@Query("keyword") String keyword);

    @GET("notices/category/{category}")
    Call<List<Notice>> getCategoryNotices(@Path("category") String category);*/

    @POST("chat")
    Call<ChatResponse> getAiSummaryAndNotices(@Body ChatRequest request);
}
