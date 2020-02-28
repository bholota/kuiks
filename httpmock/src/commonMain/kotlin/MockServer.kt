interface MockServer {
    fun start(port: Int)
    fun route(route: Map<String, String>)
    fun shutdown()
}

expect class MockServerFactory() {
    fun mockServer(): MockServer
}