package com.fajar.githubuserappdicoding.detail_user.presentation.uiview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.fajar.githubuserappdicoding.core.R.drawable.favorite_no
import com.fajar.githubuserappdicoding.core.R.drawable.favorite_yes
import com.fajar.githubuserappdicoding.core.di.util.GenericSavedStateViewModelFactory
import com.fajar.githubuserappdicoding.core.domain.model.User
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.core.presentation.collectChannelFlowOnLifecycleStarted
import com.fajar.githubuserappdicoding.core.presentation.collectLatestOnLifeCycleStarted
import com.fajar.githubuserappdicoding.core.presentation.getDrawableRes
import com.fajar.githubuserappdicoding.core.presentation.loadImage
import com.fajar.githubuserappdicoding.core.presentation.showToast
import com.fajar.githubuserappdicoding.detail_user.R
import com.fajar.githubuserappdicoding.detail_user.databinding.FragmentDetailBinding
import com.fajar.githubuserappdicoding.detail_user.di.initDI
import com.fajar.githubuserappdicoding.detail_user.presentation.adapter.ViewPagerAdapter
import com.fajar.githubuserappdicoding.detail_user.presentation.uistate.DetailUiState
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.DetailVM
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.DetailViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null

    @Inject
    lateinit var factory: DetailViewModelFactory

    private lateinit var intentUsername: String
    private val viewModel: DetailVM by viewModels {
        GenericSavedStateViewModelFactory(factory, this, arguments)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDI(requireActivity()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager(savedInstanceState)

        binding?.apply {
            btnBackNav.setOnClickListener { view.findNavController().navigateUp() }
            ibFavorite.setOnClickListener { viewModel.toggleFavoriteStatus() }
        }


        viewLifecycleOwner.collectLatestOnLifeCycleStarted(viewModel.uiState) {
            setLayout(it)
        }


        viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(viewModel.uiEvent) {
            if (it is UIEvent.ToastMessageEvent) showToast(requireActivity(), it.message)
        }

    }

    private fun setLayout(uiState: DetailUiState) {
        uiState.apply {
            binding?.progressBar?.isVisible = isLoading
            setProfileLayout(userProfile)
            setIbFavoriteState(isUserFavorite)
        }
    }

    private fun setIbFavoriteState(isUserFavorite: Boolean) {
        binding?.ibFavorite?.background = if (isUserFavorite) {
            requireActivity().getDrawableRes(favorite_yes)
        } else requireActivity().getDrawableRes(favorite_no)
    }

    private fun setUpViewPager(savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(
            getArgs(savedInstanceState),
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
        binding?.apply {
            viewPager.adapter = adapter
            val tabsTitles = resources.getStringArray(R.array.tab_titles)
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = tabsTitles[pos]
            }.attach()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getArgs(savedInstanceState: Bundle?): Bundle {
        val args = savedInstanceState ?: Bundle()
        args.putString(EXTRA_USER, arguments?.getString(EXTRA_USER)?.also {
            intentUsername = it
        })
        return args
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_USER, intentUsername)
    }


    private fun setProfileLayout(user: User) {
        user.apply {
            binding?.apply {
                appBar.text = if (username.isNotBlank()) {
                    getString(R.string.app_bar_info, username)
                } else ""
                civDetailAvatar.loadImage(
                    requireActivity(), avatar
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