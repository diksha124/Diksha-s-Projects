package com.app.trivia.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


import com.app.trivia.appDatabase.AppDatabase;

import org.jetbrains.annotations.NotNull;



public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mViewDataBinding == null) {
            mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            mRootView = mViewDataBinding.getRoot();
        }

        return mRootView;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }


    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    public void displayIt(final Fragment mFragment, final String tag, final boolean isBack) {
        mActivity.displayIt(mFragment, tag, isBack);
    }

    public Fragment setArguments(final Fragment mFragment, Bundle mBundle) {
        return mActivity.setArguments(mFragment, mBundle);
    }

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public void showToast(String msg){
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }


    public AppDatabase getDatabase(){
        return mActivity.getDatabase();
    }

}
