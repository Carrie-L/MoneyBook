package com.carrie.lib.moneybook.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.adapter.ClassifyAdapter;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.model.Classify;
import com.carrie.lib.moneybook.viewmodel.ClassifyViewModel;

import java.util.List;

/**
 * Created by Carrie on 2018/3/28.
 */

public class ClassifyDialogFragment extends DialogFragment implements ItemClickCallback<ClassifyEntity> {
    private static final String TAG = "ClassifyDialogFragment";

    private String title;
    private RecyclerView recyclerView;
    private ClassifyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_classify, container, false);

        TextView textView = view.findViewById(R.id.tv_title);
        if (savedInstanceState != null) {
            textView.setText(savedInstanceState.getString("title"));
        }


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        adapter = new ClassifyAdapter(this);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ClassifyViewModel viewModel = ViewModelProviders.of(this).get(ClassifyViewModel.class);
        viewModel.getClassifies().observe(this, new Observer<List<ClassifyEntity>>() {
            @Override
            public void onChanged(@Nullable List<ClassifyEntity> classifyEntities) {
                if (classifyEntities != null) {
                    adapter.setClassifies(classifyEntities);
                }
            }
        });
    }

    private OnClickCallback mCallback;

    public void setOnClickCallback(OnClickCallback callback) {
        this.mCallback = callback;
    }


    @Override
    public void onItemClick(ClassifyEntity entity, int flag) {
        mCallback.onClick(entity.getClassify(), 101);
        dismiss();
    }
}
