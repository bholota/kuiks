actual class MockServer actual constructor() {
    actual fun start(port: Int) {
    }

    actual fun route(vararg rout: Pair<String, String>) {
    }

    actual fun use(scope: MockServer.() -> Unit) {
    }

    actual fun shutdown() {
    }

}