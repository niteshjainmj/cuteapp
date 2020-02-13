package com.moneytruth.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.moneytruth.R
import kotlinx.android.synthetic.main.goal_grid_item.view.*

class GoalAdapter(var mContext : Context, var aGoalList : ArrayList<GoalItem>, var mSelectedIndex : Int,
                  var  mClickCallBack:
                      (aPosition : Int, aGaolItem : GoalItem) -> Unit) :
RecyclerView.Adapter<GoalAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = aGoalList[position]
        holder.textView.text = item.mTitle
        holder.imageView.setImageResource(item.mIcon)

        holder.chileMainView.background = if (mSelectedIndex == position) mContext.getDrawable(R.drawable.rounded_corner_goal_selector_bg) else null

        holder.itemView.setOnClickListener{
            mClickCallBack(position, item)
            if (mSelectedIndex != -1){
                val lastSelect = mSelectedIndex
                mSelectedIndex = position
                notifyItemChanged(lastSelect)
                notifyItemChanged(mSelectedIndex)
            }else{
                mSelectedIndex = position
                notifyItemChanged(mSelectedIndex)
            }

        }

//        holder.itemView.setOnClickListener{
//            mClickListenerCallBack(item, position)
//        }
    }

    override fun getItemCount() = aGoalList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.mIvGoalGridRow
        val textView: TextView = view.mTvGoalGridRow
        val chileMainView : ConstraintLayout = view.mClGoalRowChild
    }
}