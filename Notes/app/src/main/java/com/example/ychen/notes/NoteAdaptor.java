package com.example.ychen.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ychen on 04/07/2017.
 */

public class NoteAdaptor extends ArrayAdapter<SingleNotes>{
    private int resourceId;

    public NoteAdaptor(Context context, int textViewResourceId, List<SingleNotes> objects){
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SingleNotes note = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(this.resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.NoteName = (TextView)view.findViewById(R.id.textname);
            viewHolder.NoteTime = (TextView)view.findViewById(R.id.textdate);
            view.setTag(viewHolder); //将viewHolder存储在view中
        } else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(); //重新获取ViewHolder
        }
        try {
            viewHolder.NoteTime.setText(note.getModifyTime());
        }
        catch (NullPointerException e){
            viewHolder.NoteTime.setText("Error occur...");
        }
        try {
            viewHolder.NoteName.setText(note.getNotes());
        }
        catch (NullPointerException e)
        {
            viewHolder.NoteName.setText(("Error occur..."));
        }

        return view;
    }

    class ViewHolder{
        TextView NoteName;
        TextView NoteTime;
    }
}
