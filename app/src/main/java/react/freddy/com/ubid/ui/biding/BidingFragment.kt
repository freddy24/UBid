package react.freddy.com.ubid.ui.biding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.BidingFragmentBinding
import react.freddy.com.ubid.ui.unbid.BidAdapter
import react.freddy.com.ubid.ui.unbid.UnbidViewModel
import react.freddy.com.ubid.util.InjectorUtils
import react.freddy.com.ubid.util.autoCleared
import timber.log.Timber

class BidingFragment : Fragment() {

    var binding by autoCleared<BidingFragmentBinding>()

    var adapter by autoCleared<BidAdapter>()

    private val viewModel: BidingViewModel by viewModels {
        InjectorUtils.provideBidingViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.biding_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val bidAdapter = BidAdapter()
        binding.bidList.adapter = bidAdapter
        adapter = bidAdapter

        viewModel.epics.observe(viewLifecycleOwner, Observer { lists ->
            Timber.i("epics observe")
            adapter.submitList(lists?.data)
        })

        viewModel.loadMoreState.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null){
                binding.loadingMore = false
            }else{
                binding.loadingMore = loadingMore.isRunning
            }
        })

        viewModel.setQueryConditions("Open", 1)
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