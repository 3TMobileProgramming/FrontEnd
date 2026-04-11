package com.example.capstonedesign;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.capstonedesign.databinding.ActivityMainBinding;

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
            Toast.makeText(MainActivity.this, "검색 테스트", Toast.LENGTH_SHORT).show();
            showLoadingState();

            v.postDelayed(new Runnable(){
                public void run(){
                    if(nList.isEmpty()){
                        showEmptyState();
                    }
                    else{
                        showSuccessState();
                    }
                    Toast.makeText(MainActivity.this, "검색 완료", Toast.LENGTH_SHORT).show();
                }
            },8000);
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
    }

    private void showEmptyState() {
        binding.ivLoading.setVisibility(View.GONE);
        binding.noticeListView.setVisibility(View.GONE);
        binding.aiSummary.setVisibility(View.GONE);

        binding.layoutEmpty.setVisibility(View.VISIBLE);
    }
}
