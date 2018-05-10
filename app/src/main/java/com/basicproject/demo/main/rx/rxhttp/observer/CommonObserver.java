package com.basicproject.demo.main.rx.rxhttp.observer;


import android.app.Dialog;


import com.basicproject.demo.common.utils.ToastUtil;
import com.basicproject.demo.main.rx.rxhttp.RxGo;
import com.basicproject.demo.main.rx.rxhttp.base.BaseObserver;

import io.reactivex.disposables.Disposable;

/**
 *  通用的Observer
 *  可以根据自己需求自定义自己的类继承BaseObserver<T>即可
 */

public abstract class CommonObserver<T> extends BaseObserver<T> {


    private Dialog mProgressDialog;

    public CommonObserver() {
    }

    public CommonObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    @Override
    public void doOnSubscribe(Disposable d) {
        RxGo.addDisposable(d);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnError(String errorMsg) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        ToastUtil.showViewToast(errorMsg);
        onError(errorMsg);
    }

    @Override
    public void doOnCompleted() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
