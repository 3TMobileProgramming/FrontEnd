package com.example.capstonedesign;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.capstonedesign.databinding.ActivityMainBinding;
import com.example.capstonedesign.models.ChatRequest;
import com.example.capstonedesign.models.ChatResponse;
import com.example.capstonedesign.models.Notice;
import com.example.capstonedesign.models.SummaryResponse;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoticeAdapter noticeAdapter;
    private ArrayList<Notice> nList;
    private String lastQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.searchBar.setShowSoftInputOnFocus(false);

        binding.aiSummary.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        nList = new ArrayList<>();

        /*nList.add(new Notice("[장학]", "2026학년도 1학기 국가장학금 신청 안내", "2026-04-01", "https://www.syu.ac.kr"));
        nList.add(new Notice("[학사]", "중간고사 기간 도서관 연장 운영 안내", "2026-04-03", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));*/

        binding.noticeListView.setLayoutManager(new LinearLayoutManager(this));

        noticeAdapter = new NoticeAdapter(nList);

        binding.noticeListView.setAdapter(noticeAdapter);

        binding.btnSearch.setOnClickListener(v -> {
            String query = binding.searchBar.getText().toString().trim();
            if (query.isEmpty()) return;

            lastQuery = query;

            binding.userQueryBox.setText(query);
            showLoadingState();

            ChatRequest request = new ChatRequest(query);

            RetrofitClient.getService().getAiSummaryAndNotices(request).enqueue(new retrofit2.Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ChatResponse result = response.body();

                        //binding.aiSummary.setText(result.getAnswer());
                        Markwon markwon = Markwon.create(MainActivity.this);
                        markwon.setMarkdown(binding.aiSummary, result.getAnswer());

                        nList.clear();
                        nList.addAll(result.getNotices());
                        noticeAdapter.notifyDataSetChanged();

                        showSuccessState();
                    } else {
                        showEmptyState();
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    showErrorState();
                }
            });

            binding.layoutSuggestions.setVisibility(View.GONE);
            binding.searchBar.clearFocus();
        });

        binding.layoutSuggestions.setVisibility(View.GONE);

        binding.searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.layoutSuggestions.setVisibility(View.VISIBLE);
                } else {
                    binding.layoutSuggestions.setVisibility(View.GONE);
                }
            }
        });

        binding.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutSuggestions.setVisibility(View.VISIBLE);
            }
        });

        binding.chipScholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchBar.setText("장학금");
                binding.searchBar.setSelection(binding.searchBar.getText().length());
                binding.layoutSuggestions.setVisibility(View.GONE);
                binding.searchBar.clearFocus();
            }
        });

        binding.chipSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchBar.setText("학사일정");
                binding.searchBar.setSelection(binding.searchBar.getText().length());
                binding.layoutSuggestions.setVisibility(View.GONE);
                binding.searchBar.clearFocus();
            }
        });

        binding.chipDormitory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchBar.setText("기숙사");
                binding.searchBar.setSelection(binding.searchBar.getText().length());
                binding.layoutSuggestions.setVisibility(View.GONE);
                binding.searchBar.clearFocus();
            }
        });

        binding.chipEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchBar.setText("수강신청");
                binding.searchBar.setSelection(binding.searchBar.getText().length());
                binding.layoutSuggestions.setVisibility(View.GONE);
                binding.searchBar.clearFocus();
            }
        });

        binding.btnRetry.setOnClickListener(v -> {
            if (!lastQuery.isEmpty()) {
                binding.searchBar.setText(lastQuery);
                binding.btnSearch.performClick();
            }
        });

        showStartState();
    }

    private void showStartState() {
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.userQueryBox.setVisibility(View.GONE);

        binding.ivLoading.setVisibility(View.GONE);
        binding.layoutEmpty.setVisibility(View.GONE);
        binding.layoutError.setVisibility(View.GONE);

        binding.tvStartGuide.setVisibility(View.VISIBLE);
    }

    private void showLoadingState() {
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.ivLoading.setVisibility(View.VISIBLE);
        binding.layoutEmpty.setVisibility(View.GONE);

        binding.tvStartGuide.setVisibility(View.GONE);


        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(binding.ivLoading);
    }

    private void showSuccessState(){
        binding.ivLoading.setVisibility(View.GONE);
        binding.noticeListView.setVisibility(View.VISIBLE);
        binding.aiSummary.setVisibility(View.VISIBLE);
        binding.userQueryBox.setVisibility(View.VISIBLE);
        binding.layoutError.setVisibility(View.GONE);
        binding.tvStartGuide.setVisibility(View.GONE);
    }

    private void showEmptyState() {
        binding.ivLoading.setVisibility(View.GONE);
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.layoutEmpty.setVisibility(View.VISIBLE);
        binding.layoutError.setVisibility(View.GONE);
        binding.tvStartGuide.setVisibility(View.GONE);
    }

    private void showErrorState() {
        binding.ivLoading.setVisibility(View.GONE);
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.layoutEmpty.setVisibility(View.GONE);
        binding.layoutError.setVisibility(View.VISIBLE);
        binding.userQueryBox.setVisibility(View.GONE);
        binding.tvStartGuide.setVisibility(View.GONE);
    }
}
