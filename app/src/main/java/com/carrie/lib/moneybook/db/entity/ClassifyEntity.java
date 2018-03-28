package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.carrie.lib.moneybook.model.Classify;

/**
 * Created by Carrie on 2018/3/27.
 * 消费类型：水果、早餐、购物...
 */
@Entity(tableName = "classify")
public class ClassifyEntity implements Classify {
    @NonNull
    @PrimaryKey
    public String classify;

    @NonNull
    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public ClassifyEntity(String classify) {
        this.classify = classify;
    }
}
