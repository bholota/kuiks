package dev.michallaskowski.kuiks.sample.android

import android.app.Application
import tech.viacom.sample_android_web.internal.server.api.createService

open class BaseSampleApplication(url: String) : Application() {
    val service = createService(url)
}