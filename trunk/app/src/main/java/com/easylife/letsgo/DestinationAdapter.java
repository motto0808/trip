package com.easylife.letsgo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xtgsy on 2015/11/8.
 */

public class DestinationAdapter
    extends RecyclerView.Adapter<DestinationViewHolder>
{
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickListener mItemClickListener;

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public DestinationAdapter(Context context, List<DestinationCard> destinations)
    {
        this.mContext = context;
        this.destinations = destinations;
    }


    private List<DestinationCard> destinations;

    private Context mContext;

    @Override
    public DestinationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.destination_card, viewGroup, false);

        DestinationViewHolder viewHolder = new DestinationViewHolder(v, mItemClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( DestinationViewHolder viewHolder, int i ) {
        DestinationCard dest = destinations.get(i);
        viewHolder.mTextView.setText(dest.name);
        int resid = dest.getImageResourceId(mContext);
        if(resid != -1)
        {
            viewHolder.mImageView.setImageResource(resid);
        }
    }

    @Override
    public int getItemCount()
    {
          return destinations == null ? 0 : destinations.size();
    }
}
