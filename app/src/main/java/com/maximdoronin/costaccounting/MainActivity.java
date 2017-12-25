// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setBackgroundResource(R.drawable.ic_save);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Transaction transaction = Transaction.get();
                transaction.reset();

                Record record = DataBaseSql.get().getElements().get(position);
                transaction.setRecord(record);
                transaction.setIsCompleted(true);

                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        DataBaseSql.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        DataBase dataBase = DataBaseSql.get();
        if (id == R.id.clear_all) {
            dataBase.deleteAll();
            fillListView();
            return true;
        } else if (id == R.id.summary) {
            List<Record> records = dataBase.getElements();
            int sum = 0;
            for (Record record : records) {
                sum += record.getSum() *
                        (record.getRecordType() == Record.RecordType.INCOME ? 1 : -1);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Summary")
                    .setMessage(String.valueOf(sum))
                    .setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        Transaction transaction = Transaction.get();
        if (transaction.isCompleted()) {
            DataBase dataBaseSql = DataBaseSql.get();
            dataBaseSql.addElement(transaction.getRecord());
            transaction.reset();
        }
        fillListView();
    }

    private void fillListView() {
        DataBase dataBase = DataBaseSql.get();

        final List<String> recordNames = new ArrayList<>();
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, recordNames);
        mListView.setAdapter(adapter);

        for (Record record : dataBase.getElements()) {
            recordNames.add(record.getName());
        }
    }
}
