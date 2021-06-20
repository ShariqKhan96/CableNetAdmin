package app.cabill.admin.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityDashboardBinding
import app.cabill.admin.interfaces.NavigationListener
import app.cabill.admin.view.ui.bill.BillFragment
import app.cabill.admin.view.ui.packages.PackagesFragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity(), NavigationListener {

    lateinit var activityDashboardBinding: ActivityDashboardBinding
    lateinit var navView: BottomNavigationView

    companion object {
        lateinit var navigation_listener: NavigationListener
    }
    init {
        navigation_listener = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(activityDashboardBinding.root)
        navView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
                R.id.navigation_packages, R.id.navigation_profile
            )
        )
        // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.e("destination", destination.label.toString())
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onNavigate(index: Int) {
        if (index == 1) {
            navView.selectedItemId = R.id.navigation_notifications
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, BillFragment())
        } else if (index == 2) {
            navView.selectedItemId = R.id.navigation_packages
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, PackagesFragment())
        }else if(index == 4){

        }

    }
}