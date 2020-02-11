package com.moneytruth.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moneytruth.R
import com.moneytruth.dummy.AppManager

import com.moneytruth.dummy.SettingMenuItem
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*

class SettingListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false
    var mGoalFragment : GoalFragment? = null
    var mUIFragment : UiFragment? = null
    var mHistoryFragment : HistoryFragment? = null
    var mSelectedIndex = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)
        mSelectedIndex  = 0
        setCurrentFragment(AppManager.SETTING_GOAL_INDEX)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setCurrentFragment(aIndex : Int){
        var fragment : Fragment? = null

        when(aIndex){
            AppManager.SETTING_GOAL_INDEX -> {
                if(mGoalFragment == null){
                    mGoalFragment = GoalFragment()
                }
                fragment = mGoalFragment
            }

            AppManager.SETTING_UI_INDEX -> {
                if(mUIFragment == null){
                    mUIFragment = UiFragment()
                }
                fragment = mUIFragment
            }

            AppManager.SETTING_HISTORY_INDEX -> {
                if(mHistoryFragment == null){
                    mHistoryFragment = HistoryFragment()
                }
                fragment = mHistoryFragment
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_detail_container, fragment!!)
            .commit()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        val settingList = AppManager.manager.getAllSettingMenuList(this)

        recyclerView.adapter =
            SimpleItemRecyclerViewAdapter(
                this,
                settingList,
                twoPane
            ){ it, position->
                if(twoPane && position != mSelectedIndex){
                    mSelectedIndex = position
                    setCurrentFragment(it.mIndex)
                }
            }
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: SettingListActivity,
        private val values: List<SettingMenuItem>,
        private val twoPane: Boolean,
        var mClickListenerCallBack : (aSettingItem : SettingMenuItem, aPosition: Int)-> Unit
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.contentView.text = item.mTitle
            holder.itemView.setOnClickListener{
                mClickListenerCallBack(item, position)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val contentView: TextView = view.content
        }
    }
}
