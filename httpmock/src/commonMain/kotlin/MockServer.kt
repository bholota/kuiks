expect class MockServer(port: Int) {

    fun use(scope: MockServer.() -> Unit)

    fun shutdown()
}

class MockServerConfig {
    var port = 8080
}

fun mockServer(init: MockServerConfig.() -> Unit): MockServer {
    return MockServerConfig().apply(init).create()
}

private fun MockServerConfig.create() = MockServer(port)