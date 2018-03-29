package com.carrie.lib.moneybook.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.FragmentClassifyEditBinding;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.utils.LogUtil;
import com.carrie.lib.moneybook.viewmodel.ClassifyViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 * ADD / EDIT / DELETE Classify
 */

public class ClassifyEditFragment extends DialogFragment {

    private ClassifyViewModel viewModel;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentClassifyEditBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classify_edit, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(ClassifyViewModel.class);
        binding.setViewModel(viewModel);
        spinner = binding.spinnerAccount;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        List<ClassifyEntity> list = bundle.getParcelableArrayList("list");


        if(viewModel.getClassifies()==null){
            LogUtil.i("ClassifyEditFragment","viewModel.getClassifies()==null");
        }else{
            LogUtil.i("ClassifyEditFragment","viewModel.getClassifies()!=null");

          if(  viewModel.getClassifies().getValue()==null){
              LogUtil.i("ClassifyEditFragment","viewModel.getClassifies().getValue()==null");
          }else{
              LogUtil.i("ClassifyEditFragment","viewModel.getClassifies().getValue()!=null: "+viewModel.getClassifies().getValue().size());
          }

        }


        spinner.setPrompt(getString(R.string.nav_classify));
        SpinnerAdapter adapter = new ArrayAdapter<>(getContext().getApplicationContext(),android.R.layout.simple_list_item_1, viewModel.getParentClassifies());
        spinner.setAdapter(adapter);

    }
}
