package com.moneytruth.screens

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.moneytruth.R
import com.moneytruth.dummy.AppManager
import com.moneytruth.dummy.DecimalDigitsInputFilter
import com.moneytruth.dummy.GoalDetails

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity() {


    private var mGoalDetails : GoalDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        mHomeWithdrawBtn.setOnClickListener{
            if(mGoalDetails != null){
                showWithDrawDialog()
            }else{
                Toast.makeText(this, getString(R.string.no_goal_selected_error), Toast.LENGTH_SHORT).show()
            }
        }

        mHomeTransferBtn.setOnClickListener{
            if(mGoalDetails != null){
                showTransferDialog()
            }else{
                Toast.makeText(this, getString(R.string.no_goal_selected_error), Toast.LENGTH_SHORT).show()
            }

        }

        mHomeDepositBtn.setOnClickListener{
            if(mGoalDetails != null){
                showDepositDialog()
            }else{
                Toast.makeText(this, getString(R.string.no_goal_selected_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkError(){
        var str : String? = null
        print(str!!.length)
    }

    private fun showWithDrawDialog(){
        val alertDialog = AlertDialog.Builder(this).create() //Read Update
        alertDialog.setTitle(getString(R.string.withdraw_dialog_title))
        alertDialog.setMessage(getString(R.string.withdraw_dialog_msg))


        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.amount_dialog_layout, null)
        alertDialog.setView(dialogView)
        val editText = dialogView.findViewById(R.id.mEtAddGroup) as EditText
        editText.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(25, 2)))
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, getString(R.string.withdraw_dialog_bank),
            DialogInterface.OnClickListener { _, _ ->
                // here you can add_group_dialog_layout functions
                val tagStr = editText.text.toString().trim()
                checkError()
                if (tagStr.length < 1) {

                    //(activity as Context).toast(getString(R.string.contact_group_add_invalid))
                } else{

                }

            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.withdraw_dialog_piggi),
            DialogInterface.OnClickListener { _, _ ->
                // here you can add_group_dialog_layout functions
                val tagStr = editText.text.toString().trim()
                checkError()

            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEUTRAL, getString(R.string.withdraw_dialog_cancel),
            DialogInterface.OnClickListener { _, _ ->

            })

        alertDialog.show()

    }

    private fun showTransferDialog(){
        val alertDialog = AlertDialog.Builder(this).create() //Read Update
        alertDialog.setTitle(getString(R.string.transfer_dialog_title))
        alertDialog.setMessage(getString(R.string.transfer_dialog_msg))


        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.amount_dialog_layout, null)
        alertDialog.setView(dialogView)
        val editText = dialogView.findViewById(R.id.mEtAddGroup) as EditText
        editText.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(25, 2)))
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, getString(R.string.transfer_dialog_bank_to),
            DialogInterface.OnClickListener { _, _ ->
                checkError()
                // here you can add_group_dialog_layout functions
                val tagStr = editText.text.toString().trim()
                if (tagStr.length < 1) {

                    //(activity as Context).toast(getString(R.string.contact_group_add_invalid))
                } else{

                }

            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.transfer_dialog_piggi_to),
            DialogInterface.OnClickListener { _, _ ->
                // here you can add_group_dialog_layout functions
                checkError()
                val tagStr = editText.text.toString().trim()

            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEUTRAL, getString(R.string.transfer_dialog_cancel),
            DialogInterface.OnClickListener { _, _ ->

            })

        alertDialog.show()

    }

    private fun showDepositDialog(){
        val alertDialog = AlertDialog.Builder(this).create() //Read Update
        alertDialog.setTitle(getString(R.string.deposit_dialog_title))
        alertDialog.setMessage(getString(R.string.deposit_dialog_msg))


        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.amount_dialog_layout, null)
        alertDialog.setView(dialogView)
        val editText = dialogView.findViewById(R.id.mEtAddGroup) as EditText
        editText.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(25, 2)))
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, getString(R.string.deposit_dialog_bank),
            DialogInterface.OnClickListener { _, _ ->
                checkError()
                // here you can add_group_dialog_layout functions
                val tagStr = editText.text.toString().trim()
                if (tagStr.length < 1) {

                    //(activity as Context).toast(getString(R.string.contact_group_add_invalid))
                } else{

                }

            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.deposit_dialog_piggi),
            DialogInterface.OnClickListener { _, _ ->
                // here you can add_group_dialog_layout functions
                checkError()
                val tagStr = editText.text.toString().trim()

            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEUTRAL, getString(R.string.deposit_dialog_cancel),
            DialogInterface.OnClickListener { _, _ ->

            })

        alertDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_setting){
            startActivity(Intent(this, SettingListActivity::class.java))
            return true
        }else{
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI(){
        mGoalDetails = AppManager.manager.getSavedGoalDetails(this)
        val appManager = AppManager.manager
        mHomeMainView.setBackgroundColor(appManager.getBgColor(this))
        mCvPiggiHome.setCardBackgroundColor(appManager.getPiggiBgColor(this))
        mCvSavingHome.setCardBackgroundColor(appManager.getSavingBgColor(this))



        mHomeDepositBtn.setBackgroundColor(appManager.getBtnBgColor(this))
        mHomeWithdrawBtn.setBackgroundColor(appManager.getBtnBgColor(this))
        mHomeTransferBtn.setBackgroundColor(appManager.getBtnBgColor(this))


        var title = getString(R.string.home_saving_type_label) + " - "
        var year = getString(R.string.year_label) + " - "
        var amount = getString(R.string.home_total_type_label) + " - "
        var icon  = R.drawable.goal_image

        if(mGoalDetails != null){
            title =  getString(R.string.home_saving_type_label) + " - "+  mGoalDetails?.mGoalName
            year = getString(R.string.home_year_type_label) + " - "+
                    mGoalDetails?.mYears + " " +  getString(R.string.year_label)
            amount = getString(R.string.home_total_type_label) + " - $ " + mGoalDetails?.mAmount
            icon = AppManager.manager.getIconForGOalIndex(mGoalDetails!!.mGoalIndex)
        }

        mTvSavingGoalName.text = title
        mTvSavingGoalYear.text = year
        mTvSavingGoalAmount.text = amount
        mIvSavingGoalIcon.setImageResource(icon)

    }

}
