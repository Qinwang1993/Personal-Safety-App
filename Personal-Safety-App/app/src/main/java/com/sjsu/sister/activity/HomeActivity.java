package com.sjsu.sister.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.navigation.NavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sjsu.sister.R;
import com.sjsu.sister.adapter.MyPagerAdapter;
import com.sjsu.sister.entity.TabEntity;
import com.sjsu.sister.fragment.DiscoverFragment;
import com.sjsu.sister.fragment.HomeFragment;
import com.sjsu.sister.fragment.MapsFragment;
import com.sjsu.sister.fragment.UserFragment;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.UniversalImagerLoader;

import java.util.ArrayList;

//public class HomeActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener{
public class HomeActivity extends BaseActivity {
    private static final String TAG ="sister" ;
    private static final int REQUEST_CODE = 9;
    private String mEmail;

//    private String latitude;
//    private String longitud;


    private String[] mTitles = { "Home","Map","Discover" };
    private int[] mIconUnselectIds = { R.mipmap.home_unselect, R.mipmap.map_unselect,  R.mipmap.discover_unselect};
    private int[] mIconSelectIds = { R.mipmap.home_selected,R.mipmap.map_selected, R.mipmap.discover_selected};

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;
    private DatabaseHelper databaseHelper;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    private HomeFragment myHomeFragment;
    private MapsFragment myMapsFragment;
    private UserFragment myUserFragment;
    private DiscoverFragment myDiscoverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initImageLoader();

        // Receive Data
        mEmail = getIntent().getStringExtra("Email");
        if(mEmail != null){
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            User mUser = databaseHelper.userOfEmail(mEmail);
            CustomToast.pop(this, "Hi," + mUser.getName() + ",welcome to Sister!", Toast.LENGTH_LONG).show();

        }else{
            SharedPreferences sharedPreferences= getSharedPreferences("Sister", Context.MODE_PRIVATE);
            mEmail =  sharedPreferences.getString("Email","");
        }

        // HomeActivity bottom TabLayout
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
        myHomeFragment = HomeFragment.newInstance(mEmail);
        myMapsFragment = MapsFragment.newInstance();
        //myUserFragment = UserFragment.newInstance();
        myDiscoverFragment = DiscoverFragment.newInstance();
        mFragments.add(myHomeFragment);
        mFragments.add(myMapsFragment);
        //mFragments.add(myUserFragment);
        mFragments.add(myDiscoverFragment);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }

    public void saveSharedPreferences(String email){
        SharedPreferences sp = getSharedPreferences("Sister", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Email",mEmail);
        editor.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        myMapsFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initImageLoader(){
        UniversalImagerLoader universalImagerLoader = new UniversalImagerLoader(HomeActivity.this);
        ImageLoader.getInstance().init(universalImagerLoader.getConfig());
    }
}