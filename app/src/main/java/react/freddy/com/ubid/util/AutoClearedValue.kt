package react.freddy.com.ubid.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * data :2020/7/24
 * auth :wjp
 * Description :
 */
class AutoClearedValue<T: Any>(private val fragment: Fragment) : ReadWriteProperty<Fragment, T> {

    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver{
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer {viewLifeCycleOwner ->
                    viewLifeCycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver{
                        override fun onDestroy(owner: LifecycleOwner) {
                            _value = null
                        }
                    })
                })
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalArgumentException("should not call auto-cleared-value when it might not be available")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

fun <T: Any> Fragment.autoCleared() = AutoClearedValue<T>(this)