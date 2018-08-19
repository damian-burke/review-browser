package com.brainasaservice.reviewbrowser.util

import com.azimolabs.conditionwatcher.Instruction

class WaitInstruction(ms: Long = DEFAULT_WAIT_TIME) : Instruction() {
    private val endTime = System.currentTimeMillis() + ms

    override fun getDescription(): String = "Wait a certain amount of milliseconds."

    override fun checkCondition(): Boolean = endTime < System.currentTimeMillis()

    companion object {
        const val DEFAULT_WAIT_TIME = 1000L
    }
}
