package com.carrie.lib.moneybook.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.adapter.ClassifyAdapter;
import com.carrie.lib.moneybook.databinding.ActivityClassifyBinding;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.utils.LogUtil;
import com.carrie.lib.moneybook.viewmodel.ClassifyViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.carrie.lib.moneybook.utils.Constant.FLAG_ON_CLICK;
import static com.carrie.lib.moneybook.utils.Constant.FLAG_ON_LONG_CLICK;

/**
 * Created by Carrie on 2018/3/27.
 * 消费类型页
 * 长按删除
 */

public class ClassifyFragment extends Fragment implements ItemClickCallback<ClassifyEntity> {
    public static final String TAG = "ClassifyFragment";
    private ActivityClassifyBinding mBinding;
    private ClassifyAdapter adapter;
    private List<ClassifyEntity> list = new ArrayList<>();
    private ClassifyEditFragment mEditFragment;
    private Bundle args = new Bundle();
    private ClassifyViewModel viewModel;
    ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_classify, container, false);

        adapter = new ClassifyAdapter(this);
        mBinding.classifyRv.setAdapter(adapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(ClassifyViewModel.class);
        subscribeUI(viewModel);

        mBinding.fabClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment(true);
            }
        });
    }

    private void subscribeUI(ClassifyViewModel viewModel) {
        viewModel.getClassifies().observe(this, new Observer<List<ClassifyEntity>>() {
            @Override
            public void onChanged(@Nullable List<ClassifyEntity> classifyEntities) {
                if (classifyEntities != null) {
                    list = classifyEntities;
                    adapter.setClassifies(classifyEntities);
                }
            }
        });
    }

    private void showDialogFragment(boolean isAddMode) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("Dialog");
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.addToBackStack(null);

        mEditFragment = new ClassifyEditFragment();
        mEditFragment.show(transaction, "Dialog");
        viewModel.isAddMode.set(isAddMode);
    }

    @Override
    public void onItemClick(final ClassifyEntity entity, int flag) {
        showDialogFragment(false);
                Bundle args = new Bundle();
                args.putInt("id",entity.getId());
                args.putInt("parentId",entity.parentId);
                args.putParcelable("entity",entity);
                mEditFragment.setArguments(args);


//        switch (flag){
//            case FLAG_ON_CLICK:
//                showDialogFragment(false);
//                Bundle args = new Bundle();
//                args.putInt("id",entity.getId());
//                args.putInt("parentId",entity.parentId);
//                args.putParcelable("entity",entity);
//                mEditFragment.setArguments(args);
//                break;
//
//            case FLAG_ON_LONG_CLICK:
//                LogUtil.i(TAG,"on long click");
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Delete?")
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                viewModel.deleteItem(entity);
////                                list.remove(entity);
////                                adapter.setClassifies(list);
//                            }
//                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
//                break;
//        }
    }

}
