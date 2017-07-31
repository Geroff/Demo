package com.example.lgf.demo.utils;

import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TabStopSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * 在Android.text.style包下，有一些Span类，可以提供我们完成一些在TextView中的特殊内容。（比如：部分内容颜色、字体、大小不同等等，更有部分字体可点击。）<br/>
 * SpannableStringBuilder，可以帮助我们设置Span。<br/>
 * <b>设置Span</b><br/>
 * SpannableStringBuilder.setSpan(Object what, int start, int end, int flags)<br/>
 * <b>这里的Flag表示：start和end是开区间还是闭区间。</b><br/>
 * Flag:<br/>
 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE —— (a,b)<br/>
 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE —— (a,b]<br/>
 * Spanned.SPAN_INCLUSIVE_EXCLUSIVE —— [a,b)<br/>
 * Spanned.SPAN_INCLUSIVE_INCLUSIVE —— [a,b]<br/>
 *
 * <b>备注：</b> </br>
 * 各种span如果要同时显示，需要使用同一个SpannableStringBuilder</br>
 * 见方法：getForegroundAndBackgroundColorSpan</br>
 */
public class SpanUtils {

    /**
     * URLSpan<br/>
     * 功能：点击文字，可以打开一个URL。<br/>
     */
    public static SpannableStringBuilder getURLSpan(String content, String url, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new URLSpan(url), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * UnderlineSpan<br/>
     * 功能：设置文字下划线。<br/>
     */
    public static SpannableStringBuilder getUnderlineSpan(String content, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new UnderlineSpan(), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * TypefaceSpan<br/>
     * 功能：设置文字字体。<br/>
     */
    public static SpannableStringBuilder getTypefaceSpan(String content, String typeface, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new TypefaceSpan(typeface), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * TextAppearanceSpan<br/>
     * 功能：设置文字字体、文字样式（粗体、斜体、等等）、文字颜色状态、文字下划线颜色状态等等。<br/>
     * <b>TextAppearanceSpan的三个构造方法</b> <br/>
     * TextAppearanceSpan(Context context, int appearance) <br/>
     * TextAppearanceSpan(Context context, int appearance, int colorList) <br/>
     * TextAppearanceSpan(String family, int style, int size,ColorStateList color, ColorStateList linkColor) <br/>
     * family: <br/>
     * monospace <br/>
     * serif <br/>
     * sans-serif <br/>
     * style: <br/>
     * Typeface.NORMAL <br/>
     * Typeface.BOLD <br/>
     * Typeface.ITALIC <br/>
     * Typeface.BOLD_ITALIC <br/>
     * size:表示字体大小（单位px） <br/>
     */
    public static SpannableStringBuilder getTextAppearanceSpan(String content, String typeface, int style, int size, ColorStateList color, ColorStateList linkColor, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new TextAppearanceSpan(typeface, style, size, color, linkColor), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * TabStopSpan.Standard <br/>
     * 功能：每行的MarginLeft的偏移量（跟 \t 和 \n 有关系，没有\t和\n无效）。<br/>
     */
    public static SpannableStringBuilder getTabStopSpan(String content, int where, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new TabStopSpan.Standard(where), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * SuperscriptSpan <br/>
     * 功能：文字设置为上标，数学公式中用到。<br/>
     */
    public static SpannableStringBuilder getSuperscriptSpan(String content, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * SubscriptSpan <br/>
     * 功能：文字设置为下标，化学式中用到。<br/>
     */
    public static SpannableStringBuilder getSubscriptSpan(String content, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new SubscriptSpan(), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * StrikethroughSpan <br/>
     * 功能：文字设置删除线。<br/>
     */
    public static SpannableStringBuilder getStrikethroughSpan(String content, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * ScaleXSpan <br/>
     * 功能：文字横向缩放。<br/>
     */
    public static SpannableStringBuilder getScaleXSpan(String content, float proportion, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new ScaleXSpan(proportion), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * ForegroundColorSpan <br/>
     * 功能：设置文字颜色。<br/>
     */
    public static SpannableStringBuilder getForegroundColorSpan(String content, int color, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * BackgroundColorSpan <br/>
     * 功能：背景色。<br/>
     */
    public static SpannableStringBuilder getBackgroundColorSpan(String content, int color, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new BackgroundColorSpan(color), start, end, flag);
        return spannableStringBuilder;
    }

    /**
     * BackgroundColorSpan <br/>
     * 功能：文字颜色和背景色。<br/>
     */
    public static SpannableStringBuilder getForegroundAndBackgroundColorSpan(String content, int foregroundColor, int backgroundColor, int start, int end, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new BackgroundColorSpan(foregroundColor), start, end, flag);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(backgroundColor), start, end, flag);
        return spannableStringBuilder;
    }

}
