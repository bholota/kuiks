import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

actual class MockServer {
    private val server = MockWebServer()
    actual fun start(port: Int) {
        server.start(port)
    }

    actual fun route(vararg rout: Pair<String, String>) {
        val map = hashMapOf(*rout)
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                map[request.path]?.let { response ->
                    MockResponse().setBody(response).setResponseCode(200)
                } ?: MockResponse().setResponseCode(200)
        }
    }

    actual fun use(scope: MockServer.() -> Unit) {
        scope()
        shutdown()
    }

    actual fun shutdown() =
        server.shutdown()

}