package com.pkemo.NIVBible;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Nelson on 12/14/2016.
 */

public class NIVLoadXmlFile extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog progressDialog;

    public NIVLoadXmlFile(Context context) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Loading...");
    }

    protected Void doInBackground(Void... params) {
        try {
            SplashScreen.verses = new XmlPullParserHandler().parse(this.context.getAssets().open("one.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPreExecute() {
        this.progressDialog.show();
        super.onPreExecute();
    }

    protected void onPostExecute(Void aVoid) {
        this.progressDialog.dismiss();
        this.context.startActivity(new Intent(this.context, NIVBookListActivity.class));
    }
}
