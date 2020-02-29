import okhttp3.*
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.BufferedSink
import java.io.IOException


internal class ReverseProxyDispatcher(val remotes: Map<String, String>) :
    Dispatcher() {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        val url = remotes.get(request.path)
        val proxiedUri: HttpUrl = HttpUrl.parse(url)!!
            .newBuilder()
            .build()
        val requestBuilder: Request.Builder = Request.Builder()
            .url(proxiedUri)
            .headers(request.headers)
            .removeHeader("Host")
        if (request.bodySize != 0L) {
            requestBuilder.method(request.method, object : RequestBody() {
                override fun contentType(): MediaType {
                    return MediaType.parse(request.getHeader("Content-Type"))!!
                }

                @Throws(IOException::class)
                override fun writeTo(sink: BufferedSink?) {
                    request.body.clone().readAll(sink)
                }

                @Throws(IOException::class)
                override fun contentLength(): Long {
                    return request.bodySize
                }
            })
        }
        var response: Response? = null
        try {
            response = client.newCall(requestBuilder.build()).execute()
        } catch (ignored: IOException) {
        }
        return if (response == null) {
            MockResponse()
                .setStatus("Reverse proxy error")
                .setResponseCode(500)
        } else MockResponse()
            .setHeaders(response.headers())
            .setBody(response.body()!!.source().buffer())
            .setResponseCode(response.code())
    }
}