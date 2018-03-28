package com.carrie.lib.moneybook.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.carrie.lib.moneybook.AppExecutor;
import com.carrie.lib.moneybook.db.converter.DateConverter;
import com.carrie.lib.moneybook.db.dao.AccountDao;
import com.carrie.lib.moneybook.db.dao.ChargeDao;
import com.carrie.lib.moneybook.db.dao.ClassifyDao;
import com.carrie.lib.moneybook.db.dao.ClassifyParentDao;
import com.carrie.lib.moneybook.db.dao.TemplateDao;
import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.db.entity.ChargeEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyParentEntity;
import com.carrie.lib.moneybook.model.Charge;
import com.carrie.lib.moneybook.utils.DataGenerator;
import com.carrie.lib.moneybook.utils.LogUtil;

import java.util.concurrent.Callable;

/**
 * Created by Carrie on 2018/3/27.
 */
@Database(entities = {AccountEntity.class, ChargeEntity.class, ClassifyEntity.class, ClassifyParentEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG="AppDatabase";
    public static final String DATABASE_NAME = "MoneyBook.db";
    private static AppDatabase sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract ChargeDao chargeDao();

    public abstract AccountDao accountDao();

    public abstract ClassifyParentDao classifyParentDao();

    public abstract ClassifyDao classifyDao();

    public abstract TemplateDao templateDao();

    public static AppDatabase getInstance(Context appContext, AppExecutor executor) {
        LogUtil.i(TAG,"getInstance");
        if (sInstance == null) {
            LogUtil.i(TAG,"sInstance == null");
            synchronized (AppDatabase.class) {
                LogUtil.i(TAG,"synchronized");
                if (sInstance == null) {
                    LogUtil.i(TAG,"synchronized:sInstance == null");
                    sInstance = buildDatabase(appContext.getApplicationContext(), executor);
                    sInstance.updateDatabaseCreated(appContext.getApplicationContext());
                }
            }
        }else{
            LogUtil.i(TAG,"sInstance != null");
        }
        return sInstance;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static AppDatabase buildDatabase(final Context context, final AppExecutor executor) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        LogUtil.i(TAG,"buildDatabase:onCreate ");


                        executor.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.i(TAG," executor.diskIO().execute");

                                // Add a delay to simulate a long-running operation
//                                addDelay();
                                // Generate the data for pre-population
                                final AppDatabase database = AppDatabase.getInstance(context, executor);
                                database.runInTransaction(new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtil.i(TAG," database.runInTransaction");

                                        long startTime = System.currentTimeMillis();
                                        database.classifyParentDao().insertAll(DataGenerator.generateClassifyParents());
                                        database.classifyDao().insertAll(DataGenerator.generateClassifies());
                                        database.accountDao().insertAll(DataGenerator.generateAccounts());
                                        long endTime = System.currentTimeMillis();
                                        LogUtil.i(TAG,"DataGenerator spend time: "+(endTime-startTime)+" ms");
                                    }
                                });
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .build();
    }

    private static void addDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }


}
