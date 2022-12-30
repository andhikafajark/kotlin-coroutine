import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.broadcast
import org.junit.jupiter.api.Test

class BroadcastChannelTest {
    @Test
    fun testBroadcastChannel() {
        val broadcastChannel = BroadcastChannel<Int>(10)

        val receiveChannel1 = broadcastChannel.openSubscription()
        val receiveChannel2 = broadcastChannel.openSubscription()

        val scope = CoroutineScope(Dispatchers.IO)

        val jobSend = scope.launch {
            repeat(10) {
                broadcastChannel.send(it)
            }
        }

        val job1 = scope.launch {
            repeat(10) {
                println("Job 1: ${receiveChannel1.receive()}")
            }
        }

        val job2 = scope.launch {
            repeat(10) {
                println("Job 2: ${receiveChannel2.receive()}")
            }
        }

        runBlocking {
            joinAll(jobSend, job1, job2)
        }
    }

    @Test
    fun testBroadcastFunction() {
        val scope = CoroutineScope(Dispatchers.IO)

//        val broadcastChannel = BroadcastChannel<Int>(10)
//        val jobSend = scope.launch {
//            repeat(10) {
//                broadcastChannel.send(it)
//            }
//        }
        val  broadcastChannel = scope.broadcast<Int>(capacity = 10) {
            repeat(10) {
                send(it)
            }
        }

        val receiveChannel1 = broadcastChannel.openSubscription()
        val receiveChannel2 = broadcastChannel.openSubscription()

        val job1 = scope.launch {
            repeat(10) {
                println("Job 1: ${receiveChannel1.receive()}")
            }
        }

        val job2 = scope.launch {
            repeat(10) {
                println("Job 2: ${receiveChannel2.receive()}")
            }
        }

        runBlocking {
            joinAll(job1, job2)
        }
    }

    @Test
     fun testConflatedBroadcastChannel() {
        val conflatedBroadcastChannel = ConflatedBroadcastChannel<Int>()
        val receiveChannel = conflatedBroadcastChannel.openSubscription()

        val  scope = CoroutineScope(Dispatchers.IO)

        val job1 = scope.launch {
            repeat(10) {
                delay(1000)
                conflatedBroadcastChannel.send(it)
            }
        }

        val job2 = scope.launch {
            repeat(10) {
                delay(2000)
                println("Job 2: ${receiveChannel.receive()}")
            }
        }

        runBlocking {
            delay(11000)
            scope.cancel()
            joinAll(job1, job2)
        }
    }
}