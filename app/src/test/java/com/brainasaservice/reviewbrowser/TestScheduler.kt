package com.brainasaservice.reviewbrowser

import com.brainasaservice.common.scheduler.SchedulerConfiguration
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

object TestScheduler : SchedulerConfiguration {
    val testScheduler = TestScheduler()

    override val io: Scheduler = Schedulers.trampoline()

    override val computation: Scheduler = Schedulers.trampoline()

    override val ui: Scheduler = Schedulers.trampoline()

    override val timer: Scheduler = testScheduler
}
