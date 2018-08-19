package com.brainasaservice.reviewbrowser.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class ViewModelActivity<T: ViewModel>(private val viewModelClass: Class<T>): DaggerAppCompatActivity() {
    @Inject
    protected lateinit var viewModelFactory: DaggerViewModelFactory<T>

    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }
}
