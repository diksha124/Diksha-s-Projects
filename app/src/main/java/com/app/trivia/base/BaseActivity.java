package com.app.trivia.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.trivia.appDatabase.AppDatabase;

import java.util.Objects;



public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements BaseFragment.Callback, FragmentManager.OnBackStackChangedListener {

    private FragmentTransaction fragmentTransaction;
    private Handler mHandler;

    private AppDatabase database;


    public Fragment currentFragment;
    private T mViewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

        performDataBinding();
        database = AppDatabase.Companion.getAppDataBase(this);

    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    public void displayIt(final Fragment mFragment, final String tag, final boolean isBack) {
        mHandler.post(() -> {
            currentFragment = mFragment;
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();

            if (isBack) {
                fragmentTransaction.addToBackStack(tag);
            }

            fragmentTransaction
                    .replace(setContainerLayout(), currentFragment, tag)
                    .commitAllowingStateLoss();
        });

    }

    public Fragment setArguments(final Fragment mFragment, Bundle mBundle) {
        if (mBundle != null) {
            mFragment.setArguments(mBundle);
        }
        return mFragment;
    }


    public abstract int setContainerLayout();


    @Override
    public void onBackStackChanged() {
        FragmentManager localFragmentManager = getSupportFragmentManager();
        int i = localFragmentManager.getBackStackEntryCount();
        if (i == 1 || i == 0) {
            finish();
        } else {
            mHandler.postDelayed(localFragmentManager::popBackStack, 100);
        }
    }

    @Override
    public void onBackPressed() {
        onBackStackChanged();
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();

            if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
                int[] scrcoords = new int[2];
                view.getLocationOnScreen(scrcoords);
                float x = ev.getRawX() + view.getLeft() - scrcoords[0];
                float y = ev.getRawY() + view.getTop() - scrcoords[1];
                if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                    ((InputMethodManager) Objects.requireNonNull(this.getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
            }

        return super.dispatchTouchEvent(ev);
    }

    public AppDatabase getDatabase(){
        return database;
    }


}

