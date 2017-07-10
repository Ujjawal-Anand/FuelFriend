package io.uscool.fuelfriend.activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import io.uscool.fuelfriend.R;
import io.uscool.fuelfriend.fragment.BaseSearchFragment;
import io.uscool.fuelfriend.fragment.SearchViewFragment;
import io.uscool.fuelfriend.service.DownloadResultReceiver;
import io.uscool.fuelfriend.service.DownloadService;


public class MainActivity extends AppCompatActivity
        implements BaseSearchFragment.BaseSearchFragmentCallbacks, NavigationView.OnNavigationItemSelectedListener, DownloadResultReceiver.Receiver {


//    private final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private DownloadResultReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startDownloadService();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(new SearchViewFragment());

    }
    private void startDownloadService() {
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }

    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {
        searchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
//            Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManagerActivity.class);
//            startActivity(dbmanager);
        } else if (id == R.id.nav_gallery) {
            Intent serviceActivity = new Intent(getApplicationContext(), DownloadTestActivity.class);
            startActivity(serviceActivity);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
//                Toast.makeText(this, "Service Completed Successfully",
//                        Toast.LENGTH_SHORT).show();

                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
