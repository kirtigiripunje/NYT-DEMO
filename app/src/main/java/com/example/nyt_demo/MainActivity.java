package com.example.nyt_demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nyt_demo.list.ui.NYTListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.searchImageView)
    ImageView searchImageView;

    @BindView(R.id.moreOptionImageView)
    ImageView moreOptionImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        NYTListFragment fragment = new NYTListFragment();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }


    @OnClick(R.id.searchImageView)
    void searchClick() {
        Toast.makeText(this, getString(R.string.search_text), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.moreOptionImageView)
    void moreOptionClick() {
        Toast.makeText(this, getString(R.string.more_text), Toast.LENGTH_SHORT).show();
    }
}
