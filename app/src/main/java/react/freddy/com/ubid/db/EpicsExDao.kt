package react.freddy.com.ubid.db

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import react.freddy.com.ubid.vo.EpicSearchResult
import react.freddy.com.ubid.vo.EpicVo

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */
@Dao
abstract class EpicsExDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(ex: List<EpicVo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(searchResult: EpicSearchResult)

    @Query("SELECT * FROM EpicSearchResult WHERE currentPage = :currentPage AND status = :status")
    abstract fun getSearchResult(currentPage: Int, status: String): LiveData<EpicSearchResult>

    fun loadEpics(epicIds: List<Int>): LiveData<List<EpicVo>>{
        val order = SparseIntArray()
        epicIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return loadEpicById(epicIds).map { repositories ->
            repositories.sortedWith(compareBy {
                order.get(it.id)
            })
        }
    }

    @Query("SELECT * FROM EpicVo WHERE id in (:epicIds)")
    protected abstract fun loadEpicById(epicIds: List<Int>): LiveData<List<EpicVo>>
}