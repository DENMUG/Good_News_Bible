package com.pkemo.NIVBible;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NIVBookListActivity extends AppCompatActivity {
    public static NIVBooksAdapter arrayAdapter;
    public static List<NIVBibleBooks> booksList;
    public static ListView listView;
    public static boolean oldornew;
    private static String appVersion;
    static {
        booksList = null;
        oldornew = false;
        appVersion = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        setTitle(null);

        booksList = new ArrayList<>();
        int i;
        if (oldornew) {
            for (i = 39; i < SplashScreen.verses.size(); i++) {
                booksList.add(SplashScreen.verses.get(i));
            }
            setTitle("New Testament");
        } else {
            for (i = 0; i < 39; i++) {
                booksList.add(SplashScreen.verses.get(i));
            }
            setTitle("Old Testament");
        }

        arrayAdapter = new NIVBooksAdapter(this, R.layout.books_item, booksList);
        View header = (View) getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView = (ListView) findViewById(R.id.listViewBooks);
        listView.setAdapter(arrayAdapter);
        listView.smoothScrollToPosition(0);
        listView.setOnItemClickListener(new myonclickItemListener());
    }

    public void onBackPressed() {
        Builder alert = new Builder(this);
        alert.setTitle((int) R.string.app_name);
        alert.setMessage((CharSequence) "Exit the App?");
        alert.setNegativeButton((CharSequence) "No", new NoexitListener());
        alert.setPositiveButton((CharSequence) "Exit", new ExitListener());
        alert.create();
        alert.show();
    }

    class NoexitListener implements OnClickListener {
        NoexitListener() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }


    class ExitListener implements OnClickListener {
        ExitListener() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            NIVBookListActivity.this.finish();
        }
    }

    public class myonclickItemListener implements OnItemClickListener {
        myonclickItemListener() {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(NIVBookListActivity.this, NIVChapterActivity.class);
            intent.putExtra("position", position);
            NIVBookListActivity.this.startActivity(intent);

        }
    }

    /*private void showSearch() {
        Intent i = new Intent(this, SearchActivity.class);
        if (this.currentBook != null) {
            i.putExtra("bookNo", this.currentBook.getId());
        }
        startActivity(i);
    }*/

    public static String getAppFullName(Context context, boolean lat) {
        return new StringBuilder(String.valueOf(context.getResources().getString(lat ? R.string.app_name_lat : R.string.app_name))).append(getAppVersion(context) == null ? "" : "  " + getAppVersion(context)).toString();
    }

    public static String getAppVersion(Context context) {
        if (appVersion == null) {
            try {
                appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (Exception e) {
            }
        }
        return appVersion;
    }

    private String getMarketUrl(boolean isWeb) {
        return getString(isWeb ? R.string.market_url_web : R.string.market_url) + getResources().getString(R.string.market_details);
    }

    private String getPreporuka() {
        return getResources().getString(R.string.str_recommendation);
    }

    private String getFacebookUrl() {
        return "http://www.facebook.com/sharer.php?u=" + getMarketUrl(true) + "&t=" + getPreporuka();
    }

    private String getTwetterUrl() {
        return "https://twitter.com/intent/tweet?text=" + getPreporuka() + "&url=" + getMarketUrl(true) + "&via=Kiprotech";
    }

    private String getGooglePlusUrl() {
        return "https://plus.google.com/share?url=" + getMarketUrl(true);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    public boolean onOptionsItemSelected(MenuItem item) {
        String market_detailes_link = getString(R.string.market_details);
        int id = item.getItemId();
        int i;
        Intent intent = null;

        if (id == R.id.mnm_new_testament) {
            booksList.clear();
            for (i = 39; i < SplashScreen.verses.size(); i++) {
                booksList.add(SplashScreen.verses.get(i));
            }
            setTitle("New Testament");
            oldornew = true;
            arrayAdapter.notifyDataSetChanged();
        } else if (id == R.id.mnm_old_testament) {
            booksList.clear();
            for (i = 0; i <= 38; i++) {
                booksList.add(SplashScreen.verses.get(i));
            }
            setTitle("Old Testament");
            oldornew = false;
            arrayAdapter.notifyDataSetChanged();
        }
        //else if (item.getItemId() == R.id.mnm_light) {
            /*Settings.changeContrast();
            updateContrast();
            showBibleBooks();
            if (this.currentBook == null) {
                return true;
            }
            showBookChapters(this.currentBook, true);
            return true;*/
        //}
        else if (item.getItemId() == R.id.mnm_bookmark) {
            intent = new Intent(this, NIVBookMarksActivity.class);
            intent.addFlags(67108864);
            startActivity(intent);
            return true;
        }
//        else if (item.getItemId() == R.id.mnm_search) {
//            //showSearch();
//            return true;
//        }
        else {
            if (item.getItemId() == R.id.mnm_share_facebook) {
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(getFacebookUrl()));
            } else if (item.getItemId() == R.id.mnm_share_tweteer) {
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(getTwetterUrl()));
            } else if (item.getItemId() == R.id.mnm_share_googlep) {
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(getGooglePlusUrl()));
            } else if (item.getItemId() == R.id.mnm_rate) {
//                intent = new Intent("android.intent.action.VIEW");
//                intent.setData(Uri.parse(getString(R.string.market_url) + market_detailes_link));
                Uri uri = Uri.parse("market://details?id=com.goodlen.bible.news");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.goodlen.bible.news")));
                }
            } else if (item.getItemId() == R.id.mnm_more_app) {
//                intent = new Intent("android.intent.action.VIEW");
//                intent.setData(Uri.parse(getString(R.string.market_url) + getString(R.string.market_developer)));
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:funny+videos")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=funny+videos")));
                }
            } else if (item.getItemId() == R.id.mnm_share_more) {
                intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", getAppFullName(this, true));
                intent.putExtra("android.intent.extra.TEXT", getPreporuka() + "\n\n" + getMarketUrl(true));
                try {
                    startActivity(Intent.createChooser(intent, getText(R.string.str_share)));
                    return true;
                } catch (ActivityNotFoundException e) {
                    return true;
                }
            }
            if (intent == null) {
                return super.onOptionsItemSelected(item);
            }
            try {
                startActivity(intent);
                return true;
            } catch (Exception e2) {
                return false;
            }

        }
        return true;
    }
}

