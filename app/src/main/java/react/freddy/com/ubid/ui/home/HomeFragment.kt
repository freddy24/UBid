package react.freddy.com.ubid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_home.view.*
import react.freddy.com.ubid.MainActivity
import react.freddy.com.ubid.R
import react.freddy.com.ubid.ui.ShareViewModel
import react.freddy.com.ubid.ui.dragger.*
import react.freddy.com.ubid.util.InjectorUtils
import javax.inject.Inject
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val shareViewModel: ShareViewModel by viewModels{
        InjectorUtils.provideShareViewModelFactory(requireActivity())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareViewModel.loginInfo.observe(viewLifecycleOwner, Observer { logininfo ->
            val account = logininfo.user.account
            val name = logininfo.person.name
            (requireActivity() as MainActivity).updateHeaderView(account, name)
        })
    }

    override fun onResume() {
        super.onResume()

        val account: String? = MMKV.defaultMMKV().decodeString("account")
        if(!account.isNullOrBlank()){
            shareViewModel.setAccountValue(account)
        }
    }
}