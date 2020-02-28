package com.laskowski.kuiks

import platform.XCTest.*

actual val platform: Platform = Platform.iOS

class UIElementWrapper(val element: XCUIElement): AppElement {
    override fun tap() {
        element.tap()
    }

    override fun elementWithTestId(testId: String): AppElement {
        return elementWith(testId, XCUIElementTypeAny)
    }

    override fun table(withId: String): AppElement {
        return elementWith(withId, XCUIElementTypeTable)
    }

    override fun cell(withId: String): AppElement {
        val query = element.cells.elementMatchingType(XCUIElementTypeCell, withId)
        return UIElementWrapper(query)
    }

    override fun waitForExistence(timeout: Long) {
        element.waitForExistenceWithTimeout(timeout)
    }

    override fun hasText(text: String, timeout: Long) {
        element.waitForExistenceWithTimeout(text)
    }

    private fun elementWith(testId: String, type: XCUIElementType): AppElement {
        val query = element.descendantsMatchingType(type).matchingIdentifier(testId)
        val element = query.element
        return UIElementWrapper(element)
    }

    override val debugDescription: String
        get() = element.debugDescription()
}

actual class ApplicationWrapper actual constructor(identifier: String) : Application {

    private val app: XCUIApplication = XCUIApplication()

    override fun launch() {
        app.launch()
    }

    override fun tap() {
        UIElementWrapper(app).tap()
    }

    override fun elementWithTestId(testId: String): AppElement {
        return UIElementWrapper(app).elementWithTestId(testId)
    }

    override fun table(withId: String): AppElement {
        return UIElementWrapper(app).table(withId)
    }

    override fun cell(withId: String): AppElement {
        return UIElementWrapper(app).table(withId)
    }

    override fun waitForExistence(timeout: Long) {
        return UIElementWrapper(app).waitForExistence(timeout)
    }

    override fun hasText(text: String, timeout: Long) {
        return UIElementWrapper(app).hasText(text, 0)
    }

    override val debugDescription: String
        get() = app.debugDescription()
}
