package com.example.capstonedesign;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.capstonedesign.databinding.ActivityMainBinding;
import com.google.android.material.chip.Chip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.ArrayList;

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
            showLoadingState();

            RetrofitClient.getService().getAllNotices().enqueue(new retrofit2.Callback<java.util.List<Notice>>() {
                @Override
                public void onResponse(retrofit2.Call<java.util.List<Notice>> call, retrofit2.Response<java.util.List<Notice>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        nList.clear();
                        nList.addAll(response.body());

                        if (nList.isEmpty()) {
                            showEmptyState();
                        } else {
                            noticeAdapter.notifyDataSetChanged();
                            showSuccessState();
                        }
                        Toast.makeText(MainActivity.this, "서버 연결 성공!", Toast.LENGTH_SHORT).show();
                    } else {
                        showErrorState();
                        Toast.makeText(MainActivity.this, "응답 실패 (코드: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<java.util.List<Notice>> call, Throwable t) {
                    showErrorState();
                    Toast.makeText(MainActivity.this, "연결 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
}
