package com.lgf.common.lib.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by as on 2016/9/7.
 */
public class CommonViewHolder {
    private SparseArray<View> viewSparseArray;
    private View convertView;

    private CommonViewHolder(Context context, int resId, ViewGroup parent) {
        viewSparseArray = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(resId, parent, false);
        convertView.setTag(this);
    }

    public static CommonViewHolder getViewHolder(Context context, int resId, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new CommonViewHolder(context, resId, parent);
        } else {
            return  (CommonViewHolder) convertView.getTag();
        }
    }

    public <T extends View> View getView(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

}
