expect class MockServer() {
    fun start(port: Int)
    fun route(rout: Map<String, String>)
    fun use(scope: MockServer.() -> Unit)

    fun shutdown()
}