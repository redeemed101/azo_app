package com.fov.shorts.utils

import android.view.View
import com.fov.shorts.views.Shorts

interface ShortsCallback {
    fun done()

    fun onNextCalled(view: View, shorts : Shorts, index: Int)
}