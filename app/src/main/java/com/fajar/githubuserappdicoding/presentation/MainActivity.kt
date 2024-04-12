package com.fajar.githubuserappdicoding.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.fajar.githubuserappdicoding.R
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    private val windowController: WindowInsetsControllerCompat by lazy {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setSystemBarVisibility(isShown: Boolean) {
        if (isShown) {
            windowController.show(WindowInsetsCompat.Type.systemBars())
        } else windowController.hide(WindowInsetsCompat.Type.systemBars())
    }
}