package com.example.ychen.notes;

import android.media.Image;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import   java.text.SimpleDateFormat;

/**
 * Created by ychen on 04/07/2017.
 */

// use Serializable to transfer the data(indent)
public class SingleNotes implements Serializable {
    private String Notes;
    //todo
    //add the user-defined picture.
    private Image Image;
    private Date AddTime;
    private String strAddTime;
    private Date ModifyTime;
    private String strModifyTime;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public SingleNotes(String text, Date time){
        this.Notes = text;
        this.AddTime = time;
        this.strAddTime = formatter.format(time);
    }

    public SingleNotes(String text){
        this.Notes = text;
        this.AddTime = new Date(System.currentTimeMillis());
        this.strAddTime = formatter.format(this.AddTime);
    }

    public void ModifyText(String text)
    {
        Date curDate =  new Date(System.currentTimeMillis());
        this.strModifyTime = formatter.format(curDate);
        this.Notes = text;

    }

    public String getNotes()
    {
        return this.Notes;
    }

    public String getCreateTime()
    {
        return this.strAddTime;
    }

    public String getModifyTime() {
        if (this.strModifyTime == null)
            return this.strAddTime;
        else
            return this.strModifyTime;
    }
}
