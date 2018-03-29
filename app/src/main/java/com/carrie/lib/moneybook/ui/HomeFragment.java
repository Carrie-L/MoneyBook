package com.carrie.lib.moneybook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.HomeFragmentBinding;
import com.carrie.lib.moneybook.viewmodel.MainViewModel;

/**
 * Created by Carrie on 2018/3/28.
 * 首页
 */

public class HomeFragment extends Fragment implements OnClickCallback {
    public static final String TAG = "HomeFragment";
    private HomeFragmentBinding binding;
    private static final Integer FLAG_CLASSIFY = 1;
    private static final Integer FLAG_ACCOUNT = 2;
    private static final Integer FLAG_DATE = 3;

    private EditText etClassify;
    private MainViewModel mainViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        etClassify = binding.classify;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainViewModel = new MainViewModel(getActivity().getApplication());
        binding.setMainViewModel(mainViewModel);
        binding.setClickCallback(this);
        binding.executePendingBindings();
        mainViewModel.setOnClickCallback(this);

        watchPayEdit();

    }

    private void watchPayEdit(){
        mainViewModel.payMoney.set(binding.etMoney.getText().toString());
    }


    @Override
    public <T> void onClick(T object, int flag) {
        if (flag == FLAG_CLASSIFY) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("Dialog");
            if (fragment != null) {
                transaction.remove(fragment);
            }
            transaction.addToBackStack(null);

            Bundle args = new Bundle();
            args.putString("title", getString(R.string.nav_classify));

            ClassifyDialogFragment dialogFragment = new ClassifyDialogFragment();
            dialogFragment.setArguments(args);
            dialogFragment.show(transaction, "Dialog");
            dialogFragment.setOnClickCallback(this);
        } else if (flag == 101) {
            etClassify.setText((String) object);
        }
    }
}
