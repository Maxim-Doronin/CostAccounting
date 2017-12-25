// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;


public class Record {

    public enum RecordType {
        CONSUMPTION,
        INCOME
    }

    private String mName;
    private String mDescription;
    private RecordType mRecordType;
    private int mSum;

    public Record(String name, String description, RecordType recordType, int sum) {
        mName = name;
        mDescription = description;
        mRecordType = recordType;
        mSum = sum;
    }

    public String getName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public int getSum() {
        return mSum;
    }
    public RecordType getRecordType() {
        return mRecordType;
    }
}
