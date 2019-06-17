package com.example.nyt_demo.list.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.nyt_demo.MainActivity;
import com.example.nyt_demo.R;
import com.example.nyt_demo.detail.ui.NYTDetailFragment;
import com.example.nyt_demo.model.NYTModel;
import com.example.nyt_demo.model.Result;
import com.example.nyt_demo.network.NYTAPI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class NYTListFragment extends Fragment implements OnSingleNYTClicked, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.container)
    SwipeRefreshLayout container;

    @BindView(R.id.noDataFrameLayout)
    FrameLayout noDataFrameLayout;


    private View v;
    private MainActivity activity;
    private NYTAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nyt_home_fragment, container, false);
        ButterKnife.bind(this, v);

        activity = (MainActivity) getActivity();

        this.container.setOnRefreshListener(this);
        this.container.setColorSchemeResources(
                android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_purple);
        this.container.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        getNYTList();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onRefresh() {
        getNYTList();
    }


    private void getNYTList() {

        if (container != null)
            container.setRefreshing(true);

        Call<NYTModel> authVOCall = new NYTAPI(NYTAPI.BASE_URL).getAuthService().getArticles1("all-sections", "30.json", "aRfsXFcJFAGH8SFNb7V8UGM4Iz3kCaEP");

        authVOCall.enqueue(new Callback<NYTModel>() {
            @Override
            public void onResponse(Call<NYTModel> call, retrofit2.Response<NYTModel> response) {
                NYTModel object = response.body();

                adapter = new NYTAdapter(getActivity(), object.getResults(), NYTListFragment.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (container != null)
                    container.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<NYTModel> call, Throwable t) {
                Log.d("", "");
                if (container != null)
                    container.setRefreshing(false);

            }
        });
    }


    @Override
    public void onNYTRowClicked(NYTAdapter adapter, List<Result> mItem, int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", mItem.get(position));
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        NYTDetailFragment fragment = new NYTDetailFragment();
        ft.addToBackStack("");
        fragment.setArguments(bundle);
        ft.add(R.id.container, fragment);
        ft.commit();

    }


}
