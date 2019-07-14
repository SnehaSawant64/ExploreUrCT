package com.example.android.exploreurct;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * Created by Admin on 02/10/2018.
 */

public class LLocationAdapter extends RecyclerView.Adapter<LLocationAdapter.MyViewHolder>  {


    private List<LLocation> noticeList;
    Date d;
    public Context mcontext;

    public interface OnLLocationClickListener {
        void onLLocationClick(int position);
    }

    private OnLLocationClickListener listener;

    public LLocationAdapter(List<LLocation> list, Context context) {
        noticeList = list;
        mcontext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView notice_id, notice_title, notice_subj,notice_sdate;
        View v;


        public MyViewHolder(View view) {
            super(view);
            notice_sdate=(TextView)view.findViewById(R.id.n_date);
            notice_id = (TextView) view.findViewById(R.id.notice_id);
            notice_title = (TextView) view.findViewById(R.id.notice_title);
            notice_subj = (TextView) view.findViewById(R.id.notice_subj);
        }

        @Override
        public void onClick(View view) {
            Log.e("open notice","position clicked :"+getPosition()+"");
        }
    }

    public void setListener(OnLLocationClickListener listener) {
        this.listener = listener;
    }


    public LLocationAdapter(List<LLocation> noticeList) {
        this.noticeList = noticeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LLocation movie = noticeList.get(position);
        holder.notice_id.setText(movie.getId());
        Calendar cal=Calendar.getInstance();

        holder.notice_sdate.setText(movie.getId());
        holder.notice_title.setText(movie.getName());
        holder.notice_subj.setText(movie.getDisc()+"\n"+movie.getUrl());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }
}
