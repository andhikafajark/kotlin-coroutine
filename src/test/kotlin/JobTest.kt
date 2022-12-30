import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class JobTest {
    @Test
    fun testJob() {
        runBlocking {
            GlobalScope.launch {
                delay(1000)
                println("Done ${Thread.currentThread().name}")
            }
        }
    }

    @Test
    fun testJobLazy() {
        runBlocking {
            val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
                delay(1000)
                println("Done ${Thread.currentThread().name}")
            }

            job.start()

            delay(3000)
        }
    }

    @Test
    fun testJobJoin() {
        runBlocking {
            val job = GlobalScope.launch() {
                delay(1000)
                println("Done ${Thread.currentThread().name}")
            }

            job.join()
        }
    }

    @Test
    fun testJobCancel() {
        runBlocking {
            val job = GlobalScope.launch() {
                delay(1000)
                println("Done ${Thread.currentThread().name}")
            }

            job.cancel()
            delay(3000)
        }
    }

    @Test
    fun testJobJoinAll() {
        runBlocking {
            val job1 = GlobalScope.launch() {
                delay(1000)
                println("Job1 Done ${Thread.currentThread().name}")
            }

            val job2 = GlobalScope.launch() {
                delay(2000)
                println("Job2 Done ${Thread.currentThread().name}")
            }

            joinAll(job1, job2)
            delay(3000)
        }
    }
}