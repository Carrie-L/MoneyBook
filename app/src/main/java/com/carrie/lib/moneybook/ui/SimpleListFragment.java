package com.carrie.lib.moneybook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.adapter.SimpleListAdapter;
import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.model.Common;
import com.carrie.lib.moneybook.model.SimpleList;
import com.carrie.lib.moneybook.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 * <p>
 * Bundle 接收参数： [list](must)、 [flag](optional)、[title](op)
 */

public class SimpleListFragment extends DialogFragment {

    private List<AccountEntity> lists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        Bundle args = getArguments();
        lists = args.getParcelableArrayList("list");
        AppUtils.logListString(lists);

        String title = args.getString("title");
        if (!TextUtils.isEmpty(title)) {
            ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        }

        SimpleListAdapter adapter = new SimpleListAdapter(lists, mCallback, args.getInt("flag"));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);


    }

    private OnClickCallback mCallback;

    public void setOnClickCallback(OnClickCallback callback) {
        mCallback = callback;
    }


}
