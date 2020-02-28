expect class MockServer(port: Int) {

    fun use(scope: MockServer.() -> Unit)

    fun shutdown(): Unit
}

class MockServerBuilder {
    var port = 8080
}

fun mockServer(init: MockServerBuilder.() -> Unit): MockServer {
    return MockServerBuilder().apply(init).create()
}

private fun MockServerBuilder.create() = MockServer(port)