package com.example.demo.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.demo.R;
import com.lgf.common.lib.utils.SpanUtils;


public class SpanActivity extends AppCompatActivity {
    public static final String URL = "http://www.baidu.com";
    public static final String TYPEFACE_MONOSPACE = "monospace";
    public static final String TYPEFACE_SERIF = "serif";
    public static final String TYPEFACE_SANS_SERIF = "sans-serif";

    private TextView tvTestURLSpan;
    private TextView tvTestUnderlineSpan;
    private TextView tvTestTypefaceSpan;
    private TextView tvTestTextAppearanceSpan;
    private TextView tvTestTabStopSpanStandard;
    private TextView tvTestSuperscriptSpan;
    private TextView tvTestSubscriptSpan;
    private TextView tvTestStrikeThroughSpan;
    private TextView tvTestScaleXSpan;
    private TextView tvTestForegroundSpan;
    private TextView tvTestBackgroundSpan;
    private TextView tvTestForegroundAndBackgroundSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);
        initView();
        initData();
    }

    private void initData() {
        testURLSpan();
        testUnderlineSpan();
        testTypefaceSpan();
        testTextAppearanceSpan();
        testTabStopSpanStandard();
        testSuperscriptSpan();
        testSubscriptSpan();
        testStrikeThroughSpan();
        testScaleXSpan();
        testForegroundSpan();
        testBackgroundSpan();
        testForegroundAndBackgroundSpan();
    }

    private void initView() {
        tvTestURLSpan = (TextView) findViewById(R.id.tv_test_url_span);
        tvTestUnderlineSpan = (TextView) findViewById(R.id.tv_test_underline_span);
        tvTestTypefaceSpan = (TextView) findViewById(R.id.tv_test_typeface_span);
        tvTestTextAppearanceSpan = (TextView) findViewById(R.id.tv_test_text_appearance_span);
        tvTestTabStopSpanStandard = (TextView) findViewById(R.id.tv_test_tab_stop_span_standard);
        tvTestSuperscriptSpan = (TextView) findViewById(R.id.tv_test_superscript_span);
        tvTestSubscriptSpan = (TextView) findViewById(R.id.tv_test_subscript_span);
        tvTestStrikeThroughSpan = (TextView) findViewById(R.id.tv_test_strike_through_span);
        tvTestScaleXSpan = (TextView) findViewById(R.id.tv_test_scale_x_span);
        tvTestForegroundSpan = (TextView) findViewById(R.id.tv_test_foreground_span);
        tvTestBackgroundSpan = (TextView) findViewById(R.id.tv_test_background_span);
        tvTestForegroundAndBackgroundSpan = (TextView) findViewById(R.id.tv_test_foreground_and_background_span);
    }


    /**
     * 功能：点击文字，可以打开一个URL。
     */
    private void testURLSpan() {
        String content = tvTestURLSpan.getText().toString();
        SpannableStringBuilder urlSpan = SpanUtils.getURLSpan(content, URL, 0, content.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestURLSpan.setText(urlSpan);
        //setMovementMethod必须要设，否则不会跳转
        tvTestURLSpan.setMovementMethod(LinkMovementMethod.getInstance());
        tvTestURLSpan.setHighlightColor(0xFF8FABCC);
    }

    /**
     * 功能：设置文字下划线。
     */
    private void testUnderlineSpan() {
        String content = tvTestUnderlineSpan.getText().toString();
        SpannableStringBuilder underlineSpan = SpanUtils.getUnderlineSpan(content, 0, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestUnderlineSpan.setText(underlineSpan);
    }

    /**
     * 功能：设置文字字体。
     */
    private void testTypefaceSpan() {
        String content = tvTestTypefaceSpan.getText().toString();
        int level = (int) (content.length() / 3);

        SpannableStringBuilder typefaceSpan1 = SpanUtils.getTypefaceSpan(content, TYPEFACE_SANS_SERIF, 0, level, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableStringBuilder typefaceSpan2 = SpanUtils.getTypefaceSpan(content, TYPEFACE_MONOSPACE, level, 2 * level, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableStringBuilder typefaceSpan3 = SpanUtils.getTypefaceSpan(content, TYPEFACE_SERIF, 2 * level, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestTypefaceSpan.setText(typefaceSpan1);
        tvTestTypefaceSpan.setText(typefaceSpan2);
        tvTestTypefaceSpan.setText(typefaceSpan3);
    }

    /**
     * 功能：设置文字字体、文字样式（粗体、斜体、等等）、文字颜色状态、文字下划线颜色状态等等。
     */
    private void testTextAppearanceSpan() {
        String content = tvTestTextAppearanceSpan.getText().toString();
        SpannableStringBuilder textAppearanceSpan = SpanUtils.getTextAppearanceSpan(content, TYPEFACE_MONOSPACE, Typeface.BOLD, 20, ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)), ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)), 0, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestTextAppearanceSpan.setText(textAppearanceSpan);
    }

    /**
     * 功能：每行的MarginLeft的偏移量（跟 \t 和 \n 有关系，没有\t和\n无效）。
     */
    private void testTabStopSpanStandard() {
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            content.append("\t").append(tvTestTabStopSpanStandard.getText().toString()).append(" ");
            content.append("\n");
        }
        SpannableStringBuilder tabStopSpan = SpanUtils.getTabStopSpan(content.toString(), 120, 0, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestTabStopSpanStandard.setText(tabStopSpan);
    }

    /**
     * 功能：文字设置为上标，数学公式中用到。<br/>
     */
    private void testSuperscriptSpan() {
        String content = tvTestSuperscriptSpan.getText().toString();
        SpannableStringBuilder superscriptSpan = SpanUtils.getSuperscriptSpan(content, 1, 2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestSuperscriptSpan.setText(superscriptSpan);
    }

    /**
     * 功能：文字设置为下标，化学式中用到。<br/>
     */
    private void testSubscriptSpan() {
        String content = tvTestSubscriptSpan.getText().toString();
        SpannableStringBuilder subscriptSpan = SpanUtils.getSubscriptSpan(content, 1, 2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestSubscriptSpan.setText(subscriptSpan);
    }

    /**
     * 功能：文字设置删除线。<br/>
     */
    private void testStrikeThroughSpan() {
        String content = tvTestStrikeThroughSpan.getText().toString();
        SpannableStringBuilder strikethroughSpan = SpanUtils.getStrikethroughSpan(content, 1, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestStrikeThroughSpan.setText(strikethroughSpan);
    }

    /**
     * 功能：文字横向缩放。<br/>
     */
    private void testScaleXSpan() {
        String content = tvTestScaleXSpan.getText().toString();
        SpannableStringBuilder scaleXSpan = SpanUtils.getScaleXSpan(content, 2.0f, 1, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestScaleXSpan.setText(scaleXSpan);
    }

    /**
     * 功能：文字颜色和背景色。<br/>
     */
    private void testForegroundAndBackgroundSpan() {
        String content = tvTestForegroundAndBackgroundSpan.getText().toString();
        SpannableStringBuilder foregroundSpan = SpanUtils.getForegroundAndBackgroundColorSpan(content, Color.BLUE, Color.YELLOW, 0, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestForegroundAndBackgroundSpan.setText(foregroundSpan);
    }

    /**
     * 功能：文字颜色。<br/>
     */
    private void testForegroundSpan() {
        String content = tvTestForegroundSpan.getText().toString();
        SpannableStringBuilder foregroundSpan = SpanUtils.getForegroundColorSpan(content, Color.BLUE, 0, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestForegroundSpan.setText(foregroundSpan);
    }

    /**
     * 功能：文字背景色。<br/>
     */
    private void testBackgroundSpan() {
        String content = tvTestBackgroundSpan.getText().toString();
        SpannableStringBuilder backgroundSpan = SpanUtils.getBackgroundColorSpan(content, Color.YELLOW, 0, content.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        tvTestBackgroundSpan.setText(backgroundSpan);
    }
}
