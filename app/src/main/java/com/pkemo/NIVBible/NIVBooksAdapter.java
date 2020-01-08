package com.pkemo.NIVBible;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nelson on 11/21/2016.
 */

public class NIVBooksAdapter extends ArrayAdapter {
    Context context;
    TextHolder holder;
    int layoutid;
    List<NIVBibleBooks> objects;

    static class TextHolder {
        TextView bookname, noofchapters;
        TextHolder() {
        }
    }

    public NIVBooksAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutid = resource;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = ((Activity) this.context).getLayoutInflater().inflate(this.layoutid, parent, false);
            this.holder = new TextHolder();
            this.holder.bookname = (TextView) row.findViewById(R.id.title);
            this.holder.noofchapters = (TextView) row.findViewById(R.id.noofchapters);
            row.setTag(this.holder);
        } else {
            this.holder = (TextHolder) row.getTag();
        }
        NIVBibleBooks bookitem = this.objects.get(position);
        this.holder.bookname.setText(bookitem.getBname());
       // this.holder.noofchapters.setText("Chapters " + bookitem.getCc().size());
        return row;
    }
}

