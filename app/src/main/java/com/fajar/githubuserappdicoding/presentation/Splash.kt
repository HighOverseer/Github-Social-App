package com.fajar.githubuserappdicoding.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.core.presentation.changeTheme
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
                    val intent = Intent(
                        this@Splash,
                        Class.forName(
                            "com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiview.MainActivity"
                        )
                    )
                    delay(DELAY_MILLIS)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}