package com.example.zakiahmedjava.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.MyViewHolder> {

    protected Context context;
    protected ArrayList<NewsModel> newsList;
    protected RecyclerViewInterface recyclerViewInterface;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsModel> newsList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.newsList = newsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvHeadline, tvContent, tvNewsAuthor, tvPublishedAt;
        protected ImageView imageViewNews;

        protected ConstraintLayout constraintLayout;
        public MyViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvHeadline = itemView.findViewById(R.id.tv_headline);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvNewsAuthor = itemView.findViewById(R.id.tv_newsAuthor);
            tvPublishedAt = itemView.findViewById(R.id.tv_newsPublishedAt);
            imageViewNews = itemView.findViewById(R.id.imageViewNews);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

            constraintLayout.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        recyclerViewInterface.onItemClick(position);
                }
            });
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, null, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvHeadline.setText(newsList.get(position).getTitle());
        holder.tvContent.setText(newsList.get(position).getContent());
        holder.tvNewsAuthor.setText(newsList.get(position).getAuthor());
        holder.tvPublishedAt.setText(newsList.get(position).getPublishedAt());
        Glide.with(context).load(newsList.get(position).getUrlToImage()).into(holder.imageViewNews);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
