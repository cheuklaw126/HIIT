package com.example.kenneth.hiit;

/**
 * Created by Administrator on 2018/3/25.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/28.
 */
public class HistoryListAdapter extends BaseAdapter {


    private LayoutInflater contextView;
    private ArrayList<History> historys;

    private class ViewHolder {
        TextView h_date;
        TextView h_video;
        TextView h_comp;

        public ViewHolder(TextView h_date, TextView h_video, TextView h_comp) {
            this.h_date = h_date;
            this.h_video = h_video;
            this.h_comp = h_comp;

        }
    }

    public HistoryListAdapter(Context context, ArrayList<History> historys) {
        contextView = LayoutInflater.from(context);
        this.historys = historys;
    }

    @Override
    public int getCount() {
        return historys.size();
    }

    @Override
    public Object getItem(int i) {
        return historys.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = contextView.inflate(R.layout.history_info, null);
            holder = new ViewHolder((TextView) view.findViewById(R.id.h_date),
                    (TextView) view.findViewById(R.id.h_video),
                    (TextView) view.findViewById(R.id.h_comp));

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        History history = historys.get(i);
        holder.h_date.setText(history.getC_date());
        holder.h_video.setText(history.getVname());
        holder.h_comp.setText(history.getComplete());

        return view;
    }
}



