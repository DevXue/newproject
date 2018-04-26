package com.basicproject.demo.main.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basicproject.demo.R;
import com.basicproject.demo.common.BaseFragment;
import com.basicproject.demo.common.utils.L;
import com.basicproject.demo.common.utils.ToastUtil;
import com.basicproject.demo.main.constants.Const;
import com.basicproject.demo.demo.DemoActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.go)
    TextView go;

    String content;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        content = getArguments().getString(Const.DATA);
        go.setText(content);
        return view;
    }

    @Override
    protected void initData() {
        L.e("执行"+content);
        ToastUtil.showViewToast(content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.go)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),DemoActivity.class));
    }
}
