// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DataBaseSql extends SQLiteOpenHelper implements DataBase {
    private static DataBaseSql mInstance = null;

    private SQLiteDatabase mDatabase;

    public static DataBase get() {
        return mInstance;
    }

    public static void init(Context context) {
        mInstance = new DataBaseSql(context);
    }

    private DataBaseSql(Context context) {
        super(context, "ElementsDataBase", null, 2);
        mDatabase = getWritableDatabase();
    }

    @Override
    public void addElement(Record record) {
        mDatabase.execSQL("INSERT INTO records VALUES (" +
                "\"" + record.getName() + "\"," +
                "\"" + record.getmDescription() + "\"," +
                "\"" + record.getRecordType().toString() + "\"" + "," +
                record.getSum() +  ");");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        mDatabase = sqLiteDatabase;
        mDatabase.execSQL(
                "CREATE TABLE records (ELNAME TEXT, ELSUM TEXT, ELTYPE TEXT, ELVALUE INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mDatabase = db;
    }

    public List<Record> getElements() {
        List<Record> result = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM records WHERE 1", new String[]{});
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            result.add(new Record(cursor.getString(0), cursor.getString(1),
                    Record.RecordType.valueOf(cursor.getString(2)), cursor.getInt(3)));
            cursor.moveToNext();
        }
        return result;
    }

    public void deleteElement(int activeElement) {
        Record killed = DataBaseSql.get().getElements().get(activeElement);
        mDatabase.execSQL("DELETE FROM records WHERE ELNAME=\"" + killed.getName()+"\";");
    }

    public Record getElementByName(String name) {
        List<Record> records = getElements();
        for (Record record : records) {
            if (record.getName().equals(name)) {
                return record;
            }
        }
        return null;
    }

    public void deleteAll() {
        List<Record> records = getElements();
        for (int i = records.size() - 1; i >= 0; i--) {
            deleteElement(i);
        }
    }
}
