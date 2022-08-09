package com.fov.common_ui.theme

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

object ThemeHelper {

    @Composable
    fun isDarkTheme(): Boolean {
        return true//isSystemInDarkTheme()
    }
    /*@Composable
    fun getLogoResource() : Int {

        return if(isDarkTheme()){
            R.drawable.logo
        } else{
            R.drawable.logo_red
        }
    }*/
    @Composable
    fun getPxFromDP(sizeInDp: Dp) : Float{
        val scale = Resources.getSystem().displayMetrics.density
        val dpAsPixels = (sizeInDp.value * scale + 0.5f).toFloat();
        return dpAsPixels;
    }


}