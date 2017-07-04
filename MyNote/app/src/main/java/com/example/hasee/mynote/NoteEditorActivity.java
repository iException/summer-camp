/**
 * Created by hasee on 2017/7/4.
 */
package com.example.hasee.mynote;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;

public class NoteEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        Button  btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.acitivity_note_edit);
                Intent intent = new Intent();
                String str = editText.getText().toString();
                intent.putExtra("backData",str);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
