import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

class ExecutorServiceTest {
    @Test
    internal fun testSingleThreadPool() {
        val executorService = Executors.newSingleThreadExecutor()

        repeat(10) {
            val runnable = Runnable {
                Thread.sleep(1_000)
                println("Done: ${Thread.currentThread().name}")
            }

            executorService.execute(runnable)
        }

        println("Waiting Thread")
        Thread.sleep(11_000)
        println("Finish")
    }

    @Test
    internal fun testFixThreadPool() {
        val executorService = Executors.newFixedThreadPool(3)

        repeat(10) {
            val runnable = Runnable {
                Thread.sleep(1_000)
                println("Done: ${Thread.currentThread().name}")
            }

            executorService.execute(runnable)
        }

        println("Waiting Thread")
        Thread.sleep(11_000)
        println("Finish")
    }

    @Test
    internal fun testCacheThreadPool() {
        val executorService = Executors.newCachedThreadPool()

        repeat(10) {
            val runnable = Runnable {
                Thread.sleep(1_000)
                println("Done: ${Thread.currentThread().name}")
            }

            executorService.execute(runnable)
        }

        println("Waiting Thread")
        Thread.sleep(11_000)
        println("Finish")
    }
}