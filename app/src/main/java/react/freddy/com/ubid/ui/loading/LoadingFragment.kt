package react.freddy.com.ubid.ui.loading

import android.animation.Animator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.loading_fragment.view.*
import react.freddy.com.ubid.MainActivity
import react.freddy.com.ubid.R
import timber.log.Timber

class LoadingFragment : Fragment() {

    companion object {
        fun newInstance() = LoadingFragment()
    }

    private lateinit var viewModel: LoadingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.loading_fragment, container, false)
        root.lottie_animation_view.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                Timber.i("onAnimationEnd")
                findNavController().navigate(R.id.action_loadingFragment_to_nav_home)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        } )

        return root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        (requireActivity() as MainActivity).setDrawerLayoutEnable(false)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as MainActivity).setDrawerLayoutEnable(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoadingViewModel::class.java)

    }

}