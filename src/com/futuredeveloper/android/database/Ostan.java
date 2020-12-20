package com.futuredeveloper.android.database;

import java.util.ArrayList;
import android.database.Cursor;


public class Ostan extends DataAccess {

    private String        shahr;
    private Integer       ostan;
    private int           id;
    ArrayList<StrucOstan> Data = new ArrayList<StrucOstan>();


    public void setOstan(Integer integer) {
        ostan = integer;
    }


    public Integer getOstan() {
        return ostan;
    }


    public void setShahr(String shahrestan) {
        shahr = shahrestan;
    }


    public String getShahr() {
        return shahr;
    }


    public ArrayList<StrucOstan> getAllOfOstan() {
        Data.clear();
        openDatabase();
        String sql = "SELECT name,id FROM Tbl_Ostan";
        Cursor cursor = newDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            StrucOstan data = new StrucOstan();

            data.name = cursor.getString(cursor.getColumnIndex("name"));
            data.id = cursor.getInt(cursor.getColumnIndex("id"));

            Data.add(data);
        }

        cursor.close();
        newDb.close();
        return Data;

    }


    public ArrayList<StrucOstan> getAllOfShahr() {
        Data.clear();
        //Log.i("LOG", "|||" + getIdOstan());
        openDatabase();
        String sql = "SELECT name,id FROM Tbl_Shahrestan WHERE pk_ostan = '" + getOstan() + "'";

        Cursor cursor = newDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            StrucOstan data = new StrucOstan();

            data.name = cursor.getString(cursor.getColumnIndex("name"));
            data.id = cursor.getInt(cursor.getColumnIndex("id"));

            Data.add(data);
        }
        cursor.close();
        newDb.close();
        return Data;

    }

    /* public int getIdOstan() {
         Log.i("LOG", "+1++" + getOstan());
         String s = "'" + getOstan() + '"';
         openDatabase();
         String sql = "SELECT id FROM Tbl_Ostan WHERE name ='" + getOstan() + '"';
         Cursor cursor = newDb.rawQuery(sql, null);
         int id = 0;
         while (cursor.moveToNext()) {
             id = cursor.getInt(cursor.getColumnIndex("id"));

         }
         //cursor.close();
         //newDb.close();
         return id;

     }*/
    /*public void updateLike() {
        openDatabase();
        String sql = "update bakeshop set like='" + getLike() + "' where id='" + getId() + "'";
        newDb.execSQL(sql);
        newDb.close();

    }*/
}
