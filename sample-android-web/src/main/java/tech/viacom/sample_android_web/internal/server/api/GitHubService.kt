package tech.viacom.sample_android_web.internal.server.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import tech.viacom.sample_android_web.internal.server.data.Contributor

interface GitHubService {
    @GET("/repos/{owner}/{repo}/contributors")
    fun contributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<Contributor>>
}