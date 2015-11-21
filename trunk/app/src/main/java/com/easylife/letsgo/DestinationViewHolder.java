package com.easylife.letsgo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xtgsy on 2015/11/17.
 */
public class DestinationViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener
{
    public CardView mCardView;
    public TextView mDestTitle;
    public TextView mDestDesc;
    public ImageView mImageView;
    public Button mBtnLove;
    public Button mBtnMore;

    private DestinationAdapter.OnItemClickListener mListener;

    public DestinationViewHolder( View v, DestinationAdapter.OnItemClickListener listener)
    {
        super(v);
        mCardView = (CardView) v.findViewById(R.id.card_view);
        mDestTitle = (TextView) v.findViewById(R.id.dest_title);
        mDestDesc = (TextView) v.findViewById(R.id.dest_desc);

        mImageView = (ImageView) v.findViewById(R.id.dest_pic);
        mBtnLove = (Button) v.findViewById(R.id.btn_love);
        mBtnMore = (Button) v.findViewById(R.id.btn_more);

        mListener = listener;

        //v.setOnClickListener(this);
        //v.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(mListener != null)
        {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(mListener != null)
        {
            mListener.onItemLongClick(v, getLayoutPosition());
        }
        return false;
    }
}
