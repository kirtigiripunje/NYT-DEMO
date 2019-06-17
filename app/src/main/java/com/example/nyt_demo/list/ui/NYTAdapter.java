package com.example.nyt_demo.list.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyt_demo.R;
import com.example.nyt_demo.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NYTAdapter extends RecyclerView.Adapter<NYTAdapter.ViewHolder> {

    private Context con;
    private OnSingleNYTClicked onSingleNYTClicked;
    private ViewHolder holder;
    private List<Result> mItem;


    public NYTAdapter(Context con, List<Result> mItem, OnSingleNYTClicked onSingleNYTClicked) {
        this.con = con;
        this.mItem = mItem;
        this.onSingleNYTClicked = onSingleNYTClicked;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nyt_row_child, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        this.holder = holder;

        holder.nytCarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSingleNYTClicked.onNYTRowClicked(NYTAdapter.this, mItem, position);
            }
        });


        Result result = mItem.get(position);

        holder.typeTextView.setText(result.getType());
        holder.publishedDateTextView.setText(result.getPublishedDate());
        holder.titleTextView.setText(result.getTitle());
        holder.abstractTextView.setText(result.getAbstract());

        if (result.getMedia() != null) {
            if (result.getMedia().size() > 0) {
                if (result.getMedia().get(0).getMediaMetadata() != null) {
                    if (result.getMedia().get(0).getMediaMetadata().size() > 0) {
                        Picasso.with(con)
                                .load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                                .into(holder.nytImageView, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nytCarView)
        CardView nytCarView;

        @BindView(R.id.typeTextView)
        TextView typeTextView;

        @BindView(R.id.publishedDateTextView)
        TextView publishedDateTextView;

        @BindView(R.id.titleTextView)
        TextView titleTextView;

        @BindView(R.id.abstractTextView)
        TextView abstractTextView;

        @BindView(R.id.nytImageView)
        ImageView nytImageView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
