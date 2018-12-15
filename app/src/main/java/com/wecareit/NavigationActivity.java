package com.wecareit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wecareit.common.Global;
import com.wecareit.fragments.attendance.AttendanceFragment;
import com.wecareit.fragments.document.DocumentFragment;
import com.wecareit.fragments.drivinglog.DrivingLogFragment;
import com.wecareit.fragments.events.EventsFragment;
import com.wecareit.fragments.information.InformationFragment;
import com.wecareit.fragments.meeting.AgendaFragment;
import com.wecareit.fragments.meeting.MeetingsFragment;
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.fragments.notes.NotesFragment;
import com.wecareit.fragments.routine.RoutineFragment;
import com.wecareit.fragments.start.StartFragment;
import com.wecareit.fragments.tasks.TasksFragment;
import com.wecareit.model.Area;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.LogoutResponse;
import com.wecareit.model.Main_Category;
import com.wecareit.model.Major_Keyword;
import com.wecareit.model.NewsResponse;
import com.wecareit.model.Spinners;
import com.wecareit.model.User;
import com.wecareit.model.Vehicle;
import com.wecareit.model.Vehicles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_LAST_FRAGMENT = "last_fragment";
    public static String TAG = "NavigationActivity";

    public Fragment mContentFragment = null;

    private FloatingActionButton mFloatingButton;
    private FloatingActionButton mMonthButton;

    private ArrayList<Client> client;
    private ArrayList<AuthorRes> users;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Area> areas;
    private ArrayList<Main_Category> main_categories;
    private ArrayList<Major_Keyword> major_keywords;
    private DrawerLayout lnMain;
    //private ArrayList<Spinners> spiners_array;
    //private ArrayList<Major_Keyword> major_keywords;
    /*private ArrayList<Major_Keyword> major_keywords;
    private ArrayList<Major_Keyword> major_keywords;*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getLists();

        Global.toolbar = (Toolbar) findViewById(R.id.toolbar);
        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        setSupportActionBar(Global.toolbar);

        Global.floatingButton = (FloatingActionButton) findViewById(R.id.fab);
        Global.monthlyButton = (FloatingActionButton) findViewById(R.id.month);
        Global.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Global.fragmentManager = this.getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, Global.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvUsername = (TextView) headerView.findViewById(R.id.nav_header_profile_username);
        tvUsername.setText(Global.user.getUsername());

        TextView tvTitle = (TextView) headerView.findViewById(R.id.nav_header_profile_title);
        tvTitle.setText(Global.user.getTitle());

        ImageView ivUser = (ImageView) headerView.findViewById(R.id.nav_header_profile_image);
        if (Global.user.getAvatar() != null) {
            Picasso.with(this).load(Global.user.getAvatar()).fit().into(ivUser);
        } else {
            ivUser.setImageDrawable(this.getDrawable(R.drawable.ic_user_template));
        }
        navigationView.setNavigationItemSelectedListener(this);

        /*lnMain = (DrawerLayout) findViewById(R.id.drawer_layout);
        lnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(NavigationActivity.this);
            }
        });*/

        ImageView ivLogo = (ImageView) headerView.findViewById(R.id.nav_header_logo_image);
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                //Global.fragmentManager = this.getSupportFragmentManager();
                drawer.closeDrawer(Gravity.LEFT);
                Global.startFragment = StartFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.startFragment)
                        .addToBackStack(null)
                        .commit();
                Global.floatingButton.setVisibility(View.GONE);
            }
        });


        Global.startFragment = StartFragment.createInstance();
        Global.fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Global.startFragment)
                .addToBackStack(null)
                .commit();
        Global.floatingButton.setVisibility(View.GONE);

        //initData();
    }

    /*public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            if (activity.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity
                        .INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = this.getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        String mFragment = sharedPreferences.getString("Current", "");
        if (mFragment.equals("Information")) {
            mContentFragment = InformationFragment.createInstance();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Current", "");
            editor.apply();
        }
        if (mContentFragment == null) {
            mContentFragment = StartFragment.createInstance();
        }
        // no backstack here
        Global.fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mContentFragment)
                .commit();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        mContentFragment = Global.fragmentManager.getFragment(savedInstanceState, KEY_LAST_FRAGMENT);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mContentFragment != null && mContentFragment.isAdded() && getFragmentManager() != null) {
            Global.fragmentManager.putFragment(savedInstanceState,
                    KEY_LAST_FRAGMENT, mContentFragment);
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {

        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        if (id == R.id.nav_news) {
            Global.newsFragment = NewsFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.newsFragment)
                    .addToBackStack(null)
                    .commit();
            Global.floatingButton.setVisibility(View.GONE);
            Global.monthlyButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_notes) {
            Global.notesFragment = NotesFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.notesFragment)
                    .addToBackStack(null)
                    .commit();
            Global.floatingButton.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_info) {
            Global.informationFragment = InformationFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.informationFragment)
                    .addToBackStack(null)
                    .commit();
            Global.floatingButton.setVisibility(View.GONE);
            Global.monthlyButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_weekly_schedule) {
            Global.eventsFragment = EventsFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.eventsFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.VISIBLE);
        } else if (id ==  R.id.nav_attendance) {
            Global.attendanceFragment = AttendanceFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.attendanceFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_driving_journal) {
            Global.drivingLogFragment = DrivingLogFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.drivingLogFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_meeting_protocol) {
            boolean b=!menu.findItem(R.id.nav_meeting_agenda).isVisible();
            //setting submenus visible state
            menu.findItem(R.id.nav_meeting_agenda).setVisible(b);
            menu.findItem(R.id.nav_meeting_meetings).setVisible(b);
            return true;
        } else if (id == R.id.nav_meeting_agenda) {
            Global.agendaFragment = AgendaFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.agendaFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_meeting_meetings) {
            Global.meetingsFragment = MeetingsFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.meetingsFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_task) {
            Global.tasksFragment = TasksFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.tasksFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_route_schedule) {
            Global.routineFragment = RoutineFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.routineFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_document) {
            Global.documentFragment = DocumentFragment.createInstance();
            Global.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Global.documentFragment)
                    .addToBackStack(null)
                    .commit();
            Global.monthlyButton.setVisibility(View.GONE);
            Global.floatingButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_logout) {
            Call<LogoutResponse> apiCall = Global.getAPIService.doLogout();
            apiCall.enqueue(new Callback<LogoutResponse>() {

                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                    if (response.isSuccessful()) {
                        String detail = response.body().getDetail();
                        Log.d(TAG, detail);
                        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                        NavigationActivity.this.startActivity(intent);
                        NavigationActivity.this.finish();
                    }
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    Toast.makeText(NavigationActivity.this, "Ange ett korrekt användarnamn och lösenord. Observera att båda fälten är skiftlägeskänsliga.", Toast.LENGTH_LONG).show();
                }
            });
        }

        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getLists(){

        Global.clients = new ArrayList<Client>();
        Global.clientslist = new ArrayList<String>();
        Call<ArrayList<Client>> call = Global.getAPIService.readClients("Token " + Global.token);

        call.enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                if (response.isSuccessful()) {
                    client = response.body();
                    Collections.sort(client, new Comparator<Client>() {
                        @Override
                        public int compare(Client a, Client b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (Client client : client) {

                        Global.clientslist.add(client.getName());
                        Global.clients.add(client);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
            }
        });

        Global.users = new ArrayList<AuthorRes>();
        Call<ArrayList<AuthorRes>> call1 = Global.getAPIService.readUsers("Token " + Global.token);

        call1.enqueue(new Callback<ArrayList<AuthorRes>>() {
            @Override
            public void onResponse(Call<ArrayList<AuthorRes>> call, Response<ArrayList<AuthorRes>> response) {
                if (response.isSuccessful()) {
                    //ArrayList<Spinners> spinArray = new ArrayList<>();
                    users = response.body();
                    for (AuthorRes user : users) {

                        Global.userslist.add(user.getName());
                        Spinners spinners = new Spinners();
                        spinners.setTitle(user.getName());
                        spinners.setSelected(false);
                        Global.testSpinArray.add(spinners);
                        Global.users.add(user);

                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AuthorRes>> call, Throwable t) {
            }
        });

        Global.vehicles = new ArrayList<Vehicle>();
        Call<ArrayList<Vehicle>> call2 = Global.getAPIService.readVehicles("Token " + Global.token);

        call2.enqueue(new Callback<ArrayList<Vehicle>>() {
            @Override
            public void onResponse(Call<ArrayList<Vehicle>> call, Response<ArrayList<Vehicle>> response) {
                if (response.isSuccessful()) {
                    vehicles = response.body();
                    Collections.sort(vehicles, new Comparator<Vehicle>() {
                        @Override
                        public int compare(Vehicle a, Vehicle b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (Vehicle vehicle : vehicles) {

                        Global.vehicleslist.add(vehicle.getName());
                        Global.vehiclesIDlist.add(vehicle.getId());
                        Global.vehicles.add(vehicle);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Vehicle>> call, Throwable t) {
            }
        });

        Call<ArrayList<Client>> call_drivingcategory = Global.getAPIService.readDrivingCategories("Token " + Global.token);

        call_drivingcategory.enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                if (response.isSuccessful()) {
                    client = response.body();

                    for (Client client : client) {

                        Global.drivingcategories.add(client.getName());
                        Global.drivingcategoriesID.add(client.getId());

                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
            }
        });

        Call<ArrayList<Area>> call3 = Global.getAPIService.readAreas("Token " + Global.token);
        call3.enqueue(new Callback<ArrayList<Area>>() {
            @Override
            public void onResponse(Call<ArrayList<Area>> call, Response<ArrayList<Area>> response) {
                if (response.isSuccessful()) {
                    areas = response.body();
                    Collections.sort(areas, new Comparator<Area>() {
                        @Override
                        public int compare(Area a, Area b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (Area area : areas) {
                        Global.areaslist.add(area.getTitle());

                    }
                } else {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Area>> call, Throwable t) {
            }
        });

        Call<ArrayList<Main_Category>> call_maincategory = Global.getAPIService.readMainCategories("Token " + Global.token);
        call_maincategory.enqueue(new Callback<ArrayList<Main_Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Main_Category>> call, Response<ArrayList<Main_Category>> response) {
                if (response.isSuccessful()) {
                    main_categories = response.body();
                    Collections.sort(main_categories, new Comparator<Main_Category>() {
                        @Override
                        public int compare(Main_Category a, Main_Category b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (Main_Category area : main_categories) {

                        Global.main_categorieslist.add(area.getTitle());

                    }
                } else {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Main_Category>> call, Throwable t) {
            }
        });

        Call<ArrayList<Major_Keyword>> call_majorkeyword = Global.getAPIService.readMajorKeywords("Token " + Global.token);
        call_majorkeyword.enqueue(new Callback<ArrayList<Major_Keyword>>() {
            @Override
            public void onResponse(Call<ArrayList<Major_Keyword>> call, Response<ArrayList<Major_Keyword>> response) {
                if (response.isSuccessful()) {
                    major_keywords = response.body();
                    Collections.sort(major_keywords, new Comparator<Major_Keyword>() {
                        @Override
                        public int compare(Major_Keyword a, Major_Keyword b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (Major_Keyword major_keyword : major_keywords) {
                        Global.major_keywordslist.add(major_keyword.getTitle());
                    }
                } else {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Major_Keyword>> call, Throwable t) {
            }
        });
    }
}
