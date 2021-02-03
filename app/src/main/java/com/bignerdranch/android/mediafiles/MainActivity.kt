package com.bignerdranch.android.mediafiles

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bignerdranch.android.mediafiles.app.permission.PermissionCallback
import com.bignerdranch.android.mediafiles.app.permission.PermissionsManager
import com.bignerdranch.android.mediafiles.dagger.ActivityComponent
import com.bignerdranch.android.mediafiles.discovery.Discovery
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : BeanAwareActivity() {
    @Inject protected lateinit var permissionsManager: PermissionsManager
    @Inject protected lateinit var discovery: Provider<Discovery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //requestPermission();
        requestPermission()

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun injectThis(component: ActivityComponent) {
        component.inject(this)
    }

    private fun requestPermission() {
        permissionsManager.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                { discovery.get().initAsync() }, null);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager!!.onRequestPermissionResult(requestCode, permissions, grantResults)
    }
}