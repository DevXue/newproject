package com.basicproject.demo.main.rx.rxhttp.base;


import com.basicproject.demo.main.rx.rxhttp.bean.Rtn;
import com.basicproject.demo.main.rx.rxhttp.exception.ApiException;
import com.basicproject.demo.main.rx.rxhttp.interfaces.IDataSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类BaseObserver使用BaseData
 */

public abstract class BaseDataObserver<T> implements Observer<Rtn<T>>, IDataSubscriber<T> {

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(Rtn<T> baseData) {
        doOnNext(baseData);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }


    private void setError(String errorMsg) {
        doOnError(errorMsg);
    }

}
