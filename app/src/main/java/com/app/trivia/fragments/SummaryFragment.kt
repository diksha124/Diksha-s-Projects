package com.app.trivia.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.trivia.R
import com.app.trivia.databinding.FragSummaryLayoutBinding
import com.application.triviaapp.app.TriBaseFragment


class SummaryFragment : TriBaseFragment<FragSummaryLayoutBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewDataBinding.nameTV.text = arguments!!.getString("NAME")
        viewDataBinding.answerTV.text = arguments!!.getString("BEST_CRICKETER")
        viewDataBinding.answer2TV.text = arguments!!.getString("FLAG_COLOR")

        viewDataBinding.finishBtn.setOnClickListener {

            displayIt(
                HomeFragment(),
                HomeFragment::class.java.canonicalName,
                true
            )


            fragmentManager?.popBackStack(
                SummaryFragment::class.java.name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            fragmentManager?.popBackStack(
                HomeFragment::class.java.name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

        }

    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_summary_layout
    }

}

