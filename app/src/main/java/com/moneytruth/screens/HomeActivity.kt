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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moneytruth.R
import com.moneytruth.app.*
import com.moneytruth.viewmodels.HomeViewModel
import com.moneytruth.viewmodels.HomeViewModelFactory

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import java.math.BigDecimal

class HomeActivity : AppCompatActivity() {

    private lateinit var mViewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        setTitle("")
        mViewModel = ViewModelProvider(this, HomeViewModelFactory(application)).get(HomeViewModel::class.java)

                // ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initView()
    }

    private fun initView() {
        mHomeWithdrawBtn.setOnClickListener{

            if(mViewModel.mGoalDetails?.value != null){
                showWithDrawDialog()
            }else{
                Toast.makeText(this, getString(R.string.no_goal_selected_error), Toast.LENGTH_SHORT).show()
            }
        }

        mHomeTransferBtn.setOnClickListener{
            if(mViewModel.mGoalDetails?.value != null){
                showTransferDialog()
            }else{
                Toast.makeText(this, getString(R.string.no_goal_selected_error), Toast.LENGTH_SHORT).show()
            }

        }

        mHomeDepositBtn.setOnClickListener{
            if(mViewModel.mGoalDetails?.value != null){
                showDepositDialog()
            }else{
                Toast.makeText(this, getString(R.string.no_goal_selected_error), Toast.LENGTH_SHORT).show()
            }
        }


        mViewModel.mMsgSender.observe(this, Observer {
            if(it.isNotEmpty()){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.updateFlag.observe(this, Observer {
            //Update all saving details
            var saveInterestRate = getString(R.string.home_bank_interest_label)
            var saveBalance = getString(R.string.home_bank_current_balance)
            var saveFuture = getString(R.string.home_bank_future)

            var currentBalance = getString(R.string.home_total_current_money_label)



            var piggyInflationRate = getString(R.string.home_rate_of_inflation)
            var piggyBalance = getString(R.string.home_piggi_current_balance)
            var piggyFuture = getString(R.string.home_piggi_future)


            if(mViewModel.mGoalDetails?.value != null){
                saveInterestRate += " : " + getDoubleToString(mViewModel.mGoalDetails?.value!!.mInterestRate) + " %"
                saveBalance += " $ : " + getBigDecimalToString(AppManager.manager.getSavingAccountBalance(this))



                saveFuture +=  " $ : " + getCompoundInterestString(AppManager.manager.getSavingAccountBalance(this),
                    mViewModel.mGoalDetails!!.value!!.mInterestRate,  mViewModel.mGoalDetails!!.value!!.mYears)


                // PIGGY BANK RELATED VALUES
                piggyInflationRate += " : " + getDoubleToString(AppManager.manager.getInflationRate(this)) + " %"
                piggyBalance += " $ : " + getBigDecimalToString(AppManager.manager.getPiggyBankBalance(this))

                piggyFuture +=  " $ : " + getInflationBasedValue(AppManager.manager.getPiggyBankBalance(this),
                    AppManager.manager.getInflationRate(this),  mViewModel.mGoalDetails!!.value!!.mYears)


                val currentBalanceBigDecimal = AppManager.manager.getSavingAccountBalance(this).add(AppManager.manager.getPiggyBankBalance(this))
                currentBalance += " - $ " + getBigDecimalToString(currentBalanceBigDecimal)
            }

            mTvHomeSavingIntrestRate.text = saveInterestRate
            mTvHomeSavingBalance.text = saveBalance
            mTvHomeSavingFuture.text = saveFuture

            mTvHomePiggyIntrestRate.text = piggyInflationRate
            mTvHomePiggyBalance.text = piggyBalance
            mTvHomePiggyFuture.text = piggyFuture

            mTvSavingGoalCurrentBalance.text = currentBalance
        })


//        mViewModel.mHistoryList.observe(this, Observer {
//
//           val list = it
//            print(it.count())
//        })


        mViewModel.mGoalDetails?.observe(this, Observer {
                var title = getString(R.string.home_saving_type_label) + " - "
                var year = getString(R.string.year_label) + " - "
                var amount = getString(R.string.home_total_type_label) + " - "
                var icon  = R.drawable.goal_image

                if(mViewModel.mGoalDetails?.value != null){
                    title =  getString(R.string.home_saving_type_label) + " - "+  mViewModel.mGoalDetails?.value?.mGoalName
                    year = getString(R.string.home_year_type_label) + " - "+
                            mViewModel.mGoalDetails?.value?.mYears + " " +  getString(R.string.year_label)
                    amount = getString(R.string.home_total_type_label) + " - $ " +  mViewModel.mGoalDetails?.value?.mAmount
                    icon = AppManager.manager.getIconForGoalIndex( mViewModel.mGoalDetails?.value!!.mGoalIndex)
                }

                mTvSavingGoalName.text = title
                mTvSavingGoalYear.text = year
                mTvSavingGoalAmount.text = amount
                mIvSavingGoalIcon.setImageResource(icon)

            mViewModel.updateFlag.postValue(true)
        })

    }



    private fun showWithDrawDialog(){
        val alertDialog = AlertDialog.Builder(this).create() //Read Update
        alertDialog.setTitle(getString(R.string.withdraw_dialog_title))
        alertDialog.setMessage(getString(R.string.withdraw_dialog_msg))


        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.amount_dialog_layout, null)
        alertDialog.setView(dialogView)
        val editText = dialogView.findViewById(R.id.mEtAddGroup) as EditText
        editText.setFilters(arrayOf<InputFilter>(
            DecimalDigitsInputFilter(
                25,
                2
            )
        ))
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, getString(R.string.withdraw_dialog_bank),
            DialogInterface.OnClickListener { _, _ ->
                // here you can add_group_dialog_layout functions
                val amountStr = editText.text.toString().trim()
                if(amountStr.isNotEmpty()){
                    val moneyBigDecimal = BigDecimal(amountStr)
                    if(moneyBigDecimal.compareTo(BigDecimal.ZERO) > 0){
                        mViewModel.handleSaveWithDraw(moneyBigDecimal, this)
                    }else{
                        showToast(getString(R.string.invalid_amount))
                    }
                }else{
                    showToast(getString(R.string.no_amount_error))
                }
            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.withdraw_dialog_piggi),
            DialogInterface.OnClickListener { _, _ ->
                val amountStr = editText.text.toString().trim()
                if(amountStr.isNotEmpty()){
                    val moneyBigDecimal = BigDecimal(amountStr)
                    if(moneyBigDecimal.compareTo(BigDecimal.ZERO) > 0){
                        mViewModel.handlePiggyWithDraw(moneyBigDecimal, this)
                    }else{
                        showToast(getString(R.string.invalid_amount))
                    }
                }else{
                    showToast(getString(R.string.no_amount_error))
                }
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
        editText.setFilters(arrayOf<InputFilter>(
            DecimalDigitsInputFilter(
                25,
                2
            )
        ))
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, getString(R.string.transfer_dialog_bank_to),
            DialogInterface.OnClickListener { _, _ ->
                val amountStr = editText.text.toString().trim()
                if(amountStr.isNotEmpty()){
                    val moneyBigDecimal = BigDecimal(amountStr)
                    if(moneyBigDecimal.compareTo(BigDecimal.ZERO) > 0){
                        mViewModel.handleTransferSavingToPiggyBank(moneyBigDecimal, this)
                    }else{
                        showToast(getString(R.string.invalid_amount))
                    }
                }else{
                    showToast(getString(R.string.no_amount_error))
                }
            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.transfer_dialog_piggi_to),
            DialogInterface.OnClickListener { _, _ ->
                val amountStr = editText.text.toString().trim()
                if(amountStr.isNotEmpty()){
                    val moneyBigDecimal = BigDecimal(amountStr)
                    if(moneyBigDecimal.compareTo(BigDecimal.ZERO) > 0){
                        mViewModel.handleTransferPiggyBankToSaving(moneyBigDecimal, this)
                    }else{
                        showToast(getString(R.string.invalid_amount))
                    }
                }else{
                    showToast(getString(R.string.no_amount_error))
                }
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
        alertDialog.setCancelable(false)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.amount_dialog_layout, null)
        alertDialog.setView(dialogView)
        val editText = dialogView.findViewById(R.id.mEtAddGroup) as EditText
        editText.setFilters(arrayOf<InputFilter>(
            DecimalDigitsInputFilter(
                25,
                2
            )
        ))
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE, getString(R.string.deposit_dialog_bank),
            DialogInterface.OnClickListener { _, _ ->
                val amountStr = editText.text.toString().trim()
                if(amountStr.isNotEmpty()){
                   val moneyBigDecimal = BigDecimal(amountStr)
                    if(moneyBigDecimal.compareTo(BigDecimal.ZERO) > 0){
                        mViewModel.handleSaveDeposit(moneyBigDecimal, this)
                    }else{
                        showToast(getString(R.string.invalid_amount))
                    }
                }else{
                    showToast(getString(R.string.no_amount_error))
                }


            })

        alertDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.deposit_dialog_piggi),
            DialogInterface.OnClickListener { _, _ ->
                val amountStr = editText.text.toString().trim()
                if(amountStr.isNotEmpty()){
                    val moneyBigDecimal = BigDecimal(amountStr)
                    if(moneyBigDecimal.compareTo(BigDecimal.ZERO) > 0){
                        mViewModel.handlePiggyDeposit(moneyBigDecimal, this)
                    }else{
                        showToast(getString(R.string.invalid_amount))
                    }
                }else{
                    showToast(getString(R.string.no_amount_error))
                }

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
        mViewModel.mGoalDetails?.postValue(AppManager.manager.getSavedGoalDetails(this))
        updateUI()
        mViewModel.checkDailyUpdater(this)
    }

    fun updateUI(){
        val appManager = AppManager.manager
        mHomeMainView.setBackgroundColor(appManager.getBgColor(this))
        mCvPiggiHome.setCardBackgroundColor(appManager.getPiggiBgColor(this))
        mCvSavingHome.setCardBackgroundColor(appManager.getSavingBgColor(this))

        mHomeDepositBtn.setBackgroundColor(appManager.getBtnBgColor(this))
        mHomeWithdrawBtn.setBackgroundColor(appManager.getBtnBgColor(this))
        mHomeTransferBtn.setBackgroundColor(appManager.getBtnBgColor(this))



    }

    fun showToast(aStr : String){
        Toast.makeText(this, aStr, Toast.LENGTH_SHORT).show()
    }

}
