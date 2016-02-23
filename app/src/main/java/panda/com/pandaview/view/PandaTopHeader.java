package panda.com.pandaview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
public class PandaTopHeader extends RelativeLayout {

    private static String TAG = PandaTopHeader.class.getSimpleName();

    public static int HEADER_LEFT = 100;
    public static int HEADER_TITLE = 101;
    public static int HEADER_RIGHT = 102;

    public static int STATE_VISIBLE = 200;
    public static int STATE_GONE = 201;
    public static int STATE_INVISIBLE = 202;

    private static int TYPE_TEXTVIEW = 300;
    private static int TYPE_IMAGEVIEW = 301;

    private Context mContext;

    private TextView mBtnLeft;
    private ImageView mImgBtnLeft;
    private TextView mTvTitle;
    private ImageView mImgTitle;
    private TextView mBtnRight;
    private ImageView mImgBtnRight;

    private int mBtnLeftState;
    private int mBtnLeftType;
    private int mTitleState;
    private int mTitleType;
    private int mBtnRightState;
    private int mBtnRightType;

    private float mPadding;

    private int mBtnLeftTextColor;
    private float mBtnLeftTextSize;
    private Drawable mBtnLeftResource;
    private Drawable mBtnLeftDrawable;
    private String mBtnLeftText;
    private float mBtnLeftDrawablePadding;

    private int mTvTitleTextColor;
    private float mTvTitleTextSize;
    private Drawable mTvTitleResource;
    private String mTvTitleText;

    private int mBtnRightTextColor;
    private float mBtnRightTextSize;
    private Drawable mBtnRightResource;
    private String mBtnRightText;

    private OnClickHeaderListener onClickHeaderListener;

    public interface OnClickHeaderListener {
        void onClick(View view, int position, int viewType);
    }

