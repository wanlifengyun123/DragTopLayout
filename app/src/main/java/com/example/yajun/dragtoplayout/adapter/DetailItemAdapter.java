package com.example.yajun.dragtoplayout.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.bean.NItem;

import butterknife.Bind;

/**
 * Created by yajun on 2016/6/7.
 */
public class DetailItemAdapter extends ListBaseAdapter<NItem> {

    public DetailItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_news_detail;
    }

    @Override
    public void display(int position, BaseViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        NItem nItem = data.get(position);
        if(!TextUtils.isEmpty(nItem.type)){
            if(nItem.type.equals("img")){
                holder.contentImg.setVisibility(View.VISIBLE);
                holder.content.setVisibility(View.GONE);
                Glide.with(context)
                        .load(nItem.imgSrc)
                        .crossFade()
                        .into(holder.contentImg);
            }else if(nItem.type.equals("p")){
                holder.contentImg.setVisibility(View.GONE);
                holder.content.setVisibility(View.VISIBLE);
                holder.content.setText(nItem.imgTxt);
            }
        }
    }

    @Override
    public BaseViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder {
        @Bind(R.id.content_img)
        ImageView contentImg;
        @Bind(R.id.content)
        TextView content;


        public ViewHolder(View view) {
            super(view);
        }
    }
}
