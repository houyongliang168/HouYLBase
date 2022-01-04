package workmanager

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 *
 * created by houyl
 * on  9:35 上午
 */

class CheckSystemWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        Log.e("TEST", "Checking system。。。。。。。。")
        Thread.sleep(3000)
        Log.e("TEST", "Checking system done.")
        return Result.success()
    }

}

object MainWorker {
    val checkSystem = OneTimeWorkRequestBuilder<CheckSystemWorker>().build()

    init {

    }

    fun doWork(app: Application) {
        WorkManager.getInstance(app).enqueue(checkSystem)

    }

}
