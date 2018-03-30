package com.carrie.lib.moneybook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.InverseMethod;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.carrie.lib.moneybook.BasicApp;
import com.carrie.lib.moneybook.DataRepository;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.ui.ClassifyEditFragment;
import com.carrie.lib.moneybook.ui.OnClickCallback;
import com.carrie.lib.moneybook.utils.AppUtils;
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
    private final LiveData<List<ClassifyEntity>> classifies;


    /*-----      ClassifyEditFragment                    -----*/
    /**  true: 打开左边开关; false，右边  */
    public final ObservableBoolean isSwitchLeft = new ObservableBoolean(true);
    /**  true: add ; false: edit */
    public final ObservableBoolean isAddMode = new ObservableBoolean(true);
    /**  给 switch 开关着色用的 */
    public final ObservableField<Drawable> tintDrawable=new ObservableField<>();

    public final ObservableField<String> mClassifyName = new ObservableField<>();
    public final ObservableField<String> mParentName = new ObservableField<>();
    public final ObservableField<String> mBudget = new ObservableField<>();
    /*-----      --------------------                   -----*/

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

   private MutableLiveData<ClassifyEntity> mObservableEntity;
    public void setIsSelected(){
        mObservableEntity=new MutableLiveData<>();
//        mObservableEntity.postValue();
    }


    /*-----      ClassifyEditFragment                    -----*/
    public void onSwitchClick() {
        isSwitchLeft.set(!isSwitchLeft.get());
    }

    public boolean isClassifyExisted(String newClassify) {
        return mDataRepository.isClassifyExisted(newClassify);
    }

    public void insertOrUpdateItem(ClassifyEntity entity){
        if(isAddMode.get()){
            mDataRepository.insertClassifyItem(entity);
        }else{
            mDataRepository.updateClassifyItem(entity);
        }

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
        return list;
    }

    public String getParentName(int parentId){
      return   mDataRepository.getParentName(parentId);
    }

    public void migrateChildClassifies(int originalParentId,int newId){
        mDataRepository.migrateChildClassifies(originalParentId,newId);
    }

    public double getBudget(){
        return Double.parseDouble(mBudget.get()==null?"0.00":mBudget.get());
    }

     /*-----      --------------------                   -----*/

    public boolean getMode(){
        return isAddMode.get();
    }

    public void deleteItem(ClassifyEntity entity){
        mDataRepository.deleteItem(entity);
    }

    public void deleteItems(int parentId){
        mDataRepository.deleteItems(parentId);
    }

    public void resetAllData(){
        isSwitchLeft.set(true);
        mClassifyName.set("");
        mParentName.set("");
        mBudget.set("");
    }







}
