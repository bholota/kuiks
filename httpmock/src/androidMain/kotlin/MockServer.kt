import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockServerImpl: MockServer {

    private val server = MockWebServer()
    override fun start(port: Int) {
        server.start(port)
    }

    override fun route(rout: Map<String, String>) {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                rout[request.path]?.let { response ->
                    MockResponse().setBody(response).setResponseCode(200)
                } ?: MockResponse().setResponseCode(200)
        }
    }

    override fun routeRemote(remotes: Map<String, String>) {
        TODO()
    }

    override fun shutdown() =
        server.shutdown()
}

actual class MockServerFactory {
    actual fun mockServer(): MockServer {
        return MockServerImpl()
    }
}