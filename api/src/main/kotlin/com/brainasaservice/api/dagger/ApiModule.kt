package com.brainasaservice.api.dagger

import com.brainasaservice.api.review.ReviewApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesReviewApi(retrofit: Retrofit): ReviewApi = retrofit.create(ReviewApi::class.java)
}
