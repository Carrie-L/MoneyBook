package com.carrie.lib.moneybook.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.databinding.FragmentClassifyEditBinding;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.utils.AppUtils;
import com.carrie.lib.moneybook.utils.LogUtil;
import com.carrie.lib.moneybook.viewmodel.ClassifyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/29.
 * ADD / EDIT / DELETE Classify Dialog
 *
 * Edit Mode:
 * ① 子 -> 父， 子id 不变，parentId 为 Max Parent Id +1.
 * ② 父 -> 子， 底下所有子类都会迁入到新父类.
 * ③ 删除一个父类， 底下所有子类都会被删除.
 *
 * todo 当parents 为0 时，点击 + 只能添加父类。
 *
 */

public class ClassifyEditFragment extends DialogFragment implements OnClickCallback{
    private static final String TAG = "ClassifyEditFragment";
    private ClassifyViewModel viewModel;
    private FragmentClassifyEditBinding binding;
    private ClassifyEntity parentSelectEntity;
    private List<ClassifyEntity> parents;
    private SimpleListFragment parentDialogFragment;
    private int parentId,id;
    private ClassifyEntity mEditClassifyEntity;
    /** 在Edit Mode 中，检测 父子类型 是否发生了变化（父 -> 子,子 -> 父 ）  */
    private boolean isTypeChangedInEdit;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classify_edit, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(ClassifyViewModel.class);
        binding.setViewModel(viewModel);
        binding.setCallback(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LogUtil.i(TAG, "isAddMode=" + viewModel.getMode());

        init();

        onSelectParent();
        onOk();
        onCancel();

        Drawable drawable = AppUtils.getTintDrawable(getResources().getDrawable(R.drawable.ic_switch_right), ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        viewModel.tintDrawable.set(drawable);
    }

    private void init() {
        if (viewModel.isAddMode.get()) {
            viewModel.resetAllData();

            // 当parents 为0 时，点击 + 只能添加父类。
            getParents();
            if(parents.size()==0){
                viewModel.isSwitchLeft.set(false);
                binding.ivSwitch.setClickable(false);
            }

        }else{
            Bundle args = getArguments();
            id=     args.getInt("id");
            parentId=     args.getInt("parentId");
            mEditClassifyEntity=     args.getParcelable("entity");

            viewModel.mClassifyName.set(mEditClassifyEntity.classify);
            viewModel.isSwitchLeft.set(!mEditClassifyEntity.isParent);
            if(mEditClassifyEntity.isParent){
                viewModel.mBudget.set(String.valueOf(mEditClassifyEntity.budget));
                viewModel.mParentName.set(""); // initial
            }else{
                viewModel.mParentName.set(viewModel.getParentName(mEditClassifyEntity.parentId));
                viewModel.mBudget.set("0.00"); // initial
            }

            isTypeChangedInEdit = mEditClassifyEntity.isParent;

        }
    }

    private void onSelectParent() {
        binding.selectParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showParents();
            }
        });
    }

    private void getParents(){
        if(parents==null){
            LogUtil.i(TAG,">>>>>> getParents()");
            parents = viewModel.getParentClassifies2();
        }
    }

    private void showParents(){
        getParents();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("ParentDialog");
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.addToBackStack(null);

        Bundle args = new Bundle();
        args.putString("title", getString(R.string.select_parent));
        args.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) parents);

        parentDialogFragment = new SimpleListFragment();
        parentDialogFragment.setArguments(args);
        parentDialogFragment.show(transaction, "ParentDialog");
        parentDialogFragment.setOnClickCallback(this);
    }

    private void onOk() {
        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(viewModel.mClassifyName.get())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_name_not_null), Toast.LENGTH_SHORT).show();
                    binding.etName.requestFocus();
                    return;
                }

                if (viewModel.isSwitchLeft.get() && TextUtils.isEmpty(binding.selectParent.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_parent_not_null), Toast.LENGTH_SHORT).show();
                    binding.selectParent.callOnClick();
                    return;
                }

                if (viewModel.isAddMode.get()&&viewModel.isClassifyExisted(viewModel.mClassifyName.get().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_name_must_unique), Toast.LENGTH_LONG).show();
                    binding.etName.requestFocus();
                    return;
                }

                if(viewModel.isAddMode.get()){
                    addItem();
                }else{
                    updateItem();
                }

                dismiss();
            }
        });
    }

    private void addItem(){
        ClassifyEntity entity = new ClassifyEntity();
        entity.setClassify(viewModel.mClassifyName.get().trim());
        entity.isParent = !viewModel.isSwitchLeft.get();
        if (entity.isParent) {
            entity.budget = viewModel.getBudget();
            entity.parentId =getMaxParentId(); // 因为list按照parentId 减序，所以最上面一个就是parentId最大的
        }else{
            entity.parentId =parentSelectEntity.parentId;
        }
        LogUtil.i(TAG,"entity="+entity);
        viewModel.insertOrUpdateItem(entity);
    }

    private void updateItem(){
        mEditClassifyEntity.setClassify(viewModel.mClassifyName.get().trim());
        mEditClassifyEntity.isParent = !viewModel.isSwitchLeft.get();
        mEditClassifyEntity.budget = viewModel.getBudget();
        if(isTypeChangedInEdit==mEditClassifyEntity.isParent){
            LogUtil.i(TAG,"很好哦O(∩_∩)O~，类型没有改变。");
            mEditClassifyEntity.parentId = parentSelectEntity==null? mEditClassifyEntity.parentId:parentSelectEntity.parentId;
            viewModel.insertOrUpdateItem(mEditClassifyEntity);
        }else{
            LogUtil.i(TAG,"o(╥﹏╥)o，类型改变了。");
            if(mEditClassifyEntity.isParent){ // child -> parent
                mEditClassifyEntity.parentId = getMaxParentId();
                viewModel.insertOrUpdateItem(mEditClassifyEntity);
            }else{
                // parent -> child
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("父类 ——> 子类");
                builder.setMessage("将父类修改为子类后，原先底下所有子类及记录都会被迁移到新父类中。\n\n是否继续？");
                builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.insertOrUpdateItem(mEditClassifyEntity); // 先更新这个item所有除parentId外的数据。然后再统一迁移到新父类中去。
                        viewModel.migrateChildClassifies( mEditClassifyEntity.parentId,parentSelectEntity.parentId);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }

    }

    private int getMaxParentId(){
        getParents();
       return parents.size()==0?1:parents.get(0).parentId+1;
    }

    private void onCancel() {
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public <T> void onClick(T object, Integer flag) {
        if(flag==-1){ // DELETE
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(mEditClassifyEntity.isParent?getString(R.string.delete_parent_classify_title):getString(R.string.delete_child_classify_title));
                builder.setMessage(mEditClassifyEntity.isParent?getString(R.string.delete_parent_classify_msg):getString(R.string.delete_child_classify_msg));
                       builder .setPositiveButton(R.string.delete_parent_classify_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mEditClassifyEntity.isParent){
                                    viewModel.deleteItems(mEditClassifyEntity.parentId);
                                }else{
                                    viewModel.deleteItem(mEditClassifyEntity);
                                }
                                dismiss();
                            }
                        }).setNegativeButton(R.string.delete_parent_classify_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        }else{
            parentSelectEntity= (ClassifyEntity) object;
            LogUtil.i(TAG,"parentEntity="+parentSelectEntity.toString());
            binding.selectParent.setText(parentSelectEntity.getClassify());
            parentDialogFragment.dismiss();
        }
    }
}
