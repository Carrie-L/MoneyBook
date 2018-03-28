package com.carrie.lib.moneybook.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.adapter.ClassifyAdapter;
import com.carrie.lib.moneybook.databinding.ActivityClassifyBinding;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.utils.LogUtil;
import com.carrie.lib.moneybook.viewmodel.ClassifyViewModel;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 * 消费类型页
 */

public class ClassifyFragment extends Fragment implements ItemClickCallback {
    public static final String TAG = "ClassifyFragment";
    private ActivityClassifyBinding mBinding;
    private ClassifyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_classify, container, false);
        LogUtil.i(TAG,"onCreateView");

        adapter = new ClassifyAdapter(this);
        mBinding.classifyRv.setAdapter(adapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ClassifyViewModel viewModel = ViewModelProviders.of(this).get(ClassifyViewModel.class);
        subscribeUI(viewModel);

    }

    private void subscribeUI(ClassifyViewModel viewModel) {
        viewModel.getClassifies().observe(this, new Observer<List<ClassifyEntity>>() {
            @Override
            public void onChanged(@Nullable List<ClassifyEntity> classifyEntities) {
                if (classifyEntities != null) {
                    adapter.setClassifies(classifyEntities);
                }
            }
        });
    }

    @Override
    public <T> void onClick(T t) {

    }
}