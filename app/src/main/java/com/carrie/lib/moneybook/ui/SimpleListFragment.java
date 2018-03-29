package com.carrie.lib.moneybook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.adapter.SimpleListAdapter;
import com.carrie.lib.moneybook.model.SimpleList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 */

public class SimpleListFragment extends Fragment implements ItemClickCallback{

    private RecyclerView recyclerView;
    private List<SimpleList> lists=new ArrayList<>();
    private SimpleListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new SimpleListAdapter(lists,this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
//        args.get

    }


    @Override
    public <T> void onClick(T t) {

    }
}
