package dev.michallaskowski.kuiks.sample
import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

 class RunOnTestApplicationRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application =
        super.newApplication(cl, TestApplication::class.java.name, context)
}