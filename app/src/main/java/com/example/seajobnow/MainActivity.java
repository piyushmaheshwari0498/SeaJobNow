package com.example.seajobnow;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.seajobnow.actions.ShowSnackbar;
import com.example.seajobnow.databinding.ActivityMainBinding;
import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    boolean doubleBackPressed = false;
    AppSharedPreference appSharedPreference;
    //Create these objects above OnCreate()of your main activity
    TextView menu_post_counts;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);

        appSharedPreference = AppSharedPreference.getAppSharedPreference(this);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_post_job, R.id.nav_candidates, R.id.nav_plans, R.id.nav_post_advertisement)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().getItem(0).setChecked(true);

        //These lines should be added in the OnCreate() of your main activity
        menu_post_counts = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_post_job));

        View headerView = navigationView.getHeaderView(0);
        ImageView logout = headerView.findViewById(R.id.imageView_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

        /*
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         **/
        appSharedPreference.checkLogin(this);
        getSupportActionBar().setSubtitle(appSharedPreference.getString(Constants.INTENT_KEYS.KEY_COMPANY_NAME));
        initializeCountDrawer();
    }

    private void initializeCountDrawer(){
        //Gravity property aligns the text
        menu_post_counts.setGravity(Gravity.CENTER_VERTICAL);
        menu_post_counts.setTypeface(null, Typeface.BOLD);
        menu_post_counts.setTextColor(getResources().getColor(R.color.bule_700));
        menu_post_counts.setText("99+");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        getSupportActionBar().setSubtitle(appSharedPreference.getString(Constants.INTENT_KEYS.KEY_COMPANY_NAME));
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            super.onBackPressed();
//            finish();
        }
        doubleBackPressed = true;
        new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.exit_prompt));

    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.logout_bottom_sheet);

        MaterialButton logout_yes = bottomSheetDialog.findViewById(R.id.btn_logout_yes);
        MaterialButton logout_no = bottomSheetDialog.findViewById(R.id.btn_logout_no);

        bottomSheetDialog.show();

        logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
                appSharedPreference.logoutUser(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        logout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Copy is Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
    }


}