actual class MockServerFactory {

    companion object {
        var instance: MockServer? = null
    }

    actual fun mockServer(): MockServer {
        return instance!!
    }
}