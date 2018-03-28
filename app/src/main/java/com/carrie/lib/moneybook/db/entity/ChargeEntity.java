package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.carrie.lib.moneybook.model.Charge;

import java.util.Date;

/**
 * 记账流水
 * Created by Carrie on 2018/3/27.
 */
@Entity(tableName = "charge")
// foreignKeys = @ForeignKey(
//        entity = ChargeEntity.class, parentColumns = {"id","id"}, childColumns = {"classify", "account"},
//        onDelete = ForeignKey.CASCADE,
//        onUpdate = ForeignKey.CASCADE)
public class ChargeEntity implements Charge {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double money;
    public String classify;
    public String account;
    public Date date;
    /**
     * 是否为支出
     */
    public int isExpense;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public double getMoney() {
        return money;
    }

    @Override
    public String getClassify() {
        return classify;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public Date getConsumeDay() {
        return date;
    }

    @Override
    public int isExpense() {
        return isExpense;
    }
}
