package com.app.trivia.fragments

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.app.trivia.R
import com.app.trivia.databinding.HomeFragmentBinding
import com.application.triviaapp.app.TriBaseFragment
import com.application.triviaapp.database.GameData
import com.application.triviaapp.database.TriviaGame
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : TriBaseFragment<HomeFragmentBinding>() {

    private var gameData: GameData? = null
    private var bestCricketer : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameData = database.gameDao()

        viewDataBinding.nextBtn.setOnClickListener {

            when (viewDataBinding.viewFlipper.displayedChild) {

                0 -> {

                    if (validateEditText(viewDataBinding.llStepOne.editTextName)) {

                        viewDataBinding.llHistory.visibility = View.GONE
                        viewDataBinding.viewFlipper.showNext()

                    }
                }

                1 -> {



                    val radioButtonID = viewDataBinding.llQuestionOne.radioGroup.checkedRadioButtonId

                    val radioButton = viewDataBinding.llQuestionOne.radioGroup.findViewById<RadioButton>(radioButtonID)

                    if (radioButton != null){

                        bestCricketer = radioButton.text as String
                        viewDataBinding.viewFlipper.showNext()

                    }else{
                        showToast("Please select any one option.")
                    }


                }

                2 -> {

                    if (viewDataBinding.llQuestionTwo.cbOne.isChecked || viewDataBinding.llQuestionTwo.cbTwo.isChecked
                        || viewDataBinding.llQuestionTwo.cbThree.isChecked || viewDataBinding.llQuestionTwo.cbFour.isChecked){


                            val flagColor = ArrayList<String>()

                        if (viewDataBinding.llQuestionTwo.cbOne.isChecked)
                            flagColor.add(viewDataBinding.llQuestionTwo.cbOne.text.toString())

                        if (viewDataBinding.llQuestionTwo.cbTwo.isChecked)
                            flagColor.add(viewDataBinding.llQuestionTwo.cbTwo.text.toString())

                        if (viewDataBinding.llQuestionTwo.cbThree.isChecked)
                            flagColor.add(viewDataBinding.llQuestionTwo.cbThree.text.toString())

                        if (viewDataBinding.llQuestionTwo.cbFour.isChecked)
                            flagColor.add(viewDataBinding.llQuestionTwo.cbFour.text.toString())


                        val game  = TriviaGame()
                        game.playedAt = System.currentTimeMillis()
                        game.playedName = viewDataBinding.llStepOne.editTextName.text.toString()
                        game.answerOne = bestCricketer
                        game.answerTwo = flagColor.joinToString { it }

                        Observable.fromCallable {

                            gameData!!.insertCard(game)

                        }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()


                        val bundle = Bundle()
                        bundle.putString("NAME", viewDataBinding.llStepOne.editTextName.text.toString())
                        bundle.putString("BEST_CRICKETER", bestCricketer)
                        bundle.putString("FLAG_COLOR", flagColor.joinToString { it })
                        displayIt(setArguments(
                            SummaryFragment(),bundle),
                            SummaryFragment::class.java.canonicalName,
                            true
                        )

                    }else{
                        showToast("Please select at least one option.")
                    }


                }

            }
        }


        viewDataBinding.llHistory.setOnClickListener {

            displayIt(
                HistoryFragment(),
                HistoryFragment::class.java.canonicalName,
                true
            )
        }

    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}

