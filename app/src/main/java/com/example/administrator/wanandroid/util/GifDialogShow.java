package com.example.administrator.wanandroid.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.wanandroid.R;


public class GifDialogShow {

    private static Dialog mDialog;
    private static View dialog;
    private static ImageView mImageView;

    public static void showRoundProcessDialog(Context mContext) {
        if (mContext == null) {
            return;
        }
        OnKeyListener keyListener = new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mDialog.dismiss();
                }
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return false;
            }
        };
        mDialog = new Dialog(mContext, R.style.gif_dialog);
        dialog = LayoutInflater.from(mContext).inflate(R.layout.loading_process_gif_dialog_icon,
                null);
        mImageView = dialog.findViewById(R.id.loading_process_dialog_img);
        Glide.with(mContext).load(R.mipmap.dy).into(mImageView);
        /*.apply(new RequestOptions().error(R.mipmap.dy).placeholder(R.mipmap.dy))*/
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(keyListener);
        mDialog.setCancelable(false);
        mDialog.setContentView(dialog);
        mDialog.show();
    }

    public static void closeDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public static boolean isShow() {
        return mDialog.isShowing();
    }
}
