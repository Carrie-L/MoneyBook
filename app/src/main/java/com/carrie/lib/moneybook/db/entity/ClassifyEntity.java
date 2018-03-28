package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.carrie.lib.moneybook.model.Classify;
import com.carrie.lib.moneybook.model.ClassifyParent;

/**
 * Created by Carrie on 2018/3/27.
 * 消费类型：水果、早餐、购物...
 */
@Entity(tableName = "classify",
        foreignKeys = @ForeignKey(entity = ClassifyParentEntity.class, parentColumns = "id", childColumns = "parentId", onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "parentId"))
public class ClassifyEntity implements Classify {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int parentId;

    @NonNull
    public String classify;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getParentId() {
        return 0;
    }

    @NonNull
    public String getClassify() {
        return classify;
    }

    public void setClassify(@NonNull String classify) {
        this.classify = classify;
    }


}
