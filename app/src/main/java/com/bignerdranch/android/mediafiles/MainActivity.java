package com.bignerdranch.android.mediafiles;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.bignerdranch.android.mediafiles.app.permission.PermissionCallback;
import com.bignerdranch.android.mediafiles.app.permission.PermissionsManager;
import com.bignerdranch.android.mediafiles.dagger.ActivityComponent;
import com.bignerdranch.android.mediafiles.discovery.Discovery;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;
import javax.inject.Provider;

public class MainActivity extends BeanAwareActivity {
    @Inject protected PermissionsManager permissionsManager;
    @Inject protected Provider<Discovery> discovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    protected void injectThis(@NonNull ActivityComponent component) {
        component.inject(this);
    }

    private void requestPermission() {
        permissionsManager.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                () -> discovery.get().initAsync(), null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}
