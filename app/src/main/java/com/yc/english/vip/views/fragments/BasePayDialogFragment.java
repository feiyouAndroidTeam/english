package com.yc.english.vip.views.fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.jakewharton.rxbinding.view.RxView;
import com.kk.securityhttp.domain.ResultInfo;
import com.umeng.analytics.MobclickAgent;
import com.yc.english.R;
import com.yc.english.base.helper.TipsHelper;
import com.yc.english.base.view.SharePopupWindow;
import com.yc.english.main.hepler.UserInfoHelper;
import com.yc.english.main.model.domain.Constant;
import com.yc.english.main.model.domain.UserInfo;
import com.yc.english.news.model.domain.OrderGood;
import com.yc.english.news.model.domain.OrderParams;
import com.yc.english.pay.PayConfig;
import com.yc.english.pay.alipay.IAliPay1Impl;
import com.yc.english.pay.alipay.IPayCallback;
import com.yc.english.pay.alipay.IWXPay1Impl;
import com.yc.english.pay.alipay.OrderInfo;
import com.yc.english.setting.model.bean.Config;
import com.yc.english.setting.model.bean.GoodInfo;
import com.yc.english.setting.model.bean.GoodInfoWrapper;
import com.yc.english.vip.contract.VipBuyContract;
import com.yc.english.vip.model.bean.GoodsType;
import com.yc.english.vip.model.bean.VipGoodInfo;
import com.yc.english.vip.presenter.VipBuyPresenter;
import com.yc.english.vip.utils.VipDialogHelper;
import com.yc.english.vip.utils.VipInfoHelper;
import com.yc.english.vip.views.activity.ProtocolActivity;
import com.yc.english.weixin.model.domain.CourseInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BaseDialogFragment;
import yc.com.blankj.utilcode.util.AppUtils;
import yc.com.blankj.utilcode.util.LogUtils;
import yc.com.blankj.utilcode.util.ScreenUtils;
import yc.com.blankj.utilcode.util.TimeUtils;
import yc.com.blankj.utilcode.util.ToastUtils;

/**
 * Created by wanglin  on 2017/11/27 15:16.
 */

public class BasePayDialogFragment extends BaseDialogFragment<VipBuyPresenter> implements VipBuyContract.View, BaseVipPayFragment.onVipClickListener, SharePopupWindow.OnShareItemClickListener {
    @BindView(R.id.m_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.tv_right_introduce)
    TextView tvRightIntroduce;

    @BindView(R.id.btn_share)
    Button btnShare;

    @BindView(R.id.btn_follow)
    Button btnFollow;

    private List<String> mTitles = new ArrayList<>();
    private int goodsType = GoodsType.TYPE_SVIP;

    private BaseVipPayFragment currentFragment;

    private CourseInfo courseInfo;

    private GoodInfo mGoodInfo;
    private String mPayWayName;

    private IAliPay1Impl iAliPay;
    private IWXPay1Impl iwxPay;

    private int currentPosition = 0;

    SharePopupWindow sharePopupWindow;

    private int timeNum = 0;

    private Timer timer = null;
    private boolean isDiandu;
    private boolean isWeike;


//    private boolean isOther;

    @Override
    public void init() {
        mPresenter = new VipBuyPresenter(getActivity(), this);
        iAliPay = new IAliPay1Impl(getActivity());
        iwxPay = new IWXPay1Impl(getActivity());

//        mTitles.add(getString(R.string.tutorship));
        mTitles.add(getString(R.string.member));
        Bundle bundle = getArguments();
        if (bundle != null) {
            goodsType = bundle.getInt(GoodsType.GOODS_KEY);
            if (goodsType == GoodsType.TYPE_SINGLE_INDIVIDUALITY_PLAN || goodsType == GoodsType.TYPE_GENERAL_VIP) {
                //隐藏mTabLayout
                mTabLayout.setVisibility(View.GONE);
                window.setLayout(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight() * 3 / 5);

            } else if (goodsType == GoodsType.TYPE_SINGLE_WEIKE) {
                //显示三项
                courseInfo = bundle.getParcelable("courseInfo");
                mTitles.add(0, getString(R.string.synchronization_weike));
//                mTitles.add(1, getString(R.string.member));
                isWeike = true;
            } else if (goodsType == GoodsType.TYPE_SINGLE_DIANDU) {
                mTitles.add(0, getString(R.string.textbook_read));
//                mTitles.add(1, getString(R.string.member));
                isDiandu = true;
            }
        }
//        else {
//            mTitles.add(0, getString(R.string.member));
//            isOther = true;
//        }
//        setPayTitle(0);

        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mTitles));
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
//        final View customView = LayoutInflater.from(getActivity()).inflate(R.layout.vip_tab_item, null);
//        final TextView textView = (TextView) customView.findViewById(R.id.tab_vip);

