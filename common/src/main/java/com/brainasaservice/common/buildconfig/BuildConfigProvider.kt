package com.brainasaservice.common.buildconfig

interface BuildConfigProvider {
    val appVersion: String

    val apiBaseUrl: String

    val timeFormat: String

    val debuggable: Boolean
}
