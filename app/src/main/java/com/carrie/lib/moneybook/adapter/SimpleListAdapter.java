package com.carrie.lib.moneybook.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.ItemSimpleListBinding;
import com.carrie.lib.moneybook.model.Common;
import com.carrie.lib.moneybook.model.SimpleList;
import com.carrie.lib.moneybook.ui.ItemClickCallback;
import com.carrie.lib.moneybook.ui.OnClickCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 * 通用简单列表
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder> {
    private List<? extends Common> lists = new ArrayList<>();
    private OnClickCallback mCallback;
    private Integer mFlag;

    public SimpleListAdapter(List<? extends Common> lists, OnClickCallback mCallback,Integer flag) {
        this.lists = lists;
        this.mCallback = mCallback;
        this.mFlag = flag;
    }

    public void setList(List<? extends Common> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSimpleListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_simple_list, parent, false);
        binding.setCallback(mCallback);
        binding.setFlag(mFlag);
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
