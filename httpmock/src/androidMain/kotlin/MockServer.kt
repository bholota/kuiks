import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

actual class MockServer {
    private val server = MockWebServer()
    actual fun start(port: Int) {
        server.start(port)
    }

    actual fun route(rout: Pair<String, String>) {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                if (rout.first.equals(request.path)) {
                    MockResponse().setBody(rout.second).setResponseCode(200)
                } else
                    MockResponse().setResponseCode(200)
        }
    }

    actual fun use(scope: MockServer.() -> Unit) {
        scope()
        shutdown()
    }

    actual fun shutdown() =
        server.shutdown()

}