package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.carrie.lib.moneybook.model.Account;

/**
 * Created by Carrie on 2018/3/27.
 * 账户类型：支付宝、微信、
 */
@Entity(tableName = "account")
public class AccountEntity implements Account {
    @NonNull
    @PrimaryKey
    public String account="";

    @NonNull
    @Override
    public String getAccount() {
        return account;
    }

    public void setName(String name) {
        this.account = name;
    }

    public AccountEntity(String account) {
        this.account = account;
    }

    @Ignore
    public AccountEntity(Account account) {
        this.account = account.getAccount();
    }


}
