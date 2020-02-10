package com.moneytruth.screens

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.moneytruth.R
import com.moneytruth.dummy.AppManager

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

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
        setAllViewColors()
    }

    fun setAllViewColors(){
        val appManager = AppManager.manager
        mHomeMainView.setBackgroundColor(appManager.getBgColor(this))
        mCvPiggiHome.setCardBackgroundColor(appManager.getPiggiBgColor(this))
        mCvSavingHome.setCardBackgroundColor(appManager.getSavingBgColor(this))



        mHomeDepositBtn.setBackgroundColor(appManager.getBtnBgColor(this))
        mHomeWithdrawBtn.setBackgroundColor(appManager.getBtnBgColor(this))
        mHomeTransferBtn.setBackgroundColor(appManager.getBtnBgColor(this))



    }

}
