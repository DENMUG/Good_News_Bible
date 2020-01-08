package com.pkemo.NIVBible;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class NIVBookMarksActivity extends AppCompatActivity {
    static ArrayList<NIVBookMark> bookmarks_list;
    NIVBookMarksAdapter adapter;
    private Paint f14p;
    RecyclerView recyclerView;


    class C03891 implements OnClickListener {
        C03891() {
        }

        public void onClick(View v) {
            onBackPressed();
        }
    }


    class C03903 implements DialogInterface.OnClickListener {
        C03903() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            onBackPressed();
        }
    }


    class C07132 implements NIVBookMarksAdapter.OnItemClickListener {
        C07132() {
        }

        public void onItemClick(NIVBookMark item, int position, View view) {
        }
    }


    class C07584 extends SimpleCallback {
        C07584(int x0, int x1) {
            super(x0, x1);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            return false;
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == 4) {
                NIVBookMarksActivity.this.adapter.remove(position);
            }
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == 1) {
                View itemView = viewHolder.itemView;
                float width = (((float) itemView.getBottom()) - ((float) itemView.getTop())) / 3.0f;
                if (dX < 0.0f) {
                    NIVBookMarksActivity.this.f14p.setColor(Color.parseColor("#D32F2F"));
                    c.drawRect(new RectF(((float) itemView.getRight()) + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom()), NIVBookMarksActivity.this.f14p);
                    c.drawBitmap(BitmapFactory.decodeResource(NIVBookMarksActivity.this.getResources(), R.drawable.delete), null, new RectF(((float) itemView.getRight()) - (2.0f * width), ((float) itemView.getTop()) + width, ((float) itemView.getRight()) - width, ((float) itemView.getBottom()) - width), NIVBookMarksActivity.this.f14p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    public NIVBookMarksActivity() {
        this.f14p = new Paint();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_book_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
       // setSupportActionBar(toolbar);
        setTitle("Bookmarks");
        toolbar.setNavigationContentDescription((CharSequence) "Back Button");
        toolbar.setNavigationIcon((int) R.drawable.backar);
        toolbar.setNavigationOnClickListener(new C03891());
        new NIVBookMarkerClass(this).open();
        bookmarks_list = NIVBookMarkerClass.getAllComments();
        this.recyclerView = (RecyclerView) findViewById(R.id.bookmarks);
        this.adapter = new NIVBookMarksAdapter(this, bookmarks_list, new C07132());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        initSwipe();
        initDialog();
    }

    private void initDialog() {
        if (bookmarks_list.size() == 0) {
            Builder alertDialog = new Builder(this);
            alertDialog.setView(getLayoutInflater().inflate(R.layout.dialog_alert, null));
            alertDialog.setPositiveButton((CharSequence) "Ok", new C03903());
            alertDialog.setTitle((CharSequence) "Notification!");
            alertDialog.show();
        }
    }

    private void initSwipe() {
        new ItemTouchHelper(new C07584(0, 12)).attachToRecyclerView(this.recyclerView);
    }
}
