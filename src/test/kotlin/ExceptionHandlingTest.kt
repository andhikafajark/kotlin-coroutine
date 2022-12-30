import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class ExceptionHandlingTest {
    @Test
    fun testExceptionLaunch() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Started")
                throw IllegalStateException("Exception")
            }
            job.join()
            println("Finished")
        }
    }

    @Test
    fun testExceptionAsync() {
        runBlocking {
            val deferred = GlobalScope.async {
                println("Started")
                throw IllegalStateException()
            }

            try {
                deferred.await()
                println("Finished")
            } catch (error: Exception) {
                println("Exception: $error")
            } finally {
                println("Finally")
            }
        }
    }

    @Test
    fun testExceptionHandler() {
        val exceptionHandler = CoroutineExceptionHandler { _, error ->
            println("Exception: ${error.message}")
        }

        runBlocking {
            val job = GlobalScope.launch(exceptionHandler) {
                println("Started")
                throw IllegalStateException()
            }

            job.join()
            println("Finished")
        }
    }
}