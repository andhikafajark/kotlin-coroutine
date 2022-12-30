import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class AsyncTest {
    suspend fun getFoo(): Int {
        delay(1000)
        return 10
    }

    suspend fun getBar(): Int {
        delay(1000)
        return 10
    }

    @Test
    fun testAsync() {
        runBlocking {
            val time = measureTimeMillis {
                val foo = async { getFoo() }
                val bar = async { getBar() }

                val total = foo.await() + bar.await()

                println("Total: $total")
            }

            println("Total time: $time")
        }
    }

    @Test
    fun testAwaitAll() {
        runBlocking {
            val time = measureTimeMillis {
                val foo = async { getFoo() }
                val bar = async { getBar() }

                val total = awaitAll(foo, bar).sum()

                println("Total: $total")
            }

            println("Total time: $time")
        }
    }
}