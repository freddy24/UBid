package react.freddy.com.ubid.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import react.freddy.com.ubid.ui.gallery.GalleryFragment
import react.freddy.com.ubid.ui.slideshow.SlideshowFragment
import java.lang.IndexOutOfBoundsException

/**
 * data :2020/7/15
 * auth :wjp
 * Description :
 */
const val HOME_PAGE_INDEX_FIRST = 0
const val HOME_PAGE_INDEX_SECOND = 1

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val tabFragmentsCreators: Map<Int, Fragment> = mapOf(
        HOME_PAGE_INDEX_FIRST to GalleryFragment(),
        HOME_PAGE_INDEX_SECOND to SlideshowFragment()
    )

    override fun getItemCount(): Int {
        return tabFragmentsCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position] ?: throw IndexOutOfBoundsException()
    }

}