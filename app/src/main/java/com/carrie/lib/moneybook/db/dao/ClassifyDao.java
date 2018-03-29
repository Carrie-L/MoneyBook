package com.carrie.lib.moneybook.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carrie.lib.moneybook.db.entity.ClassifyEntity;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */
@Dao
public interface ClassifyDao {

    @Query("select * from classify order by parentId")
    LiveData<List<ClassifyEntity>> getAllClassifies();

//    @Query("select * from classify where isParent=1")
//    LiveData<List<ClassifyEntity>> getParentClassifies();

    @Query("select * from classify where isParent=1")
    List<ClassifyEntity> getParentClassifies();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ClassifyEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClassifyEntity> list);

    @Delete
    void deleteAll(ClassifyEntity... classifies);

    @Query("select * from classify where classify = :classify")
     boolean isClassifyExisted(String classify);

}
