package react.freddy.com.ubid

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.nav_header_main.view.*
import react.freddy.com.ubid.ui.ShareViewModel
import react.freddy.com.ubid.ui.dragger.ZhaiNan
import react.freddy.com.ubid.util.InjectorUtils
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private val shareViewModel: ShareViewModel by viewModels{
        InjectorUtils.provideShareViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Timber.i("navView count = ${navView.childCount}  + navView = ${navView.getHeaderView(0)}")
        val headerIcon: ImageView = navView.getHeaderView(0).findViewById(R.id.header_icon)
        headerIcon.setOnClickListener {
            Timber.i("header icon click")
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.login_fragment)
        }

        navView.setNavigationItemSelectedListener { p0 ->
            p0.isChecked = true
            Snackbar.make(navView, p0.title, Snackbar.LENGTH_SHORT).show()
            true
        }

        subscribeUi()
    }

    private fun subscribeUi(){
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setDrawerLayoutEnable(enable: Boolean){
        val lockMode: Int = if (enable){
            DrawerLayout.LOCK_MODE_UNLOCKED
        }else{
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        }
        drawerLayout.setDrawerLockMode(lockMode)
    }

    fun updateHeaderView(account: String, name: String){
        val headerTitle: TextView = navView.getHeaderView(0).findViewById(R.id.header_title)
        headerTitle.text = account

        val subHeaderTitle: TextView = navView.getHeaderView(0).findViewById(R.id.header_subtitle)
        subHeaderTitle.text = name
    }
}