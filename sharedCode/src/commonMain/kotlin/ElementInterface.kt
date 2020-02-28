package com.laskowski.kuiks

interface AppElement {
    fun tap()
    fun elementWithTestId(testId: String) : AppElement
    fun table(withId: String): AppElement
    fun cell(withId: String): AppElement
    fun waitForExistence(timeout: Long = 10)
    fun hasText(text: String, timeout: Long = 10)
    fun getText(timeout: Long = 10): String
    val debugDescription: String
}

interface Application: AppElement {
    fun launch()
}

@Target(AnnotationTarget.FUNCTION)
annotation class iOSTest

expect class ApplicationWrapper(identifier: String): Application

enum class Platform {
    iOS, Android
}

expect val platform: Platform