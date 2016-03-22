package panda.com.pandaview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import panda.com.pandaview.R;
import panda.com.pandaview.util.Util;

public class MLYSegmentView extends LinearLayout {
    private TextView textView1;
    private TextView textView2;
    private onSegmentViewClickListener listener;
    private Context mContext;

    public MLYSegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MLYSegmentView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.setLayoutParams(new LayoutParams(dp2Px(getContext(), 60), 70));
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView1.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView2.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        XmlPullParser xrp = getResources().getXml(R.color.seg_text_color_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
        } catch (Exception e) {
        }
        textView1.setText("作品");
        textView2.setText("设计师");
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView1.setPadding(Util.dp2px(context,1), Util.dp2px(context,3), Util.dp2px(context,1), Util.dp2px(context,3));
        textView2.setPadding(Util.dp2px(context,1), Util.dp2px(context,3), Util.dp2px(context,1), Util.dp2px(context,3));
        setSegmentTextSize(14);
        textView1.setBackgroundResource(R.drawable.seg_left);
        textView2.setBackgroundResource(R.drawable.seg_right);
        textView1.setSelected(true);
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.invalidate();


        textView1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    return;
                }
                textView1.setSelected(true);
                textView2.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    return;
                }
                textView2.setSelected(true);
                textView1.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });
    }

    public void setSegmentTextSize(int sp) {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    public void setSegmentSelected(int position) {
        if (position == 0) {
            if (textView1.isSelected()) {
                return;
            }
            textView1.setSelected(true);
            textView2.setSelected(false);
            if (listener != null) {
                listener.onSegmentViewClick(textView1, 0);
            }
        } else if (position == 1) {
            if (textView2.isSelected()) {
                return;
            }
            textView2.setSelected(true);
            textView1.setSelected(false);
            if (listener != null) {
                listener.onSegmentViewClick(textView2, 1);
            }
        }
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {
        this.listener = listener;
    }


    /**
     * 设置文字
     *
     * @param text
     * @param position
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            textView1.setText(text);
        }
        if (position == 1) {
            textView2.setText(text);
        }
    }


    public static interface onSegmentViewClickListener {
        /**
         * @param v
         * @param position 0-左边 1-右边
         */
        public void onSegmentViewClick(View v, int position);
    }

}
