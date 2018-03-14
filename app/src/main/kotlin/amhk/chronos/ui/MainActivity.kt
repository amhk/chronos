package amhk.chronos.ui

import amhk.chronos.R
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import androidx.view.forEach

internal interface Navigator {
    fun goForward(newFragment: Fragment)
    fun goBackward()
    fun goReplace(newFragment: Fragment)
}

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.main_navigation)
        navigationView.setNavigationItemSelectedListener {
            navigationView.menu.forEach {
                item -> item.isChecked = it.title == item.title
            }

            drawer.closeDrawer(navigationView, true)
            when (it.title) {
                "Block list" -> goReplace(BlockListFragment.newInstance())
                "bar" -> goReplace(BarFragment.newInstance())
                "bar 2" -> goReplace(BarFragment.newInstance())
            }
            true
        }

        if (savedInstanceState == null) {
            goReplace(BlockListFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        goBackward()
    }

    override fun goForward(newFragment: Fragment) =
            supportFragmentManager.goForward(R.id.main_container, newFragment)

    override fun goBackward() =
            supportFragmentManager.goBackward(this)

    override fun goReplace(newFragment: Fragment) =
            supportFragmentManager.goReplace(R.id.main_container, newFragment)

}

private fun FragmentManager.goForward(@IdRes containerId: Int, newFragment: Fragment) {
    beginTransaction()
            .addToBackStack(null)
            .replace(containerId, newFragment)
            .commit()
}

private fun FragmentManager.goBackward(activity: AppCompatActivity) {
    if (backStackEntryCount > 0) {
        popBackStack()
    } else {
        activity.finish()
    }
}

private fun FragmentManager.goReplace(@IdRes containerId: Int, newFragment: Fragment) {
    while (backStackEntryCount > 0) {
        popBackStackImmediate()
    }
    beginTransaction()
            .replace(containerId, newFragment)
            .commit()
}