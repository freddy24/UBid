package react.freddy.com.ubid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_view_pager_home.*
import react.freddy.com.ubid.MainActivity
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.FragmentViewPagerHomeBinding
import react.freddy.com.ubid.ui.ShareViewModel
import react.freddy.com.ubid.util.InjectorUtils

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
        val binding = FragmentViewPagerHomeBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = HomePagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewPager, object :TabLayoutMediator.OnConfigureTabCallback{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = getTabTitle(position)
            }
        }).attach()

        return binding.root
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

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            HOME_PAGE_INDEX_FIRST -> getString(R.string.un_bid)
            HOME_PAGE_INDEX_SECOND -> getString(R.string.biding)
            else -> null
        }
    }
}