package react.freddy.com.ubid.ui.unbid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.UnbidFragmentBinding
import react.freddy.com.ubid.util.InjectorUtils

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

        viewModel.setPageNumberValue(0)

        viewModel.epicsEx.observe(viewLifecycleOwner, Observer { lists ->
            if (lists.data != null){

            }
        })
    }

}