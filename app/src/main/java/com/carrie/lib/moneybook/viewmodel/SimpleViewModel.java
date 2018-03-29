package com.carrie.lib.moneybook.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.SimpleList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 */

public class SimpleViewModel extends ViewModel {

    public MutableLiveData<List<SimpleList>> mObservableList = new MutableLiveData<>();

    public void setClassifies(List<ClassifyEntity> classifies) {
        List<SimpleList> lists = new ArrayList<>();
        int size = classifies.size();
        ClassifyEntity entity;
        for (int i = 0; i < size; i++) {
            entity = classifies.get(i);
            if (entity.isParent) {
                lists.add(new SimpleList(entity.getId(), entity.getClassify()));
            }
        }
        mObservableList.setValue(lists);
    }
}
