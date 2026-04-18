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

        // 1. XML에 있는 View들 찾아오기
        EditText searchBar = findViewById(R.id.search_bar);
        LinearLayout layoutSuggestions = findViewById(R.id.layout_suggestions);

        Chip chipSchedule = findViewById(R.id.chip_schedule);
        Chip chipScholarship = findViewById(R.id.chip_scholarship);
        Chip chipDormitory = findViewById(R.id.chip_dormitory);
        Chip chipEnrollment = findViewById(R.id.chip_enrollment);

// 2. 초기 상태: 추천 영역 숨기기
        layoutSuggestions.setVisibility(View.GONE);

        // 3. 검색창에 포커스가 갈 때 추천 영역 띄우기
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    layoutSuggestions.setVisibility(View.VISIBLE);
                } else {
                    // 다른 곳을 터치해서 포커스를 잃으면 다시 숨기기
                    layoutSuggestions.setVisibility(View.GONE);
                }
            }
        });

// 터치 이벤트도 추가 (포커스가 이미 있는 상태에서 눌렀을 때를 대비)
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSuggestions.setVisibility(View.VISIBLE);
            }
        });

        // 4. '장학금' 칩 클릭 이벤트
        chipScholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setText("장학금");
                searchBar.setSelection(searchBar.getText().length()); // 커서를 글자 맨 뒤로 이동
                layoutSuggestions.setVisibility(View.GONE); // 칩 영역 숨기기
                searchBar.clearFocus(); // 키보드 내릴 준비 (선택사항)
            }
        });

// '학사일정' 칩 클릭 이벤트
        chipSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setText("학사일정");
                searchBar.setSelection(searchBar.getText().length());
                layoutSuggestions.setVisibility(View.GONE);
                searchBar.clearFocus();
            }
        });

// 나머지 칩(기숙사, 수강신청)도 동일한 방식으로 복사해서 글자만 바꿔서 넣어주세요!
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
