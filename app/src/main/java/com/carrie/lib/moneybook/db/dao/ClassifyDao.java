package com.carrie.lib.moneybook.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.Classify;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */
@Dao
public interface ClassifyDao {

    @Query("select * from classify")
    LiveData<List<ClassifyEntity>> getAllClassifies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ClassifyEntity entity);

    @Delete
    void deleteAll(ClassifyEntity... classifies);
}
