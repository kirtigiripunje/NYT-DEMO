package com.example.nyt_demo.list.ui;

import com.example.nyt_demo.model.Result;

import java.util.List;

public interface OnSingleNYTClicked {

    public void onNYTRowClicked(NYTAdapter adapter, List<Result> mItem, int position);

}