package camp.baixing.com.camp_android_notebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import components.NotebookAdapter;
import entities.Note;
import util.InternalStorage;


public class MainActivity extends AppCompatActivity {

    /*
     * NoteEditorActivity may pass some info about how to change the data,
     * and all I/O related operations on data is done within this activity
     */
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_CONTENT = "extra_content";
    public static final String EXTRA_OP = "extra_operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_listview);

        final List<Note> data = getData();

        // if set or delete button is clicked, do the operation
        doOperation(data);

        final NotebookAdapter adapter = new NotebookAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // set the listener on add_button
        initButton(data, adapter);
    }

    private void initButton(final List<Note> data, final NotebookAdapter adapter) {
        Button addButton = (Button) findViewById(R.id.add_button);
        final Context context = this;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(context);
                new AlertDialog.Builder(context)
                        .setTitle("Title")
                        .setView(editText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = editText.getText().toString();
                                Intent intent = new Intent(context, NoteEditorActivity.class);
                                intent.putExtra(NoteEditorActivity.EXTRA_TITLE, title);
                                intent.putExtra(NoteEditorActivity.EXTRA_CONTENT, "");
                                intent.putExtra(NoteEditorActivity.EXTRA_POSITION, data.size());
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });
    }

    /* deal with data operations like set or delete */
    private void doOperation(List<Note> data) {
        final String op = getIntent().getStringExtra(EXTRA_OP);
        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        final String content = getIntent().getStringExtra(EXTRA_CONTENT);
        final int pos = getIntent().getIntExtra(EXTRA_POSITION, -1);

        if (op != null) {
            switch (op) {
                case "delete":
                    try {
                        data.remove(pos);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    break;
                case "set":
                    try {
                        Note note = data.get(pos);
                        note.setTitle(title);
                        note.setContent(content);
                    } catch (IndexOutOfBoundsException e) {
                        data.add(new Note(title, content));
                    }
                    break;
            }
        }
        // persist data
        saveData(data);
    }

    /* persist data to local storage */
    boolean saveData(List<Note> data) {
        final InternalStorage is = new InternalStorage(this);
        try {
            is.set("notes", data);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /* read persisted data from local storage */
    List<Note> getData() {
        final InternalStorage is = new InternalStorage(this);
        List<Note> ret = new ArrayList<>();
        try {
            ret.addAll(is.get("notes"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}