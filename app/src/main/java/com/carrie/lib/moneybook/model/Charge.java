package com.carrie.lib.moneybook.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * 记账流水
 * Created by Carrie on 2018/3/27.
 */
public interface Charge {

    int getId();

    double getMoney();

    String getClassify();

    String getAccount();

    Date getConsumeDay();

    /**
     * 是否为支出
     */
    int isExpense();


}
