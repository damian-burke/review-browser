package com.brainasaservice.reviewbrowser.ui.review

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.brainasaservice.api.review.ReviewRepository
import com.brainasaservice.api.review.ReviewSortBy
import com.brainasaservice.api.review.ReviewSortDirection
import com.brainasaservice.api.review.response.Review
import com.brainasaservice.common.scheduler.SchedulerConfiguration
import com.brainasaservice.reviewbrowser.util.default
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class ReviewViewModel @Inject constructor(
        private val reviewRepository: ReviewRepository,
        private val schedulers: SchedulerConfiguration
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val reviewLiveData: MutableLiveData<List<Review>> = MutableLiveData<List<Review>>().default(emptyList())

    private val countLiveData: MutableLiveData<Int> = MutableLiveData<Int>().default(INITIAL_COUNT)

    private val pageLiveData: MutableLiveData<Int> = MutableLiveData<Int>().default(INITIAL_PAGE)

    private val ratingLiveData: MutableLiveData<Int> = MutableLiveData<Int>().default(INITIAL_RATING)

    private val ratingFilterEnabledLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>().default(INITIAL_RATING != 0)

    private val sortByLiveData: MutableLiveData<String> = MutableLiveData<String>().default(INITIAL_SORT_BY.value)

    private val directionLiveData: MutableLiveData<String> = MutableLiveData<String>().default(INITIAL_SORT_DIRECTION.value)

    private val onlyWithTextLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>().default(INITIAL_ONLY_WITH_TEXT)

    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>().default(true)

    fun observeReviews(): LiveData<List<Review>> = reviewLiveData

    fun observeCountSetting(): LiveData<Int> = countLiveData

    fun observeReviewPage(): LiveData<Int> = pageLiveData

    fun observeRatingSetting(): LiveData<Int> = ratingLiveData

    fun observeRatingFilterEnabledSetting(): LiveData<Boolean> = ratingFilterEnabledLiveData

    fun observeSortBySetting(): LiveData<String> = sortByLiveData

    fun observeDirectionSetting(): LiveData<String> = directionLiveData

    fun observeOnlyWithTextSetting(): LiveData<Boolean> = onlyWithTextLiveData

    init {
        loadData(false)
    }

    fun setCount(count: Int) {
        if (countLiveData.value != count) {
            countLiveData.value = count
            refreshData()
        }
    }

    fun setRating(rating: Int) {
        if (ratingLiveData.value != rating) {
            ratingLiveData.value = rating
            ratingFilterEnabledLiveData.value = rating != 0
            refreshData()
        }
    }

    fun toggleSortBy() {
        val sortBy = sortByLiveData.value?.let {
            ReviewSortBy.fromValue(it)
        } ?: INITIAL_SORT_BY

        val newSortBy = when (sortBy) {
            ReviewSortBy.DATE -> ReviewSortBy.RATING
            ReviewSortBy.RATING -> ReviewSortBy.DATE
        }

        sortByLiveData.value = newSortBy.value
        refreshData()
    }

    fun toggleSortDirection() {
        val sortDirection = directionLiveData.value?.let {
            ReviewSortDirection.fromValue(it)
        } ?: INITIAL_SORT_DIRECTION

        val newDirection = when (sortDirection) {
            ReviewSortDirection.ASC -> ReviewSortDirection.DESC
            ReviewSortDirection.DESC -> ReviewSortDirection.ASC
        }

        directionLiveData.value = newDirection.value
        refreshData()
    }

    fun toggleOnlyWithText() {
        val onlyWithText = onlyWithTextLiveData.value ?: INITIAL_ONLY_WITH_TEXT

        val newOnlyWithText = !onlyWithText

        onlyWithTextLiveData.value = newOnlyWithText
        refreshData()
    }

    fun loadNextPage() {
        if (isLoadingLiveData.value == false) {
            loadData(true)
        }
    }

    private fun refreshData() {
        Timber.v("refreshData()")
        pageLiveData.value = 0
        reviewLiveData.value = emptyList()
        loadData(false)
    }

    private fun loadData(incrementPage: Boolean = false) {
        val count = countLiveData.value ?: INITIAL_COUNT

        val pageIncrementer = if (incrementPage) {
            1
        } else {
            0
        }
        val page = (pageLiveData.value ?: INITIAL_PAGE) + pageIncrementer

        val rating = ratingLiveData.value ?: INITIAL_RATING
        val sortBy = ReviewSortBy.fromValue(sortByLiveData.value ?: INITIAL_SORT_BY.value)
        val sortDirection = ReviewSortDirection.fromValue(directionLiveData.value
                ?: INITIAL_SORT_DIRECTION.value)

        Timber.v("loadData(count=$count, page=$page, rating=$rating, sortBy=${sortBy.value}, sortDirection=${sortDirection.value}, incrementPage=$incrementPage)")
        loadReviews(
                count,
                page,
                rating,
                sortBy,
                sortDirection
        ).subscribe({
            Timber.v("*** Updated data.")
        }, {
            Timber.e(it, "loadData(increment=$incrementPage)")
        }).also { disposable.add(it) }
    }

    private fun loadReviews(
            count: Int,
            page: Int,
            rating: Int,
            sortBy: ReviewSortBy,
            direction: ReviewSortDirection
    ): Completable {
        return reviewRepository.fetchReviews(
                testLocation,
                testTour,
                count,
                page,
                rating,
                sortBy,
                direction
        )
                .subscribeOn(schedulers.io)
                .observeOn(schedulers.ui)
                .doOnSubscribe { isLoadingLiveData.value = true }
                .doOnEvent { _, _ -> isLoadingLiveData.value = false }
                .doOnSuccess { list ->
                    // add items to list livedata
                    val itemList = reviewLiveData.value?.toMutableList() ?: mutableListOf()
                    itemList.addAll(list)
                    reviewLiveData.value = if (onlyWithTextLiveData.value == true) {
                        itemList.filter { it.message.isNotBlank() }
                    } else {
                        itemList
                    }
                }
                .doOnSuccess { list ->
                    if (list.isNotEmpty()) {
                        pageLiveData.value = page
                    }
                }
                .toCompletable()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    private companion object {
        const val testLocation = "berlin-l17"
        const val testTour = "tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776"

        const val INITIAL_COUNT = 25
        const val INITIAL_PAGE = 0
        const val INITIAL_RATING = 0
        const val INITIAL_ONLY_WITH_TEXT = true

        val INITIAL_SORT_DIRECTION = ReviewSortDirection.DESC
        val INITIAL_SORT_BY = ReviewSortBy.DATE
    }
}
