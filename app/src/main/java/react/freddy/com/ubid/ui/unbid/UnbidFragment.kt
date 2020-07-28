package react.freddy.com.ubid.ui.unbid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.UnbidFragmentBinding
import react.freddy.com.ubid.util.InjectorUtils
import react.freddy.com.ubid.util.autoCleared
import react.freddy.com.ubid.vo.Status

class UnbidFragment : Fragment() {

    private lateinit var binding: UnbidFragmentBinding

    var adapter by autoCleared<BidAdapter>()

    private val viewModel: UnbidViewModel by viewModels {
        InjectorUtils.provideUnbidViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UnbidFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        val bidAdapter = BidAdapter()
        binding.bidList.adapter = bidAdapter
        adapter = bidAdapter



        viewModel.epics.observe(viewLifecycleOwner, Observer { lists ->
            if(lists.status == Status.ERROR){
                val errorMessage = lists.message
                if (!errorMessage.isNullOrEmpty() && errorMessage == "unAuth"){
                    //跳转登录
                    findNavController().navigate(R.id.action_nav_home_to_login_fragment)
                }else{
                    Snackbar.make(binding.root, lists.message ?: "", Snackbar.LENGTH_SHORT).show()
                }
            }else{
                adapter.submitList(lists?.data)
            }
        })

        viewModel.loadMoreState.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null){
                binding.loadingMore = false
            }else{
                binding.loadingMore = loadingMore.isRunning
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setQueryConditions("Unbidding", 1)
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