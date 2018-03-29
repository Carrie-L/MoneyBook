package com.carrie.lib.moneybook.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.ObservableBoolean;

/**
 * Created by Carrie on 2018/3/27.
 * 子类别
 * 消费类型：水果、早餐、购物...
 */
public interface Classify {
    int getId();

        int getParentId();
    String getClassify();

    boolean isParent();

    double getBudget();
}
