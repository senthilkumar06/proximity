package com.geeks4share.materix

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by senthil-zt121 on 06/02/18.
 */

open class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        hideBar()
    }

    private fun hideBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_IMMERSIVE
    }
}
