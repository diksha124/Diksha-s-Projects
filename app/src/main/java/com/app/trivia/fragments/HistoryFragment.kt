package com.app.trivia.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.trivia.R
import com.app.trivia.base.adapter.RecyclerViewGenricAdapter

import com.app.trivia.databinding.HistoryFragmentBinding
import com.app.trivia.databinding.ItemHistoryBinding

import com.application.triviaapp.app.TriBaseFragment


import com.application.triviaapp.database.GameData
import com.application.triviaapp.database.TriviaGame

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HistoryFragment : TriBaseFragment<HistoryFragmentBinding>() {

    private var gameData: GameData? = null

    private var mAdapter: RecyclerViewGenricAdapter<TriviaGame, ItemHistoryBinding>? = null
    private var mList : ArrayList<TriviaGame> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameData = database.gameDao()


        Observable.fromCallable {

            val dbGames = gameData!!.getCards()
            Log.e("Games",dbGames.size.toString())

            mList.addAll(dbGames)


        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        mAdapter =
            RecyclerViewGenricAdapter(mList, R.layout.item_history) {
                    binder, model, position, itemView ->


                binder.tvGame.text = "GAME ${position + 1} : ${getDateFromMillies(model.playedAt!!)}"
                binder.textName.text = "User Name: ${model.playedName}"
                binder.answerTV.text = model.answerOne
                binder.answer2TV.text = model.answerTwo


            }

        val mLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
      //  viewDataBinding.rvHistory.addItemDecoration(MyDividerItemDecoration(getContainerActivity(), LinearLayoutManager.VERTICAL, 16))
        viewDataBinding.rvHistory.layoutManager = mLayoutManager
        viewDataBinding.rvHistory.adapter = mAdapter

        Handler(Looper.getMainLooper()).postDelayed({

            mAdapter!!.notifyDataSetChanged()


        }, 1000)

    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.history_fragment
    }

}

