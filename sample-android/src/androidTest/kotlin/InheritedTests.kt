import dev.michallaskowski.kuiks.sample.sharedTests.TestExample
import org.junit.Test


class InheritedTests : TestExample() {

    @Test
    fun req() {
        // nope
    }

    @Test
    override fun testServer() {
        super.testServer()
    }
}