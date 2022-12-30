import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import org.junit.jupiter.api.Test

class ActorTest {
    @Test
    fun testActor() {
        val scope = CoroutineScope(Dispatchers.IO)
        val sendChannel = scope.actor<Int>(capacity = 10) {
            repeat(10) {
                println("Actor received ${receive()}")
            }
        }

        val job = scope.launch {
            repeat(10) {
                sendChannel.send(it)
            }
        }

        runBlocking {
            job.join()
        }
    }
}