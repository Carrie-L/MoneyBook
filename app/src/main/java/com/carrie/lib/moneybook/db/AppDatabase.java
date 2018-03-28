package com.carrie.lib.moneybook.db;

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
import com.carrie.lib.moneybook.db.dao.TemplateDao;
import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.db.entity.ChargeEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.Charge;

/**
 * Created by Carrie on 2018/3/27.
 */
@Database(entities = {AccountEntity.class, ChargeEntity.class, ClassifyEntity.class}, version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "MoneyBook";
    private static AppDatabase sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract ChargeDao chargeDao();

    public abstract AccountDao accountDao();

    public abstract ClassifyDao classifyDao();

    public abstract TemplateDao templateDao();

    public static AppDatabase getInstance(Context appContext, AppExecutor executor) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(appContext.getApplicationContext(), executor);
                    sInstance.updateDatabaseCreated(appContext.getApplicationContext());
                }
            }
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
                        AppDatabase database = AppDatabase.getInstance(context, executor);
                        database.setDatabaseCreated();
                    }
                })
                .build();
    }

}
