actual class MockServerFactory {

    companion object {
        lateinit var instance: MockServer
    }

    actual fun mockServer(): MockServer {
        return instance
    }
}