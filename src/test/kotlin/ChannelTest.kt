import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class ChannelTest {
    @Test
    fun testChannel() {
        runBlocking {
            val channel = Channel<Int>()
            val job1 = launch {
                println("Send data 1 to channel")
                channel.send(1)
                println("Send data 2 to channel")
                channel.send(2)
            }
            val job2 = launch {
                println("Receive data from channel: ${channel.receive()}")
                println("Receive data from channel: ${channel.receive()}")
            }

            joinAll(job1, job2)
        }
    }

    @Test
    fun testChannelUnlimited() {
        runBlocking {
            val channel = Channel<Int>(Channel.UNLIMITED)
            val job1 = launch {
                println("Send data 1 to channel")
                channel.send(1)
                println("Send data 2 to channel")
                channel.send(2)
            }
            val job2 = launch {
                println("Receive data from channel: ${channel.receive()}")
            }

            joinAll(job1, job2)
        }
    }

    @Test
    fun testChannelConflated() {
        runBlocking {
            val channel = Channel<Int>(Channel.CONFLATED)
            val job1 = launch {
                println("Send data 1 to channel")
                channel.send(1)
                println("Send data 2 to channel")
                channel.send(2)
            }
            val job2 = launch {
                println("Receive data from channel: ${channel.receive()}")
            }

            joinAll(job1, job2)
        }
    }

    @Test
    fun testChannelBufferOverflow() {
        runBlocking {
            val channel = Channel<Int>(10, BufferOverflow.DROP_LATEST)
            val job1 = launch {
                repeat(100) {
                    println("Send data $it to channel")
                    channel.send(it)
                }
            }
            val job2 = launch {
                repeat(10) {
                    println("Receive data from channel: ${channel.receive()}")
                }
            }

            joinAll(job1, job2)
        }
    }

    @Test
    fun testUndeliveredElement() {
        val channel = Channel<Int>(10) {
            println("Undelivered element: $it")
        }
        channel.close()

        runBlocking {
            val job = launch {
                channel.send(1)
                channel.send(2)
            }
            job.join()
        }
    }

    @Test
    fun testProduce() {
        val scope = CoroutineScope(Dispatchers.IO)
//        val channel = Channel<Int>()
//
//        val job1 = scope.launch {
//            repeat(10) {
//                channel.send(it)
//            }
//        }
        val channel = scope.produce {
            repeat(100) {
                send(it)
            }
        }

        val job2 = scope.launch {
            repeat(10) {
                println("Receive data from channel: ${channel.receive()}")
            }
        }

        runBlocking {
            joinAll(job2)
        }
    }
}