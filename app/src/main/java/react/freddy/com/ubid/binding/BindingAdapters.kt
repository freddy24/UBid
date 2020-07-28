package react.freddy.com.ubid.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * data :2020/7/28
 * auth :wjp
 * Description :
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean){
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}