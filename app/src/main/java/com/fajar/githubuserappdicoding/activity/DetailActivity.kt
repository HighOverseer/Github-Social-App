package com.fajar.githubuserappdicoding.activity


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.adapter.ViewPagerAdapter
import com.fajar.githubuserappdicoding.databinding.ActivityDetailBinding
import com.fajar.githubuserappdicoding.domain.getDrawableRes
import com.fajar.githubuserappdicoding.domain.loadImage
import com.fajar.githubuserappdicoding.domain.showToast
import com.fajar.githubuserappdicoding.model.User
import com.fajar.githubuserappdicoding.uistate.DetailUiState
import com.fajar.githubuserappdicoding.viewmodel.DetailVM
import com.fajar.githubuserappdicoding.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var intentUsername: String
    private lateinit var vm: DetailVM

    companion object {
        const val EXTRA_USER = "USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = obtainViewModel(savedInstanceState)

        setUpViewPager(savedInstanceState)

        binding.apply {
            btnBackNav.setOnClickListener { finish() }
            ibFavorite.setOnClickListener { vm.toggleFavoriteStatus() }
        }


        vm.uiState.observe(this) { uiState ->
            setLayout(uiState)
        }
    }


    private fun setLayout(uiState: DetailUiState) {
        uiState.apply {
            binding.progressBar.isVisible = isLoading
            setProfileLayout(userProfile)
            setIbFavoriteState(isUserFavorite)
            toastMessage?.let {
                showToast(
                    this@DetailActivity, it
                )
            }
        }
    }

    private fun setIbFavoriteState(isUserFavorite: Boolean) {
        binding.ibFavorite.background = if (isUserFavorite) {
            this.getDrawableRes(R.drawable.favorite_yes)
        } else this.getDrawableRes(R.drawable.favorite_no)
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

    private fun obtainViewModel(savedInstanceState: Bundle?): DetailVM {
        val args = getArgs(savedInstanceState)
        val factory = ViewModelFactory.getInstance(applicationContext, args)
        return ViewModelProvider(this, factory)[DetailVM::class.java]
    }

    private fun getArgs(savedInstanceState: Bundle?): Bundle {
        val args = savedInstanceState?:Bundle()
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