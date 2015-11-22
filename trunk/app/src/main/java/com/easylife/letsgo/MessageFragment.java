package com.easylife.letsgo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
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
    public static MessageFragment newInstance(int section_number) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section_number);

        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        //添加背景图片
        ImageView imageView = (ImageView) rootView.findViewById(R.id.message_black_imageView);
        imageView.invalidate();

        //添加一个文字说明
        TextView textView = (TextView) rootView.findViewById(R.id.message_NULL_textView);
        textView.setText(getString(R.string.message_empty));
        textView.setGravity(Gravity.CENTER);

        //添加发现探索
        Button mbutton = (Button) rootView.findViewById(R.id.message_NULL_button);
        mbutton.setText(getString(R.string.message_btn_empty));
        
        /* 新建一个Intent对象 */
        //Intent intent = new Intent();
        /* 指定intent要启动的类 */
        //intent.setClass(this.getActivity(), MessageMainActivity.class);
        /* 启动一个新的Activity */
        //startActivity(intent);
        /* 关闭当前的Activity */
        //this.getActivity().finish();
        return rootView;
    }
}
