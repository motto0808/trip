package com.easylife.letsgo;

import android.content.Context;
import android.graphics.BitmapFactory;
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
    extends RecyclerView.Adapter<DestinationAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ImageView mImageView;

        public ViewHolder( View v )
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
            mImageView = (ImageView) v.findViewById(R.id.pic);
        }
    }

    public DestinationAdapter(Context context, List<DestinationCard> destinations)
    {
        this.mContext = context;
        this.destinations = destinations;
    }


    private List<DestinationCard> destinations;

    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.destination_card, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i ) {
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
