package react.freddy.com.ubid.ui.unbid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.unbid_fragment.view.*
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.UnbidFragmentBinding
import react.freddy.com.ubid.util.InjectorUtils
import react.freddy.com.ubid.util.autoCleared
import react.freddy.com.ubid.vo.Status
import timber.log.Timber

class UnbidFragment : Fragment() {

    var binding by autoCleared<UnbidFragmentBinding>()

    var adapter by autoCleared<BidAdapter>()

    private val viewModel: UnbidViewModel by viewModels {
        InjectorUtils.provideUnbidViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<UnbidFragmentBinding>(inflater, R.layout.unbid_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val bidAdapter = BidAdapter()
        binding.bidList.adapter = bidAdapter
        adapter = bidAdapter

        Timber.i("binding = $binding fragment = $this")

        viewModel.epics.observe(viewLifecycleOwner, Observer { lists ->
            Timber.i("epics observe")
            if(lists.status == Status.ERROR){
                Timber.i("epics observe on error")
                val errorMessage = lists.message
                Toast.makeText(requireContext(), errorMessage ?: "", Toast.LENGTH_SHORT).show()
//                Snackbar.make(binding.bidList, errorMessage ?: "", Snackbar.LENGTH_SHORT).show()
            }else{
                adapter.submitList(lists?.data)
            }
        })

        viewModel.isLogin.observe(viewLifecycleOwner, Observer {
           it.getContentIfNotHandled()?.let { isLogin ->
               if (!isLogin){
                   //跳转登录
//                   findNavController().navigate(R.id.login_fragment)
               }
           }
        })

        viewModel.loadMoreState.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null){
                binding.loadingMore = false
            }else{
                binding.loadingMore = loadingMore.isRunning
            }
        })

        viewModel.setQueryConditions("Unbidding", 1)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun initRecyclerView(){
        binding.bidList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1){
                    //load more, cannot reuse setPageNumber
                    viewModel.loadMorePage()
                }
            }
        })
    }
}