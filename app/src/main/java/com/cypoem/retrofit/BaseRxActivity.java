package com.cypoem.retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseRxActivity extends AppCompatActivity implements BaseImpl {
    private CustomProgressDialog mProgressDialog;
    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
    private CompositeDisposable disposables2Destroy;// 管理Destroy取消订阅者者

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (disposables2Destroy != null) {
            throw new IllegalStateException("onCreate called multiple times");
        }
        disposables2Destroy = new CompositeDisposable();

    }


    /**
     * 显示ProgressDialog
     */
    public void showProgress(String msg) {
        mProgressDialog= new CustomProgressDialog.Builder(this)
                .setMessage(msg)
                .setTheme(R.style.ProgressDialogStyle)
                .build();
        mProgressDialog.show();
    }
    /**
     * 显示ProgressDialog
     */
    public void showProgress() {
        mProgressDialog= new CustomProgressDialog.Builder(this)
                .setTheme(R.style.ProgressDialogStyle)
                .build();
        mProgressDialog.show();
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public boolean addRxStop(Disposable disposable) {
        if (disposables2Stop == null) {
            throw new IllegalStateException(
                    "addUtilStop should be called between onStart and onStop");
        }
        disposables2Stop.add(disposable);
        return true;
    }

    public boolean addRxDestroy(Disposable disposable) {
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "addUtilDestroy should be called between onCreate and onDestroy");
        }
        disposables2Destroy.add(disposable);
        return true;
    }

    public void remove(Disposable disposable) {
        if (disposables2Stop == null && disposables2Destroy == null) {
            throw new IllegalStateException("remove should not be called after onDestroy");
        }
        if (disposables2Stop != null) {
            disposables2Stop.remove(disposable);
        }
        if (disposables2Destroy != null) {
            disposables2Destroy.remove(disposable);
        }
    }

    public void onStart() {
        super.onStart();
        if (disposables2Stop != null) {
            throw new IllegalStateException("onStart called multiple times");
        }
        disposables2Stop = new CompositeDisposable();
    }

    public void onStop() {
        super.onStop();
        if (disposables2Stop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not called");
        }
        disposables2Stop.dispose();
        disposables2Stop = null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "onDestroy called multiple times or onCreate not called");
        }
        disposables2Destroy.dispose();
        disposables2Destroy = null;
    }

}
