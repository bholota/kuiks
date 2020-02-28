expect class MockServer() {
    fun start(port: Int)
    fun route(rout: Pair<String, String>)
    fun use(scope: MockServer.() -> Unit)

    fun shutdown()
}