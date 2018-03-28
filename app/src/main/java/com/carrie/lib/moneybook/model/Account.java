package com.carrie.lib.moneybook.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Carrie on 2018/3/27.
 * 账户类型：支付宝、微信、
 */
public interface Account {
    String getAccount();
}
