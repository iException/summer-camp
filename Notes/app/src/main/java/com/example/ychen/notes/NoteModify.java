package com.example.ychen.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.StringTokenizer;

public class NoteModify extends AppCompatActivity {

    public static final String NOTE_INFO = "note_info";
    public static final int NOTE_REQ = 1;
    private SingleNotes note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_modify);

        final EditText noteedit = (EditText) findViewById(R.id.note_edit);
        noteedit.requestFocus();

        TextView notetime = (TextView)findViewById(R.id.note_time);

        Button btnSave = (Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.ModifyText(noteedit.getText().toString());
//                Toast.makeText(NoteModify.this, "notetext is " + note.getNotes() + "\nnotetime is " + note.getModifyTime(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(NOTE_INFO, note);
                setResult(NOTE_REQ, intent);
                finish();
            }
        });


        note = (SingleNotes) getIntent().getSerializableExtra(NOTE_INFO);
        if (note != null) {
            noteedit.setText(note.getNotes());
            notetime.setText(note.getModifyTime());
        }
        else
        {
            Toast.makeText(this, "Error occur...\nUnable to fetch the note info...", Toast.LENGTH_SHORT).show();
        }
    }
}
