package com.moneytruth.screens


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.moneytruth.R
import com.moneytruth.dummy.AppManager
import com.moneytruth.dummy.GoalAdapter
import com.moneytruth.dummy.GoalItem
import kotlinx.android.synthetic.main.fragment_goal.*


/**
 * A simple [Fragment] subclass.
 */
class GoalFragment : Fragment() {


    var mAllGoalItem = ArrayList<GoalItem>()
    var mSelectedIndex  = -1

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
        val layoutManager = GridLayoutManager(activity, 5)
        mRvGoal.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            mRvGoal.getContext(),
            layoutManager.getOrientation()
        )
        //mRvGoal.addItemDecoration(dividerItemDecoration)
        mAllGoalItem = AppManager.manager.getAllGoalList(activity as Context)

        val adapter = GoalAdapter(activity as Context, mAllGoalItem, mSelectedIndex){
            position, it->
            mSelectedIndex = position
        }
        mRvGoal.adapter = adapter
    }


}
