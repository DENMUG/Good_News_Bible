package com.pkemo.NIVBible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class NIVChapterActivity extends AppCompatActivity {
    public static int index;
    public static String title;
    public static List<String> listOfChapters;
    InterstitialAd interstitial;

    private static final String AD_UNIT_ID = "ca-app-pub-7275956025529918/4962472011";
    private InterstitialAd interstitialAd;



    public NIVChapterActivity() {
        this.listOfChapters = null;
    }

    static {
        index = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        setContentView((int) R.layout.activity_chapter);
        this.listOfChapters = new ArrayList<>();

        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId(AD_UNIT_ID);
        AdRequest adRequest = new AdRequest.Builder().build();

        interstitialAd.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }

            }


            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (NIVBookListActivity.oldornew) {
                index = bundle.getInt("position") + 39;
            } else {
                index = bundle.getInt("position");
            }
        }

        title = ((NIVBibleBooks) SplashScreen.verses.get(index)).getBname();
        setTitle(title);
        for (int y = 1; y <= ((NIVBibleBooks) SplashScreen.verses.get(index)).getCc().size(); y++) {
            this.listOfChapters.add(y + BuildConfig.FLAVOR);
        }

        GridView gridView = (GridView) findViewById(R.id.grid_viewer);
        gridView.setAdapter(new NIVChapterAdapter(this, R.layout.chapter_item, this.listOfChapters));
        gridView.smoothScrollToPosition(0);
        gridView.setOnItemClickListener(new myonclickItemListener());
    }

    public class myonclickItemListener implements AdapterView.OnItemClickListener {
        myonclickItemListener(){
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(NIVChapterActivity.this, VersesActivity.class);
            intent.putExtra("position", position);
            NIVChapterActivity.this.startActivity(intent);

        }
    }
}
