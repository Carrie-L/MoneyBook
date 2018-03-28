package com.carrie.lib.moneybook.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.carrie.lib.moneybook.db.entity.ChargeEntity;
import com.carrie.lib.moneybook.model.Charge;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */
@Dao
public interface ChargeDao {

    @Query("SELECT * FROM charge")
    LiveData<List<ChargeEntity>> getAllCharges();



}
