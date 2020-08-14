package com.tk.lib_floatmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

/**
 * Created by wengyiming on 2017/9/13.
 */

public class FloatMenu extends BaseFloatDialog {
    private CheckBox leftBackText;
    private CheckBox rightBackText;
    private String defText;

    public interface IOnItemClicked {
        void onBackItemClick(boolean isChecked);//返回键按下

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
        leftBackText.setText(defText);
        leftBackText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mHintLocation == BaseFloatDialog.LEFT) {
                    itemClickedListener.onBackItemClick(isChecked);
                    rightBackText.performClick();
                }
                ImageView icon = view.findViewById(R.id.icon);
                if (isChecked) {
                    if (mHintLocation == BaseFloatDialog.LEFT) {
                        changeLogo(R.drawable.widget_float_button_lock);
                    }
                    icon.setImageResource(R.drawable.widget_float_button_lock);
                } else {
                    if (mHintLocation == BaseFloatDialog.LEFT) {
                        changeLogo(R.drawable.widget_float_button_unlock);
                    }
                    icon.setImageResource(R.drawable.widget_float_button_unlock);
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
        rightBackText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mHintLocation == BaseFloatDialog.RIGHT) {
                    itemClickedListener.onBackItemClick(isChecked);
                    leftBackText.performClick();
                }
                ImageView icon = view.findViewById(R.id.icon);
                if (isChecked) {
                    if (mHintLocation == BaseFloatDialog.RIGHT) {
                        changeLogo(R.drawable.widget_float_button_lock);
                    }
                    icon.setImageResource(R.drawable.widget_float_button_lock);
                } else {
                    if (mHintLocation == BaseFloatDialog.RIGHT) {
                        changeLogo(R.drawable.widget_float_button_unlock);
                    }
                    icon.setImageResource(R.drawable.widget_float_button_unlock);
                }
            }
        });
        return view;
    }

    public void setChecked(boolean isChecked) {
        if (mHintLocation == BaseFloatDialog.LEFT) {
            leftBackText.setChecked(isChecked);
        } else {
            rightBackText.setChecked(isChecked);
        }
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
        if (isApplictionDialog()) {
            if (getContext() instanceof Activity) {
                dismiss();
            }
        }
    }

    public void show(String info) {
        super.show();
        if (leftBackText != null)
            leftBackText.setText(Html.fromHtml(info));
        if (rightBackText != null)
            rightBackText.setText(Html.fromHtml(info));
    }

    public void setText(String info) {
        if (leftBackText != null)
            leftBackText.setText(Html.fromHtml(info));
        if (rightBackText != null)
            rightBackText.setText(Html.fromHtml(info));
    }
}
