package com.carrie.lib.moneybook.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Carrie on 2018/3/27.
 * 消费类型：水果、早餐、购物...
 */
public interface Classify {
    String getClassify();
}
