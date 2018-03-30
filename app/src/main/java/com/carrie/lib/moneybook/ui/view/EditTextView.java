package com.carrie.lib.moneybook.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.carrie.lib.moneybook.R;
import com.carrie.lib.moneybook.utils.AppUtils;
import com.carrie.lib.moneybook.utils.LogUtil;

/**
 * Created by Carrie on 2018/3/30.
 */

public class EditTextView extends android.support.v7.widget.AppCompatEditText {
    private int mTextHintColor;
    private Drawable mRightDrawable;
    private Drawable mLeftDrawable;

    private Resources resources;
    private int mTintColor;

    public EditTextView(Context context) {
        super(context);
    }

    public EditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        resources = context.getResources();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextView);
        mTextHintColor = typedArray.getColor(R.styleable.EditTextView_textHintColor, resources.getColor(R.color.colorPrimaryLight));
        mTintColor = typedArray.getColor(R.styleable.EditTextView_tintColor, resources.getColor(R.color.colorPrimary));
        int textColor = typedArray.getColor(R.styleable.EditTextView_textColor, resources.getColor(R.color.textColorPrimary));
        int rightIconResId = typedArray.getResourceId(R.styleable.EditTextView_drawableRight, R.drawable.edit_close);
        int leftIconResId = typedArray.getResourceId(R.styleable.EditTextView_drawableLeft, 0);
        int textHintSize = typedArray.getInteger(R.styleable.EditTextView_textHintSize, 14);
        String textHint = typedArray.getString(R.styleable.EditTextView_hint);

        typedArray.recycle();

        setTextColor(textColor);

        // set compound drawable
        if (leftIconResId != 0) {
            mLeftDrawable = resources.getDrawable(leftIconResId);
            setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, null, null);
        }
        mRightDrawable = resources.getDrawable(rightIconResId);
        setCompoundDrawablePadding(32);

        // set hint
        if (!TextUtils.isEmpty(textHint)) {
            SpannableString ss = new SpannableString(textHint);
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textHintSize, true);
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setHint(new SpannedString(ss));
        }
        setHintTextColor(mTextHintColor);




        setTint();
    }

    private void setTint() {
        mRightDrawable = AppUtils.getTintDrawable(mRightDrawable, ColorStateList.valueOf(mTintColor));
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        // focus & 有文字 ，显示 clear 图标
        if (hasFocus() && text.length() > 0) {
            setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, mRightDrawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, null, null);
        }
    }

    /**
     * ic_clear clicked event
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mRightDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            if ((getWidth() - mRightDrawable.getIntrinsicWidth() - getPaddingRight() * 2) < event.getX() && event.getX() < getWidth()) {  // getPaddingRight() * 2 是为了icon加个padding left，增加icon的点击范围
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
