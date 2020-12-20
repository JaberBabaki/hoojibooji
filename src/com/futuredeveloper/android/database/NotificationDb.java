package com.futuredeveloper.android.database;

import java.util.ArrayList;
import android.database.Cursor;
import android.util.Log;


public class NotificationDb extends DataAccess {

    private String _text;
    private String _date;
    private String _title;
    private String _image;
    private int    _id;
    private int    _view;


    public void setText(String value) {
        _text = value;
    }


    public void setDate(String value) {
        _date = value;
    }


    public void setImage(String value) {
        _image = value;
    }


    public void setId(int value) {
        _id = value;
    }


    public void setView(int value) {
        _view = value;
    }


    public void setTitle(String value) {
        _title = value;
    }


    public ArrayList<StrucNotification> getNotific() {
        ArrayList<StrucNotification> Data = new ArrayList<StrucNotification>();
        openDatabase();
        String sql = "SELECT id,title,text,date,view,image,logic_delete FROM notific ORDER BY id desc";
        Cursor cursor = newDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("logic_delete")) == 0) {
                StrucNotification notific = new StrucNotification();
                notific.id = cursor.getInt(cursor.getColumnIndex("id"));
                notific.title = cursor.getString(cursor.getColumnIndex("title"));
                notific.text = cursor.getString(cursor.getColumnIndex("text"));
                notific.date = cursor.getString(cursor.getColumnIndex("date"));
                notific.view = cursor.getInt(cursor.getColumnIndex("view"));
                notific.image = cursor.getString(cursor.getColumnIndex("image"));
                Data.add(notific);
            }

        }

        cursor.close();
        newDb.close();
        return Data;

    }


    public void inesrtNotic() {
        _view = 0;
        int _logic_delete = 0;
        openDatabase();
        String sql = "INSERT INTO notific VALUES (" + _id + ",'" + _title + "','" + _text + "','" + _date + "'," + _view + ",'" + _image + "'," + _logic_delete + ")";
        Log.i("NOT", sql);
        newDb.execSQL(sql);
        newDb.close();

    }


    public void deleteOne() {

        openDatabase();
        String sql = "update notific set logic_delete=1 WHERE id='" + _id + "'";
        newDb.execSQL(sql);
        newDb.close();

    }


    public int selectId() {
        openDatabase();
        String sql = "select id from notific where id='" + _id + "'";
        Cursor cursor = newDb.rawQuery(sql, null);
        int id = 0;
        while (cursor.moveToNext()) {

            id = cursor.getInt(cursor.getColumnIndex("id"));
        }

        cursor.close();
        newDb.close();
        return id;

    }


    public ArrayList<StrucNotification> selectOne() {
        ArrayList<StrucNotification> Data = new ArrayList<StrucNotification>();
        openDatabase();
        String sql = "select date,title,text,image from notific where id='" + _id + "'";
        Cursor cursor = newDb.rawQuery(sql, null);
        int id = 0;
        while (cursor.moveToNext()) {
            StrucNotification notific = new StrucNotification();
            notific.date = cursor.getString(cursor.getColumnIndex("date"));
            notific.title = cursor.getString(cursor.getColumnIndex("title"));
            notific.text = cursor.getString(cursor.getColumnIndex("text"));
            notific.image = cursor.getString(cursor.getColumnIndex("image"));
            Data.add(notific);
        }

        cursor.close();
        newDb.close();
        return Data;

    }


    public void updateView() {
        openDatabase();
        String sql = "update notific set view=1 where id=" + _id;
        Log.i("KOK", _id + "    " + sql);
        newDb.execSQL(sql);
        newDb.close();

    }

}
