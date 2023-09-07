package com.fajar.githubuserappdicoding.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.adapter.UserAdapter
import com.fajar.githubuserappdicoding.databinding.ActivityMainBinding
import com.fajar.githubuserappdicoding.databinding.SwitchMenuLayoutBinding
import com.fajar.githubuserappdicoding.domain.changeTheme
import com.fajar.githubuserappdicoding.domain.checkIsUsingDarkTheme
import com.fajar.githubuserappdicoding.domain.getDrawableRes
import com.fajar.githubuserappdicoding.domain.showToast
import com.fajar.githubuserappdicoding.domain.toDp
import com.fajar.githubuserappdicoding.model.UserPreview
import com.fajar.githubuserappdicoding.uiaction.MainUiAction
import com.fajar.githubuserappdicoding.uistate.FavoriteState
import com.fajar.githubuserappdicoding.uistate.MainUIState
import com.fajar.githubuserappdicoding.viewmodel.MainVM
import com.fajar.githubuserappdicoding.viewmodel.ViewModelFactory
import com.google.android.material.R.id.search_view_clear_button
import com.google.android.material.search.SearchView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainVM

    private val onItemGetClicked = ::toDetailAct
    private var popUpBinding: SwitchMenuLayoutBinding? = null
    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = obtainViewModel()

        vm.themeState.observe(this) { isDarkTheme ->
            val isAlreadyDarkTheme = checkIsUsingDarkTheme(resources)
            if (isDarkTheme != isAlreadyDarkTheme) {
                changeTheme(isDarkTheme)
            }
        }

        setUpComponents()

        vm.uiState.observe(this) { uiState ->
            setLayout(uiState)
        }

    }

    private fun setUpComponents() {
        setUpSearchView()
        setUpSearchBar()
    }

    private fun setUpSearchBar() {
        binding.apply {
            searchBar.apply {
                inflateMenu(R.menu.menu_theme)
                popUpBinding = SwitchMenuLayoutBinding.inflate(layoutInflater)
                popUpBinding?.switchTheme?.isChecked = checkIsUsingDarkTheme(resources)
                popUpBinding?.switchTheme?.setOnCheckedChangeListener { _, _ ->
                    vm.sendAction(MainUiAction.ChangeTheme)
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
        binding.apply {
            searchView.apply {
                setupWithSearchBar(searchBar)
                editText.setOnEditorActionListener { _, _, _ ->
                    val query = searchView.text.toString().trim()
                    searchBar.text = query
                    if (query.isNotBlank()) {
                        vm.sendAction(MainUiAction.SearchUser(query))
                        clearFocusAndHideKeyboard()
                    } else {
                        searchView.text?.clear()
                        searchView.hide()
                        showToast(
                            this@MainActivity,
                            getString(R.string.query_blank)
                        )
                    }
                    false
                }
                val btnClear = toolbar.findViewById<ImageButton>(search_view_clear_button)
                btnClear.setOnClickListener {
                    text?.clear()
                    toolbar.collapseActionView()
                    clearFocusAndHideKeyboard()
                    vm.sendAction(MainUiAction.ClearSearchList)
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
                        searchBar.text = query
                        if (query.isNotBlank()) {
                            vm.sendAction(MainUiAction.SearchingUser(query))
                        } else vm.sendAction(MainUiAction.ClearSearchList)
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                inflateMenu(R.menu.menu_favorite)
                setOnMenuItemClickListener { selectedMenuItem ->
                    when (selectedMenuItem.itemId) {
                        R.id.menu_favorite -> {
                            vm.sendAction(MainUiAction.ChangeMode)
                        }
                    }
                    false
                }
            }
        }
    }


    override fun onRestart() {
        super.onRestart()
        vm.sendAction(MainUiAction.RestoreJobRes)
    }

    private fun setLayout(uiState: MainUIState?) {
        binding.apply {
            uiState?.apply {
                searchBar.text = query
                progressBar.isVisible = isLoading
                setSearchViewCollapseAction(favoriteState)
                setFavoriteMenuItemIcon(favoriteState)
                if (isLoading) {
                    setRv(emptyList())
                } else setRv(listUserPreview)
                tvEmptyInfo.isVisible = !isLoading && listUserPreview.isEmpty()
                toastMessage?.let {
                    showToast(this@MainActivity, it)
                }
            }
        }
    }

    private fun setSearchViewCollapseAction(favoriteState: FavoriteState) {
        favoriteState.toggleSingleEvent.getContentIfNotHandled()?.let { isToggled ->
            if (isToggled) {
                binding.searchView.apply {
                    text?.clear()
                    toolbar.collapseActionView()
                    clearFocusAndHideKeyboard()
                }
            }
        }

    }

    private fun showPopupMenu() {
        val switchMenuItemView = this@MainActivity.findViewById<View>(R.id.menu_change_theme)
        popupWindow?.showAsDropDown(
            switchMenuItemView,
            0,
            -45f.toDp(resources).toInt(),
            Gravity.TOP
        )
    }

    private fun setFavoriteMenuItemIcon(favoriteState: FavoriteState) {
        val favoriteMenuItem = binding.searchView.toolbar.menu.findItem(R.id.menu_favorite)
        favoriteMenuItem.icon = if (favoriteState.newIsFavoriteList) {
            this.getDrawableRes(R.drawable.favorite_yes)
        } else this.getDrawableRes(R.drawable.favorite_no)
    }

    private fun setRv(listUserPreview: List<UserPreview>) {
        val adapter = UserAdapter(listUserPreview, onItemGetClicked)
        binding.apply {
            rvListUsers.adapter = adapter
            rvListUsers.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun obtainViewModel(): MainVM {
        val viewModelFactory = ViewModelFactory.getInstance(applicationContext)
        return ViewModelProvider(this, viewModelFactory)[MainVM::class.java]
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        binding.apply {
            val isSearchViewHidden = (
                    searchView.currentTransitionState == SearchView.TransitionState.HIDDEN
                            || searchView.currentTransitionState == SearchView.TransitionState.HIDING
                    )
            if (!isSearchViewHidden) {
                searchView.hide()
            } else super.onBackPressed()
        }
    }

    private fun toDetailAct(username: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, username)
        vm.sendAction(
            MainUiAction.ClickUser
        )
        startActivity(intent)
    }

}