package com.yc.english.weixin.views.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.yc.english.R;
import com.yc.english.weixin.views.fragments.CourseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangkai on 2017/9/6.
 */

public class TabsUtils {
    public static class MyAdapter extends Indicator.IndicatorAdapter {
        private Activity mContext;
        private String[] mTitles;

        public MyAdapter(Activity context, String[] titles) {
            super();
            this.mContext = context;
            this.mTitles = titles;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mContext.getLayoutInflater().inflate(R.layout.weixin_tab, parent, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(mTitles[position]);
            return convertView;
        }
    }

    public static class MyFragmentAdapter extends FragmentStatePagerAdapter {
        private List<CourseFragment> courseFragments;
        private int count;
        public MyFragmentAdapter(FragmentManager fm, String[] types) {
            super(fm);
            courseFragments = new ArrayList<>();
            for(int i=0; i < types.length; i++){
                CourseFragment courseFragment = new CourseFragment();
                courseFragment.setType(types[i]);
                courseFragments.add(courseFragment);
            }
            count = types.length;
        }

        @Override
        public Fragment getItem(int position) {
            return courseFragments.get(position);
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
