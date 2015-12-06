package com.easylife.letsgo.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.easylife.letsgo.R;
import com.easylife.letsgo.ui.adapter.ConversationListAdapterEx;
import com.easylife.letsgo.ui.fragment.ContactFragment;
import com.easylife.letsgo.ui.fragment.ItineraryFragment;
import com.easylife.letsgo.ui.fragment.StartFragment;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

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


    private String mUserId;
    //Token 用户访问令牌
    private static final String Token = "EQLdcXTrMtUAbzHnx9++mhUiRdf6elHkp9Esc75UM9KbX75gpwHy6iopuZE0A4F4pmLlEL/liKS7HdphT3UgDUOQUB0yTHHCP7AKrlsDJfqx9UzAL66Faw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //服务器建立连接
        /**
         * IMKit SDK调用第二步
         * 建立与服务器的连接
         */
        connectRongServer(Token);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPageFragment.add(StartFragment.newInstance());
        mPageFragment.add(ItineraryFragment.newInstance(2));

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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);


        MenuItem searchViewButton = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchViewButton.getActionView();
        if (searchView != null) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            SearchView.SearchAutoComplete completeView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

            if (completeView != null) {
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
            case R.id.add_new_message: {
                /**
                 * 启动客服聊天界面。
                 *
                 * @param context          应用上下文。
                 * @param conversationType 开启会话类型。
                 * @param targetId         客服 Id。
                 * @param title            客服标题。
                 */
                RongIM.getInstance().startConversation(this, Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU144879668568541", "客服");

                // if (mUserId != null && RongIM.getInstance() != null)
                //此处聊天是写死的 实际开发中 大家应该写成动态的startPrivateChat
                //  RongIM.getInstance().startPrivateChat(MainActivity.this, mUserId.equals("10010") ? "10086" : "10010", mUserId.equals("10010") ? "移动" : "联通");

            }
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

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.nav_itinerary) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.nav_message) {
            mViewPager.setCurrentItem(2);
        } else if (id == R.id.nav_contact) {
            mViewPager.setCurrentItem(3);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    return getString(R.string.fragment_title_itinerary);
                case 2:
                    return getString(R.string.fragment_title_message);
                case 3:
                    return getString(R.string.fragment_title_contact);
            }
            return null;
        }
    }

    /**
     * 连接融云服务器
     *
     * @param token
     */
    private void connectRongServer(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            @Override
            public void onSuccess(String userId) {
                mUserId = userId;
                Toast.makeText(MainActivity.this, "connet server success",
                        Toast.LENGTH_SHORT).show();
                //
                Log.e(LOG_TAG, "connect success userid is :" + userId);

                // 默认设置头像
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo("10010", "曾经故人", Uri.parse("http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"))
                    );
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(LOG_TAG,
                        "connect failure errorCode is :" + errorCode.getValue());
            }

            @Override
            public void onTokenIncorrect() {
                Log.e(LOG_TAG, "token is error , please check token and appkey ");
            }
        });
    }
}
