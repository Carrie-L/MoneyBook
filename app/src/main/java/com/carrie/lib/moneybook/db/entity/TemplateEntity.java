package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.carrie.lib.moneybook.model.Template;

/**
 * Created by Carrie on 2018/3/27.
 */
@Entity(tableName = "template")
public class TemplateEntity implements Template {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double expense;
    public String classify;
    public String account;

    public TemplateEntity(int id, String name, double expense, String classify, String account) {
        this.id = id;
        this.name = name;
        this.expense = expense;
        this.classify = classify;
        this.account = account;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getExpense() {
        return expense;
    }

    @Override
    public String getClassify() {
        return classify;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
