import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

class CoroutineDispatcherTest {
    @Test
    fun testDispatcher() {
        runBlocking {
            println("runBlocking ${Thread.currentThread().name}")
            val job1 = launch(Dispatchers.Default) {
                println("Job1 is running on thread ${Thread.currentThread().name}")
            }
            val job2 = launch(Dispatchers.IO) {
                println("Job2 is running on thread ${Thread.currentThread().name}")
            }
            joinAll(job1, job2)
        }
    }

    @Test
    fun testUnconfined() {
        runBlocking {
            println("runBlocking ${Thread.currentThread().name}")
            launch(Dispatchers.Unconfined) {
                println("Unconfined: ${Thread.currentThread().name}")
                delay(1000)
                println("Unconfined: ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) {
                println("Confined: ${Thread.currentThread().name}")
                delay(1000)
                println("Confined: ${Thread.currentThread().name}")
            }

            delay(2000)
        }
    }

    @Test
    fun testExecutorService() {
        val dispatcherService = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val dispatcherWeb = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

        runBlocking {
            val job1 = launch(dispatcherService) {
                println("Job 1 is running on thread ${Thread.currentThread().name}")
            }
            val job2 = launch(dispatcherWeb) {
                println("Job 2 is running on thread ${Thread.currentThread().name}")
            }
            joinAll(job1, job2)
        }
    }

    @Test
    fun testWithContext() {
        val dispatcherClient = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

        runBlocking {
            val job = launch {
                println("1 is running on thread ${Thread.currentThread().name}")
                withContext(dispatcherClient) {
                    println("2 is running on thread ${Thread.currentThread().name}")
                }
                println("3 is running on thread ${Thread.currentThread().name}")
            }
            job.join()
        }
    }

    @Test
    fun testCancelFinally() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("Start Job")
                    delay(1000)
                    println("End Job")
                } finally {
                    println(isActive)
                    delay(1000)
                    println("finally")
                }
            }
            job.cancelAndJoin()
        }
    }

    @Test
    fun testNonCancellable() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("Start Job")
                    delay(1000)
                    println("End Job")
                } finally {
                    withContext(NonCancellable) {
                        println(isActive)
                        delay(1000)
                        println("finally")
                    }
                }
            }
            job.cancelAndJoin()
        }
    }
}