package amhk.chronos.model

import amhk.chronos.database.ChronosDatabase
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

internal class BlockDetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val map: MutableMap<Long, LiveData<Block>> = HashMap()
    private val dao = ChronosDatabase.get(getApplication()).blockEntityDao()

    fun getBlockById(id: Long): LiveData<Block> {
        var value = map[id]
        if (value == null) {
            @Suppress("UNCHECKED_CAST")
            value = dao.selectById(id) as LiveData<Block>
            map[id] = value
        }
        return value
    }
}