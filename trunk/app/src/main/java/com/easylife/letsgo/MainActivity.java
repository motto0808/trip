package com.easylife.letsgo;

import android.app.SearchManager;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.widget.RadioGroup;


import android.support.v7.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener {


    private static final String LOG_TAG = "MainActivity";



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> mPageFragment = new ArrayList<>();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPageFragment.add(StartFragment.newInstance(1));
        mPageFragment.add(ItineraryFragment.newInstance(2));
        mPageFragment.add(MessageFragment.newInstance(3));
        mPageFragment.add(ContactFragment.newInstance(4));

        mSectionsPagerAdapter = new SectionsPagerAdapter(mPageFragment, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        mRadioGroup.setOnCheckedChangeListener(this);

        Log.w(LOG_TAG, "Test Log");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        MenuItem searchViewButton = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView)searchViewButton.getActionView();
        if(searchView != null)
        {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            SearchView.SearchAutoComplete completeView = (SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);

            if(completeView != null)
            {
                completeView.setHintTextColor(getResources().getColor(R.color.colorAccent));
                completeView.setDropDownBackgroundResource(R.drawable.tab_bg);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                break;
            case R.id.action_search:
                break;
                //return super.onOptionsItemSelected(item);
            case R.id.action_settings:
                Toast.makeText(this, "Settings Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_sign_out:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRadioGroup.check(R.id.tab_rb_start);
                break;
            case 1:
                mRadioGroup.check(R.id.tab_rb_itinerary);
                break;
            case 2:
                mRadioGroup.check(R.id.tab_rb_message);
                break;
            case 3:
                mRadioGroup.check(R.id.tab_rb_contact);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.tab_rb_start:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_rb_itinerary:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_rb_message:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab_rb_contact:
                mViewPager.setCurrentItem(3);
                break;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(List<Fragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        private List<Fragment> mFragments;

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fragment_title_start);
                case 1:
                    return getString(R.string.fragment_title_itnerary);
                case 2:
                    return getString(R.string.fragment_title_message);
                case 3:
                    return getString(R.string.fragment_title_contact);
            }
            return null;
        }
    }
}
