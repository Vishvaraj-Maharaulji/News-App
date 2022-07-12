package com.example.vnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

interface NewsItemClicked {
    void onItemClicked(NewsData item);
}

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    ArrayList<NewsData> newsItem = new ArrayList<>();
    private NewsItemClicked mListener;

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView titleView;
        private final TextView authorView;
        private final TextView descView;
        private final TextView dateView;
        private final TextView sourceView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            titleView = (TextView) itemView.findViewById(R.id.title);
            authorView = (TextView) itemView.findViewById(R.id.authors);
            descView = (TextView) itemView.findViewById(R.id.description);
            dateView = (TextView) itemView.findViewById(R.id.publishDate);
            sourceView = (TextView) itemView.findViewById(R.id.source_name);

        }

    }

    public NewsListAdapter(NewsItemClicked listener) {
        mListener = listener;
    }

    void updateNews(ArrayList<NewsData> updatedNews) {
        newsItem.clear();
        newsItem.addAll(updatedNews);

        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(newsItem.get(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {

        Glide.with(holder.itemView.getContext()).load(newsItem.get(position).getUrlToImage()).into(holder.imageView);

        holder.titleView.setText(newsItem.get(position).getTitle());

        holder.authorView.setText(newsItem.get(position).getAuthor());

        holder.descView.setText(newsItem.get(position).getDescription());

        holder.dateView.setText(trimmingDate(newsItem.get(position).getPublishedAt()));

        holder.sourceView.setText(newsItem.get(position).getSourceName());

    }

    @Override
    public int getItemCount() {
        return newsItem.size();
    }

    private String trimmingDate(String s) {
       return s.replaceAll("[A-Za-z]"," ");
    }

}