package com.scottz.lifttracker;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scottz.lifttracker.model.Record;

import java.util.List;

/**
 * Created by scottz on 7/25/17.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private RecordClickListener mOnClickListener;

    private List<Record> mRecordList;

    public interface RecordClickListener {
        void onRecordClick (int index);
    }

    public RecordAdapter(List<Record> recordList, RecordClickListener listener) {
        mRecordList = recordList;
        mOnClickListener = listener;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.record_row, parent, false /* attach to parent immediately */);
        RecordViewHolder viewHolder = new RecordViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        if (position >= 0 && position < mRecordList.size()) {
            holder.recordTextView.setText(mRecordList.get(position).toString());
        } else {
            holder.recordTextView.setText("Undefined");
        }
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    public void addRecord(Record record) {
        mRecordList.add(0 /* head index */, record);
        notifyDataSetChanged();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView recordTextView;

        public RecordViewHolder(View itemView) {
            super(itemView);

            recordTextView = (TextView) itemView.findViewById(R.id.tv_record);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
