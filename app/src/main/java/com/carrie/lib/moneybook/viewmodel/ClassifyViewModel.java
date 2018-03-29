package com.carrie.lib.moneybook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.carrie.lib.moneybook.BasicApp;
import com.carrie.lib.moneybook.DataRepository;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */

public class ClassifyViewModel extends AndroidViewModel {
    private static final String TAG = "ClassifyViewModel";
    private final MediatorLiveData<List<ClassifyEntity>> mObservableClassifies;
    private DataRepository mDataRepository;

    //-----      ClassifyEditFragment                    -----
    public final ObservableBoolean isSwithLeft = new ObservableBoolean(true);
    private final LiveData<List<ClassifyEntity>> classifies;

    public ClassifyViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = ((BasicApp) application).getRepository();


        mObservableClassifies = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableClassifies.setValue(null);

        classifies = mDataRepository.getAllClassifies();

        LogUtil.i(TAG, "classifies:");

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
    public LiveData<List<ClassifyEntity>> getClassifies() {
        return mObservableClassifies;
    }


    public void onSwitchClick() {
        isSwithLeft.set(!isSwithLeft.get());
    }

    public boolean isClassifyExisted(String newClassify) {
        boolean isClassifyExisted= mDataRepository.isClassifyExisted(newClassify);
        LogUtil.i(TAG,"isClassifyExisted="+isClassifyExisted);
        return isClassifyExisted;
    }

    public void insertItem(ClassifyEntity entity){
        mDataRepository.insertClassifyItem(entity);
    }

    public List<ClassifyEntity> getParentClassifies2() {
        List<ClassifyEntity> list = new ArrayList<>();
        if (mObservableClassifies.getValue() != null) {
            int size = mObservableClassifies.getValue().size();
            for (int i = 0; i < size; i++) {
                if (mObservableClassifies.getValue().get(i).isParent) {
                    list.add(mObservableClassifies.getValue().get(i));
                }
            }
        }
        LogUtil.i(TAG, "getParentClassifies:" + list.size());
        return list;
    }

    public String[] getParentClassifies() {
        int size = mObservableClassifies.getValue().size();
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (mObservableClassifies.getValue().get(i).isParent) {
                items.add(mObservableClassifies.getValue().get(i).getClassify());
            }
        }
        return items.toArray(new String[0]);
    }


}
