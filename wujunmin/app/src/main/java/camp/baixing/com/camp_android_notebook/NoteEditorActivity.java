package camp.baixing.com.camp_android_notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zijing on 03/07/2017
 */

public class NoteEditorActivity extends AppCompatActivity {
    /* Note's data is passed from MainActivity */
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_CONTENT = "extra_content";
    public static final String EXTRA_POSITION = "position";

    private EditText editText;
    private String title;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        // read the passed data
        editText = (EditText) findViewById(R.id.acitivyt_note_edit);
        title = getIntent().getStringExtra(EXTRA_TITLE);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        position = getIntent().getIntExtra(EXTRA_POSITION, -1);

        if (content != null) {
            this.setTitle(title);
            editText.setText(content);
        }

        // set listener on save_button
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent("set");
            }
        });

        // set listener on delete_button
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent("delete");
            }
        });

    }

    private void clickEvent(String op) {
        Intent intent = new Intent(this, MainActivity.class);
        // index of note
        intent.putExtra(MainActivity.EXTRA_POSITION, position);
        // title of note
        intent.putExtra(MainActivity.EXTRA_TITLE, title);
        // content of note
        intent.putExtra(MainActivity.EXTRA_CONTENT, editText.getText().toString());
        // operations that the user would like to perform (like set and delete)
        intent.putExtra(MainActivity.EXTRA_OP, op);
        // get back to the previous activity and clear all activities after it
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
}