package com.yc.english.setting.view.activitys;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.yc.english.R;
import com.yc.english.base.utils.StatusBarCompat;
import com.yc.english.base.view.BaseActivity;
import com.yc.english.main.hepler.UserInfoHelper;
import com.yc.english.main.model.domain.UserInfo;
import com.yc.english.vip.utils.VipDialogHelper;
import com.yc.english.vip.views.fragments.BasePayItemView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by wanglin  on 2017/11/8 11:19.
 */

public class VipEquitiesActivity extends BaseActivity {


    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_vip_end_time)
    TextView mTvEndTime;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.btn_open_vip)
    Button mBtnOpenVip;
    @BindView(R.id.ll_vip_container)
    LinearLayout llVipContainer;
    @BindView(R.id.tv_rights_title)
    TextView mTvRightsTitle;
    @BindView(R.id.basePayItemView_vip)
    BasePayItemView basePayItemViewVip;
    @BindView(R.id.basePayItemView_ceping)
    BasePayItemView basePayItemViewCeping;
    @BindView(R.id.vip_icon)
    ImageView mVipIcon;
    @BindView(R.id.baseItemView_weike)
    BasePayItemView baseItemViewWeike;
    private UserInfo userInfo;

    private boolean isVip = false;


    @Override
    public void init() {
        toolbar.setNavigationIcon(R.mipmap.vip_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatusBarCompat.compat(this, appbarLayout, toolbar);

        userInfo = UserInfoHelper.getUserInfo();
        mTvNickname.setText(userInfo.getNickname());

        String endTime = TimeUtils.date2String(new Date(userInfo.getVip_end_time() * 1000), new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));

        mTvEndTime.setText(String.format(getString(R.string.vip_end_time), endTime));
        if (userInfo.getIsVip() == 1) {
            isVip = true;
        }
        initView();
        initListener();

    }

    private void initListener() {
        RxView.clicks(mBtnOpenVip).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                VipDialogHelper.showVipDialog(getSupportFragmentManager(), "", null);
            }
        });

    }

    private void initView() {

        if (userInfo.getIsVip() == 0) {//非会员
            mBtnOpenVip.setVisibility(View.VISIBLE);
            llVipContainer.setVisibility(View.GONE);
            basePayItemViewVip.setVisibility(View.VISIBLE);
            basePayItemViewCeping.setVisibility(View.VISIBLE);
            mTvRightsTitle.setText(getString(R.string.exclusive_right));
        } else {
            mBtnOpenVip.setVisibility(View.GONE);
            llVipContainer.setVisibility(View.VISIBLE);
            if (userInfo.getIsVip() == 1) {
                mTvRightsTitle.setText(getString(R.string.general_vip_right));
                basePayItemViewVip.setVisibility(View.GONE);
                basePayItemViewCeping.setVisibility(View.GONE);
                mVipIcon.setImageResource(R.mipmap.vip_vip);
            } else {
                mTvRightsTitle.setText(getString(R.string.tutorship_vip_right));
                mVipIcon.setImageResource(R.mipmap.vip_tifen);
                baseItemViewWeike.setContentAndIcon("同步微课", 0);
            }
        }


        if (isVip) {


        } else {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip_equity;
    }


}
