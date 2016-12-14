package com.adorgolap.as_sunnahtrustqa.adpater;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adorgolap.as_sunnahtrustqa.model.QA;
import com.adorgolap.as_sunnahtrustqa.R;

import java.util.ArrayList;

/**
 * Created by ifta on 12/3/16.
 */

public class MainListViewAdapter extends ArrayAdapter<QA>{
    Context context;
    LayoutInflater inflater;
    public MainListViewAdapter(Context context, int resource, ArrayList<QA> data) {
        super(context, resource, data);
        this.context = context;
        inflater = ((Activity)context).getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        QAHolder holder;
        if(convertView == null)
        {
            view = inflater.inflate(R.layout.main_list_single_item,null);
            holder = new QAHolder();
            holder.tvNo = (TextView) view.findViewById(R.id.tvNo);
            holder.tvCategory=(TextView) view.findViewById(R.id.tvCategory);
            holder.tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            holder.tvAnswer = (TextView) view.findViewById(R.id.tvAnswer);
            view.setTag(holder);
        }else {
            holder = (QAHolder) view.getTag();
        }
        QA qa = getItem(position);
        holder.tvNo.setText(qa.getId()+"");
        holder.tvCategory.setText(qa.getCategory());
        holder.tvQuestion.setText(qa.getQuestion());
        holder.tvAnswer.setText(qa.getAnswer());
        return view;
    }
    class QAHolder{
        TextView tvNo,tvCategory,tvQuestion,tvAnswer;
    }
}
