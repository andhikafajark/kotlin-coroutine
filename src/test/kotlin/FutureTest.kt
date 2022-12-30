import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

class FutureTest {

    val executorService = Executors.newFixedThreadPool(10)

    fun getFoo(): Int {
        Thread.sleep(1_000)
        return 10
    }

    fun getBar(): Int {
        Thread.sleep(1_000)
        return 10
    }

    @Test
    fun testNonParallel() {
        val time = measureTimeMillis {
            val foo = getFoo()
            val bar = getBar()

            val result = foo + bar

            println("result: $result")
        }

        println("time: $time")
    }

    @Test
    fun testFutureGet() {
        val time = measureTimeMillis {
            val foo = executorService.submit(Callable { getFoo() })
            val bar = executorService.submit(Callable { getBar() })

            val result = foo.get() as Int + bar.get() as Int

            println("result: $result")
        }

        println("time: $time")
    }
}