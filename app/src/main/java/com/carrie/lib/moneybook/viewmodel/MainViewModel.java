package com.carrie.lib.moneybook.viewmodel;

import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carrie.lib.moneybook.BasicApp;
import com.carrie.lib.moneybook.DataRepository;
import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.ui.OnClickCallback;

import java.util.Date;
import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    public final ObservableBoolean isCardViewShow = new ObservableBoolean(true);
    public final ObservableField<String> date = new ObservableField<>(new Date().toString());
    public final ObservableField<String> payMoney = new ObservableField<>("0.00");
    private DataRepository mDataRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = ((BasicApp)application).getRepository();
    }

    public void onCardViewShow(boolean bool) {
        Log.i(TAG, "onCardViewShow");
        isCardViewShow.set(bool);
    }

    public List<AccountEntity> getAccounts(){
        return mDataRepository.getAccounts();
    }







}
