package com.fajar.githubuserappdicoding.uiaction


sealed class MainUiAction private constructor() {
    data class SearchUser(val query: String) : MainUiAction()
    data class SearchingUser(val chunkedQuery: String) : MainUiAction()
    object RestoreJobRes : MainUiAction()
    object ClickUser : MainUiAction()
    object ClearSearchList : MainUiAction()
    object ChangeMode : MainUiAction()
    object ChangeTheme : MainUiAction()
}

