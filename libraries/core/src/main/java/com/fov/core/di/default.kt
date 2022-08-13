package com.fov.core.di

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }