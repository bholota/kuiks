package server

interface Dispatcher {
    fun dispatch(request: Request) : Response
}