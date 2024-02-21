package com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiaction


sealed class MainUiAction private constructor() {
    data class SearchUser(val query: String) : MainUiAction()
    data class SearchingUser(val chunkedQuery: String) : MainUiAction()

    /*   data object RestoreJobRes : MainUiAction()
       data object ClickUser : MainUiAction()*/
    data object ClearSearchList : MainUiAction()
    data object ChangeMode : MainUiAction()
    data object ChangeTheme : MainUiAction()
}

