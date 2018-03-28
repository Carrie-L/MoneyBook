package com.carrie.lib.moneybook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;

/**
 * Created by Carrie on 2018/3/27.
 */

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    public final ObservableBoolean isCardViewShow = new ObservableBoolean(true);
    public final ObservableField<String> date = new ObservableField<>(new Date().toString());


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void onCardViewShow(boolean bool) {
        Log.i(TAG, "onCardViewShow");
        isCardViewShow.set(bool);
    }

    public void onClassifyClick(){

    }

    public void onAccountClick(){

    }

    public void onDateClick(){

    }


}