//        if (isDiandu || isWeike) {
//            mTabLayout.getTabAt(1).setCustomView(customView);
//            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));
//        } else if (isOther) {
//            mTabLayout.getTabAt(0).setCustomView(customView);
//            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));
//        }
//        else {
//            mTabLayout.getTabAt(0).setCustomView(customView);
//        }
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (isDiandu || isWeike) {
//                    if (tab.getPosition() == 2) {
//                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));
//                    }
//                } else if (isOther) {
//                    if (tab.getPosition() == 1) {
//                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));
//                    }
//                } else {
//                    if (tab.getPosition() == 0) {
//                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));
//                    }
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
////                if (((isDiandu || isWeike) && tab.getPosition() == 2) || (isOther && tab.getPosition() == 1) || tab.getPosition() == 0) {
//                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));
////                }
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        UserInfo userInfo = UserInfoHelper.getUserInfo();
        if (userInfo != null) {
            if (userInfo.getIsVip() == 1 && userInfo.getVip_end_time() > userInfo.getTest_end_time()) {
                btnShare.setVisibility(View.GONE);
            } else {
                btnShare.setVisibility(View.VISIBLE);
            }

        } else {
            btnShare.setVisibility(View.VISIBLE);
        }

        restoreGoodInfoAndPayWay(0);
        initListener();

    }

    private void initListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                restoreGoodInfoAndPayWay(position);
                switch (position) {
                    case 0:
                        if (isDiandu || isWeike) {
                            currentFragment = singleFragment;
                        } else {
                            currentFragment = generalFragment;
                        }
//                        else {
//                            currentFragment = vipPayFragment;
//                        }
                        break;
                    case 1:
//                        if (isOther) {
//                            currentFragment = vipPayFragment;
//                        } else {
                        currentFragment = generalFragment;
//                        }
                        break;
                    case 2:
//                        if (isDiandu || isWeike) {
                        currentFragment = vipPayFragment;
//                        } else {
//                            currentFragment = singleFragment;
//                        }
                        break;
                }
                currentPosition = position;
                currentFragment.setOnVipClickListener(BasePayDialogFragment.this);
