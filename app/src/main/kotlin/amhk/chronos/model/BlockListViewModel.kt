package amhk.chronos.model

import amhk.chronos.database.ChronosDatabase

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

internal class BlockListViewModel(app: Application) : AndroidViewModel(app) {
    val liveData: LiveData<List<Block>>

    init {
        val database = ChronosDatabase.get(getApplication())
        val dao = database.blockEntityDao()
        liveData = Transformations.map(dao.selectAll()) {
            listOfBlockEntities -> listOfBlockEntities as List<Block>
        }
    }
}
