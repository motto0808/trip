package com.easylife.letsgo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xtgsy on 2015/11/17.
 */
public class DestinationViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener
{
    public TextView mTextView;

    public ImageView mImageView;

    public ImageView mImageViewLove;

    private DestinationAdapter.OnItemClickListener mListener;

    public DestinationViewHolder( View v, DestinationAdapter.OnItemClickListener listener)
    {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.name);
        mImageView = (ImageView) v.findViewById(R.id.pic);
        mImageViewLove = (ImageView) v.findViewById(R.id.pic_love);

        mListener = listener;

        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
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
