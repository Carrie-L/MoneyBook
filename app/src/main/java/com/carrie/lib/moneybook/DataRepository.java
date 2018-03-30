package com.carrie.lib.moneybook;

import android.arch.lifecycle.LiveData;

import com.carrie.lib.moneybook.db.AppDatabase;
import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.db.entity.ChargeEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.Account;

import java.util.List;

import static com.carrie.lib.moneybook.utils.AppUtils.logListString;

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

    public void updateClassifyItem(ClassifyEntity entity){
        database.classifyDao().updateItem(entity);
    }

    public String getParentName(int parentId){
       return database.classifyDao().getParentName(parentId);
    }

    /**
     *  当父类转为子类时，迁移旗下所有子类到新的父类中。
     *  @param originalParentId 原父类parentId
     */
    public void migrateChildClassifies(int originalParentId,int newId){
     database.classifyDao().updateItems(originalParentId,newId);
    }


    /*  Account  */
    public List<AccountEntity> getAccounts(){
        return database.accountDao().getAccounts();
    }

    public void deleteItem(ClassifyEntity entity){
        database.classifyDao().deleteItem(entity);
    }

    public void deleteItems(int parentId){
        database.classifyDao().deleteItems(parentId);
    }



}
