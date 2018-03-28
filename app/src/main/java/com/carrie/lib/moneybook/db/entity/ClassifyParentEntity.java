package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.carrie.lib.moneybook.model.ClassifyParent;

/**
 * Created by Carrie on 2018/3/28.
 * id 需从1开始
 *
 */
@Entity(tableName = "classifyParent")
public class ClassifyParentEntity implements ClassifyParent {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String classifyParent;

    public double budget;

    public ClassifyParentEntity(int id, String classifyParent, double budget) {
        this.id = id;
        this.classifyParent = classifyParent;
        this.budget = budget;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassifyParent(String classifyParent) {
        this.classifyParent = classifyParent;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public int getParentId() {
        return id;
    }

    @Override
    public String getClassifyParent() {
        return classifyParent;
    }

    @Override
    public double getBudget() {
        return budget;
    }
}
