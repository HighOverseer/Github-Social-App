package com.fajar.githubuserappdicoding.presentation.uiview

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.presentation.util.UIEvent
import com.fajar.githubuserappdicoding.presentation.util.changeTheme
import com.fajar.githubuserappdicoding.presentation.viewmodel.SplashVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Splash : AppCompatActivity() {

    private val vm: SplashVM by viewModels()

    companion object {
        private const val DELAY_MILLIS = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.isDarkThemeFlow.collectLatest {
                    changeTheme(it)
                    vm.sendEvent(UIEvent.OnThemeChangedEvent)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.uiEvent.collectLatest {
                    val intent = Intent(this@Splash, MainActivity::class.java)
                    delay(DELAY_MILLIS)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}