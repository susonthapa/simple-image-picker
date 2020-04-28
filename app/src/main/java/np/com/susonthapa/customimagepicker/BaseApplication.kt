package np.com.susonthapa.customimagepicker

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        Timber.plant(Timber.DebugTree())

    }
}