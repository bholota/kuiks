package server

import Method.Method

data class Request(
    val method: Method,
    val headers: List<Header>
)