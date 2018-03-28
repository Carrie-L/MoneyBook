package com.carrie.lib.moneybook.ui;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.HomeFragmentBinding;
import com.carrie.lib.moneybook.utils.LogUtil;
import com.carrie.lib.moneybook.viewmodel.MainViewModel;

/**
 * Created by Carrie on 2018/3/28.
 * 首页
 */

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private HomeFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        LogUtil.i(TAG,"onCreateView");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel mainViewModel = new MainViewModel(getActivity().getApplication());
        binding.setMainViewModel(mainViewModel);
        binding.executePendingBindings();

    }
}
