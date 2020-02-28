class MockServer {
}

fun mockServer(init: MockServer.() -> Unit): MockServer {
    return MockServer().apply(init)
}