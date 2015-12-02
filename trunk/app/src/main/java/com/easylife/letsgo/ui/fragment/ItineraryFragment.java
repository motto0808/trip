package com.easylife.letsgo.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easylife.letsgo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItineraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryFragment extends Fragment {
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
     * @return A new instance of fragment ItineraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItineraryFragment newInstance(int section_number) {
        ItineraryFragment fragment = new ItineraryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section_number);

        fragment.setArguments(args);
        return fragment;
    }

    public ItineraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNum = getArguments().getInt(ARG_SECTION_NUMBER);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_itinerary, container, false);
    }


}
