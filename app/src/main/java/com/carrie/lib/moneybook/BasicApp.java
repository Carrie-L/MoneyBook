package com.carrie.lib.moneybook;

import android.app.Application;

import com.carrie.lib.moneybook.db.AppDatabase;
import com.carrie.lib.moneybook.utils.CrashHandler;

/**
 * Created by Carrie on 2018/3/27.
 */

public class BasicApp extends Application {

    private AppExecutor mAppExecutor;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutor = new AppExecutor();

        CrashHandler.getInstance(getApplicationContext());

    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(getApplicationContext(), mAppExecutor);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
