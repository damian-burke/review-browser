package com.brainasaservice.common.scheduler

import io.reactivex.Scheduler

interface SchedulerConfiguration {
    val io: Scheduler

    val computation: Scheduler

    val ui: Scheduler

    val timer: Scheduler
}
