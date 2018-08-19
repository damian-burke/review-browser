package com.brainasaservice.reviewbrowser.dagger.module

import com.brainasaservice.reviewbrowser.ui.review.ReviewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {
    @ContributesAndroidInjector
    abstract fun bindReviewActivity(): ReviewActivity
}
