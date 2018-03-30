package com.carrie.lib.moneybook.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */
@Dao
public interface AccountDao {

    @Query("select * from account")
    List<AccountEntity> getAccounts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AccountEntity> list);

}
