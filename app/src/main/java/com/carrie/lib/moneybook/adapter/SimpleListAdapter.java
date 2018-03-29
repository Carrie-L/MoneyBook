package com.carrie.lib.moneybook.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.ItemSimpleListBinding;
import com.carrie.lib.moneybook.model.SimpleList;
import com.carrie.lib.moneybook.ui.ItemClickCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 * 通用简单列表
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder> {
    private List<SimpleList> lists=new ArrayList<>();
    private ItemClickCallback mCallback;

    public SimpleListAdapter(List<SimpleList> lists, ItemClickCallback mCallback) {
        this.lists = lists;
        this.mCallback = mCallback;
    }

    public void setList(List<SimpleList> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSimpleListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_simple_list, parent, false);
        binding.setCallback(mCallback);
        return new SimpleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.binding.setObj(lists.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private ItemSimpleListBinding binding;

        public SimpleViewHolder(ItemSimpleListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