//                setPayTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        RxView.clicks(btnPay).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                OrderParams orderParams = new OrderParams();
                orderParams.setTitle(mGoodInfo.getName());
                orderParams.setMoney(mGoodInfo.getPay_price());
                orderParams.setPayWayName(mPayWayName);
                List<OrderGood> list = new ArrayList<>();
                OrderGood orderGood = new OrderGood();
                orderGood.setGood_id(mGoodInfo.getId());
                orderGood.setNum(1);

                list.add(orderGood);
                orderParams.setGoodsList(list);
                mPresenter.createOrder(orderParams);

                umenStatistics(currentPosition);


            }
        });

        RxView.clicks(tvRightIntroduce).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(getActivity(), ProtocolActivity.class);
                startActivity(intent);
            }
        });

        RxView.clicks(btnShare).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                sharePopupWindow = new SharePopupWindow(getActivity());
                sharePopupWindow.setBackColor(R.drawable.share_vip_bg);
                sharePopupWindow.setOnShareItemClickListener(BasePayDialogFragment.this);
                sharePopupWindow.setFromPay(true);
                sharePopupWindow.show(rootView);
            }
        });
        //关注
        RxView.clicks(btnFollow).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                if (!AppUtils.isInstallApp("com.tencent.mm")) {
                    ToastUtils.showLong("请安装微信");
                    return;
                }

                openWeiXin();
            }
        });
    }

    public void openWeiXin() {

        MobclickAgent.onEvent(getActivity(), "weixin_click", AppUtils.getAppVersionName());
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText(null, "ssyingyu"));
        ToastUtils.showLong("复制成功，可以关注公众号了");

        try {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui" +
                    ".LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            getActivity().startActivityForResult(intent, 1);

            timeStart();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    public void timeStart() {
        if (timer == null) {
            timer = new Timer();
        }
        timeNum = 0;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeNum++;
                if (timeNum > 12) {
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        computeTime();
    }

    public void computeTime() {
        if (timeNum > 12) {
            UserInfo userInfo = UserInfoHelper.getUserInfo();
            if (userInfo != null) {
                mPresenter.getShareVipAllow(userInfo.getUid());
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_pay_popupwindow;
    }

    private void restoreGoodInfoAndPayWay(int position) {
        mPayWayName = PayConfig.wx_pay;
        GoodInfoWrapper goodInfoWrapper = VipInfoHelper.getGoodInfoWrapper();
        if (goodInfoWrapper != null) {
            if (position == 0) {
                if (isDiandu) {
                    if (goodInfoWrapper.getDiandu() != null && goodInfoWrapper.getDiandu().size() > 0) {
                        List<GoodInfo> dianduList = VipInfoHelper.getGoodInfoWrapper().getDiandu();
                        Collections.sort(dianduList, new Comparator<GoodInfo>() {
                            @Override
                            public int compare(GoodInfo o1, GoodInfo o2) {
                                return Integer.parseInt(o1.getUse_time_limit()) - Integer.parseInt(o2.getUse_time_limit());
                            }
                        });
                        mGoodInfo = dianduList.get(0);
                    }
                } else if (isWeike) {
                    if (goodInfoWrapper.getWvip() != null && goodInfoWrapper.getWvip().size() > 0) {
                        mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getWvip().get(0);
                    }
                } else {
                    if (goodInfoWrapper.getVip() != null && goodInfoWrapper.getVip().size() > 0)
                        mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getVip().get(0);
                }
//            else {
//                if (goodInfoWrapper.getSvip() != null && goodInfoWrapper.getSvip().size() > 0)
//                    mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getSvip().get(0);
//            }
            } else if (position == 1) {
//            if (isOther) {
//            if (goodInfoWrapper.getSvip() != null && goodInfoWrapper.getSvip().size() > 0)
//                mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getSvip().get(0);
//            } else {
                if (goodInfoWrapper.getVip() != null && goodInfoWrapper.getVip().size() > 0)
                    mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getVip().get(0);
//            }
            } else if (position == 2) {
                if (isDiandu || isWeike) {
                    if (goodInfoWrapper.getSvip() != null && goodInfoWrapper.getSvip().size() > 0)
                        mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getSvip().get(0);
                }
//            else if (goodsType == GoodsType.TYPE_SINGLE_WEIKE) {
//                if (goodInfoWrapper.getWvip() != null && goodInfoWrapper.getWvip().size() > 0) {
//                    mGoodInfo = VipInfoHelper.getGoodInfoWrapper().getWvip().get(0);
//                }
//            }
            }
        }

    }

    @Override
    public void showOrderInfo(ResultInfo<OrderInfo> orderInfo, String money, String name) {

        if (orderInfo != null) {
            if (orderInfo.code == 2) {
                TipsHelper.tips(getActivity(), orderInfo.message);
                updateSuccessData();
                return;
            }
            if (orderInfo.data != null) {
                orderInfo.data.setMoney(Float.parseFloat(money));
                orderInfo.data.setName(name);
                if (mPayWayName.equals(PayConfig.ali_pay)) {
                    aliPay(orderInfo.data);
                } else {
                    wxPay(orderInfo.data);
                }
            }
        }
    }


    @Override
    public void showLoadingDialog(String msg) {
        ((BaseActivity) getActivity()).showLoadingDialog(msg);
    }

    @Override
    public void dismissDialog() {
        ((BaseActivity) getActivity()).dismissDialog();
    }


    @Override
    public void onVipClick(GoodInfo goodInfo, String payWayName, int type) {
        mPayWayName = payWayName;

        mGoodInfo = goodInfo;

        LogUtils.e(mGoodInfo.getPay_price() + "---" + payWayName);
    }

    private BaseVipPayFragment vipPayFragment;
    private BaseVipPayFragment generalFragment;
    private BaseVipPayFragment singleFragment;


    private class MyPagerAdapter extends FragmentPagerAdapter {

        private List<String> mTitles;

        private MyPagerAdapter(FragmentManager fm, List<String> titles) {
            super(fm);
            this.mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {


            if (position == 0) {
                if (isDiandu || isWeike) {
                    if (singleFragment == null) {
                        singleFragment = new BaseVipPayFragment();
                        singleFragment.setType(goodsType);
                        singleFragment.setCourseInfo(courseInfo);
                    }
                    singleFragment.setOnVipClickListener(BasePayDialogFragment.this);
                    return singleFragment;
                }
//                if (isOther) {
                if (generalFragment == null) {
                    generalFragment = new BaseVipPayFragment();
                    generalFragment.setType(GoodsType.TYPE_GENERAL_VIP);
                }
                generalFragment.setOnVipClickListener(BasePayDialogFragment.this);
                return generalFragment;
//                }


            } else if (position == 1) {
                if (isDiandu || isWeike) {
                    if (generalFragment == null) {
                        generalFragment = new BaseVipPayFragment();
                        generalFragment.setType(GoodsType.TYPE_GENERAL_VIP);
                    }
                    return generalFragment;
                }


            } else if (position == 2) {
                if (isDiandu || isWeike) {
                    if (vipPayFragment == null) {
                        vipPayFragment = new BaseVipPayFragment();
                        vipPayFragment.setType(GoodsType.TYPE_SVIP);
                    }
                    return vipPayFragment;
                }

//                if (singleFragment == null) {
//                    singleFragment = new BaseVipPayFragment();
//                    singleFragment.setType(goodsType);
//                    singleFragment.setCourseInfo(courseInfo);
//                }
//                return singleFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

    // 微信支付
    private void wxPay(OrderInfo orderInfo) {
        iwxPay.pay(orderInfo, new IPayCallback() {
            @Override
            public void onSuccess(OrderInfo orderInfo) {
                updateSuccessData();
            }

            @Override
            public void onFailure(OrderInfo orderInfo) {
//                ToastUtils.showLong("支付失败");
            }
        });
    }

    //支付宝支付
    private void aliPay(OrderInfo orderInfo) {
        iAliPay.pay(orderInfo, new IPayCallback() {
            @Override
            public void onSuccess(OrderInfo orderInfo) {
                updateSuccessData();
            }

            @Override
            public void onFailure(OrderInfo orderInfo) {
            }
        });
    }

    private void updateSuccessData() {
        boolean isVip = false;
        UserInfo userInfo = UserInfoHelper.getUserInfo();
        if (mGoodInfo.getType_id().equals("4")) {
            userInfo.setIsVip(2);
            isVip = true;
        } else if (mGoodInfo.getType_id().equals("1")) {
            userInfo.setIsVip(1);
            isVip = true;
        } else if (mGoodInfo.getType_id().equals("3")) {
            userInfo.setIsVip(4);
            isVip = true;
        } else if (mGoodInfo.getType_id().equals("5")) {
            userInfo.setIsVip(3);
            isVip = true;
        } else if (mGoodInfo.getType_id().equals("2")) {//单次微课支付
            isVip = false;

        }

        Date date = new Date();
        userInfo.setVip_start_time(date.getTime() / 1000);
        if (mGoodInfo.getUse_time_limit() != null) {
            int use_time_Limit = Integer.parseInt(mGoodInfo.getUse_time_limit());
            long vip_end_time = date.getTime() + use_time_Limit * 30 * (Config.MS_IN_A_DAY);
            userInfo.setVip_end_time(vip_end_time / 1000);
        }
        UserInfoHelper.saveUserInfo(userInfo);
        if (isVip) {
            RxBus.get().post(Constant.COMMUNITY_ACTIVITY_REFRESH, "form pay");
        } else {
            RxBus.get().post(Constant.PAY_SIGNAL_SUCCESS, "signal success");
        }
        VipDialogHelper.dismissVipDialog();
    }

    private void umenStatistics(int position) {
        switch (position) {
            case 0:
                if (isWeike || isDiandu) {
                    MobclickAgent.onEvent(getActivity(), "single_buy", "单次购买");
                } else {
                    MobclickAgent.onEvent(getActivity(), "general_vip", "普通会员");
                }
//                else {
//                    MobclickAgent.onEvent(getActivity(), "score_vip", "提分辅导");
//                }
                break;
            case 1:
//                if (isOther) {
//                    MobclickAgent.onEvent(getActivity(), "score_vip", "提分辅导");
//                } else {
                MobclickAgent.onEvent(getActivity(), "general_vip", "普通会员");
//                }
                break;
            case 2:
                MobclickAgent.onEvent(getActivity(), "score_vip", "提分辅导");
                break;
        }
    }

    private void setPayTitle(int position) {
        switch (position) {
            case 0:
                if (isDiandu || isWeike) {
                    btnPay.setText("立即开通");
                    tvRightIntroduce.setVisibility(View.GONE);
                } else {
                    btnPay.setText("立即开通(仅限人教版用户)");
                    tvRightIntroduce.setText(getString(R.string.study_introduce));
                    tvRightIntroduce.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
//                if (isOther) {
                btnPay.setText("立即开通(仅限人教版用户)");
                tvRightIntroduce.setText(getString(R.string.study_introduce));
                tvRightIntroduce.setVisibility(View.VISIBLE);
//                } else {
//                    btnPay.setText("立即开通");
//                    tvRightIntroduce.setVisibility(View.GONE);
//                }
                break;
            case 2:
//                if (isDiandu) {
                btnPay.setText("立即开通(仅限人教版用户)");
                tvRightIntroduce.setText(getString(R.string.study_introduce));
                tvRightIntroduce.setVisibility(View.VISIBLE);
//                } else {
//                    btnPay.setText("立即开通");
//                    tvRightIntroduce.setVisibility(View.GONE);
//                }
                break;
        }

    }



    @Override
    public void showNoNet() {
        if (sharePopupWindow != null && sharePopupWindow.isShowing()) {
            sharePopupWindow.dismiss();
        }
        VipDialogHelper.dismissVipDialog();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onShareSuccess() {
        ToastUtils.showLong("分享成功");
        UserInfo userInfo = UserInfoHelper.getUserInfo();
        if (userInfo != null) {
            mPresenter.getShareVipAllow(userInfo.getUid());
        }
    }

    @Override
    public void shareAllow(Integer data) {

        if (sharePopupWindow != null && sharePopupWindow.isShowing()) {
            sharePopupWindow.dismiss();
        }
        VipDialogHelper.dismissVipDialog();

        if (data > 0) {
            LogUtils.i("shareAllow --->");
            UserInfo userInfo = UserInfoHelper.getUserInfo();
            userInfo.setIsVip(1);

            userInfo.setVip_start_time(TimeUtils.getNowMills() / 1000);
//MS_IN_A_DAY
            long vip_end_time = TimeUtils.getNowMills() + data * (Config.MS_IN_A_DAY);
            long test_end_time = TimeUtils.getNowMills() + data * (Config.MS_IN_A_DAY);
            userInfo.setVip_end_time(vip_end_time / 1000);
            userInfo.setTest_end_time(test_end_time / 1000);

            UserInfoHelper.saveUserInfo(userInfo);
        }

        RxBus.get().post(Constant.COMMUNITY_ACTIVITY_REFRESH, "form pay");
    }

    @Override
    public void showVipGoodInfos(List<VipGoodInfo> vipGoodInfos) {

    }

    @Override
    public void showGoodInfoList(List<GoodInfo> data) {

    }
}
