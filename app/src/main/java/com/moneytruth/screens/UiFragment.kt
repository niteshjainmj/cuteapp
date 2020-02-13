package com.moneytruth.screens


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moneytruth.R
import com.moneytruth.app.AppManager
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.fragment_ui.*


/**
 * A simple [Fragment] subclass.
 */
class UiFragment : Fragment() {


    private var mBgColStr = ""
    private var mPiggiBgColStr = ""
    private var mSavingBgColStr = ""
    private var mBtnBgColStr = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ui, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView(){
        mIvUIBg.setBackgroundColor(AppManager.manager.getBgColor(activity as Context))

        mIvUIPiggiBg.setBackgroundColor(AppManager.manager.getPiggiBgColor(activity as Context))

        mIvUISavingBg.setBackgroundColor(AppManager.manager.getSavingBgColor(activity as Context))

        mIvUIBtnBg.setBackgroundColor(AppManager.manager.getBtnBgColor(activity as Context))

        mUISaveChangeBtn.setBackgroundColor(AppManager.manager.getBtnBgColor(activity as Context))


        mIvUIBg.setOnClickListener{
            ColorPickerDialog.Builder(activity)
                .setTitle("Color Picker Dialog")
                .setPreferenceName("MyColorPickerDialogBg")
                .setPositiveButton(getString(R.string.confirm),
                    ColorEnvelopeListener { envelope, fromUser ->
                    //    setLayoutColor(envelope)
                        mIvUIBg.setBackgroundColor(envelope.color)
                        mBgColStr = envelope.hexCode

                    })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true) // default is true. If false, do not show the BrightnessSlideBar.
                .show()
        }

        mIvUIPiggiBg.setOnClickListener{
            ColorPickerDialog.Builder(activity)
                .setTitle("Color Picker Dialog")
                .setPreferenceName("MyColorPickerDialogPiggi")
                .setPositiveButton(getString(R.string.confirm),
                    ColorEnvelopeListener { envelope, fromUser ->
                        //    setLayoutColor(envelope)

                        mIvUIPiggiBg.setBackgroundColor(envelope.color)
                        mPiggiBgColStr = envelope.hexCode

                    })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true) // default is true. If false, do not show the BrightnessSlideBar.
                .show()
        }

        mIvUISavingBg.setOnClickListener{
            ColorPickerDialog.Builder(activity)
                .setTitle("Color Picker Dialog")
                .setPreferenceName("MyColorPickerDialogSaving")
                .setPositiveButton(getString(R.string.confirm),
                    ColorEnvelopeListener { envelope, fromUser ->
                        //    setLayoutColor(envelope)

                        mIvUISavingBg.setBackgroundColor(envelope.color)
                        mSavingBgColStr = envelope.hexCode
                    })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true) // default is true. If false, do not show the BrightnessSlideBar.
                .show()
        }

        mIvUIBtnBg.setOnClickListener{
            ColorPickerDialog.Builder(activity)
                .setTitle("Color Picker Dialog")
                .setPreferenceName("MyColorPickerDialogBtn")
                .setPositiveButton(getString(R.string.confirm),
                    ColorEnvelopeListener { envelope, fromUser ->
                        //    setLayoutColor(envelope)
                        mIvUIBtnBg.setBackgroundColor(envelope.color)
                        mBtnBgColStr = envelope.hexCode
                    })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true) // default is true. If false, do not show the BrightnessSlideBar.
                .show()
        }


        mUISaveChangeBtn.setOnClickListener {
            var anyChanges = false

            if(mBgColStr.isNotEmpty()){
                anyChanges = true
                AppManager.manager.setBackgroundColor(activity as Context, mBgColStr)
            }

            if(mPiggiBgColStr.isNotEmpty()){
                anyChanges = true
                AppManager.manager.setPiggiBankColor(activity as Context, mPiggiBgColStr)
            }

            if(mSavingBgColStr.isNotEmpty()){
                anyChanges = true
                AppManager.manager.setSavingColor(activity as Context, mSavingBgColStr)
            }

            if(mBtnBgColStr.isNotEmpty()){
                anyChanges = true
                AppManager.manager.setBtnColor(activity as Context, mBtnBgColStr)
            }

            if(anyChanges){
                updateCurrentUI()
                Toast.makeText(context, getString(R.string.save_change_sucess), Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun updateCurrentUI(){
        mUISaveChangeBtn.setBackgroundColor(AppManager.manager.getBtnBgColor(activity as Context))
    }

}
