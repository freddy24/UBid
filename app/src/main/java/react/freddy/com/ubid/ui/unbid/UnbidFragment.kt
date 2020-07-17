package react.freddy.com.ubid.ui.unbid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.UnbidFragmentBinding
import react.freddy.com.ubid.util.InjectorUtils
import react.freddy.com.ubid.vo.Status

class UnbidFragment : Fragment() {

    private lateinit var binding: UnbidFragmentBinding

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

        val adapter = BidAdapter()
        binding.bidList.adapter = adapter

        viewModel.setPageNumberValue(0)

        viewModel.epicsEx.observe(viewLifecycleOwner, Observer { lists ->
            if(lists.status == Status.ERROR){
                Snackbar.make(binding.root, lists.message ?: "", Snackbar.LENGTH_SHORT).show()
            }else{
                if (lists.data != null){
                    adapter.submitList(lists.data)
                }
            }
        })
    }

}