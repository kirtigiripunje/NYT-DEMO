package com.example.nyt_demo.detail.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyt_demo.MainActivity;
import com.example.nyt_demo.R;
import com.example.nyt_demo.model.Result;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NYTDetailFragment extends Fragment {

    private View v;
    private MainActivity activity;
    private Bundle bundle;
    private Result result;

    @BindView(R.id.publish_date_time)
    TextView publishDateTime;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.main_image)
    ImageView mainImage;

    @BindView(R.id.description_webview)
    WebView descriptionWebview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nyt_detail_fragment, container, false);
        ButterKnife.bind(this, v);

        activity = (MainActivity) getActivity();

        Bundle bundle = getArguments();

        if (bundle != null)
            result = (Result) bundle.getSerializable("detail");


        setData(result);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void setData(Result result) {

        if (result != null) {

            publishDateTime.setText(result.getPublishedDate());
            title.setText(result.getTitle());


            if (result.getMedia() != null) {
                if (result.getMedia().size() > 0) {
                    if (result.getMedia().get(0).getMediaMetadata() != null) {
                        if (result.getMedia().get(0).getMediaMetadata().size() > 0) {
                            Picasso.with(activity)
                                    .load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                                    .into(mainImage, new com.squareup.picasso.Callback() {
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
            setUpWebView(descriptionWebview, result.getUrl());
        }

    }


    private void setUpWebView(WebView webView, String html) {

        // Basic Setting of WebView

        // Get the web view settings instance
        String settings = new String();
        webView.getSettings();

        // Enable java script in web view
        webView.getSettings().getJavaScriptEnabled();

        // Enable and setup web view cache
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().getCacheMode();

//        // Zoom web view text
        webView.getSettings().getTextZoom();

        // Enable disable images in web view
        webView.getSettings().getBlockNetworkImage();
        // Whether the WebView should load image resources
        webView.getSettings().getLoadsImagesAutomatically();

        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.getSettings().getSafeBrowsingEnabled();
        }
        //settings.pluginState = WebSettings.PluginState.ON
        webView.getSettings().getUseWideViewPort();
        webView.getSettings().getLoadWithOverviewMode();
        webView.getSettings().getMediaPlaybackRequiresUserGesture();

        // More optional settings, you can enable it by yourself
        webView.getSettings().getDomStorageEnabled();
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().getLoadWithOverviewMode();
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().getAllowUniversalAccessFromFileURLs();
        webView.getSettings().getAllowFileAccess();

        /*
            if SDK version is greater of 19 then activate hardware acceleration
            otherwise activate software acceleration
        */

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webView.loadData(html, "text/html", "UTF-8");
    }


}
