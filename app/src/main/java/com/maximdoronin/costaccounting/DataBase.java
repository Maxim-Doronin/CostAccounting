// Copyright (c) 2017. All rights reserved.
// Author: Maxim Doronin <maximdoronin@yandex-team.ru>

package com.maximdoronin.costaccounting;

import java.util.List;


public interface DataBase {

    void addElement(Record record);

    List<Record> getElements();

    void deleteElement(int activeElement);

    Record getElementByName(String name);

    void deleteAll();
}
