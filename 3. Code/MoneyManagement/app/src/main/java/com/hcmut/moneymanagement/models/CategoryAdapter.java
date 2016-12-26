package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<ListViewModel> {
    Activity context = null;
    ArrayList<ListViewModel> tools = null;
    int layoutId;

    public CategoryAdapter(Activity context, int layoutId, ArrayList<ListViewModel>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.tools = arr;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.tool_name);

        ListViewModel tool = tools.get(position);
        //Gán giá trị cho những control đo

        name.setText(tool.getTitle());
        String uri_icon = "drawable/" + tool.getIcon();
        int ImageResoure = convertView.getContext().getResources().getIdentifier(uri_icon, null, convertView.getContext().getApplicationContext().getPackageName());
        Drawable image = convertView.getContext().getResources().getDrawable(ImageResoure);
        icon.setImageDrawable(image);

        return convertView;

    }
}
