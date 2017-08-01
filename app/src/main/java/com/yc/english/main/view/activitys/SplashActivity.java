package com.yc.english.main.view.activitys;

import android.content.Intent;

import com.yc.english.R;
import com.yc.english.base.view.BaseActivity;
import com.yc.english.group.view.activitys.GroupListJoinActivity;
import com.yc.english.main.contract.LoginContract;
import com.yc.english.main.contract.SplashContract;
import com.yc.english.main.hepler.UserInfoHelper;
import com.yc.english.main.model.domain.UserInfo;
import com.yc.english.main.presenter.LoginPresenter;
import com.yc.english.main.presenter.SplashPresenter;

/**
 * Created by zhangkai on 2017/8/1.
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @Override
    public void init() {
        mPresenter = new SplashPresenter(this, this);
        UserInfo userInfo = UserInfoHelper.getUserInfo();
        if (userInfo != null) {
            mPresenter.connect(userInfo.getUid(), new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_splash;
    }
}
