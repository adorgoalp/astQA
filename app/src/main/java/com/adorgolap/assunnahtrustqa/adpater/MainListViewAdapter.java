package com.adorgolap.assunnahtrustqa.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adorgolap.assunnahtrustqa.model.QA;
import com.adorgolap.as_sunnahtrustqa.R;

import java.util.ArrayList;

/**
 * Created by ifta on 12/3/16.
 */

public class MainListViewAdapter extends ArrayAdapter<QA>{
    Context context;
    LayoutInflater inflater;
    final String Q = "প্রশ্ন: ";
    public MainListViewAdapter(Context context, int resource, ArrayList<QA> data) {
        super(context, resource, data);
        this.context = context;
        inflater = ((Activity)context).getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final QAHolder holder;
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
        final QA qa = getItem(position);
        holder.tvNo.setText(qa.getId()+"");
        holder.tvCategory.setText(qa.getCategory());
        holder.tvQuestion.setText(qa.getQuestion());
        holder.tvAnswer.setText(qa.getAnswer());
        holder.tvAnswer.setVisibility(View.GONE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.tvAnswer.getVisibility() == View.GONE)
                {
                    holder.tvAnswer.setVisibility(View.VISIBLE);
                }else {
                    holder.tvAnswer.setVisibility(View.GONE);
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String data = Q + qa.getQuestion()+"\n\n"+qa.getAnswer();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Share"));
                return true;
            }
        });
        return view;
    }
    class QAHolder{
        TextView tvNo,tvCategory,tvQuestion,tvAnswer;
    }
}
