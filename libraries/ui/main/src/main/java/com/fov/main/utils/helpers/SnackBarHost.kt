package com.fov.main.utils.helpers

import android.content.Context
import android.widget.Toast
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.fov.common_ui.ui.composers.sections.DarkOverlay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun setUpSnackBar(message : String?, state : SnackbarHostState, scope : CoroutineScope){
    if (!message.isNullOrEmpty()) {
        scope.launch {
            var result = state.showSnackbar(
                message = message!!
            )

        }
    }
}
fun setUpToast(message:String?,context : Context){
    if(!message.isNullOrEmpty()){
        Toast.makeText(
            context,
            message!!,
            Toast.LENGTH_SHORT
        )
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun setUpBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope
){
    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed)
        DarkOverlay {
            scope.launch {

                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()

                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
}