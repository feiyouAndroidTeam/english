package com.yc.english.read.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.english.R;
import com.yc.english.read.model.domain.EnglishCourseInfo;

import java.util.List;


public class ReadCourseItemClickAdapter extends BaseMultiItemQuickAdapter<EnglishCourseInfo, BaseViewHolder> {

    private Context mContext;

    private int languageType = 1;

    private int lastPosition;

    public int getLanguageType() {
        return languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public ReadCourseItemClickAdapter(Context mContext, List<EnglishCourseInfo> data) {
        super(data);
        this.mContext = mContext;
        addItemType(EnglishCourseInfo.CLICK_ITEM_VIEW, R.layout.read_course_play_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final EnglishCourseInfo item) {
        helper.setText(R.id.tv_chinese_title, item.getMeans())
                .setText(R.id.tv_english_title, item.getSubTitle())
                .addOnClickListener(R.id.layout_play)
                .addOnClickListener(R.id.iv_tape);

        if (item.isPlay()) {
            helper.setVisible(R.id.iv_audio_gif_play, true);
            Glide.with(mContext).load(R.mipmap.read_audio_gif_play).into((ImageView) helper.getView(R.id.iv_audio_gif_play));
            helper.setTextColor(R.id.tv_chinese_title, ContextCompat.getColor(mContext, R.color.black_333)).setTextColor(R.id.tv_english_title, ContextCompat.getColor(mContext, R.color.black_333));
            //helper.setVisible(R.id.layout_tape, true);
            helper.setVisible(R.id.iv_tape,true);
            //helper.setVisible(R.id.iv_result, false);
        } else {
            helper.setVisible(R.id.iv_audio_gif_play, false);
            helper.setTextColor(R.id.tv_chinese_title, ContextCompat.getColor(mContext, R.color.gray_999)).setTextColor(R.id.tv_english_title, ContextCompat.getColor(mContext, R.color.gray_999));
            if(helper.getAdapterPosition() == getLastPosition()) {
                helper.setVisible(R.id.iv_tape, true);
            }else{
                helper.setVisible(R.id.iv_tape, false);
            }
        }

        LogUtils.i("item--->current position" + helper.getAdapterPosition() + "---last---"+ getLastPosition());

        if(item.isShow()) {
            helper.setVisible(R.id.iv_tape,true);
            if (item.isSpeakResult()) {
                helper.setBackgroundRes(R.id.iv_tape, R.mipmap.listen_result_yes);
            } else {
                helper.setBackgroundRes(R.id.iv_tape, R.mipmap.listen_result_no);
            }
        }else{
            //helper.setVisible(R.id.iv_tape,false);
        }

        switch (languageType) {
            case 1:
                helper.setVisible(R.id.tv_chinese_title, true).setVisible(R.id.tv_english_title, true);
                break;
            case 2:
                helper.setVisible(R.id.tv_chinese_title, false).setVisible(R.id.tv_english_title, true);
                break;
            case 3:
                helper.setVisible(R.id.tv_chinese_title, true).setVisible(R.id.tv_english_title, false);
                break;
            default:
                break;
        }
    }
}