package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.carrie.lib.moneybook.model.Account;
import com.carrie.lib.moneybook.model.Common;

/**
 * Created by Carrie on 2018/3/27.
 * 账户类型：支付宝、微信、
 */
@Entity(tableName = "account")
public class AccountEntity implements Common, Parcelable {

    @NonNull
    @PrimaryKey
    public String account="";

    public void setName(String name) {
        this.account = name;
    }

    public AccountEntity(String account) {
        this.account = account;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return account;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
    }

    protected AccountEntity(Parcel in) {
        this.account = in.readString();
    }

    public static final Parcelable.Creator<AccountEntity> CREATOR = new Parcelable.Creator<AccountEntity>() {
        public AccountEntity createFromParcel(Parcel source) {
            return new AccountEntity(source);
        }

        public AccountEntity[] newArray(int size) {
            return new AccountEntity[size];
        }
    };

    @Override
    public String toString() {
        return "AccountEntity{" +
                "account='" + account + '\'' +
                '}';
    }
}
