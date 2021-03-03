package com.example.stationhere;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<SearchData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {//그 안에 들어가는 요소, 클래스
        // each data item is just a string in this case
        public TextView korName;
        public TextView engName;
        public ImageView line;
        public MyViewHolder(View v) {
            super(v);
            korName = v.findViewById(R.id.korName);
            engName = v.findViewById(R.id.engName);
            line = v.findViewById(R.id.lineImage);
            //Button = v.findViewById(R.id.btn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<SearchData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,//한 로우에 이미지 파일을 연결하는거, 항목 화면 연결, 함수
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.TextView_title.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 0;
    }
}
