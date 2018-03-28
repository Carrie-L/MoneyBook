package com.carrie.lib.moneybook;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.carrie.lib.moneybook.db.AppDatabase;
import com.carrie.lib.moneybook.db.entity.ChargeEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.Charge;

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


}
