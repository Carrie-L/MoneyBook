package com.carrie.lib.moneybook.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.carrie.lib.moneybook.BR;
import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.ItemClassifyBinding;
import com.carrie.lib.moneybook.databinding.ItemClassifyParentBinding;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.ui.ItemClickCallback;

import java.util.List;

/**
 * Created by Carrie on 2018/3/27.
 */

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ClassifyViewHolder> {
    private static final String TAG = "ClassifyAdapter";
    private ItemClickCallback mCallback;

    private List<ClassifyEntity> mClassifies;

    private static final Integer TYPE_PARENT = 0;
    private static final Integer TYPE_CHILD = 1;

    public ClassifyAdapter(ItemClickCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void setClassifies(final List<ClassifyEntity> list) {
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
                    ClassifyEntity newClassify = list.get(newItemPosition);
                    ClassifyEntity oldClassify = mClassifies.get(oldItemPosition);
                    return newClassify.getClassify().equals(oldClassify.getClassify());
                }
            });
            mClassifies = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ClassifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_PARENT){
            ItemClassifyParentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_classify_parent, parent, false);
            binding.setCallback(mCallback);
            return new ClassifyViewHolder(binding);
        }else{
            ItemClassifyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_classify, parent, false);
            binding.setCallback(mCallback);
            return new ClassifyViewHolder(binding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mClassifies.get(position).isParent()) {
            return TYPE_PARENT;
        }else{
            return TYPE_CHILD;
        }
    }

    @Override
    public void onBindViewHolder(ClassifyViewHolder holder, int position) {
        holder.binding.setVariable(BR.obj,mClassifies.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mClassifies == null ? 0 : mClassifies.size();
    }

    static class ClassifyViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        ClassifyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
