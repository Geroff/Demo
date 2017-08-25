package com.lgf.common.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.lgf.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geroff on 2017/8/24.
 */

public class FlowLayout extends ViewGroup {
    public static final String TAG = "FlowLayout";
    public static final int DEFAULT_SPACING = 20;
    private int usedWidth;
    private int horizontalSpacing = DEFAULT_SPACING;
    private int verticalSpacing = DEFAULT_SPACING;
    private boolean fillRow;
    private List<Line> lineList = new ArrayList<>();
    private Line line;
    private int gravity = Gravity.NO_GRAVITY;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        if (context == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.flowLayout);
        gravity = typedArray.getInt(R.styleable.flowLayout_android_gravity, Gravity.NO_GRAVITY);
        fillRow = typedArray.getBoolean(R.styleable.flowLayout_fill_row, false);
        horizontalSpacing = typedArray.getDimensionPixelSize(R.styleable.flowLayout_android_horizontalSpacing, DEFAULT_SPACING);
        verticalSpacing = typedArray.getDimensionPixelSize(R.styleable.flowLayout_android_verticalSpacing, DEFAULT_SPACING);
        typedArray.recycle();
    }

    public void setFillRow(boolean fillRow) {
        this.fillRow = fillRow;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        if (this.horizontalSpacing != horizontalSpacing) {
            this.horizontalSpacing = horizontalSpacing;
            requestLayout();
        }
    }

    public void setVerticalSpacing(int verticalSpacing) {
        if (this.verticalSpacing != verticalSpacing) {
            this.verticalSpacing = verticalSpacing;
            requestLayout();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout()");
        int top = getPaddingTop();
        int left = getPaddingLeft();
        int lines = lineList.size();
        for (int i = 0; i < lines; i++) {
            Line line = lineList.get(i);
            line.layout(left, top); // 布局每一行
            top += verticalSpacing + line.height;
        }
    }

    private void restoreLine() {
        lineList.clear();
        line = new Line();
        usedWidth = 0;
    }

    /**
     * 可能被调用多次
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        restoreLine(); // 需要清除数据，因为onMeasure可能被调用不止一次
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int widthSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode); // 生成子view的宽度的测量规格
            int heightSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode); // 生成子view的高度的测量规格
            childView.measure(widthSpec, heightSpec); // 重新测量
            if (line == null) {
                line = new Line();
            }

            int childWidth = childView.getMeasuredWidth();
            usedWidth += childWidth;
            if (usedWidth < widthSize) { // 如果没有大于父View宽度
                line.addView(childView);
                usedWidth += horizontalSpacing;
            } else {  // 如果大于父View宽度
                if (line.getViewCount() == 0) {  // 如果该行没有view则添加子View
                    line.addView(childView);
                } else {  // 如果该行有view则另起一行添加view
                    newLine();
                    line.addView(childView);
                    usedWidth += horizontalSpacing + childWidth;
                }
            }
        }

        // 添加最后一行的
        if (line != null && line.getViewCount() > 0 && !lineList.contains(line)) {
            lineList.add(line);
        }

        int totalHeight = 0;
        int lines = lineList.size();
        for (int i = 0; i < lines; i++) {
            totalHeight += lineList.get(i).height;
        }
        totalHeight += (lines - 1) * verticalSpacing;
        totalHeight += getPaddingBottom() + getPaddingTop();
        int measureHeight = resolveSize(totalHeight, heightMeasureSpec); // 获取测量高度
        setMeasuredDimension(widthSize, measureHeight);
    }

    private boolean newLine() {
        lineList.add(line);
        if (lineList.size() < Integer.MAX_VALUE) {
            line = new Line();
            usedWidth = 0;
            return true;
        }

        return false;
    }

    private class Line {
        public int width; // 该行中所有的子View累加的宽度
        public int height; // 该行中所有的子View中最高的那个子View的高度
        private List<View> viewList; // 存放一行中View

        public Line() {
            viewList = new ArrayList<>();
        }

        public void addView(View child) {
            if (child == null) {
                return;
            }

            viewList.add(child);
            this.width += child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            this.height = this.height < childHeight ? childHeight : this.height; // 高度等于一行中最高的View
        }

        public void layout(int left, int top) {
            int totalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight(); // 行的宽度
            int count = getViewCount();
            int spaceLeft = totalWidth - width - (count - 1) * horizontalSpacing; // 剩余宽度
            int averageWidth = spaceLeft / count;

            for (int i = 0; i < count; i++) {
                View childView = viewList.get(i);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();

                if (fillRow) { // 如果填充剩余宽度
                    childWidth += averageWidth;
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                    childView.measure(widthMeasureSpec, heightMeasureSpec); // 重新测量子View宽高
                }

                if (gravity == Gravity.NO_GRAVITY) { // 如果没有设置对齐
                    childView.layout(left, top, left + childWidth, top + childHeight);
                } else if (gravity == Gravity.CENTER || gravity == Gravity.BOTTOM) {
                    int topOffset;
                    if (gravity == Gravity.BOTTOM) { // 如果设置底部对齐
                        topOffset = height - childHeight;
                    } else { // 如果设置中心对齐
                        topOffset = (height - childHeight) / 2;
                    }
                    childView.layout(left, top + topOffset, left + childWidth, top + topOffset + childHeight); // 布局子View
                }
                left += childWidth + horizontalSpacing;
            }
        }

        public int getViewCount() { // 一行的子View个数
            return viewList.size();
        }
    }

}
