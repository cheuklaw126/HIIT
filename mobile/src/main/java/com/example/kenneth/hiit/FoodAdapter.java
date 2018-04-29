package com.example.kenneth.hiit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 2018/03/20.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ModelFood> mList;

    FoodAdapter(Context context, ArrayList<ModelFood> list){
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.rv_food_items,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ModelFood foodItem = mList.get(position);

        ImageView image = holder.item_image;
        TextView name,place,price;

        name = holder.item_name;
        place = holder.item_place;
        price = holder.item_price;

        image.setImageResource(foodItem.getImage());

        name.setText(foodItem.getName());
        place.setText(foodItem.getPlace());
        price.setText(foodItem.getPrice());

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_image;
        TextView item_name, item_place, item_price;


        public ViewHolder(View itemView){
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_place = itemView.findViewById(R.id.item_place);
            item_price = itemView.findViewById(R.id.item_price);
        }

    }
}
