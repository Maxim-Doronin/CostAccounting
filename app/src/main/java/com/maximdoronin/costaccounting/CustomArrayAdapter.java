// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public CustomArrayAdapter(Context context, List<String> values) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView rowName = rowView.findViewById(R.id.row_name);
        TextView rowSum = rowView.findViewById(R.id.row_sum);

        Record record = DataBaseSql.get().getElements().get(position);
        if (record != null) {
            rowName.setText(record.getName());
            rowSum.setText(String.valueOf(record.getSum()));

            if (record.getRecordType() == Record.RecordType.INCOME) {
                rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            } else {
                rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            }
        }
        return rowView;
    }
}
