package com.pkemo.NIVBible;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nelson on 11/23/2016.
 */

public class NIVChapterAdapter extends ArrayAdapter {
    Context context;
    TextHolder holder;
    int layoutid;
    List<String> objects;

    static class TextHolder {
        TextView number;
        TextView txtTitle;

        TextHolder() {
        }
    }

    public NIVChapterAdapter(Context context, int resource, List objects) {
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
            this.holder.number = (TextView) row.findViewById(R.id.number);
            this.holder.txtTitle = (TextView) row.findViewById(R.id.chaptername);
            row.setTag(this.holder);
        } else {
            this.holder = (TextHolder) row.getTag();
        }

        this.holder.number.setText((this.objects.get(position)) + ".");
        this.holder.txtTitle.setText("Chapter");
        return row;
    }
}
