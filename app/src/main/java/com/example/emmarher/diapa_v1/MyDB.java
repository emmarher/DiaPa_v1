package com.example.emmarher.diapa_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.ContentValues;
import android.database.Cursor;

public class MyDB extends SQLiteOpenHelper {

    Sujeto mySujeto = new Sujeto();
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sujetoDB.db";
    public static final String TABLE_NAME = "Sujeto";
    public static final String COLUMN_ID = "SujetoID";
    public static final String COLUMN_NAME = "SujetoName";
    public static final String COLUMN_MAIL = "SujetoMail";
    public static final String COLUMN_EDAD = "SujetoEdad";
    public static final String COLUMN_GEN = "SujetoGen";
    public static final String COLUMN_ACCX = "SujetoAccX";
    public static final String COLUMN_ACCY = "SujetoAccY";
    public static final String COLUMN_ACCZ = "SujetoAccZ";
    //initialize the database
    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);// Son los elementos de la linea de arriba
    }
    @Override

    public void onCreate(SQLiteDatabase db) {//Creamos la tabla
       /* String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID +
                " INTEGER PRIMARYKEY ," + COLUMN_NAME + " TEXT, "+COLUMN_MAIL + " TEXT, "+ COLUMN_EDAD + " INTEGER )";
        db.execSQL(CREATE_TABLE);
       */ //aquí creamos la tabla de usuario (dni, nombre, ciudad, numero)
       db.execSQL("create table usuario(dni integer primary key, nombre text, mail text, edad integer, genero text, acelx text,acely text, acelz text, gyro_x text, gyro_y text, gyro_z text, pulso integer, tempe text)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
       // db.execSQL();
    }

/*
    public String loadHandler() {
        String result = "";
        String query = "Select*FROM" + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }*/
}