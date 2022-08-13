package com.fov.common_ui.theme

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.fov.common_ui.R

object ThemeHelper {

    @Composable
    fun isDarkTheme(): Boolean {
        return true//isSystemInDarkTheme()
    }
    @Composable
    fun getLogoResource() : Int {

        return if(isDarkTheme()){
            R.drawable.avatar
        } else{
            R.drawable.avatar
        }
    }
    @Composable
    fun getPxFromDP(sizeInDp: Dp) : Float{
        val scale = Resources.getSystem().displayMetrics.density
        val dpAsPixels = (sizeInDp.value * scale + 0.5f).toFloat();
        return dpAsPixels;
    }


}