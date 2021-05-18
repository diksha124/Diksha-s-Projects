package com.app.trivia.application

import android.os.Bundle
import com.app.trivia.R
import com.app.trivia.base.BaseActivity
import com.app.trivia.databinding.ActivityHomeBinding

import com.app.trivia.fragments.HomeFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayIt(
            HomeFragment(),
            HomeFragment::class.java.canonicalName,
            true
        )

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun setContainerLayout(): Int {
        return viewDataBinding.frameContainer.id
    }
}