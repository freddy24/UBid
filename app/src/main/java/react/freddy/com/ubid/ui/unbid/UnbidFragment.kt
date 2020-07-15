package react.freddy.com.ubid.ui.unbid

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import react.freddy.com.ubid.R

class UnbidFragment : Fragment() {

    companion object {
        fun newInstance() = UnbidFragment()
    }

    private lateinit var viewModel: UnbidViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.unbid_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UnbidViewModel::class.java)

    }

}