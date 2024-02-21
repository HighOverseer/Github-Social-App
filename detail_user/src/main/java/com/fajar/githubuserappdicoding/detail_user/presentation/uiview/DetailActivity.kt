package com.fajar.githubuserappdicoding.detail_user.presentation.uiview


import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.fajar.githubuserappdicoding.detail_user.presentation.uistate.DetailUiState
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.DetailVM
import com.fajar.githubuserappdicoding.core.R.drawable.favorite_yes
import com.fajar.githubuserappdicoding.core.R.drawable.favorite_no
import com.fajar.githubuserappdicoding.core.di.util.GenericSavedStateViewModelFactory
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.detail_user.presentation.adapter.ViewPagerAdapter
import com.fajar.githubuserappdicoding.core.presentation.collectChannelFlowOnLifecycleStarted
import com.fajar.githubuserappdicoding.core.presentation.collectLatestOnLifeCycleStarted
import com.fajar.githubuserappdicoding.core.domain.model.User
import com.fajar.githubuserappdicoding.core.presentation.getDrawableRes
import com.fajar.githubuserappdicoding.core.presentation.loadImage
import com.fajar.githubuserappdicoding.core.presentation.showToast
import com.fajar.githubuserappdicoding.detail_user.R
import com.fajar.githubuserappdicoding.detail_user.databinding.ActivityDetailBinding
import com.fajar.githubuserappdicoding.detail_user.di.initDI
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.DetailViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: DetailViewModelFactory

    private lateinit var binding: ActivityDetailBinding
    private lateinit var intentUsername: String
    private val vm: DetailVM by viewModels{
        GenericSavedStateViewModelFactory(factory, this, intent.extras)
    }

    companion object {
        const val EXTRA_USER = "USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initDI(this).inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //vm = obtainViewModel(savedInstanceState)

        setUpViewPager(savedInstanceState)

        binding.apply {
            btnBackNav.setOnClickListener { finish() }
            ibFavorite.setOnClickListener { vm.toggleFavoriteStatus() }
        }


        collectLatestOnLifeCycleStarted(vm.uiState) {
            setLayout(it)
        }

        collectChannelFlowOnLifecycleStarted(vm.uiEvent) {
            if (it is UIEvent.ToastMessageEvent) showToast(this, it.message)
        }
    }


    private fun setLayout(uiState: DetailUiState) {
        uiState.apply {
            binding.progressBar.isVisible = isLoading
            setProfileLayout(userProfile)
            setIbFavoriteState(isUserFavorite)
        }
    }

    private fun setIbFavoriteState(isUserFavorite: Boolean) {
        binding.ibFavorite.background = if (isUserFavorite) {
            this.getDrawableRes(favorite_yes)
        } else this.getDrawableRes(favorite_no)
    }

    private fun setUpViewPager(savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(getArgs(savedInstanceState), this)
        binding.apply {
            viewPager.adapter = adapter
            val tabsTitles = resources.getStringArray(R.array.tab_titles)
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = tabsTitles[pos]
            }.attach()

        }
    }


    private fun getArgs(savedInstanceState: Bundle?): Bundle {
        val args = savedInstanceState ?: Bundle()
        args.putString(EXTRA_USER, intent.getStringExtra(EXTRA_USER)?.also {
            intentUsername = it
        })
        return args
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_USER, intentUsername)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setProfileLayout(user: User) {
        user.apply {
            binding.apply {
                appBar.text = if (username.isNotBlank()) {
                    getString(R.string.app_bar_info, username)
                } else ""
                civDetailAvatar.loadImage(
                    this@DetailActivity, avatar
                )
                tvUserRepos.text = repository
                tvUserFollowing.text = following
                tvUserFollower.text = follower
                if (name.isNotBlank()) {
                    tvDetailNama.isVisible = true
                    tvDetailNama.text = name
                } else tvDetailNama.isVisible = false
                if (company.isNotBlank()) {
                    tvDetailCompany.isVisible = true
                    tvDetailCompany.text = company
                } else tvDetailCompany.isVisible = false
                if (location.isNotBlank()) {
                    tvDetailLocation.isVisible = true
                    tvDetailLocation.text = location
                } else tvDetailLocation.isVisible = false
            }
        }
    }

}