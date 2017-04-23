package com.cypoem.retrofit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhpan on 2017/4/13.
 */

public class CustomProgressDialog extends Dialog {
    private static CustomProgressDialog mProgressDialog;

    private Context context;
    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    //创建dialog的样式
    public static CustomProgressDialog createDialog(Context context){

        mProgressDialog = new CustomProgressDialog(context, R.style.ProgressDialogStyle);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return mProgressDialog;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (mProgressDialog == null) {
            return;
        }
        //添加控件  执行帧动画
        ImageView imageView = (ImageView) mProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public CustomProgressDialog setMessage(String strMessage){
        TextView tvMessage = (TextView) mProgressDialog.findViewById(R.id.tv_loadingmsg);

        if (tvMessage != null){
            tvMessage.setText(strMessage);
        }
        return mProgressDialog;
    }
}
