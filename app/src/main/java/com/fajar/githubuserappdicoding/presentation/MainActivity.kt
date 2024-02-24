package com.fajar.githubuserappdicoding.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.fajar.githubuserappdicoding.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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