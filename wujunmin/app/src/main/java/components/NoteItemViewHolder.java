package components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import camp.baixing.com.camp_android_notebook.R;

/**
 * Created by zijing on 03/07/2017
 */

public class NoteItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public NoteItemViewHolder(Context context, ViewGroup parent) {
        this(LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false));
    }

    public NoteItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.list_item_note_content);
    }

    public void setText(String text) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}