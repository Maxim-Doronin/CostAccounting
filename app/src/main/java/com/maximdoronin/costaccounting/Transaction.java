// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;


public class Transaction {
    private static Transaction mInstance;

    private Record mRecord;
    private boolean mIsCompleted;

    public Transaction() {
        reset();
    }

    public void reset() {
        mRecord = null;
        mIsCompleted = false;
    }

    public static Transaction get() {
        if (mInstance == null)
            mInstance = new Transaction();
        return mInstance;
    }

    public void setRecord(Record record) {
        mRecord = record;
    }

    public Record getRecord() {
        return mRecord;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        if (mRecord != null) {
            mIsCompleted = isCompleted;
        } else {
            mIsCompleted = false;
        }
    }
}
