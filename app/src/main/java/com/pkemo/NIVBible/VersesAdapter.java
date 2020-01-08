package com.pkemo.NIVBible;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.View.OnLongClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nelson on 12/15/2016.
 */

public class VersesAdapter extends Adapter<VersesAdapter.MyViewHolder> {
    static MyViewHolder holder;
    Context context;
    private final OnItemClickListener listener;
    SparseBooleanArray selectedItems;
    ArrayList<Verse> vers;

    public interface OnItemClickListener {
        void onItemClick(Verse Verse, int i, View view);
    }

    public class MyViewHolder extends ViewHolder {
        TextView verse_tv;
        TextView vnumber_tv;

        class C04021 implements OnLongClickListener {
            final /* synthetic */ OnItemClickListener longclicklistener;
            final /* synthetic */ Verse val$Verse;

            C04021(OnItemClickListener onItemClickListener, Verse Verse) {
                this.longclicklistener = onItemClickListener;
                this.val$Verse = Verse;
            }

            public boolean onLongClick(View v) {
                this.longclicklistener.onItemClick(this.val$Verse, MyViewHolder.this.getAdapterPosition(), v);
                return false;
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            this.vnumber_tv = (TextView) itemView.findViewById(R.id.vnumber);
            this.verse_tv = (TextView) itemView.findViewById(R.id.Verse);
        }

        public void bind(Verse Verse, OnItemClickListener listener) {
            this.itemView.setOnLongClickListener(new C04021(listener, Verse));
        }
    }

    public VersesAdapter(Context context, ArrayList<Verse> vers, OnItemClickListener listener) {
        this.selectedItems = new SparseBooleanArray();
        this.vers = vers;
        this.context = context;
        this.listener = listener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.list_item, parent, false));
        return holder;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind((Verse) this.vers.get(position), this.listener);
        Verse Verse = (Verse) this.vers.get(position);
        holder.vnumber_tv.setText(Verse.getVnumber() + ".");
        holder.verse_tv.setText(Verse.getVerse());
        holder.verse_tv.setTextSize(1, VersesActivity.textsize);
        holder.vnumber_tv.setTextSize(1, VersesActivity.textsize);
        if (VersesActivity.sba.size() > 0) {
            for (int x = 0; x < VersesActivity.sba.size(); x++) {
                if (position == VersesActivity.sba.keyAt(x)) {
                    holder.verse_tv.setTextColor(VersesActivity.colorcode);
                }
            }
        }
        int color = NIVBookMarkerClass.getMeColor(VersesActivity.bookname, VersesActivity.f15j, Verse.getVnumber());
        if (color != 0) {
            holder.verse_tv.setTextColor(color);
        }
    }

    public int getItemCount() {
        return this.vers.size();
    }
}

