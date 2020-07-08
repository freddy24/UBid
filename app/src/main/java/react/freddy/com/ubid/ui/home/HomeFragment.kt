package react.freddy.com.ubid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.view.*
import react.freddy.com.ubid.R
import react.freddy.com.ubid.ui.dragger.DaggerQuickPlatform
import react.freddy.com.ubid.ui.dragger.ZhaiNan

class HomeFragment : Fragment() {

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
            .build()
            .waimai();

        root.button_test1.setOnClickListener { it ->
            Snackbar.make(it, zhaiNan.eat(), Snackbar.LENGTH_SHORT).show()
        }
        return root
    }
}