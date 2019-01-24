package com.example.mis_internee.innowave.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mis_internee.innowave.Model.Details_Model;
import com.example.mis_internee.innowave.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by MIS-Internee on 1/23/2019.
 */

public class Details_Adapter  extends BaseAdapter{

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<Details_Model> modellist;
    ArrayList<Details_Model> arrayList;

    //constructor
    public Details_Adapter(Context context, ArrayList<Details_Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Details_Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView fNameTv ;
        ImageView fAvatar;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.details_adapter_layout, null);

            //locate the views in row.xml
            holder.fNameTv = view.findViewById(R.id.f_name);
            holder.fAvatar = view.findViewById(R.id.f_avatar);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.fNameTv.setText(modellist.get(postition).getName().toString());

        Picasso.with(mContext).load(modellist.get(postition).getAvatar().toString())
                                        .resize(100, 100).noFade().into(holder.fAvatar);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code later

            }
        });


        return view;
    }

    //filter
//    public void filter(String charText){
//        charText = charText.toLowerCase(Locale.getDefault());
//        modellist.clear();
//        if (charText.length()==0){
//            modellist.addAll(arrayList);
//        }
//        else {
//            for (Details_Model model : arrayList){
//                if (model.getName().toLowerCase(Locale.getDefault())
//                        .contains(charText)){
//                    modellist.add(model);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}