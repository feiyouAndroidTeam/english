package com.yc.english.main.view.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.yc.english.R;
import com.yc.english.base.view.BaseActivity;
import com.yc.english.base.view.BaseToolBar;
import com.yc.english.group.view.fragments.ClassMainFragment;
import com.yc.english.main.contract.MainContract;
import com.yc.english.main.presenter.MainPresenter;
import com.yc.english.main.view.fragments.IndexFragment;
import com.yc.english.main.view.wdigets.TabBar;
import com.yc.english.setting.view.fragments.MyFragment;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tabbar)
    TabBar mTabBar;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private FragmentAdapter mFragmentAdapter;
    private int mCurrentIndex;


    @Override
    public int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    public void init() {
        mPresenter = new MainPresenter(this, this);

        mTabBar.setOnTabSelectedListener(new TabBar.OnTabSelectedListener() {
            @Override
            public void onSelected(int idx) {
                mViewPager.setCurrentItem(idx, false);
            }
        });
        mTabBar.tab(mCurrentIndex);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mCurrentIndex == position) {
                    return;
                }
                mCurrentIndex = position;
            }

            @Override
            public void onPageSelected(int position) {
                mTabBar.tab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void goToTask(){
        mTabBar.tab(1);
    }

    public void goToMy(){
        mTabBar.tab(2);
    }


    private IndexFragment mIndexFragment;
    private ClassMainFragment mClassMainFragment;
    private MyFragment mMyFragment;
    class FragmentAdapter extends FragmentStatePagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (mIndexFragment == null) {
                    mIndexFragment = new IndexFragment();
                }
                return mIndexFragment;
            } else if (position == 1) {
                if (mClassMainFragment == null) {
                    mClassMainFragment = new ClassMainFragment();
                }
                return mClassMainFragment;
            } else if (position == 2) {
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                }
                return mMyFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mClassMainFragment != null){
            BaseToolBar mToolbar = mClassMainFragment.getToolbar();
            if (mToolbar.isHasMenu()) {
                getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
                mToolbar.setOnMenuItemClickListener();
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);
        if(mClassMainFragment != null) {
            BaseToolBar mToolbar = mClassMainFragment.getToolbar();
            if (mToolbar.isHasMenu()) {
                MenuItem menuItem = menu.findItem(R.id.action);
                menuItem.setTitle(mToolbar.getMenuTitle());
                if (mToolbar.getmIconResid() != 0) {
                    menuItem.setIcon(mToolbar.getmIconResid());
                }
            }
        }
        return result;
    }
}
