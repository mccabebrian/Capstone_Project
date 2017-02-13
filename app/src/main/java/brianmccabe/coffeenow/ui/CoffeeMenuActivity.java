package brianmccabe.coffeenow.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import brianmccabe.coffeenow.R;

public class CoffeeMenuActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String shopName = getIntent().getStringExtra(CafeListFragment.SHOP_NAME_KEY);
        toolbar.setTitle(shopName);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_menu));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_favorites));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        initFragments();
    }

    private void initFragments() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MenuPagerAdapter adapter = new MenuPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
