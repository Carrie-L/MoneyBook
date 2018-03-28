package com.carrie.lib.moneybook.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyParentEntity;
import com.carrie.lib.moneybook.model.ClassifyParent;

import java.util.List;

/**
 * Created by Carrie on 2018/3/28.
 */
@Dao
public interface ClassifyParentDao  {

    @Query("SELECT * FROM classifyParent")
    LiveData<List<ClassifyParentEntity>> getClassifyParents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClassifyParentEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClassifyParentEntity> list);

    @Delete
    void delete(ClassifyParentEntity entity);





}
