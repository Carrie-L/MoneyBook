package com.carrie.lib.moneybook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.HomeFragmentBinding;
import com.carrie.lib.moneybook.viewmodel.MainViewModel;

/**
 * Created by Carrie on 2018/3/28.
 * 首页
 */

public class HomeFragment extends Fragment implements OnClickCallback{
    public static final String TAG = "HomeFragment";
    private HomeFragmentBinding binding;
    private static final Integer FLAG_CLASSIFY = 1;
    private static final Integer FLAG_ACCOUNT = 2;
    private static final Integer FLAG_DATE = 3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel mainViewModel = new MainViewModel(getActivity().getApplication());
        binding.setMainViewModel(mainViewModel);
        binding.setClickCallback(this);
        binding.executePendingBindings();
        mainViewModel.setOnClickCallback(this);

    }


    @Override
    public <T> void onClick(T object,int flag) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("测试对话框").setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        }).show();

        if(flag==FLAG_CLASSIFY){
         FragmentTransaction transaction= getFragmentManager().beginTransaction();
         Fragment fragment = getFragmentManager().findFragmentByTag("Dialog");
         if(fragment!=null){
            transaction.remove(fragment);
         }
            transaction.addToBackStack(null);

         Bundle args = new Bundle();
            args.putString("title",getString(R.string.nav_classify));

            ClassifyDialogFragment dialogFragment = new ClassifyDialogFragment();
            dialogFragment.setArguments(args);
         dialogFragment.show(transaction,"Dialog");
        }
    }
}
