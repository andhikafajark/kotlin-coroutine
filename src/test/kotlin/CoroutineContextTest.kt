import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CoroutineContextTest {
    @ExperimentalStdlibApi
    @Test
    fun testCoroutineContext() {
        runBlocking {
            val job = launch {
                val context = coroutineContext
                println(context)
                println(context[Job])
                println(context[CoroutineDispatcher])
            }
            job.join()
        }
    }

    @Test
    fun testCoroutineName() {
        val scope = CoroutineScope(Dispatchers.IO + CoroutineName("test"))
        val job = scope.launch {
            println("Parent run in thread ${Thread.currentThread().name}")
            withContext(CoroutineName("child")) {
                println("Child run in thread ${Thread.currentThread().name}")
            }
        }

        runBlocking {
            job.join()
        }
    }
}