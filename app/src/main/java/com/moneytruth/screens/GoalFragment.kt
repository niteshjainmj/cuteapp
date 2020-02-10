package com.moneytruth.screens


import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.moneytruth.R
import com.moneytruth.dummy.*
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.fragment_goal.*


class GoalFragment : Fragment() {


    var mAllGoalItem = ArrayList<GoalItem>()
    var mSelectedIndex  = -1
    var mHaveGoal = false


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

        //Set goal list
        val layoutManager = GridLayoutManager(activity, 5)
        mRvGoal.layoutManager = layoutManager
//        val dividerItemDecoration = DividerItemDecoration(
//            mRvGoal.getContext(),
//            layoutManager.getOrientation()
//        )
        //mRvGoal.addItemDecoration(dividerItemDecoration)
        mAllGoalItem = AppManager.manager.getAllGoalList(activity as Context)

        val adapter = GoalAdapter(activity as Context, mAllGoalItem, mSelectedIndex){
            position, it->
            mSelectedIndex = position
        }
        mRvGoal.adapter = adapter


    //Added Balance system
        mEtBalance.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(25, 2)))


        //Year range handler
//        mIndicatorSeekBar.onSeekChangeListener(){
//
//        }

        val selectedVal = "" + mIndicatorSeekBar.min.toInt() + " Years"
        mTvGoalYear.setText(selectedVal)

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
            if(isValidGoal() &&  isValidAmount()){
                var goalItem = GoalDetails(
                    mAllGoalItem[mSelectedIndex].mIndex,
                    mAllGoalItem[mSelectedIndex].mTitle, mAmount =  mEtBalance.text.toString().trim(), mYears = mIndicatorSeekBar.progress.toInt())
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


}
