package com.fajar.githubuserappdicoding.presentation.uiview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.presentation.util.changeTheme
import com.fajar.githubuserappdicoding.presentation.viewmodel.SplashVM
import com.fajar.githubuserappdicoding.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {

    private lateinit var vm: SplashVM

    companion object {
        private const val DELAY_MILLIS = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        vm = obtainViewModel()

        vm.themeState.observe(this) { isDarkTheme ->
            changeTheme(isDarkTheme)
            vm.setThemeEvent()
        }
        vm.themeAlreadyChangedEvent.observe(this) { singleEvent ->
            singleEvent.getContentIfNotHandled()?.let {
                lifecycleScope.launch(Dispatchers.Main) {
                    val intent = Intent(this@Splash, MainActivity::class.java)
                    delay(DELAY_MILLIS)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    private fun obtainViewModel(): SplashVM {
        val factory = ViewModelFactory.getInstance(applicationContext)
        return ViewModelProvider(this, factory)[SplashVM::class.java]
    }

}