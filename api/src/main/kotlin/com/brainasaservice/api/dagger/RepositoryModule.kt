package com.brainasaservice.api.dagger

import com.brainasaservice.api.review.ReviewRepository
import com.brainasaservice.api.review.ReviewRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsReviewRepository(
            reviewRepository: ReviewRepositoryImpl
    ): ReviewRepository
}