package com.example.kenneth.hiit;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kenneth on 14/3/2018.
 */

public class frdAdapter extends BaseAdapter {

    private LayoutInflater contextView;
    private ArrayList<JSONObject> frds;
    private Global global;
    public int tabNo;
    public int CurrentIndex;

    public FloatingActionButton FabBtn;


    private class ViewHolder {
        TextView tvLastName;
        TextView tvUid;
        ImageView imageView;
        FloatingActionButton fab;
        int index;

        public ViewHolder(TextView tvLastName, TextView tvUid, ImageView imageView, FloatingActionButton fab, int index) {
            this.tvLastName = tvLastName;
            this.tvUid = tvUid;
            this.imageView = imageView;
            this.fab = fab;
            this.index = index;
        }
    }


    public frdAdapter(Context context, ArrayList<JSONObject> frds, Global global, int tabNo) {
        this.tabNo = tabNo;
        contextView = LayoutInflater.from(context);
        System.out.println(contextView.getContext().toString());
        this.frds = frds;
        this.global = global;
    }

    @Override
    public int getCount() {
        return frds.size();
    }

    @Override
    public Object getItem(int i) {
        return frds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = contextView.inflate(R.layout.fdrow, null);
            holder = new ViewHolder((TextView) view.findViewById(R.id.LastName),
                    (TextView) view.findViewById(R.id.uid), (ImageView) view.findViewById(R.id.src), (FloatingActionButton) view.findViewById(R.id.fdrowfb), i);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        try {
            holder.tvLastName.setText(String.valueOf(frds.get(i).getString("uname")));
            holder.tvUid.setText(frds.get(i).getString("lastName").toString());
            String src = frds.get(i).getString("src").toString();
            int position = i;
            global.SetImage(holder.imageView, src);
            FabBtn = holder.fab;
            FabBtn.setClickable(true);
            FabBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (tabNo)
                    {
                        case 1:
                            try {

                                String funame = frds.get(i).getString("uname");
                                boolean chk = global.RemoveFrd(global.UserName,funame);
                                if (chk) {
                                    frds.remove(i);
                                    notifyDataSetChanged();


                                }

                            } catch (Exception ex) {

                            }

                            break;


                        case 3:

                            try {
                                String funame = frds.get(i).getString("uname");
                                global.AcceptFrd(funame);
                                frds.remove(i);
                            notifyDataSetChanged();

                            } catch (Exception ex) {

                            }


                            System.out.println("DONE");

                            break;


                    }

                }
            });


            CurrentIndex = i;

            switch (tabNo) {
                case 1:
                    holder.fab.setImageResource(R.drawable.delete);
                /*    holder.fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });*/
                    break;
                case 2:


                case 3:
                    FabBtn.setImageResource(R.drawable.ic_action_user_add);

                /*

                    holder.fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String funame = frds.get(i).getString("funame");
                                global.AcceptFrd(funame);
                                frds.remove(i);
                                notifyDataSetChanged();

                            } catch (Exception ex) {

                            }
                        }
                    });
*/
            }


        }
        catch (Exception ex) {
System.out.println(ex.toString());
        }
        return view;
    }
}