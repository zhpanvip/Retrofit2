package com.cypoem.retrofit;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by zhpan on 2018/3/22.
 */

public class ProgressUtils {
    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        Activity context;
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);

        final CustomProgressDialog dialog = getDialog(activityWeakReference);

        if ((context = activityWeakReference.get()) != null
                && !context.isFinishing()) {
             dialog.show();
        }
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Activity context;
                        if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing()) {
                            dialog.dismiss();
                        }
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                       /* Activity context;
                        if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing()) {
                           // dialog.dismiss();
                        }*/
                    }
                });
            }
        };
    }

    private static CustomProgressDialog getDialog(final WeakReference<Activity> activityWeakReference){
        final CustomProgressDialog dialog= new CustomProgressDialog.Builder(activityWeakReference.get())
                .setTheme(R.style.ProgressDialogStyle)
                .build();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                Activity context;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    try {
                        if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing()) {
                            dialog.dismiss();
                        }
                    } catch (Throwable e) {
                    }
                    return true;
                }
                return false;
            }
        });

        return dialog;
    }

}
