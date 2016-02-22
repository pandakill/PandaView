package panda.com.pandaview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import panda.com.pandaview.R;

/**
 * Created by panda on 2016/2/20:9:31.
 *
 * 顶部标题栏view
 */
public class PandaTopHeader extends RelativeLayout implements View.OnClickListener {

    private static String TAG = PandaTopHeader.class.getSimpleName();

    private TextView mBtnLeft;
    private ImageView mImgBtnLeft;
    private TextView mTvTitle;
    private ImageView mImgTitle;
    private TextView mBtnRight;
    private ImageView mImgBtnRight;

    private int mBtnLeftTextColor;
    private float mBtnLeftTextSize;
    private Drawable mBtnLeftResource;
    private Drawable mBtnLeftDrawable;
    private String mBtnLeftText;

    private int mTvTitleTextColor;
    private float mTvTitleTextSize;
    private Drawable mTvTitleResource;
    private String mTvTitleText;

    private int mBtnRightTextColor;
    private float mBtnRightTextSize;
    private Drawable mBtnRightResource;
    private String mBtnRightText;

    private LayoutParams mBtnLeftParams;
    private LayoutParams mTvTitleParams;
    private LayoutParams mBtnRightParams;

    public PandaTopHeader(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PandaTopHeader);
        if (array != null) {
            mBtnLeftTextColor = array.getColor(R.styleable.PandaTopHeader_left_textColor, -1);
            mBtnLeftTextSize = array.getDimension(R.styleable.PandaTopHeader_left_textSize, -1);
            mBtnLeftResource = array.getDrawable(R.styleable.PandaTopHeader_left_resource);
            mBtnLeftText = array.getString(R.styleable.PandaTopHeader_left_text);
            mBtnLeftDrawable = array.getDrawable(R.styleable.PandaTopHeader_left_textDrawable);

            mTvTitleTextColor = array.getColor(R.styleable.PandaTopHeader_title_textColor, -1);
            mTvTitleTextSize = array.getDimension(R.styleable.PandaTopHeader_title_textSize, -1);
            mTvTitleResource = array.getDrawable(R.styleable.PandaTopHeader_title_resource);
            mTvTitleText = array.getString(R.styleable.PandaTopHeader_title_text);

            mBtnRightTextColor = array.getColor(R.styleable.PandaTopHeader_right_textColor, -1);
            mBtnRightTextSize = array.getDimension(R.styleable.PandaTopHeader_right_textSize, -1);
            mBtnRightResource = array.getDrawable(R.styleable.PandaTopHeader_right_resource);
            mBtnRightText = array.getString(R.styleable.PandaTopHeader_right_text);

            array.recycle();

            initView(context);
        }
    }

    private void initView(Context context) {
        mBtnLeft = new TextView(context);
        mImgBtnLeft = new ImageView(context);
        mTvTitle = new TextView(context);
        mImgTitle = new ImageView(context);
        mBtnRight = new TextView(context);
        mImgBtnRight = new ImageView(context);

        setViewContribute();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViewContribute() {

        mBtnLeft.setTextSize(mBtnLeftTextSize);
        mBtnLeft.setText(mBtnLeftText);
        mBtnLeft.setTextColor(mBtnLeftTextColor);
        mBtnLeft.setBackgroundColor(Color.TRANSPARENT);
        mBtnLeftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBtnLeftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        mBtnLeftParams.addRule(CENTER_VERTICAL, TRUE);
        if (mBtnLeftResource != null) {
            mImgBtnLeft.setImageDrawable(mBtnLeftResource);
            addView(mImgBtnLeft, mBtnLeftParams);
        } else {
            if (mBtnLeftDrawable != null) {
                mBtnLeft.setCompoundDrawables(mBtnLeftDrawable, null, null, null);
                mBtnLeft.setCompoundDrawablePadding(20);
            }
            addView(mBtnLeft, mBtnLeftParams);
        }

        mTvTitle.setTextSize(mTvTitleTextSize);
        mTvTitle.setTextColor(mTvTitleTextColor);
        mTvTitle.setText(mTvTitleText);
        mTvTitleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTvTitleParams.addRule(CENTER_IN_PARENT, TRUE);
        mTvTitleParams.addRule(CENTER_VERTICAL, TRUE);
        if (mImgTitle != null) {
            mImgTitle.setImageDrawable(mTvTitleResource);
            addView(mImgTitle, mTvTitleParams);
        } else {
            addView(mTvTitle, mTvTitleParams);
        }

        mBtnRight.setTextSize(mBtnRightTextSize);
        mBtnRight.setTextColor(mBtnRightTextColor);
        mBtnRight.setText(mBtnRightText);
        mBtnRight.setBackgroundColor(Color.TRANSPARENT);
        mBtnRightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBtnRightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        mBtnRightParams.addRule(CENTER_VERTICAL, TRUE);
        if (mBtnRightResource != null) {
            mImgBtnRight.setBackground(mBtnRightResource);
            addView(mImgBtnRight, mBtnRightParams);
        } else {
            addView(mBtnRight, mBtnRightParams);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
