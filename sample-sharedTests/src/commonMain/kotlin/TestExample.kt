package dev.michallaskowski.kuiks.sample.sharedTests

import MockServer
import MockServerFactory
import com.laskowski.kuiks.ApplicationWrapper
import com.laskowski.kuiks.Platform
import com.laskowski.kuiks.platform
import kotlin.test.Test

open class TestExample {

    val identifier: String
        get() = if (platform == Platform.iOS) "dev.michallaskowski.kuiks.SampleiOS" else "MainActivity"

    @Test
    fun testOne() {
        val app = ApplicationWrapper(identifier)
        app.launch()
    }

    @Test
    fun testEmpty() {

    }

    @Test
    fun testListWithoutScrolling() {
        val app = ApplicationWrapper(identifier)
        app.launch()

        app.elementWithTestId("show_list").tap()

        val list = app.table("list")
        list.cell("1").tap()

        app.elementWithTestId("show_list").waitForExistence(1.0)
    }

    @Test
    fun testListWithScrolling() {
        val app = ApplicationWrapper(identifier)
        app.launch()

        app.elementWithTestId("show_list").tap()

        val list = app.table("list")
        list.cell("99").tap()

        app.elementWithTestId("show_list").waitForExistence(1.0)
    }

    open var mockServer: MockServer? = null

    @Test
    fun testServer() {
        val server = (mockServer ?: MockServerFactory().mockServer())
        server.start(8080)

        server.route(
            mapOf(
                "/repos/michallaskowski/kuiks/contributors" to
                        "[{\"login\":\"Dawid\",\"contributions\":1},{\"login\":\"michallaskowski\",\"contributions\":3}]",
                "/repos/michallaskowski/kuiks/testers" to
                        "[{\"login\":\"Oscar\",\"contributions\":2}]")
        )
        val app = ApplicationWrapper(identifier)
        app.launch(arguments = mapOf( "contributors_url" to "http://localhost:8080" ))
        app.elementWithTestId("make_call").tap()
        app.elementWithTestId("label").hasText("Dawid")
        check(app.elementWithTestId("label").getText().equals("Dawid, michallaskowski"))

        server.shutdown()
    }
}