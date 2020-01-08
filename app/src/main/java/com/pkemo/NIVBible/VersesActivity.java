package com.pkemo.NIVBible;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.flask.colorpicker.ColorPickerView.WHEEL_TYPE;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pkemo.NIVBible.VersesAdapter.OnItemClickListener;

import java.util.ArrayList;

public class VersesActivity extends AppCompatActivity {

    public static String bookname;
    static int chapter_index;
    static int chaptersize;
    static int colorcode;
    public static String f15j;
    static ArrayList<Verse> listvers;
    static SparseBooleanArray sba;
    static int selectedcolor;
    static float textsize;
    private NIVBookMarkerClass NIVBookMarkerClass;
    private AdapterContextMenuInfo currentMenuInfo;


    //FloatingActionButton fab;
    //FloatingActionButton fab2;
    Bundle getBund;
    LinearLayout ll;
    RecyclerView recyclerView;
    TextView textView;
    VersesAdapter VersesAdapter;
    TextView vew;
    View view;
    private AdView mAdView;



    class C07141 implements OnItemClickListener {
        C07141() {
        }

        public void onItemClick(Verse item, int c, View v) {
            VersesActivity.this.seletedItemsMethod(item, c, v);
        }
    }

    class C03952 implements OnClickListener {
        C03952() {
        }

        public void onClick(View v) {
            VersesActivity.this.nextChapter();
        }
    }

    class C03963 implements OnClickListener {
        C03963() {
        }

        public void onClick(View v) {
            VersesActivity.this.previousChapter();
        }
    }

