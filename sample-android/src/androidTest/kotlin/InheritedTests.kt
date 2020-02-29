import dev.michallaskowski.kuiks.sample.sharedTests.TestExample
import org.junit.Test


class InheritedTests : TestExample() {
    @Test
    override fun testWithRemote() {
        super.testWithRemote()
    }
}