expect class MockServer() {
    fun start(port: Int)
    fun route(vararg rout: Pair<String, String>)
    fun use(scope: MockServer.() -> Unit)

    fun shutdown()
}