package com.carrie.lib.moneybook.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.ItemClassifyBinding;
import com.carrie.lib.moneybook.model.Classify;
import com.carrie.lib.moneybook.ui.ItemClickCallback;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ClassifyViewHolder> {
    private ItemClickCallback mCallback;

    private List<? extends Classify> mClassifies;

    public ClassifyAdapter(ItemClickCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void setClassifies(final List<? extends Classify> list) {
        if (mClassifies == null) {
            mClassifies = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mClassifies.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mClassifies.get(oldItemPosition).getClassify().equals(list.get(newItemPosition).getClassify());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Classify newClassify = list.get(newItemPosition);
                    Classify oldClassify = mClassifies.get(oldItemPosition);
                    return newClassify.getClassify().equals(oldClassify.getClassify());
                }
            });
            mClassifies = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ClassifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemClassifyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_classify, parent, false);
        return new ClassifyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ClassifyViewHolder holder, int position) {
        holder.binding.setObj(mClassifies.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mClassifies == null ? 0 : mClassifies.size();
    }

    static class ClassifyViewHolder extends RecyclerView.ViewHolder {
        private ItemClassifyBinding binding;

        public ClassifyViewHolder(ItemClassifyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
