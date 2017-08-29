package com.yc.english.group.view.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comm_recyclviewadapter.BaseAdapter;
import com.example.comm_recyclviewadapter.BaseViewHolder;
import com.yc.english.R;
import com.yc.english.base.helper.GlideHelper;
import com.yc.english.group.model.bean.StudentInfo;

import java.util.List;


/**
 * Created by wanglin  on 2017/8/29 17:16.
 * 已经禁言的学生
 */

public class GroupForbidedMemberAdapter extends BaseAdapter<StudentInfo> {
    private String mTime;

    public GroupForbidedMemberAdapter(Context context, List<StudentInfo> mList) {
        super(context, mList);
    }

    @Override
    protected void convert(BaseViewHolder holder, final int position) {
        final StudentInfo studentInfo = mList.get(position);
        holder.setText(R.id.m_tv_forbid_name, studentInfo.getNick_name());
        GlideHelper.circleImageView(mContext, (ImageView) holder.getView(R.id.m_iv_forbid), studentInfo.getFace(), R.mipmap.default_avatar);
        String str = mContext.getString(R.string.forbid_time);
        holder.setText(R.id.m_tv_forbid_time, String.format(str, mTime));
        holder.setOnClickListener(R.id.m_tv_stop_forbid, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyItemRangeChanged(position, 1);
            }
        });

    }

    @Override
    public int getLayoutID(int viewType) {
        return R.layout.group_forbid_member_item;
    }

    public void setForbidTime(String item) {
        this.mTime = item;
        notifyDataSetChanged();
    }
}
