package com.yc.english.group.presenter;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.hwangjr.rxbus.RxBus;
import com.kk.securityhttp.domain.ResultInfo;
import com.yc.english.base.presenter.BasePresenter;
import com.yc.english.group.constant.BusAction;
import com.yc.english.group.contract.GroupApplyVerifyContract;
import com.yc.english.group.model.bean.ClassInfo;
import com.yc.english.group.model.bean.StudentInfoWrapper;
import com.yc.english.group.model.engin.GroupApplyVerifyEngine;
import com.yc.english.group.rong.ImUtils;
import com.yc.english.group.rong.models.CodeSuccessResult;
import com.yc.english.group.rong.models.GroupUser;
import com.yc.english.group.rong.models.GroupUserQueryResult;
import com.yc.english.group.utils.EngineUtils;
import com.yc.english.main.hepler.UserInfoHelper;
import com.yc.english.main.model.domain.UserInfo;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by wanglin  on 2017/8/2 18:06.
 */

public class GroupApplyVerifyPresenter extends BasePresenter<GroupApplyVerifyEngine, GroupApplyVerifyContract.View> implements GroupApplyVerifyContract.Presenter {
    public GroupApplyVerifyPresenter(Context context, GroupApplyVerifyContract.View view) {
        super(view);
        mEngin = new GroupApplyVerifyEngine(context);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {

    }

    /**
     * 获取申请加群列表
     *
     * @param class_id
     * @param status
     */
    @Override
    public void getMemberList(Context context, String class_id, String status, String master_id) {
        Subscription subscription = EngineUtils.getMemberList(context, class_id, status, master_id).subscribe(new Subscriber<ResultInfo<StudentInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final ResultInfo<StudentInfoWrapper> stringResultInfo) {
                handleResultInfo(stringResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        if (stringResultInfo.data.getList() != null && stringResultInfo.data.getList().size() > 0)
                            mView.showVerifyList(stringResultInfo.data.getList());
                    }
                });

            }
        });
        mSubscriptions.add(subscription);
    }

    /**
     * 接受加群
     *
     * @param class_id
     * @param master_id
     * @param user_ids
     */
    @Override
    public void acceptApply(String class_id, String master_id, String[] user_ids) {
        Subscription subscription = mEngin.acceptApply(class_id, master_id, user_ids).subscribe(new Subscriber<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final ResultInfo<String> stringResultInfo) {
                handleResultInfo(stringResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        mView.showApplyResult(stringResultInfo.data);
                    }
                });
            }
        });
        mSubscriptions.add(subscription);
    }

    public void joinGroup(String usre_id, final String groupId, final String groupName) {
        final String[] userIds = new String[]{ usre_id};
        ImUtils.queryGroup(groupId).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<GroupUserQueryResult>() {
            @Override
            public void call(GroupUserQueryResult groupUserQueryResult) {
                if (groupUserQueryResult.getCode() == 200) {
                    final List<GroupUser> users = groupUserQueryResult.getUsers();
                    ImUtils.joinGroup(userIds, groupId, groupName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CodeSuccessResult>() {
                        @Override
                        public void call(CodeSuccessResult codeSuccessResult) {
                            if (codeSuccessResult.getCode() == 200) {//加入成功
                                ToastUtils.showShort("加入成功");
//                                mView.startGroupChat(groupId, groupName);
//                                ClassInfo info = new ClassInfo("", groupName, users.size() + "", Integer.parseInt(groupId));
//                                classInfoDao.insert(info);
                                RxBus.get().post(BusAction.GROUPLIST, "from groupjoin");
                            }
                        }
                    });
                } else {
                    ToastUtils.showShort("没有该群组，请重新输入");
                }
            }
        });

    }
}
