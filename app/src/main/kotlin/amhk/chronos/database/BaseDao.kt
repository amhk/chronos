package amhk.chronos.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

internal abstract class BaseDao {
    protected fun <T> LiveData<T>.getDistinct(): LiveData<T> {
        val distinctLiveData = MediatorLiveData<T>()
        distinctLiveData.addSource(this, object : Observer<T> {
            private var initialized = false
            private var lastObj: T? = null
            override fun onChanged(obj: T?) {
                if (!initialized || obj != lastObj) {
                    initialized = true
                    lastObj = obj
                    distinctLiveData.postValue(lastObj)
                }
            }
        })
        return distinctLiveData
    }
}
