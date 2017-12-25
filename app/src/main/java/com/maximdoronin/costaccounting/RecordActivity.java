// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RecordActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mRecordNameText;
    private EditText mRecordDescriptionText;
    private EditText mRecordSumText;
    private Button mIncomeButton;
    private Button mConsumptionButton;

    private boolean isIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mRecordNameText = (EditText) findViewById(R.id.nameEdit);
        mRecordDescriptionText = (EditText) findViewById(R.id.descriptionEdit);
        mRecordSumText = (EditText) findViewById(R.id.sumEdit);

        updateView();

        mIncomeButton = (Button) findViewById(R.id.incomeButton);
        mConsumptionButton = (Button) findViewById(R.id.consumptionButton);

        setButtons();

        mIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIncome = true;
                setButtons();
            }
        });

        mConsumptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIncome = false;
                setButtons();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        updateView();
    }

    private void updateView() {
        Transaction transaction = Transaction.get();
        if (transaction.isCompleted()) {
            mRecordNameText.setText(transaction.getRecord().getName());
            mRecordDescriptionText.setText(transaction.getRecord().getmDescription());
            mRecordSumText.setText(String.valueOf(transaction.getRecord().getSum()));
            isIncome = transaction.getRecord().getRecordType() == Record.RecordType.INCOME;
            transaction.reset();
        }
    }

    private void setButtons() {
        if (isIncome) {
            mIncomeButton.setBackgroundResource(R.color.green);
            mConsumptionButton.setBackgroundResource(R.color.inactiveButton);
        } else {
            mIncomeButton.setBackgroundResource(R.color.inactiveButton);
            mConsumptionButton.setBackgroundResource(R.color.red);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cancel) {
            Intent intent = new Intent(RecordActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_save) {
            Transaction transaction = Transaction.get();
            transaction.reset();

            transaction.setRecord(new Record(
                    mRecordNameText.getText().toString(),
                    mRecordDescriptionText.getText().toString(),
                    isIncome ? Record.RecordType.INCOME : Record.RecordType.CONSUMPTION,
                    Integer.valueOf(mRecordSumText.getText().toString())));

            transaction.setIsCompleted(true);
            Intent intent = new Intent(RecordActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
