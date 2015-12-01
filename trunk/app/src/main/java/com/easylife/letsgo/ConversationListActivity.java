package com.easylife.letsgo;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class ConversationListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    /**
     * 会话TextView
     */
    private TextView mMainConversationTv;
    /**
     * 会话列表的fragment
     */
    private Fragment mConversationFragment = null;
    /**
     * 其他
     */
    private MessageFragmentPagerAdapter mFragmentPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        //显示消息列表
        RongIM.getInstance().enableNewComingMessageIcon(true);
        RongIM.getInstance().enableUnreadMessageIcon(true);

        //FragmentPagerAdapter 接口类
        mFragmentPagerAdapter = new MessageFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {

        switch (i) {
            case 0:
                selectNavSelection(0);
                break;
            case 1:
                selectNavSelection(1);
                break;
            case 2:
                selectNavSelection(2);
                break;
            case 3:
                selectNavSelection(3);
                break;
        }
    }
    private void selectNavSelection(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class MessageFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        public MessageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    mMainConversationTv.setTextColor(getResources().getColor(R.color.de_title_bg));
                    //TODO
                    if (mConversationFragment == null) {
                        ConversationListFragment listFragment = ConversationListFragment.getInstance();
                        listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
                        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                                .appendPath("conversationlist")
                                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
                                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                                .build();
                        listFragment.setUri(uri);
                        fragment = listFragment;
                    } else {
                        fragment = mConversationFragment;
                    }
                    break;
                default:
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

}