    class C07157 implements ColorPickerClickListener {
        C07157() {
        }

        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
            VersesActivity.selectedcolor = selectedColor;
            VersesActivity.colorcode = VersesActivity.selectedcolor;
            VersesActivity.this.VersesAdapter.notifyDataSetChanged();
            if (VersesActivity.sba.size() != 0) {
                for (int x = 0; x < VersesActivity.sba.size(); x++) {
                    int index = VersesActivity.sba.keyAt(x);
                    String chapter = VersesActivity.f15j + BuildConfig.FLAVOR;
                    if (VersesActivity.sba.valueAt(x)) {
                        NIVBookMarkerClass.setBookMarks(VersesActivity.bookname, chapter, ((Verse) VersesActivity.listvers.get(index)).getVnumber(), ((Verse) VersesActivity.listvers.get(index)).getVerse(), VersesActivity.selectedcolor);
                    } else {
                        Toast.makeText(VersesActivity.this, "New selections ONLY will be added to bookmark list", Toast.LENGTH_SHORT).show();
                    }
                    VersesActivity.sba.put(index, false);
                }
            }
        }
    }

    private void chooser() {
        ColorPickerDialogBuilder.with(this).setTitle("Choose color").initialColor(-16740675).wheelType(WHEEL_TYPE.FLOWER).density(12).setOnColorSelectedListener(new C07168()).setPositiveButton((CharSequence) "Set Color", new C07157()).setNegativeButton((CharSequence) "cancel", new C03996()).build().show();
    }

    class C07168 implements OnColorSelectedListener {
        C07168() {
        }

        public void onColorSelected(int selectedColor) {
        }
    }

    class C03996 implements DialogInterface.OnClickListener {
        C03996() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    static {
        textsize = 18.0f;
        listvers = new ArrayList();
        sba = new SparseBooleanArray();
        selectedcolor = -16775424;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);

        this.getBund = getIntent().getExtras();
        if (this.getBund != null) {
            chapter_index = this.getBund.getInt("position");
        }
        this.textView = (TextView) findViewById(R.id.chapters);
        this.NIVBookMarkerClass = new NIVBookMarkerClass(this);
        this.NIVBookMarkerClass.open();
        bookname = NIVChapterActivity.title;
        f15j = (chapter_index + 1) + BuildConfig.FLAVOR;
        this.VersesAdapter = new VersesAdapter(this, listvers, new C07141());
        chaptersize = ((NIVBibleBooks) SplashScreen.verses.get(NIVChapterActivity.index)).getCc().size();
        loadVerses(chapter_index);
        this.textView.setText(bookname + ":" + f15j + "/"+ chaptersize);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.VersesAdapter);
        this.recyclerView.setOnCreateContextMenuListener(new C00902());
        ((Button) findViewById(R.id.nextChapter)).setOnClickListener(new C03952());
        ((Button) findViewById(R.id.previousChapter)).setOnClickListener(new C03963());

        this.ll = (LinearLayout) findViewById(R.id.ll);
        this.ll.setLayoutParams((LayoutParams) this.ll.getLayoutParams());
        this.ll.setVisibility(View.GONE);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    private void seletedItemsMethod(Verse item, int position, View v) {
        this.view = v;
        if (sba.get(position)) {
            ((TextView) v.findViewById(R.id.Verse)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            sba.delete(position);
        } else {
            this.vew = (TextView) v.findViewById(R.id.Verse);
            sba.append(position, true);
            this.vew.setTextColor(Color.parseColor("#ce0555"));
        }
        if (sba.size() > 0) {
            this.ll.setVisibility(View.VISIBLE);
        } else {
            this.ll.setVisibility(View.GONE);
        }
    }

    private void loadVerses(int cindex) {
        listvers.clear();
        for (int x = 0; x < ((Chapter) ((NIVBibleBooks) SplashScreen.verses.get(NIVChapterActivity.index)).getCc().get(cindex)).versses.size(); x++) {
            listvers.add(((Chapter) ((NIVBibleBooks) SplashScreen.verses.get(NIVChapterActivity.index)).getCc().get(cindex)).versses.get(x));
        }
        f15j = (cindex + 1) + BuildConfig.FLAVOR;
        this.VersesAdapter.notifyDataSetChanged();
       this.textView.setText(bookname + ":" + f15j + "/"+ chaptersize);
    }

    private void previousChapter() {
        if (sba.size() > 0) {
            sba.clear();
        }
        if (chapter_index >= 1) {
            chapter_index--;
            loadVerses(chapter_index);
            this.recyclerView.smoothScrollToPosition(0);
            return;
        }
        Toast.makeText(this, "Scroll forward. You have reached the FIRST chapter of this book", Toast.LENGTH_LONG).show();
    }

    private void nextChapter() {
        if (sba.size() > 0) {
            sba.clear();
        }
        if (chapter_index < chaptersize - 1) {
            chapter_index++;
            loadVerses(chapter_index);
            this.recyclerView.smoothScrollToPosition(0);
            return;
        }
        Toast.makeText(this, "Scroll back. You have reached the LAST chapter of this book", Toast.LENGTH_LONG).show();
    }

    public boolean onContextItemSelected(MenuItem item) {
        Intent i = null;
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
        if (menuInfo != null) {
            this.currentMenuInfo = menuInfo;
        }
        if (item.getItemId() == R.id.mnm_bookmark) {
            bookMark();
        } else if (item.getItemId() == R.id.mnm_favorite) {
            //updateFav();
        } else if (item.getItemId() == R.id.mnm_share_more) {
            shareVerse();
        }
        if (i == null) {
            return super.onOptionsItemSelected(item);
        }
        startActivity(i);
        return true;
    }

    class C00902 implements OnCreateContextMenuListener {
        C00902() {
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
            VersesActivity.this.getMenuInflater().inflate(R.menu.verse_list_menu, menu);
            if (menuInfo != null) {
               // if (((Verse) VersesActivity.this.chapter.getList().get(((AdapterContextMenuInfo) menuInfo).position)).getFav()) {
                //    menu.findItem(R.id.mnm_favorite).setTitle(VersesActivity.this.getString(R.string.str_favorite_rm));
                //}
            }
        }
    }

    protected void bookMark() {
        if (sba.size() != 0) {
            for (int x = 0; x < sba.size(); x++) {
                int index = sba.keyAt(x);
                String chapter = (chapter_index + 1) + BuildConfig.FLAVOR;
                if (sba.valueAt(x)) {
                    NIVBookMarkerClass.setBookMarks(VersesActivity.bookname, chapter, ((Verse) SplashScreen.listvers.get(index)).getVnumber(), ((Verse) SplashScreen.listvers.get(index)).getVerse(), selectedcolor);
                    Toast.makeText(VersesActivity.this, "Items added to bookmark list", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VersesActivity.this, "New selections ONLY will be added to bookmark list", Toast.LENGTH_SHORT).show();
                }
                sba.put(index, false);
            }
            return;
        }
        Toast.makeText(VersesActivity.this, "Please select vers to bookmark", Toast.LENGTH_SHORT).show();
    }

    private void shareVerse() {
        String versetoshare = BuildConfig.FLAVOR;
        if (sba.size() != 0) {
            for (int x = 0; x < sba.size(); x++) {
                int index = sba.keyAt(x);
                versetoshare = versetoshare + VersesActivity.bookname + ":" + (chapter_index + 1) + "." + (index + 1) + "\n" + ((Verse) SplashScreen.listvers.get(index)).getVerse() + "\n";
            }
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            sendIntent.putExtra("android.intent.extra.TEXT", versetoshare);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share verse..."));
            return;
        }
        Toast.makeText(VersesActivity.this, "Please select vers to share", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


}

