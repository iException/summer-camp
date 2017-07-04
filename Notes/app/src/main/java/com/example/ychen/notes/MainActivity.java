package com.example.ychen.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String SharedPreference = "mynotepad";
    private List<SingleNotes> NotesList = new ArrayList<SingleNotes>();
    private Date curtime = new Date(System.currentTimeMillis());
    private int tpnote_id = 0;
    private NoteAdaptor adapter;
    private SharedPreferences localstorage;
    private SharedPreferences.Editor localeditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.btn_add);

        initNotes(); //实例化数据
        adapter = new NoteAdaptor(MainActivity.this, R.layout.singlenote, NotesList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                SingleNotes note = NotesList.get(i);
                builder.setMessage("确定删除此条目?" + "\n内容为: " + note.getNotes() + "创建于" + note.getModifyTime());
                builder.setTitle("提示");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NotesList.remove(i) != null) {
                            Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                        }
                        saveInfos();
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return false;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tpnote_id = position;
                SingleNotes note = NotesList.get(position);
                Intent intent = new Intent(MainActivity.this, NoteModify.class);
                //Serializable
                intent.putExtra(NoteModify.NOTE_INFO, note);
                startActivityForResult(intent, NoteModify.NOTE_REQ);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleNotes note = new SingleNotes("", curtime);
                NotesList.add(note);
                tpnote_id = NotesList.size() - 1;
                Intent intent = new Intent(MainActivity.this, NoteModify.class);
                //Serializable
                intent.putExtra(NoteModify.NOTE_INFO, note);
                startActivityForResult(intent, NoteModify.NOTE_REQ);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == NoteModify.NOTE_REQ) {
            SingleNotes note = (SingleNotes) data.getSerializableExtra(NoteModify.NOTE_INFO);
            NotesList.set(tpnote_id, note);
            saveInfos();
            adapter.notifyDataSetChanged();
        }
    }

    public void initNotes() {
//采用SharedPreference来实现存储
        localstorage = getSharedPreferences(SharedPreference, 0);
        localeditor = localstorage.edit();
        if (!localstorage.getString("status","").equals("ok")) {
            SingleNotes note_1 = new SingleNotes("采用SharedPreference进行存储", curtime);
            NotesList.add(note_1);
            SingleNotes note_2 = new SingleNotes("长按进行删除", curtime);
            NotesList.add(note_2);
            SingleNotes note_3 = new SingleNotes("轻触可以修改", curtime);
            NotesList.add(note_3);
            SingleNotes note_4 = new SingleNotes("Yuan Chen", curtime);
            NotesList.add(note_4);
        }
        else {
            Map<String,String>  dataset = (Map<String, String>) localstorage.getAll();
            for(int i = 0; i < dataset.size(); i ++ ){
                String stri = String.format("%d",i);
                String retStr = localstorage.getString(stri, "");

                String[] info = retStr.split("##");
                SingleNotes note = new SingleNotes(info[0]);
                NotesList.add(note);
            }
        }
    }

    public void saveInfos(){
        for(int i = 0; i < NotesList.size(); i ++) {
            String tpstr = NotesList.get(i).getNotes() + "##" + NotesList.get(i).getNotes();
            String stri = String.format("%d", i);
            localeditor.putString(stri, tpstr);
        }
        localeditor.putString("status", "ok");
        localeditor.commit();
    }
}
