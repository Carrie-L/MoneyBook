package com.carrie.lib.moneybook.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.FragmentClassifyEditBinding;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.utils.LogUtil;
import com.carrie.lib.moneybook.viewmodel.ClassifyViewModel;

/**
 * Created by Carrie on 2018/3/29.
 * ADD / EDIT / DELETE Classify
 */

public class ClassifyEditFragment extends DialogFragment {

    private ClassifyViewModel viewModel;
    private FragmentClassifyEditBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classify_edit, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(ClassifyViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectParent();
        setOk();
        setCancel();
    }

    private void selectParent() {
        binding.selectParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = viewModel.getParentClassifies();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.select_parent)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                LogUtil.i("selectParent", "items[i]=" + items[i]);
                                binding.selectParent.setText(items[i]);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void setOk() {
        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_name_not_null), Toast.LENGTH_SHORT).show();
                    binding.etName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(binding.selectParent.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_parent_not_null), Toast.LENGTH_SHORT).show();
                    binding.selectParent.requestFocus();
                    return;
                }

                if (viewModel.isClassifyExisted(binding.etName.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_name_must_unique), Toast.LENGTH_LONG).show();
                    binding.etName.requestFocus();
                    return;
                }

                ClassifyEntity entity = new ClassifyEntity();
                entity.setClassify(binding.etName.getText().toString().trim());
                entity.isParent = !viewModel.isSwithLeft.get();
                if(entity.isParent){
                    if(TextUtils.isEmpty(binding.etBudget.getText().toString().trim())){
                        entity.budget = 0.00;
                    }else{
                        entity.budget = Double.valueOf(binding.etBudget.getText().toString().trim());
                    }
                }
                viewModel.insertItem(entity);
                dismiss();

                LogUtil.i("setOk","insert success.");

            }
        });
    }

    private void setCancel() {
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
