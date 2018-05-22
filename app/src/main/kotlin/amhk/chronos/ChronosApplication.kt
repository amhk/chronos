package amhk.chronos

import android.app.Application

import com.jakewharton.threetenabp.AndroidThreeTen

class ChronosApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
