package server

data class Request(
    val method: Method,
    val headers: List<Header>
)