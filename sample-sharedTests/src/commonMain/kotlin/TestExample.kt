package dev.michallaskowski.kuiks.sample.sharedTests

import MockServer
import com.laskowski.kuiks.ApplicationWrapper
import com.laskowski.kuiks.Platform
import com.laskowski.kuiks.platform
import kotlin.test.Test

open class TestExample {

    val identifier: String
        get() = if (platform == Platform.iOS) "dev.michallaskowski.kuiks.SampleiOS" else "MainActivity"

    @Test
    fun testOne() {
        val app = ApplicationWrapper(identifier)
        app.launch()
    }

    @Test
    fun testEmpty() {

    }

    @Test
    fun testListWithoutScrolling() {
        val app = ApplicationWrapper(identifier)
        app.launch()

        app.elementWithTestId("show_list").tap()

        val list = app.table("list")
        list.cell("1").tap()

        app.elementWithTestId("show_list").waitForExistence(1)
    }

    @Test
    fun testListWithScrolling() {
        val app = ApplicationWrapper(identifier)
        app.launch()

        app.elementWithTestId("show_list").tap()

        val list = app.table("list")
        list.cell("99").tap()

        app.elementWithTestId("show_list").waitForExistence(1)
    }

    @Test
    open fun testServer() {
        MockServer()
            .apply {
                start(8080)
            }.use {
                route(
                    mapOf(
                        "/repos/michallaskowski/kuiks/contributors" to
                                "[{\"login\":\"Dawid\",\"id\":3706309,\"node_id\":\"MDQ6VXNlcjM3MDYzMDk=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3706309?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/michallaskowski\",\"html_url\":\"https://github.com/michallaskowski\",\"followers_url\":\"https://api.github.com/users/michallaskowski/followers\",\"following_url\":\"https://api.github.com/users/michallaskowski/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/michallaskowski/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/michallaskowski/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/michallaskowski/subscriptions\",\"organizations_url\":\"https://api.github.com/users/michallaskowski/orgs\",\"repos_url\":\"https://api.github.com/users/michallaskowski/repos\",\"events_url\":\"https://api.github.com/users/michallaskowski/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/michallaskowski/received_events\",\"type\":\"User\",\"site_admin\":false,\"contributions\":28},{\"login\":\"michallaskowskivimn\",\"id\":22892014,\"node_id\":\"MDQ6VXNlcjIyODkyMDE0\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/22892014?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/michallaskowskivimn\",\"html_url\":\"https://github.com/michallaskowskivimn\",\"followers_url\":\"https://api.github.com/users/michallaskowskivimn/followers\",\"following_url\":\"https://api.github.com/users/michallaskowskivimn/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/michallaskowskivimn/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/michallaskowskivimn/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/michallaskowskivimn/subscriptions\",\"organizations_url\":\"https://api.github.com/users/michallaskowskivimn/orgs\",\"repos_url\":\"https://api.github.com/users/michallaskowskivimn/repos\",\"events_url\":\"https://api.github.com/users/michallaskowskivimn/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/michallaskowskivimn/received_events\",\"type\":\"User\",\"site_admin\":false,\"contributions\":4}]",
                        "/repos/michallaskowski/kuiks/testers" to
                                "[{\"login\":\"Oscar\",\"id\":3706309,\"node_id\":\"MDQ6VXNlcjM3MDYzMDk=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3706309?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/michallaskowski\",\"html_url\":\"https://github.com/michallaskowski\",\"followers_url\":\"https://api.github.com/users/michallaskowski/followers\",\"following_url\":\"https://api.github.com/users/michallaskowski/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/michallaskowski/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/michallaskowski/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/michallaskowski/subscriptions\",\"organizations_url\":\"https://api.github.com/users/michallaskowski/orgs\",\"repos_url\":\"https://api.github.com/users/michallaskowski/repos\",\"events_url\":\"https://api.github.com/users/michallaskowski/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/michallaskowski/received_events\",\"type\":\"User\",\"site_admin\":false,\"contributions\":28},{\"login\":\"michallaskowskivimn\",\"id\":22892014,\"node_id\":\"MDQ6VXNlcjIyODkyMDE0\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/22892014?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/michallaskowskivimn\",\"html_url\":\"https://github.com/michallaskowskivimn\",\"followers_url\":\"https://api.github.com/users/michallaskowskivimn/followers\",\"following_url\":\"https://api.github.com/users/michallaskowskivimn/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/michallaskowskivimn/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/michallaskowskivimn/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/michallaskowskivimn/subscriptions\",\"organizations_url\":\"https://api.github.com/users/michallaskowskivimn/orgs\",\"repos_url\":\"https://api.github.com/users/michallaskowskivimn/repos\",\"events_url\":\"https://api.github.com/users/michallaskowskivimn/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/michallaskowskivimn/received_events\",\"type\":\"User\",\"site_admin\":false,\"contributions\":4}]"
                    )
                )
                val app = ApplicationWrapper(identifier)
                app.launch()
                app.elementWithTestId("make_call").tap()
                app.elementWithTestId("label").hasText("Dawid")
                check(app.elementWithTestId("label").getText().equals("Dawid, michallaskowskivimn"))
            }
    }
}