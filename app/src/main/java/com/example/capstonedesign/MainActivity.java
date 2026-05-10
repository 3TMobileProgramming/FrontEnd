package com.example.capstonedesign;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.capstonedesign.databinding.ActivityMainBinding;
import com.example.capstonedesign.models.Notice;
import com.example.capstonedesign.models.SummaryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoticeAdapter noticeAdapter;
    private ArrayList<Notice> nList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        nList = new ArrayList<>();

        nList.add(new Notice("[장학]", "2026학년도 1학기 국가장학금 신청 안내", "2026-04-01", "https://www.syu.ac.kr"));
        nList.add(new Notice("[학사]", "중간고사 기간 도서관 연장 운영 안내", "2026-04-03", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));
        nList.add(new Notice("[행사]", "모바일 프로그래밍 캡스톤 디자인 특강", "2026-04-04", "https://www.syu.ac.kr"));

        binding.noticeListView.setLayoutManager(new LinearLayoutManager(this));

        noticeAdapter = new NoticeAdapter(nList);

        binding.noticeListView.setAdapter(noticeAdapter);

        binding.btnSearch.setOnClickListener(v -> {
            String query = binding.searchBar.getText().toString().trim();
            if (query.isEmpty()) return;

            binding.userQueryBox.setText(query);
            showLoadingState();

            RetrofitClient.getService().getSearchNotices(query).enqueue(new retrofit2.Callback<List<Notice>>() {
                @Override
                public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        nList.clear();
                        nList.addAll(response.body());
                        noticeAdapter.notifyDataSetChanged();
                        showSuccessState();
                    } else {
                        showEmptyState();
                    }
                }
                @Override
                public void onFailure(Call<List<Notice>> call, Throwable t) {
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
        fetchMockAiSummary();
    }
    private void showLoadingState() {
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.ivLoading.setVisibility(View.VISIBLE);
        binding.layoutEmpty.setVisibility(View.GONE);


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
    }

    private void showEmptyState() {
        binding.ivLoading.setVisibility(View.GONE);
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.layoutEmpty.setVisibility(View.VISIBLE);
        binding.layoutError.setVisibility(View.GONE);
    }

    private void showErrorState() {
        binding.ivLoading.setVisibility(View.GONE);
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);
        binding.layoutEmpty.setVisibility(View.GONE);
        binding.layoutError.setVisibility(View.VISIBLE);
        binding.userQueryBox.setVisibility(View.GONE);
    }

    private void updateAiSummaryUI(SummaryResponse data) {
        if (data != null) {
            binding.aiSummary.setText(data.getContent());
        }
    }

    private void fetchMockAiSummary() {
        SummaryResponse mockData = new SummaryResponse();
        mockData.setContent("이번 주 핵심 공지: 캡스톤 디자인 기획안 제출 기한이 이번 주 일요일까지로 연장되었습니다. 서류 양식을 확인하세요!");

        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            updateAiSummaryUI(mockData);
            android.widget.Toast.makeText(MainActivity.this, "AI 요약 연동 완료!", android.widget.Toast.LENGTH_SHORT).show();
        }, 2000);
    }
}
