import org.junit.jupiter.api.Test
import java.util.Date
import kotlin.concurrent.thread

class ThreadTest {
    @Test
    fun testThreadName() {
        println("Running in thread ${Thread.currentThread().name}")
    }

    @Test
    fun testThread() {
//        Thread in Java
//        val runnable = Runnable {
//            Thread.sleep(2_000)
//            println("Finish Thread : ${Thread.currentThread().name} " + Date())
//        }
//
//        val thread = Thread(runnable)
//        thread.start()

//        Thread in Kotlin
        thread(start = true) {
            Thread.sleep(2_000)
            println("Finish Thread : ${Thread.currentThread().name} " + Date())
        }

        println("Waiting Thread")
        Thread.sleep(3_000)
        println("Finish")
    }

    @Test
    fun testMultipleThread() {
        val thread1 = Thread(Runnable {
            Thread.sleep(2_000)
            println("Finish Thread 1 : ${Thread.currentThread().name} " + Date())
        })

        val thread2 = Thread(Runnable {
            Thread.sleep(2_000)
            println("Finish Thread 2 : ${Thread.currentThread().name} " + Date())
        })

        thread1.start()
        thread2.start()

        println("Waiting Thread")
        Thread.sleep(3_000)
        println("Finish")
    }
}