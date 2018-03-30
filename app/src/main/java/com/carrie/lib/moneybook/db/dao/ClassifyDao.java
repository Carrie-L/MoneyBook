package com.carrie.lib.moneybook.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.carrie.lib.moneybook.db.entity.ClassifyEntity;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */
@Dao
public interface ClassifyDao {

    @Query("select * from classify order by parentId desc,isParent desc")
    LiveData<List<ClassifyEntity>> getAllClassifies();

//    @Query("select * from classify where isParent=1")
//    LiveData<List<ClassifyEntity>> getParentClassifies();

    @Query("select * from classify where isParent=1")
    List<ClassifyEntity> getParentClassifies();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ClassifyEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClassifyEntity> list);

    @Update
    void updateItem(ClassifyEntity entity);

    @Update
    void updateItems(List<ClassifyEntity> classifies);

    @Delete
    void deleteAll(ClassifyEntity... classifies);

    @Delete
    void deleteItem(ClassifyEntity entity);

    @Query("select * from classify where classify = :classify")
     boolean isClassifyExisted(String classify);

    @Query("select classify from classify where parentId = :parentId and isParent =1")
    String getParentName(int parentId);


    @Query("update classify set parentId = :newParentId where parentId =:originalParentId")
    void updateItems(int originalParentId,int newParentId);

    @Query("delete from classify where parentId = :parentId")
    void deleteItems(int parentId);

//    @Query("")
//    List<ClassifyEntity> getClassifies(int parentId);

}
