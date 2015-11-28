package com.easylife.letsgo;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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

    private ConversationListFragment m_conversation_list;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///测试单聊
        //Token 用户访问令牌
		String Token = "EQLdcXTrMtUAbzHnx9++mhUiRdf6elHkp9Esc75UM9KbX75gpwHy6iopuZE0A4F4pmLlEL/liKS7HdphT3UgDUOQUB0yTHHCP7AKrlsDJfqx9UzAL66Faw=="; 

        /**
         * IMKit SDK调用第二步
         * 建立与服务器的连接
         */
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(LOG_TAG, "------onTokenIncorrect----");
            }

            @Override
            public void onSuccess(String userId) {
                Log.e(LOG_TAG, "------onSuccess----" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(LOG_TAG, "------onError----" + errorCode);
            }
        });
        /*----------------------------------------------------------------------------------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPageFragment.add(StartFragment.newInstance());
        mPageFragment.add(ItineraryFragment.newInstance(2));
        //mPageFragment.add(MessageFragment.newInstance(3));

        m_conversation_list = ConversationListFragment.getInstance();
        m_conversation_list.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                .build();
        m_conversation_list.setUri(uri);

        mPageFragment.add(m_conversation_list);
        mPageFragment.add(ContactFragment.newInstance(4));

        mSectionsPagerAdapter = new SectionsPagerAdapter(mPageFragment, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        mRadioGroup.setOnCheckedChangeListener(this);
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
            case R.id.message_NULL_button:
            {
                /**
                 * 启动单聊
                 * context - 应用上下文。
                 * targetUserId - 要与之聊天的用户 Id。
                 * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(MainActivity.this, "2462", "hello");
                }

                return super.onOptionsItemSelected(item);
            }
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
