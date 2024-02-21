package com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiview


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.githubuserappdicoding.core.R.drawable.favorite_yes
import com.fajar.githubuserappdicoding.core.R.drawable.favorite_no
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.core.presentation.changeTheme
import com.fajar.githubuserappdicoding.core.presentation.collectChannelFlowOnLifecycleStarted
import com.fajar.githubuserappdicoding.core.presentation.collectLatestOnLifeCycleStarted
import com.fajar.githubuserappdicoding.core.domain.model.UserPreview
import com.fajar.githubuserappdicoding.core.presentation.getDrawableRes
import com.fajar.githubuserappdicoding.core.presentation.makeToast
import com.fajar.githubuserappdicoding.core.presentation.showToast
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.R
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.databinding.ActivityMainBinding
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.databinding.SwitchMenuLayoutBinding
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.di.initDI
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uistate.FavoriteState
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uistate.MainUIState
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiaction.MainUiAction
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.viewmodel.MainViewModel
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.adapter.UserAdapter
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.util.checkIsUsingDarkTheme
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.util.toDp
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.viewmodel.ViewModelFactory
import com.google.android.material.R.id.open_search_view_clear_button
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val vm: MainViewModel by viewModels{
        viewModelFactory
    }

    private val onItemGetClicked = ::toDetailAct
    private var popUpBinding: SwitchMenuLayoutBinding? = null
    private var popupWindow: PopupWindow? = null
    private var toastMessage: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initDI(this).inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collectLatestOnLifeCycleStarted(vm.themeState) { isDarkTheme ->
            val isAlreadyDarkTheme = checkIsUsingDarkTheme(resources)
            if (isDarkTheme != isAlreadyDarkTheme) {
                changeTheme(isDarkTheme)
            }
        }

        collectChannelFlowOnLifecycleStarted(vm.uiEvent) {
            if (it is UIEvent.ToastMessageEvent) {
                toastMessage?.cancel()
                toastMessage = makeToast(this, it.message)
                toastMessage?.show()
            }
        }


        setUpComponents()

        collectLatestOnLifeCycleStarted(vm.uiState) {
            setLayout(it)
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
                    searchBar.setText(query)
                    if (query.isNotBlank()) {
                        vm.sendAction(MainUiAction.SearchUser(query))
                        clearFocusAndHideKeyboard()
                    } else {
                        searchView.text.clear()
                        searchView.hide()
                        showToast(
                            this@MainActivity,
                            getString(R.string.query_blank)
                        )
                    }
                    false
                }
                val btnClear = toolbar.findViewById<ImageButton>(open_search_view_clear_button)
                btnClear.setOnClickListener {
                    text.clear()
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
                        println("beforeTextChanged")
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        println("query : $s")
                        val query = s.toString().trim()
                        searchBar.setText(query)
                        if (query.isNotBlank()) {
                            println("searching")
                            vm.sendAction(MainUiAction.SearchingUser(query))
                        } else {
                            println("clearing")
                            vm.sendAction(MainUiAction.ClearSearchList)
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        /*val query = s.toString().trim()
                        searchBar.setText(query)
                        if (query.isNotBlank()) {
                            vm.sendAction(MainUiAction.SearchingUser(query))
                        } else vm.sendAction(MainUiAction.ClearSearchList)*/
                    }
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
        //vm.sendAction(MainUiAction.RestoreJobRes)
    }

    private fun setLayout(uiState: MainUIState?) {
        binding.apply {
            uiState?.apply {
                searchBar.setText(query)
                println("setLayout : State : { query: $query, isLoading : $isLoading, oldFav : ${favoriteState.oldIsFavoriteList}, newFav: ${favoriteState.newIsFavoriteList} }")
                println("size : ${listUserPreview.size}")
                progressBar.isVisible = isLoading
                setSearchViewCollapseAction(favoriteState)
                setFavoriteMenuItemIcon(favoriteState)
                if (isLoading) {
                    setRv(emptyList())
                } else setRv(listUserPreview)
                tvEmptyInfo.isVisible = !isLoading && listUserPreview.isEmpty()
                /*toastMessage?.let {
                    showToast(this@MainActivity, it)
                }*/
            }
        }
    }

    private fun setSearchViewCollapseAction(favoriteState: FavoriteState) {
        favoriteState.toggleSingleEvent.getContentIfNotHandled()?.let { isToggled ->
            if (isToggled) {
                println("""
                    isToggled
                    ---------
                """.trimIndent())
                binding.searchView.apply {
                    //text.clear()
                    /*if (isModeChangedToFavorite){
                        binding.searchView.setText("")
                    }*/

                    binding.searchView.setText("")
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
            this.getDrawableRes(favorite_yes)
        } else this.getDrawableRes(favorite_no)
    }

    private fun setRv(listUserPreview: List<UserPreview>) {
        val adapter = UserAdapter(listUserPreview, onItemGetClicked)
        binding.apply {
            rvListUsers.adapter = adapter
            rvListUsers.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    /*   private fun obtainViewModel(): MainVM {
           val viewModelFactory = ViewModelFactory.getInstance(applicationContext)
           return ViewModelProvider(this, viewModelFactory)[MainVM::class.java]
       }*/

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
        try {
            val detailActivityClass = Class.forName("com.fajar.githubuserappdicoding.detail_user.presentation.uiview.DetailActivity")
            val extraUserStaticField = detailActivityClass.getDeclaredField("EXTRA_USER").run {
                isAccessible = true
                get(null)
            } as String

            val intent = Intent(this, detailActivityClass)
            intent.putExtra(extraUserStaticField, username)
            startActivity(intent)
        }catch (e:Exception){
            showToast(this, e.message.toString())
        }

    }

}