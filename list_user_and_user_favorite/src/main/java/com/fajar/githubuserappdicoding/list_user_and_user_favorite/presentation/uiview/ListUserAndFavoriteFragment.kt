package com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.fajar.githubuserappdicoding.core.R.drawable
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.core.presentation.changeTheme
import com.fajar.githubuserappdicoding.core.presentation.collectChannelFlowOnLifecycleStarted
import com.fajar.githubuserappdicoding.core.presentation.collectLatestOnLifeCycleStarted
import com.fajar.githubuserappdicoding.core.presentation.getDrawableRes
import com.fajar.githubuserappdicoding.core.presentation.makeToast
import com.fajar.githubuserappdicoding.core.presentation.showToast
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.R
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.databinding.FragmentListUserAndFavoriteBinding
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.databinding.SwitchMenuLayoutBinding
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.di.initDI
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.adapter.ListUserAdapter
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiaction.MainUiAction
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uistate.FavoriteState
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uistate.MainUIState
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.util.checkIsUsingDarkTheme
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.util.toDp
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.viewmodel.ListUserAndFavoriteViewModel
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.viewmodel.ViewModelFactory
import com.fajar.githubuserappdicoding.presentation.MainActivity
import com.google.android.material.search.SearchView
import javax.inject.Inject

class ListUserAndFavoriteFragment : Fragment() {

    private var binding: FragmentListUserAndFavoriteBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ListUserAndFavoriteViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: ListUserAdapter
    private val onItemGetClicked = ::toDetailAct
    private var popUpBinding: SwitchMenuLayoutBinding? = null
    private var popupWindow: PopupWindow? = null
    private var toastMessage: Toast? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDI(requireActivity()).inject(this)
        (activity as MainActivity).setSystemBarVisibility(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListUserAndFavoriteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.collectLatestOnLifeCycleStarted(viewModel.themeState) { isDarkTheme ->
            val isAlreadyDarkTheme = checkIsUsingDarkTheme(resources)
            if (isDarkTheme != isAlreadyDarkTheme) {
                changeTheme(isDarkTheme)
            }
        }

        viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(viewModel.uiEvent) {
            if (it is UIEvent.ToastMessageEvent) {
                toastMessage?.cancel()
                toastMessage = makeToast(requireActivity(), it.message)
                toastMessage?.show()
            }
        }


        setUpComponents()

        viewLifecycleOwner.collectLatestOnLifeCycleStarted(viewModel.uiState) {
            setLayout(it)
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            binding?.apply {
                val isSearchViewHidden = (
                        searchView.currentTransitionState == SearchView.TransitionState.HIDDEN
                                || searchView.currentTransitionState == SearchView.TransitionState.HIDING
                        )
                if (!isSearchViewHidden) {
                    searchView.hide()
                } else {
                    remove()
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setUpComponents() {
        setUpSearchView()
        setUpSearchBar()
        initRecyclerView()

    }

    private fun setUpSearchBar() {
        binding?.apply {
            searchBar.apply {
                inflateMenu(R.menu.menu_theme)
                popUpBinding = SwitchMenuLayoutBinding.inflate(layoutInflater)
                popUpBinding?.switchTheme?.isChecked = checkIsUsingDarkTheme(resources)
                popUpBinding?.switchTheme?.setOnCheckedChangeListener { _, _ ->
                    viewModel.sendAction(MainUiAction.ChangeTheme)
                }
                popupWindow = PopupWindow(
                    popUpBinding?.root,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                popupWindow?.isOutsideTouchable = true
                setOnMenuItemClickListener { selectedMenuItem ->
                    if (selectedMenuItem.itemId == R.id.menu_change_theme) {
                        showPopupMenu()
                    }
                    false
                }
            }
        }
    }

    private fun setUpSearchView() {
        binding?.apply {
            searchView.apply {
                setupWithSearchBar(searchBar)
                editText.setOnEditorActionListener { _, _, _ ->
                    val query = searchView.text.toString().trim()
                    searchBar.setText(query)
                    if (query.isNotBlank()) {
                        viewModel.sendAction(MainUiAction.SearchUser(query))
                        clearFocusAndHideKeyboard()
                    } else {
                        searchView.text.clear()
                        searchView.hide()
                        showToast(
                            requireActivity(),
                            getString(R.string.query_blank)
                        )
                    }
                    false
                }
                val btnClear =
                    toolbar.findViewById<ImageButton>(com.google.android.material.R.id.open_search_view_clear_button)
                btnClear.setOnClickListener {
                    text.clear()
                    toolbar.collapseActionView()
                    clearFocusAndHideKeyboard()
                    viewModel.sendAction(MainUiAction.ClearSearchList)
                }
                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        val query = s.toString().trim()
                        searchBar.setText(query)

                        if (query.isNotBlank()) {
                            viewModel.sendAction(MainUiAction.SearchingUser(query))

                        } else viewModel.sendAction(MainUiAction.ClearSearchList)
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                inflateMenu(R.menu.menu_favorite)
                setOnMenuItemClickListener { selectedMenuItem ->
                    when (selectedMenuItem.itemId) {
                        R.id.menu_favorite -> {
                            viewModel.sendAction(MainUiAction.ChangeMode)
                        }
                    }
                    false
                }
            }
        }
    }


    private fun setLayout(uiState: MainUIState?) {
        binding?.apply {
            uiState?.apply {
                searchBar.setText(query)

                progressBar.isVisible = isLoading
                setSearchViewCollapseAction(favoriteState)
                setFavoriteMenuItemIcon(favoriteState)

                if (isLoading) {
                    adapter.submitList(emptyList())
                } else adapter.submitList(listUserPreview)

                tvEmptyInfo.isVisible = !isLoading && listUserPreview.isEmpty()
            }
        }
    }

    private fun setSearchViewCollapseAction(favoriteState: FavoriteState) {
        favoriteState.toggleSingleEvent.getContentIfNotHandled()?.let { isToggled ->
            if (isToggled) {
                binding?.searchView?.apply {
                    binding?.searchView?.setText("")
                    toolbar.collapseActionView()
                    clearFocusAndHideKeyboard()
                }

            }
        }
    }

    private fun showPopupMenu() {
        val switchMenuItemView = view?.findViewById<View>(R.id.menu_change_theme)
        popupWindow?.showAsDropDown(
            switchMenuItemView,
            0,
            -45f.toDp(resources).toInt(),
            Gravity.TOP
        )
    }

    private fun setFavoriteMenuItemIcon(favoriteState: FavoriteState) {
        val favoriteMenuItem = binding?.searchView?.toolbar?.menu?.findItem(R.id.menu_favorite)
        favoriteMenuItem?.icon = if (favoriteState.newIsFavoriteList) {
            requireActivity().getDrawableRes(drawable.favorite_yes)
        } else requireActivity().getDrawableRes(drawable.favorite_no)
    }

    private fun initRecyclerView() {
        binding?.apply {
            adapter = ListUserAdapter(onItemGetClicked)
            rvListUsers.setHasFixedSize(true)
            rvListUsers.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        onBackPressedCallback.remove()
    }

    private fun toDetailAct(username: String) {
        val direction =
            ListUserAndFavoriteFragmentDirections.actionListUserAndFavoriteFragmentToDetailUserFragment(
                username
            )
        view?.findNavController()?.navigate(direction)
    }
}