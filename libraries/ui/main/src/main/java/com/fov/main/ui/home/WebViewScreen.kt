package com.fov.main.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.fov.common_ui.ui.composers.general.WebView
import com.fov.common_ui.viewModels.CommonViewModel


@Composable
fun WebViewScreen(
    commonViewModel: CommonViewModel
){
    val state by commonViewModel.uiState.collectAsState()
    WebView(url = state.webUrl)
}