package com.carrie.lib.moneybook.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.ActivityMainBinding;
import com.carrie.lib.moneybook.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        MainViewModel mainViewModel = new MainViewModel(getApplication());
        binding.setMainViewModel(mainViewModel);


    }
}
