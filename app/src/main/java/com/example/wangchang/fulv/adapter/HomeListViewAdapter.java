package com.example.wangchang.fulv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.entity.HomeGirlsEntityParcelable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:首页listview适配器
 * Created by liying on 2018/3/5
 */
public class HomeListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeGirlsEntityParcelable> detailList;
    private String noDataStr;

    public HomeListViewAdapter(Context context, List<HomeGirlsEntityParcelable> list) {
        this.mContext = context;
        this.detailList = list;
        noDataStr = context.getString(R.string.no_data);
    }

    @Override
    public int getCount() {
        return detailList == null ? 0 : detailList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_home_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeGirlsEntityParcelable homeGirlsEntityParcelable = detailList.get(position);
        Glide.with(mContext)
                .load(homeGirlsEntityParcelable.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.app_icon)
                .crossFade()
                .error(R.mipmap.app_icon)
                .into(viewHolder.imageIv);
        viewHolder.homeCardTitleTv.setText("来源："+ homeGirlsEntityParcelable.getSource());
        viewHolder.descriptionTv.setText(homeGirlsEntityParcelable.getCreatedAt()+ "  " + "作者：" + homeGirlsEntityParcelable.getWho());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.home_card_title_tv)
        TextView homeCardTitleTv;
        @BindView(R.id.imageIv)
        ImageView imageIv;
        @BindView(R.id.descriptionTv)
        TextView descriptionTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


