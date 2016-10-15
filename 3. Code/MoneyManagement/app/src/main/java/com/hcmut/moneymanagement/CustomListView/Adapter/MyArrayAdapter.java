package com.hcmut.moneymanagement.CustomListView.Adapter;

/**
 * Created by Admin on 15-Oct-16.
 */

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmut.moneymanagement.CustomListView.Model.ListViewModel;
import com.hcmut.moneymanagement.R;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<ListViewModel> {

    Activity context = null;
    int layoutId;
    ArrayList<ListViewModel> arr = null;
    //Contructor này dùng để lấy về những giá trị được truyền vào từ Activity
    public MyArrayAdapter(Activity context, int layoutId, ArrayList<ListViewModel> list){
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        /*
         position: là vị trí của bàu hát trong list
         convertView: dùng để lấy về các control của mỗi item
         parent: chính là datasource được truyền vào từ MainActivity
         */
        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }
        //Lấy về bài hát ở vị trí được yêu cầu
        ListViewModel item = arr.get(position);
        //Lấy ra những control được định nghĩa trong cấu trúc của mỗi item
        ImageView icon = (ImageView)convertView.findViewById(R.id.list_image);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);

        //Gán giá trị cho những control đó
        title.setText(item.getTitle());
        description.setText(item.getDescription());

        //Vì icon là ảnh nên ta phải lấy ra đường dẫn, dùng nó để lấy về image trong folder drawable
        String uri_icon = "drawable/" + item.getIcon();
        int ImageResoure = convertView.getContext().getResources().getIdentifier(uri_icon, null, convertView.getContext().getApplicationContext().getPackageName());
        Drawable image = convertView.getContext().getResources().getDrawable(ImageResoure);
        icon.setImageDrawable(image);
        return convertView;
    }
}