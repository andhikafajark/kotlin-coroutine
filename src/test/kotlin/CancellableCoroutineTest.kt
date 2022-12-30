import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.*

class CancellableCoroutineTest {
    @Test
    fun testCanNotCancel() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Start coroutine ${Date()}")
                Thread.sleep(2000)
                println("End coroutine ${Date()}")
            }

            job.cancel()
            job.join()
        }
    }

    @Test
    fun testCancellable() {
        runBlocking {
            val job = GlobalScope.launch {
                if (!isActive) throw CancellationException()
                println("Start coroutine ${Date()}")

                ensureActive()
                Thread.sleep(2000)

                ensureActive()
                println("End coroutine ${Date()}")
            }

            job.cancel()
            job.join()
        }
    }

    @Test
    fun testCancellableFinally() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("Start coroutine ${Date()}")
                    delay(2000)
                    println("End coroutine ${Date()}")
                } finally {
                    println("Finally")
                }
            }

            job.cancelAndJoin()
        }
    }
}