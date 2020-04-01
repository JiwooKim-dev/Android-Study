package com.example.lab5e_tab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment[] arrFragments = new Fragment[3];
        arrFragments[0] = new RedFragment();
        arrFragments[1] = new YellowFragment();
        arrFragments[2] = new GreenFragment();

        /* TabLayout */
        TabLayout tabLayout = findViewById(R.id.tl_tabs);

        /* ViewPager  */
        FragmentPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragments);
        ViewPager viewPager = findViewById(R.id.vp_pager);
        viewPager.setAdapter(adapter);

        /* TabLayout - ViewPager 연결 */
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragments;

        /* ViewPager */
        public MyPagerAdapter(@NonNull FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }

        /* TabLayout */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch(position){

                case 0:
                    return "RED";

                case 1:
                    return "YELLOW";

                case 2:
                    return "GREEN";

                default:
                    return "";
            }
        }
    }
}