    public PandaTopHeader(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PandaTopHeader);
        if (array != null) {
            mPadding = array.getDimension(R.styleable.PandaTopHeader_left_right_padding, -1);

            mBtnLeftTextColor = array.getColor(R.styleable.PandaTopHeader_left_textColor, -1);
            mBtnLeftTextSize = array.getDimension(R.styleable.PandaTopHeader_left_textSize, -1);
            mBtnLeftResource = array.getDrawable(R.styleable.PandaTopHeader_left_resource);
            mBtnLeftText = array.getString(R.styleable.PandaTopHeader_left_text);
            mBtnLeftDrawable = array.getDrawable(R.styleable.PandaTopHeader_left_textDrawable);
            mBtnLeftDrawablePadding = array.getDimension(R.styleable.PandaTopHeader_left_textDrawablePadding, -1);

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

        setClickable(true);
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

        mBtnLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBtnLeftTextSize);
        mBtnLeft.setText(mBtnLeftText);
        mBtnLeft.setTextColor(mBtnLeftTextColor);
        mBtnLeft.setBackgroundColor(Color.TRANSPARENT);
        mBtnLeft.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(CENTER_VERTICAL, TRUE);
        leftParams.setMargins((int) mPadding, 0, 0, 0);
        if (mBtnLeftResource != null) {
            mImgBtnLeft.setImageDrawable(mBtnLeftResource);
            addView(mImgBtnLeft, leftParams);
            mBtnLeftType = TYPE_IMAGEVIEW;
            mImgBtnLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickHeaderListener != null) {
                        onClickHeaderListener.onClick(v, HEADER_LEFT, mBtnLeftType);
                    }
                }
            });
        } else {
            if (mBtnLeftDrawable != null) {
                mBtnLeftDrawable.setBounds(0, 0, mBtnLeftDrawable.getMinimumWidth(), mBtnLeftDrawable.getMinimumHeight());
                mBtnLeft.setCompoundDrawables(mBtnLeftDrawable, null, null, null);
                mBtnLeft.setCompoundDrawablePadding((int) mBtnLeftDrawablePadding);
            }
            mBtnLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickHeaderListener != null) {
                        onClickHeaderListener.onClick(v, HEADER_LEFT, mBtnLeftType);
                    }
                }
            });
            mBtnLeftType = TYPE_TEXTVIEW;
            addView(mBtnLeft, leftParams);
        }
        mBtnLeftState = VISIBLE;

        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTvTitleTextSize);
        mTvTitle.setTextColor(mTvTitleTextColor);
        mTvTitle.setText(mTvTitleText);
        mTvTitle.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);
        titleParams.addRule(CENTER_VERTICAL, TRUE);
        if (mTvTitleResource != null) {
            mImgTitle.setImageDrawable(mTvTitleResource);
            mTitleType = TYPE_IMAGEVIEW;
            addView(mImgTitle, titleParams);
        } else {
            mTitleType = TYPE_TEXTVIEW;
            addView(mTvTitle, titleParams);
        }
        mTitleState = VISIBLE;

        mBtnRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBtnRightTextSize);
        mBtnRight.setTextColor(mBtnRightTextColor);
        mBtnRight.setText(mBtnRightText);
        mBtnRight.setBackgroundColor(Color.TRANSPARENT);
        mBtnRight.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        rightParams.addRule(CENTER_VERTICAL, TRUE);
        rightParams.setMargins(0, 0, (int) mPadding, 0);
        if (mBtnRightResource != null) {
            mImgBtnRight.setImageDrawable(mBtnRightResource);
            mBtnRightType = TYPE_IMAGEVIEW;
            mImgBtnRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickHeaderListener != null) {
                        onClickHeaderListener.onClick(v, HEADER_RIGHT, mBtnRightType);
                    }
                }
            });
            addView(mImgBtnRight, rightParams);
        } else {
            mBtnRightType = TYPE_TEXTVIEW;
            mBtnRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickHeaderListener != null) {
                        onClickHeaderListener.onClick(v, HEADER_RIGHT, mBtnRightType);
                    }
                }
            });
            addView(mBtnRight, rightParams);
        }
        mBtnRightState = VISIBLE;
    }

    /**
     * 设置监听器
     *
     * @param listener #OnclickHeaderListener
     */
    public void setOnClickHeaderListener(OnClickHeaderListener listener) {
        onClickHeaderListener = listener;
    }

    /**
     * 设置左控件的id
     *
     * @param id 通过资源文件命名的id
     */
    public void setLeftButtonId(int id) {
        if (mBtnLeftType == TYPE_TEXTVIEW) {
            mBtnLeft.setId(id);
        } else if (mBtnLeftType == TYPE_IMAGEVIEW) {
            mImgBtnLeft.setId(id);
        }
    }

    /**
     * 设置右控件的id
     *
     * @param id 一般是通过资源文件命名的id
     */
    public void setRightButtonId(int id) {
        if (mBtnRightType == TYPE_TEXTVIEW) {
            mBtnRight.setId(id);
        } else if (mBtnRightType == TYPE_IMAGEVIEW) {
            mImgBtnRight.setId(id);
        }
    }

    /**
     * 获取左控件的id
     *
     * @return 如果控件隐藏则返回0
     */
    public int getLeftButtonId() {
        if (mBtnLeftType == TYPE_TEXTVIEW) {
            return mBtnLeft.getId();
        } else if (mBtnLeftType ==TYPE_IMAGEVIEW) {
            return mImgBtnLeft.getId();
        }

        return 0;
    }

    /**
     * 获取右控件的id
     *
     * @return 如果控件隐藏则返回0
     */
    public int getRightButtonId() {
        if (mBtnRightType == TYPE_TEXTVIEW) {
            return mBtnRight.getId();
        } else if (mBtnRightType ==TYPE_IMAGEVIEW) {
            return mImgBtnRight.getId();
        }

        return 0;
    }

    public void setLeftButtonBackground(Drawable drawable) {
        if (mBtnLeftType == TYPE_TEXTVIEW) {
            mBtnLeft.setBackgroundDrawable(drawable);
        } else if (mBtnLeftType == TYPE_IMAGEVIEW) {
            mImgBtnLeft.setBackgroundDrawable(drawable);
        }
    }

    public void setRightButtonBackground(Drawable drawable) {
        if (mBtnRightType == TYPE_TEXTVIEW) {
            mBtnRight.setBackgroundDrawable(drawable);
        } else if (mBtnRightType == TYPE_IMAGEVIEW) {
            mImgBtnRight.setBackgroundDrawable(drawable);
        }
    }

    public void setButtonLeftVisible(int visible) {
        if (mBtnLeftType == TYPE_TEXTVIEW) {
            mBtnLeft.setVisibility(visible);
        } else if (mBtnLeftType == TYPE_IMAGEVIEW) {
            mImgBtnLeft.setVisibility(visible);
        }
    }

    public void setButtonRightVisible(int visible) {
        if (mBtnRightType == TYPE_TEXTVIEW) {
            mBtnRight.setVisibility(visible);
        } else if (mBtnRightType == TYPE_IMAGEVIEW) {
            mImgBtnRight.setVisibility(visible);
        }
    }

    public void setHeaderTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setButtonLeftEnable(boolean enable) {
        if (mBtnLeftType == TYPE_TEXTVIEW) {
            mBtnLeft.setEnabled(enable);
        } else if (mBtnLeftType == TYPE_IMAGEVIEW) {
            mImgBtnLeft.setEnabled(enable);
        }
    }

    public void setButtonRightEnable(boolean enable) {
        if (mBtnRightType == TYPE_TEXTVIEW) {
            mBtnRight.setEnabled(enable);
        } else if (mBtnRightType == TYPE_IMAGEVIEW) {
            mImgBtnRight.setEnabled(enable);
        }
    }
}
