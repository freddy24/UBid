package react.freddy.com.ubid.ui.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dagger_test.view.*
import react.freddy.com.ubid.R
import react.freddy.com.ubid.ui.dragger.*
import javax.inject.Inject
import javax.inject.Named


/**
 * A simple [Fragment] subclass.
 * Use the [DaggerTestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DaggerTestFragment : Fragment() {

    @Inject
    lateinit var guaZi: GuaZi

    @Inject
    lateinit var huotuichang: Huotuichang

    @Inject
    @Named("baozi")
    lateinit var baoZi: BaoZi

    @Inject
    lateinit var noodle: Noodle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dagger_test, container, false)

        val xiaochiComponent = DaggerXiaochiComponent.builder()
            .build()

        val daggerFoodComponent = DaggerFoodComponent.builder()
            .build()
            .inject(this)

        root.button_test.setOnClickListener {
            Snackbar.make(it, baoZi.toString() + " " + noodle + " " + guaZi +
            " " + huotuichang, Snackbar.LENGTH_SHORT).show()
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DaggerTestFragment().apply {
            }
    }
}