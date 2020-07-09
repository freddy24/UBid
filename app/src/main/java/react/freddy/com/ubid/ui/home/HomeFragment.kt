package react.freddy.com.ubid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.view.*
import react.freddy.com.ubid.R
import react.freddy.com.ubid.ui.dragger.*
import javax.inject.Inject
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    @Inject
    lateinit var testSingleton1: TestSingleton
    @Inject
    lateinit var testSingleton2: TestSingleton

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val zhaiNan: ZhaiNan = DaggerQuickPlatform.builder()
            .shangjiaModule(ShangjiaModule("王小二"))
            .build()
            .waimai();

//        val waimaipingtai: QuickPlatform = DaggerQuickPlatform.builder()
//            .shangjiaModule(ShangjiaModule("常德米粉"))
//            .build()
//        waimaipingtai.zhuru(this)
        DaggerActivityComponent.builder()
            .build()
            .inject(this)

        DaggerHomeFragmentComponent.builder()
            .build()
            .inject(this)

        root.button_test1.setOnClickListener { it ->
            Snackbar.make(it, zhaiNan.eat(), Snackbar.LENGTH_SHORT).show()
        }

        root.button_test2.setOnClickListener{ it ->
            Snackbar.make(it, zhaiNan.eat(), Snackbar.LENGTH_SHORT).show()
        }

        root.button_test3.setOnClickListener{
            Snackbar.make(it, "test value = ss", Snackbar.LENGTH_SHORT).show()
        }

        root.button_test4.setOnClickListener { it ->
            Snackbar.make(it, "singlton1 code = ${testSingleton1.hashCode()} and sington2 code = ${testSingleton2.hashCode()}", Snackbar.LENGTH_SHORT).show()
        }

        root.button_next.setOnClickListener { it ->
            findNavController().navigate(R.id.dagger_test_fragment)
        }
        return root
    }
}