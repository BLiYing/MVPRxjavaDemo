package com.example.wangchang.fulv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.entity.PlanEntity;
import com.example.wangchang.fulv.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:首页listview适配器
 * Created by liying on 2018/3/5
 */
public class PlanListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<PlanEntity> planList;

    public PlanListViewAdapter(Context context, List<PlanEntity> list) {
        this.mContext = context;
        this.planList = list;
    }

    @Override
    public int getCount() {
        return planList == null ? 0 : planList.size();
    }

    @Override
    public Object getItem(int position) {
        return planList == null ? null : planList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.fragment_plan_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       /* HomeGirlsEntityParcelable homeGirlsEntityParcelable = planList.get(position);
        Glide.with(mContext)
                .load(homeGirlsEntityParcelable.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.app_icon)
                .crossFade()
                .error(R.mipmap.app_icon)
                .into(viewHolder.imageIv);
        viewHolder.homeCardTitleTv.setText("来源："+ homeGirlsEntityParcelable.getSource());
        viewHolder.descriptionTv.setText(homeGirlsEntityParcelable.getCreatedAt()+ "  " + "作者：" + homeGirlsEntityParcelable.getWho());*/
        return convertView;
    }


    static class ViewHolder {

        @BindView(R.id.plantitleTv)
        TextView plantitleTv;
        @BindView(R.id.planunitTv)
        TextView planunitTv;
        @BindView(R.id.orderdateTv)
        TextView orderdateTv;
        @BindView(R.id.orderusedateTv)
        TextView orderusedateTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


