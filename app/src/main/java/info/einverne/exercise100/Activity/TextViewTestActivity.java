package info.einverne.exercise100.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.einverne.exercise100.R;

public class TextViewTestActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_test);

        mTextView = (TextView)findViewById(R.id.tv_example);

        Button btn_url = (Button) findViewById(R.id.btn_url);
        assert btn_url != null;
        btn_url.setOnClickListener(this);

        Button btn_underline = (Button) findViewById(R.id.btn_underline);
        assert btn_underline != null;
        btn_underline.setOnClickListener(this);

        Button btn_strike = (Button) findViewById(R.id.btn_strike);
        assert btn_strike != null;
        btn_strike.setOnClickListener(this);

        Button btn_style = (Button) findViewById(R.id.btn_style);
        assert btn_style != null;
        btn_style.setOnClickListener(this);

        Button btn_font = (Button) findViewById(R.id.btn_font);
        assert btn_font != null;
        btn_font.setOnClickListener(this);
        Button btn_forecolor = (Button) findViewById(R.id.btn_forecolor);
        assert btn_forecolor != null;
        btn_forecolor.setOnClickListener(this);
        Button btn_backcolor = (Button) findViewById(R.id.btn_backcolor);
        assert btn_backcolor != null;
        btn_backcolor.setOnClickListener(this);
        Button btn_imagespan = (Button)findViewById(R.id.btn_imagespan);
        assert btn_imagespan != null;
        btn_imagespan.setOnClickListener(this);
        Button btn_sub = (Button)findViewById(R.id.btn_sub);
        assert btn_sub != null;
        btn_sub.setOnClickListener(this);
        Button btn_super = (Button)findViewById(R.id.btn_super);
        assert btn_super != null;
        btn_super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_url:
                addUrlSpan();
                break;
            case R.id.btn_underline:
                addUnderLineSpan();
                break;
            case R.id.btn_strike:
                addStrikeSpan();
                break;
            case R.id.btn_style:
                addStyleSpan();
                break;
            case R.id.btn_font:
                addFontSpan();
                break;
            case R.id.btn_forecolor:
                addForeColorSpan();
                break;
            case R.id.btn_backcolor:
                addBackColorSpan();
                break;
            case R.id.btn_imagespan:
                addImageSpan();
                break;
            case R.id.btn_sub:
                addSubscript();
                break;
            case R.id.btn_super:
                addSuperscript();
                break;
            default:
                break;
        }
    }

    /**
     * 超链接
     */
    private void addUrlSpan() {
        SpannableString spannableString = new SpannableString("超链接");
        URLSpan span = new URLSpan("http://einverne.github.io");
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spannableString);
        mTextView.setMovementMethod(new LinkMovementMethod());
    }
    /**
     * 文字背景颜色
     */
    private void addBackColorSpan() {
        SpannableString spanString = new SpannableString("颜色2");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 文字颜色
     */
    private void addForeColorSpan() {
        SpannableString spanString = new SpannableString("颜色1");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 字体大小
     */
    private void addFontSpan() {
        SpannableString spanString = new SpannableString("36号字体");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(36);
        spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 粗体，斜体
     */
    private void addStyleSpan() {
        SpannableString spanString = new SpannableString("BIBI");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 删除线
     */
    private void addStrikeSpan() {
        SpannableString spanString = new SpannableString("删除线");
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 下划线
     */
    private void addUnderLineSpan() {
        SpannableString spanString = new SpannableString("下划线");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 图片
     */
    @SuppressWarnings("deprecation")
    private void addImageSpan() {
        SpannableString spanString = new SpannableString(" ");
        Drawable d = getResources().getDrawable(R.drawable.small_on_normal);
        assert d != null;
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spanString);
    }

    /**
     * 下标（数学公式会用到）
     */
    private void addSubscript() {
        SpannableString spanText = new SpannableString("x2");
        spanText.setSpan(new SubscriptSpan(), 1, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.append(spanText);
    }

    /**
     * 上标（数学公式会用到）
     */
    private void addSuperscript() {
        SpannableString spanText = new SpannableString("B0");
        spanText.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.append(spanText);
    }
}
