import android.util.Log
import com.laskowski.kuiks.ApplicationWrapper
import dev.michallaskowski.kuiks.sample.sharedTests.TestExample
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test

class InheritedTests : TestExample() {

    @Test
    fun req() {
        MockWebServer().apply {
            start(8080)

            dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.path.equals("/repos/michallaskowski/kuiks/contributors")){
                        return  MockResponse().setBody("[{\"login\":\"Dawid\",\"id\":3706309,\"node_id\":\"MDQ6VXNlcjM3MDYzMDk=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3706309?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/michallaskowski\",\"html_url\":\"https://github.com/michallaskowski\",\"followers_url\":\"https://api.github.com/users/michallaskowski/followers\",\"following_url\":\"https://api.github.com/users/michallaskowski/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/michallaskowski/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/michallaskowski/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/michallaskowski/subscriptions\",\"organizations_url\":\"https://api.github.com/users/michallaskowski/orgs\",\"repos_url\":\"https://api.github.com/users/michallaskowski/repos\",\"events_url\":\"https://api.github.com/users/michallaskowski/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/michallaskowski/received_events\",\"type\":\"User\",\"site_admin\":false,\"contributions\":28},{\"login\":\"michallaskowskivimn\",\"id\":22892014,\"node_id\":\"MDQ6VXNlcjIyODkyMDE0\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/22892014?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/michallaskowskivimn\",\"html_url\":\"https://github.com/michallaskowskivimn\",\"followers_url\":\"https://api.github.com/users/michallaskowskivimn/followers\",\"following_url\":\"https://api.github.com/users/michallaskowskivimn/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/michallaskowskivimn/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/michallaskowskivimn/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/michallaskowskivimn/subscriptions\",\"organizations_url\":\"https://api.github.com/users/michallaskowskivimn/orgs\",\"repos_url\":\"https://api.github.com/users/michallaskowskivimn/repos\",\"events_url\":\"https://api.github.com/users/michallaskowskivimn/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/michallaskowskivimn/received_events\",\"type\":\"User\",\"site_admin\":false,\"contributions\":4}]")
                            .setResponseCode(200)
                    }
                    return MockResponse().setResponseCode(200)
                }
            }

            val app = ApplicationWrapper(identifier)
            app.launch()
            app.elementWithTestId("show_list").tap()

            shutdown()
        }
    }
}