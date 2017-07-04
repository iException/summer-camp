package components;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import camp.baixing.com.camp_android_notebook.NoteEditorActivity;
import entities.Note;

/**
 * Created by zijing on 03/07/2017
 */

public class NotebookAdapter extends RecyclerView.Adapter<NoteItemViewHolder> {

    private List<Note> data;
    private Context context;

    public NotebookAdapter(Context context, List<Note> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public NoteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteItemViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(final NoteItemViewHolder holder, int position) {
        if (holder != null && data != null) {
            holder.setText(data.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NoteEditorActivity.class);
                    intent.putExtra(NoteEditorActivity.EXTRA_TITLE, data.get(holder.getAdapterPosition()).getTitle());
                    intent.putExtra(NoteEditorActivity.EXTRA_CONTENT, data.get(holder.getAdapterPosition()).getContent());
                    intent.putExtra(NoteEditorActivity.EXTRA_POSITION, holder.getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}
