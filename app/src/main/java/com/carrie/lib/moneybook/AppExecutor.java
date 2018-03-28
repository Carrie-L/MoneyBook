package com.carrie.lib.moneybook;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Carrie on 2018/3/27.
 */

public class AppExecutor {

    public final Executor mDiskIO;

    public final Executor mNetworkIO;

    public final Executor mMainThread;

    public AppExecutor(Executor mDiskIO, Executor mNetworkIO, Executor mMainThread) {
        this.mDiskIO = mDiskIO;
        this.mNetworkIO = mNetworkIO;
        this.mMainThread = mMainThread;
    }

    public AppExecutor(){
        this(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainThreadExecutor());
    }

    public Executor diskIO(){
        return mDiskIO;
    }

    public Executor networkIO(){
        return mNetworkIO;
    }

    public Executor mainThread(){
        return mMainThread;
    }


    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
