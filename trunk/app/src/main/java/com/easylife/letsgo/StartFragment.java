package com.easylife.letsgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment
    implements DestinationAdapter.OnItemClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private int mSectionNum;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param section_number Parameter 1.
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(int section_number) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    private List<DestinationCard> m_destinations = new ArrayList<>();
    private DestinationAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);

        m_destinations.clear();
        m_destinations.add(new DestinationCard("江南", "p1"));
        m_destinations.add(new DestinationCard("水乡", "p2"));
        m_destinations.add(new DestinationCard("西藏", "p3"));
        m_destinations.add(new DestinationCard("海滩", "p4"));
        m_destinations.add(new DestinationCard("青海", "p5"));

        ///----
        // 拿到RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.destination_list);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        mAdapter = new DestinationAdapter(getContext(), m_destinations);
        mAdapter.setOnItemClickListener(this);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(mAdapter);

        final PullToRefreshView pullToRefreshView = (PullToRefreshView)rootView.findViewById(R.id.pull_to_refresh);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(getContext(),com.easylife.letsgo.destination.DestinationActivity.class);
        intent.putExtra(Intent.EXTRA_TITLE, m_destinations.get(position).name);

        getContext().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        String tips = String.format("Click love pic%d.", position);
        Toast.makeText(view.getContext(), tips, Toast.LENGTH_SHORT).show();
    }
}
