package com.carrie.lib.moneybook;

import android.arch.lifecycle.LiveData;

import com.carrie.lib.moneybook.db.AppDatabase;
import com.carrie.lib.moneybook.db.entity.ChargeEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */

public class DataRepository {
    private final static String TAG = "DataRepository";
    private static DataRepository sInstance;
    private AppDatabase database;
//    private MediatorLiveData<LiveData<>>

    public DataRepository(final AppDatabase database) {
        this.database = database;
//        MediatorLiveData    mObservableProducts = new MediatorLiveData<>();
//
//        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
//                productEntities -> {
//                    if (mDatabase.getDatabaseCreated().getValue() != null) {
//                        mObservableProducts.postValue(productEntities);
//                    }
//                });


    }

    public static DataRepository getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<ChargeEntity>> getAllCharges() {
        return database.chargeDao().getAllCharges();
    }

    public LiveData<List<ClassifyEntity>> getAllClassifies(){
        return database.classifyDao().getAllClassifies();
    }

    public List<ClassifyEntity> getParentClassifies(){
        return database.classifyDao().getParentClassifies();
    }

    //todo rewrite with LiveData and RxJava (run in background thread)
    public boolean isClassifyExisted(String classify){
        return database.classifyDao().isClassifyExisted(classify);
    }


    public void insertClassifyItem(ClassifyEntity entity){
        database.classifyDao().insertItem(entity);
    }




}
