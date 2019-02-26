package com.example.doordashlite.home;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;

import com.example.doordashlite.R;
import com.example.doordashlite.restaurants.RestaurantsFragment;
import com.example.doordashlite.util.ActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity {

    private SparseArray<Fragment> fragments = new SparseArray<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    RestaurantsFragment restaurantsFragment = getFragment(item.getItemId(), RestaurantsFragment.class);
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), restaurantsFragment, R.id.contentFrame);
                    return true;
                case R.id.navigation_orders:
                    // TODO: replace with orders frame ...
                    return true;
                case R.id.navigation_account:
                    // TODO: replace with account frame ...
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private <T extends Fragment> T getFragment(@IdRes int id, Class<? extends Fragment> fragmentClass) {
        Fragment fragment = fragments.get(id);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            fragments.put(id, fragment);
        }
        return (T) fragment;
    }
}
