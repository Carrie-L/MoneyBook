package com.carrie.lib.moneybook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.carrie.lib.moneybook.BasicApp;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.Classify;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */

public class ClassifyViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<ClassifyEntity>> mObservableClassifies;

    public ClassifyViewModel(@NonNull Application application) {
        super(application);

        mObservableClassifies = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableClassifies.setValue(null);

        LiveData<List<ClassifyEntity>> classifies = ((BasicApp) application).getRepository().getAllClassifies();
        // observe the changes of the classifies from the database and forward them
        mObservableClassifies.addSource(classifies, new Observer<List<ClassifyEntity>>() {
            @Override
            public void onChanged(@Nullable List<ClassifyEntity> classifyEntities) {
                mObservableClassifies.setValue(classifyEntities);
            }
        });
    }

    /**
     * Expose the LiveData Classifies query so the UI can observe it.
     */
    public LiveData<List<ClassifyEntity>> getClassifies(){
        return mObservableClassifies;
    }


}
