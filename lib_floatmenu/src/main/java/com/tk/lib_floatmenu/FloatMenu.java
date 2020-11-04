package com.tk.lib_floatmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * Created by wengyiming on 2017/9/13.
 */

public class FloatMenu extends BaseFloatDialog {
    private TextView leftBackText;
    private TextView rightBackText;

    private Object tag;

    public interface IOnItemClicked {
        void onItemClick(Object tag);

        void onClose();//对话框折叠

        void onExpand();//对话框展开
    }

    IOnItemClicked itemClickedListener;

    /**
     * 构造函数
     *
     * @param context  上下文
     * @param location 悬浮窗停靠位置，0为左边，1为右边
     * @param callBack 点击按钮的回调
     */
    public FloatMenu(Context context, int location, int defaultY, IOnItemClicked callBack) {
        super(context, location, defaultY);
        this.itemClickedListener = callBack;
    }

    public FloatMenu(Context context, int location, int defaultY, String defText, IOnItemClicked callBack) {
        super(context, location, defaultY);
        this.defText = defText;
        this.itemClickedListener = callBack;
    }


    @Override
    protected View getLeftView(LayoutInflater inflater, View.OnTouchListener touchListener) {
        final View view = inflater.inflate(R.layout.widget_float_window_left, null);
        leftBackText = view.findViewById(R.id.back_item);
        leftBackText.setText("" + defText);
        leftBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHintLocation == BaseFloatDialog.LEFT) {
                    itemClickedListener.onItemClick(tag);
                    rightBackText.performClick();
                }
            }
        });
        return view;
    }


    @Override
    protected View getRightView(LayoutInflater inflater, View.OnTouchListener touchListener) {
        final View view = inflater.inflate(R.layout.widget_float_window_right, null);
        rightBackText = view.findViewById(R.id.back_item);
        rightBackText.setText(defText);
        rightBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHintLocation == BaseFloatDialog.RIGHT) {
                    itemClickedListener.onItemClick(tag);
                    leftBackText.performClick();
                }
            }
        });
        return view;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }


    @Override
    protected View getLogoView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.widget_float_window_logo, null);
    }

    @Override
    protected void resetLogoViewSize(int hintLocation, View logoView) {
        logoView.clearAnimation();
        logoView.setTranslationX(0);
        logoView.setScaleX(1);
        logoView.setScaleY(1);
    }

    @Override
    protected void dragingLogoViewOffset(View logoView, boolean isDraging, boolean isResetPosition, float offset) {
        if (isDraging && offset > 0) {
            logoView.setBackgroundDrawable(null);
            logoView.setScaleX(1 + offset);
            logoView.setScaleY(1 + offset);
        } else {
            logoView.setBackgroundResource(R.drawable.widget_float_button_logo_bg);
            logoView.setTranslationX(0);
            logoView.setScaleX(1);
            logoView.setScaleY(1);
        }
        if (isResetPosition) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                logoView.setRotation(offset * 360);
            }
        } else {
            logoView.setRotation(offset * 360);
        }
    }

    @Override
    public void shrinkLeftLogoView(View smallView) {
        smallView.setTranslationX(-smallView.getWidth() / 3);
    }

    @Override
    public void shrinkRightLogoView(View smallView) {
        smallView.setTranslationX(smallView.getWidth() / 3);
    }

    @Override
    public void leftViewOpened(View leftView) {
        this.itemClickedListener.onExpand();
    }

    @Override
    public void rightViewOpened(View rightView) {
        this.itemClickedListener.onExpand();
    }

    @Override
    public void leftOrRightViewClosed(View smallView) {
        this.itemClickedListener.onClose();
    }

    @Override
    protected void onDestoryed() {
        if (isApplicationDialog()) {
            if (getContext() instanceof Activity) {
                dismiss();
            }
        }
    }

    public void show(String info) {
        super.show();
        setText(info);
    }

    public void setText(String info) {
        if (leftBackText != null)
            leftBackText.setText(Html.fromHtml(info));
        if (rightBackText != null)
            rightBackText.setText(Html.fromHtml(info));
    }

    public void setTextColor(@ColorRes int colorResId) {
        if (leftBackText != null)
            leftBackText.setTextColor(ContextCompat.getColor(leftBackText.getContext(), colorResId));
        if (rightBackText != null)
            rightBackText.setTextColor(ContextCompat.getColor(rightBackText.getContext(), colorResId));
    }
}
