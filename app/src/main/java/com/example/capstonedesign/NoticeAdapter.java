package com.example.capstonedesign;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.example.capstonedesign.databinding.ItemNoticeBinding;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{
    private ArrayList<Notice> nList = new ArrayList<>();

    public NoticeAdapter(ArrayList<Notice> nlist){
        this.nList = nlist;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ItemNoticeBinding binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoticeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.bind(nList.get(position));
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {

        private final ItemNoticeBinding binding;

        public NoticeViewHolder(ItemNoticeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Notice notice) {
            binding.noticeCategory.setText(notice.getCategory());
            binding.noticeTitle.setText(notice.getTitle());
            binding.noticeDate.setText(notice.getDate());

            binding.getRoot().setOnClickListener(v -> {
                String url = notice.getNoticeUrl();
                Uri uri = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                v.getContext().startActivity(intent);
            });
        }
    }
}
