package com.brainasaservice.reviewbrowser.util

import android.arch.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
