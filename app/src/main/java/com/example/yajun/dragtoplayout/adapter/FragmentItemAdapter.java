package com.example.yajun.dragtoplayout.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.bean.News;

import butterknife.Bind;

/**
 * Created by yajun on 2016/6/7.
 *
 */
public class FragmentItemAdapter extends ListBaseAdapter<News> {

    public FragmentItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_fragment_list;
    }

    @Override
    public void display(int position, BaseViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        News news = data.get(position);
        if (news.type == 1) {
            holder.customItem.setVisibility(View.VISIBLE);
            holder.customItemPhoto.setVisibility(View.GONE);
            holder.shopName.setText(news.title);
            holder.shopInfo.setText(news.content);
            holder.shopFlag.setText(news.flag);
            holder.shopCount.setText("阅读数:" + news.count);
            Glide.with(context)
                    .load(news.thumb)
                    .centerCrop()
                    .crossFade()
                    .into(holder.imgIcon);
        } else {
            holder.customItem.setVisibility(View.GONE);
            holder.customItemPhoto.setVisibility(View.VISIBLE);
            holder.shopNamePhoto.setText(news.title);
            holder.shopInfoPhoto.setText(news.content);
            holder.shopFlagPhoto.setText("图");
            holder.shopCountPhoto.setText("阅读数:" + news.count);
            Glide.with(context)
                    .load(news.thumb)
                    .centerCrop()
                    .crossFade()
                    .into(holder.imgPhoto);
        }

    }

    @Override
    public BaseViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder {
        @Bind(R.id.img_icon)
        AppCompatImageView imgIcon;
        @Bind(R.id.shop_name)
        TextView shopName;
        @Bind(R.id.shop_info)
        TextView shopInfo;
        @Bind(R.id.custom_item)
        LinearLayout customItem;
        @Bind(R.id.shop_flag)
        TextView shopFlag;
        @Bind(R.id.shop_count)
        TextView shopCount;

        @Bind(R.id.shop_name_photo)
        TextView shopNamePhoto;
        @Bind(R.id.img_photo)
        AppCompatImageView imgPhoto;
        @Bind(R.id.shop_info_photo)
        TextView shopInfoPhoto;
        @Bind(R.id.custom_item_photo)
        LinearLayout customItemPhoto;
        @Bind(R.id.shop_flag_photo)
        TextView shopFlagPhoto;
        @Bind(R.id.shop_count_photo)
        TextView shopCountPhoto;



        public ViewHolder(View view) {
            super(view);
        }
    }
}
