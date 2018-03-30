package com.carrie.lib.moneybook.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.HomeFragmentBinding;
import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.model.Account;
import com.carrie.lib.moneybook.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * Created by Carrie on 2018/3/28.
 * 首页
 */

public class HomeFragment extends Fragment implements OnClickCallback {
    public static final String TAG = "HomeFragment";
    private HomeFragmentBinding binding;
    public static final int FLAG_CLASSIFY = 1; // 100 ： classify的子项点击事件
    public static final int FLAG_ACCOUNT = 2; // 200: account 的子项点击事件
    private static final int FLAG_DATE = 3;

    private EditText etClassify;
    private EditText etAccount;
    private MainViewModel mainViewModel;
    private SimpleListFragment dialogFragment;


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

        watchPayEdit();

    }

    private void watchPayEdit() {
        mainViewModel.payMoney.set(binding.etMoney.getText().toString());
    }


    @Override
    public <T> void onClick(T object, Integer flag) {
        switch (flag) {
            case FLAG_CLASSIFY:
                showClassifyDialog();
                break;

            case FLAG_ACCOUNT:
                showAccountDialog();
                break;

            case FLAG_DATE:

                break;
            case 100: // classify item
                etClassify.setText((String) object);
                break;

            case 200: // account item
                binding.account.setText(((AccountEntity) object).getName());
                dialogFragment.dismiss();
                break;

        }

        if (flag == FLAG_CLASSIFY) {

        } else if (flag == 101) {
            etClassify.setText((String) object);
        }
    }

    private void showClassifyDialog() {
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
    }

    private void showAccountDialog() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("Dialog");
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.addToBackStack(null);

        Bundle args = new Bundle();
        args.putString("title", getString(R.string.nav_account));
        args.putInt("flag", 200);
        args.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) mainViewModel.getAccounts());

        dialogFragment = new SimpleListFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(transaction, "Dialog");
        dialogFragment.setOnClickCallback(this);
    }


}
