package com.lgf.common.lib.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by as on 2016/9/7.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> dataList;
    protected Context context;
    protected int resourceId;

    public CommonAdapter(Context context, int resourceId, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        }

        return 0;
    }

    @Override
    public T getItem(int position) {
        if (dataList != null) {
            return dataList.get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.getViewHolder(context, resourceId, convertView, parent);
        T bean = null;
        if (dataList != null) {
            bean = dataList.get(position);
        }
        convert(holder, bean);

        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder commonViewHolder, T data);
}
