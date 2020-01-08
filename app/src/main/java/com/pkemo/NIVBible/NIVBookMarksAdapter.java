package com.pkemo.NIVBible;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nelson on 1/14/2017.
 */
public class NIVBookMarksAdapter extends RecyclerView.Adapter<NIVBookMarksAdapter.MyViewHolder> {
    ArrayList<NIVBookMark> bookmark_array_list;
    Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NIVBookMark NIVBookMark, int i, View view);
    }

    public class MyViewHolder extends ViewHolder {
        TextView bmarkcontent;
        TextView bmarktitle;


        class C03911 implements OnClickListener {
            final /* synthetic */ OnItemClickListener val$listener;
            final /* synthetic */ NIVBookMark val$verse;

            C03911(OnItemClickListener onItemClickListener, NIVBookMark NIVBookMark) {
                this.val$listener = onItemClickListener;
                this.val$verse = NIVBookMark;
            }

            public void onClick(View v) {
                this.val$listener.onItemClick(this.val$verse, MyViewHolder.this.getAdapterPosition(), v);
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            this.bmarktitle = (TextView) itemView.findViewById(R.id.bookmark_header);
            this.bmarkcontent = (TextView) itemView.findViewById(R.id.bookmark_content);
        }

        public void bind(NIVBookMark verse, OnItemClickListener listener) {
            this.itemView.setOnClickListener(new C03911(listener, verse));
        }
    }

    public NIVBookMarksAdapter(Context context, ArrayList<NIVBookMark> verses, OnItemClickListener listener) {
        this.bookmark_array_list = verses;
        this.context = context;
        this.listener = listener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.bookmark_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind((NIVBookMark) this.bookmark_array_list.get(position), this.listener);
        NIVBookMark bmarker = (NIVBookMark) this.bookmark_array_list.get(position);
        holder.bmarktitle.setText(bmarker.getBookname() + ":" + bmarker.getChapter_no() + "-" + bmarker.getB_versenumber());
        holder.bmarkcontent.setText(bmarker.getVerse());
        holder.bmarkcontent.setTextColor(bmarker.getColorcode());
    }

    public void remove(int position) {
        NIVBookMarkerClass.deleteComment(((NIVBookMark) this.bookmark_array_list.get(position)).getId());
        this.bookmark_array_list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.bookmark_array_list.size());
    }

    public int getItemCount() {
        return this.bookmark_array_list.size();
    }
}
