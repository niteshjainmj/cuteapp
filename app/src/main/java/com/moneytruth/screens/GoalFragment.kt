package com.moneytruth.screens


import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.moneytruth.R
import com.moneytruth.app.*
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.fragment_goal.*
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class GoalFragment : Fragment() {


    var mAllGoalItem = ArrayList<GoalItem>()
    var mSelectedIndex  = -1

    var mGoalDetails : GoalDetails? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        //Added Balance system
        mEtBalance.setFilters(arrayOf<InputFilter>(
            DecimalDigitsInputFilter(
                25,
                2
            )
        ))

        mEtGoalInterest.setFilters(arrayOf<InputFilter>(
            DecimalDigitsInputFilter(
                4,
                2
            )
        ))


        mGoalDetails = AppManager.manager.getSavedGoalDetails(activity as Context)
        if (mGoalDetails != null){
            mSelectedIndex = AppManager.manager.getSelectedGoalIndexForList(mGoalDetails!!.mGoalIndex)


            val bigDecimal = BigDecimal(mGoalDetails!!.mAmount)
            bigDecimal.setScale(2, BigDecimal.ROUND_UP)
            mEtBalance.setText(bigDecimal.toString())

            val nf: NumberFormat = NumberFormat.getNumberInstance()
            nf.maximumFractionDigits = 2
            val rounded: String = nf.format(mGoalDetails!!.mInterestRate)
            mEtGoalInterest.setText(rounded)

            //Year selector

            mIndicatorSeekBar.setProgress(mGoalDetails!!.mYears.toFloat())
            val selectedVal = "" + mGoalDetails!!.mYears + " Years"
            mTvGoalYear.setText(selectedVal)

        }else{
            var defaultInterestRate = "" + AppManager.DEFAULT_INTEREST_RATE
            mEtGoalInterest.setText(defaultInterestRate)

            val selectedVal = "" + mIndicatorSeekBar.min.toInt() + " Years"
            mTvGoalYear.setText(selectedVal)

        }


        //Set goal list
        mAllGoalItem = AppManager.manager.getAllGoalList(activity as Context)
        val layoutManager = GridLayoutManager(activity, 5)
        mRvGoal.layoutManager = layoutManager
//        val dividerItemDecoration = DividerItemDecoration(
//            mRvGoal.getContext(),
//            layoutManager.getOrientation()
//        )
        //mRvGoal.addItemDecoration(dividerItemDecoration)


        val adapter = GoalAdapter(
            activity as Context,
            mAllGoalItem,
            mSelectedIndex
        ) { position, it ->
            mSelectedIndex = position
        }
        mRvGoal.adapter = adapter





        //Year range handler
//        mIndicatorSeekBar.onSeekChangeListener(){
//
//        }


        mIndicatorSeekBar.setOnSeekChangeListener(object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                val yearVal = seekParams.tickText + " Years"
                mTvGoalYear.setText(yearVal)
//                Log.i("SEEKBAR", seekParams.seekBar.toString())
//                Log.i("SEEKBAR", ""+seekParams.progress)
//                Log.i("SEEKBAR", ""+seekParams.progressFloat)
//                Log.i("SEEKBAR", ""+seekParams.fromUser)
//                //when tick count > 0
//                Log.i("SEEKBAR", ""+seekParams.thumbPosition)
//                Log.i("SEEKBAR", seekParams.tickText)
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {}
        })

        mGoalBtnSave.setBackgroundColor(AppManager.manager.getBtnBgColor(activity as Context))
        mGoalBtnSave.setOnClickListener {
            if(isValidGoal() &&  isValidAmount() && isValidInterestRate()){
                var goalItem = GoalDetails(
                    mAllGoalItem[mSelectedIndex].mIndex,
                    mAllGoalItem[mSelectedIndex].mTitle,
                    mAmount = mEtBalance.text.toString().trim(),
                    mYears = mIndicatorSeekBar.progress.toInt(),
                    mInterestRate = mEtGoalInterest.text.toString().trim().toDouble(),
                    mStartDate = Date()
                )
                AppManager.manager.saveGoalDetails(activity as Context, goalItem)

                Toast.makeText(activity, getString(R.string.goal_save_sucess), Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun isValidGoal() : Boolean{
        var valid = false
        if (mSelectedIndex != -1){
            valid = true
        }else{
            Toast.makeText(activity, getString(R.string.goal_select_error_msg), Toast.LENGTH_SHORT).show()
        }

        return valid
    }

    fun isValidAmount() : Boolean{
        var valid = false
        var bal = mEtBalance.text.toString().trim()
        if (bal.isNotEmpty()){
            valid = true
        }else{
            Toast.makeText(activity, getString(R.string.goal_amount_error_msg), Toast.LENGTH_SHORT).show()
        }

        return valid
    }

    fun isValidInterestRate() : Boolean{
        var valid = false
        val interestRateStr  = mEtGoalInterest.text.toString().trim()
        if(interestRateStr.isNotEmpty()){
            var checkZero = interestRateStr.toDouble()
            if(checkZero != 0.0){
                valid = true
            }else{
                Toast.makeText(activity, getString(R.string.goal_interest_error_msg), Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(activity, getString(R.string.goal_interest_error_msg), Toast.LENGTH_SHORT).show()

        }

        return valid
    }


}
