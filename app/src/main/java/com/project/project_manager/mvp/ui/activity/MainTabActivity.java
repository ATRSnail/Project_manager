package com.project.project_manager.mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.activity.base.BaseActivity;
import com.project.project_manager.mvp.ui.fragment.AddressFragment;
import com.project.project_manager.mvp.ui.fragment.FillFragment;
import com.project.project_manager.mvp.ui.fragment.HomeFragment;
import com.project.project_manager.mvp.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class MainTabActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.group)
    RadioGroup group;
    private static int currIndex = 0;
    private int homeFragmentIndex = 0;
    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_tab;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        mToolbar.setNavigationIcon(null);
        mToolbar.setNavigationOnClickListener(null);
        fragmentManager = getSupportFragmentManager();
        initData();
        group.setOnCheckedChangeListener(this);
        chageIndex(currIndex);
        showFragment();
    }

    private void initData() {
        currIndex = 0;
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", "MemberFragment"));
    }

    private void chageIndex(int index) {
        setCustomTitle(setTabSelection(index));

        currIndex = index;
        showFragment();
    }

    private String setTabSelection(int index) {
        switch (index) {
            case 0:
                return "首页";
            case 1:
                return "归档";
            case 2:
                return "通讯录";
            case 3:
                return "我的";
        }
        return "";
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                return new FillFragment();
            case 2:
                return new AddressFragment();
            case 3:
                return new MineFragment();
            default:
                return null;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.foot_bar_home:
                chageIndex(0);
                break;
            case R.id.foot_bar_im:
                chageIndex(1);
                break;
            case R.id.foot_bar_wan:
                chageIndex(2);
                break;
            case R.id.main_footbar_user:
                chageIndex(3);
                break;
            default:
                break;
        }
        showFragment();
    }
}
