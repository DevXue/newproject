package com.basicproject.demo.demo;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.basicproject.demo.R;
import com.basicproject.demo.common.BaseActivity;
import com.basicproject.demo.common.utils.L;
import com.basicproject.demo.main.API;
import com.basicproject.demo.main.rx.rxhttp.RxGo;
import com.basicproject.demo.main.rx.rxhttp.bean.Rtn;
import com.basicproject.demo.main.rx.rxhttp.interceptor.Transformer;
import com.basicproject.demo.main.rx.rxhttp.observer.CommonObserver;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends BaseActivity {

    @Bind(R.id.go)
    Button go;


    @Override
    protected int setLayout() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.go,R.id.post})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.go:
                RxGo.go(API.class).getData(1,20).compose(Transformer.<Rtn>switchSchedulers()).subscribe(new CommonObserver<Rtn>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Log.e("onError","出错"+errorMsg);
                    }

                    @Override
                    protected void onSuccess(Rtn baseData) {
                        L.e("onSuccess",baseData.getData().toString());
                    }

                });
                break;
            case R.id.post:
                RxGo.go(API.class).postRequst("张三丰","学太极").compose(Transformer.<Rtn>switchSchedulers()).subscribe(new CommonObserver<Rtn>() {
                    @Override
                    protected void onError(String errorMsg) {
                        L.e("出错了",errorMsg);
                    }

                    @Override
                    protected void onSuccess(Rtn rtn) {
                        L.e("成功",rtn.getData());
                    }
                });
                break;
        }

    }
}
