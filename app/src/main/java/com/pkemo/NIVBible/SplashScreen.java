package com.pkemo.NIVBible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson on 12/14/2016.
 */

public class SplashScreen extends AppCompatActivity {
    public static List<NIVBibleBooks> verses;
    static ArrayList<Verse> listvers;

    static {
        verses = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            verses = new XmlPullParserHandler().parse(getAssets().open("niv.xml"));
            listvers = new ArrayList();
            for (int x = 0; x < ((Chapter) ((NIVBibleBooks) verses.get(0)).getCc().get(0)).versses.size(); x++) {
                listvers.add(((Chapter) ((NIVBibleBooks) verses.get(0)).getCc().get(0)).versses.get(x));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, NIVBookListActivity.class));
        finish();
    }
}
