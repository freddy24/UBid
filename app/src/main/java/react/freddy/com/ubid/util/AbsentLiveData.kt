package react.freddy.com.ubid.util

import androidx.lifecycle.LiveData

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
class AbsentLiveData<T: Any?> private constructor(): LiveData<T>(){
    init {
        // use post instead of set since this can be created on any thread
        postValue(null)
    }

    companion object{
        fun <T> create(): LiveData<T>{
            return AbsentLiveData()
        }
    }
}