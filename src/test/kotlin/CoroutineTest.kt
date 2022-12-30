import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.Date

class CoroutineTest {
    suspend fun hello() {
        delay(1000)
        println("Hello World")
    }

    @Test
    fun testCoroutine() {
        GlobalScope.launch {
            hello()
        }

        println("Waiting")
        runBlocking {
            delay(2000)
        }
        println("Finished")
    }

    @Test
    fun testCoroutineMany() {
        repeat(1_000_000) {
            GlobalScope.launch {
                delay(1000)
                println("Done $it : ${Date()} : ${Thread.currentThread().name}")
            }
        }
        println("Waiting")
        runBlocking {
            delay(3000)
        }
        println("Finished")
    }

    @Test
    fun testParentChild() {
        runBlocking {
            val job = GlobalScope.launch {
                launch {
                    delay(2000)
                    println("Child 1 Done")
                }
                launch {
                    delay(4000)
                    println("Child 2 Done")
                }
                delay(1000)
                println("Parent Done")
            }
            job.join()
        }
    }

    @Test
    fun testParentChildCancel() {
        runBlocking {
            val job = GlobalScope.launch {
                launch {
                    delay(2000)
                    println("Child 1 Done")
                }
                launch {
                    delay(4000)
                    println("Child 2 Done")
                }
                delay(1000)
                println("Parent Done")
            }
            job.cancelChildren()
            job.join()
        }
    }

    @Test
    fun testAwaitCancellation() {
        runBlocking {
            val job = launch {
                try {
                    println("Job Start")
                    awaitCancellation()
                } finally {
                    println("Canceled")
                }
            }
            delay(5000)
            job.join()
        }
    }
}