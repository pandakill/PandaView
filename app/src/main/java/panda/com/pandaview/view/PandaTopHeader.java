package panda.com.pandaview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
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
 * version: V0.2
 * contribute: 顶部标题栏view,默认的是三个view,可自定义设置view进行添加,具体可以参照mainActivity.java的例子演示
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
    private static int TYPE_SELFVIEW = 302;

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
    private Drawable mBtnRightDrawable;
    private String mTvTitleText;
    private float mBtnRightDrawablePadding;

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
            mBtnRightDrawable = array.getDrawable(R.styleable.PandaTopHeader_right_textDrawable);
            mBtnRightDrawablePadding = array.getDimension(R.styleable.PandaTopHeader_right_textDrawablePadding, -1);

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

        mBtnLeftState = STATE_GONE;
        mTitleState = STATE_GONE;
        mBtnRightState = STATE_GONE;

        setViewContribute();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViewContribute() {
        addLeftView(null);
        addTitleView(null, null);
        addRightView(null);
    }

    public void addLeftView(View view) {
        LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(CENTER_VERTICAL, TRUE);

        if (view == null) {
            mBtnLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBtnLeftTextSize);
            mBtnLeft.setText(mBtnLeftText);
            mBtnLeft.setTextColor(mBtnLeftTextColor);
            mBtnLeft.setBackgroundColor(Color.TRANSPARENT);
            mBtnLeft.setGravity(Gravity.CENTER_VERTICAL);
            if (mBtnLeftResource != null) {
                mImgBtnLeft.setImageDrawable(mBtnLeftResource);
                addView(mImgBtnLeft, leftParams);
                mBtnLeftType = TYPE_IMAGEVIEW;
                mBtnLeftState = STATE_VISIBLE;
                mImgBtnLeft.setPadding((int) mPadding, 0, (int) mPadding, 0);
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
                mBtnLeft.setPadding((int) mPadding, 0, (int) mPadding, 0);
                mBtnLeft.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickHeaderListener != null) {
                            onClickHeaderListener.onClick(v, HEADER_LEFT, mBtnLeftType);
                        }
                    }
                });
                mBtnLeftType = TYPE_TEXTVIEW;
                mBtnLeftState = STATE_VISIBLE;
                addView(mBtnLeft, leftParams);
            }
        } else {
            if (mBtnLeftState != STATE_GONE) {
                if (mBtnLeftType == TYPE_TEXTVIEW) {
                    mBtnLeft.setVisibility(GONE);
                } else if (mBtnLeftType == TYPE_IMAGEVIEW) {
                    mImgBtnLeft.setVisibility(GONE);
                }
            }

            if (view.getVisibility() == GONE) {
                mBtnLeftState = STATE_GONE;
            } else if (view.getVisibility() == INVISIBLE) {
                mBtnLeftState = STATE_INVISIBLE;
            } else if (view.getVisibility() == VISIBLE) {
                mBtnLeftState = STATE_VISIBLE;
            }

            mBtnLeftType = TYPE_SELFVIEW;

            addView(view, leftParams);
        }
    }

    public void addTitleView(View view, LayoutParams params) {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);
        titleParams.addRule(CENTER_VERTICAL, TRUE);

        if (view == null) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTvTitleTextSize);
            mTvTitle.setTextColor(mTvTitleTextColor);
            mTvTitle.setText(mTvTitleText);
            mTvTitle.setGravity(Gravity.CENTER_VERTICAL);
            if (mTvTitleResource != null) {
                mImgTitle.setImageDrawable(mTvTitleResource);
                mTitleType = TYPE_IMAGEVIEW;
                mTitleState = STATE_VISIBLE;
                addView(mImgTitle, titleParams);
            } else {
                mTitleType = TYPE_TEXTVIEW;
                mTitleState = STATE_VISIBLE;
                addView(mTvTitle, titleParams);
            }
        } else {
            if (mTitleState != STATE_GONE) {
                if (mTitleType == TYPE_TEXTVIEW) {
                    mTvTitle.setVisibility(GONE);
                } else if (mTitleType == TYPE_IMAGEVIEW) {
                    mImgTitle.setVisibility(GONE);
                }
            }

            if (view.getVisibility() == GONE) {
                mTitleState = STATE_GONE;
            } else if (view.getVisibility() == INVISIBLE) {
                mTitleState = STATE_INVISIBLE;
            } else if (view.getVisibility() == VISIBLE) {
                mTitleState = STATE_VISIBLE;
            }

            mTitleType = TYPE_SELFVIEW;

            if (params == null) {
                addView(view, titleParams);
            } else {
                addView(view, params);
            }
        }
    }

    public void addRightView(View view) {
        LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        rightParams.addRule(CENTER_VERTICAL, TRUE);

        if (view == null) {
            mBtnRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBtnRightTextSize);
            mBtnRight.setTextColor(mBtnRightTextColor);
            mBtnRight.setText(mBtnRightText);
            mBtnRight.setBackgroundColor(Color.TRANSPARENT);
            mBtnRight.setGravity(Gravity.CENTER_VERTICAL);
            if (mBtnRightResource != null) {
                mImgBtnRight.setImageDrawable(mBtnRightResource);
                mBtnRightState = STATE_VISIBLE;
                mBtnRightType = TYPE_IMAGEVIEW;
                mImgBtnRight.setPadding((int) mPadding, 0, (int) mPadding, 0);
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
                if (mBtnRightDrawable != null) {
                    mBtnRightDrawable.setBounds(0, 0, mBtnRightDrawable.getMinimumWidth(), mBtnRightDrawable.getMinimumHeight());
                    mBtnRight.setCompoundDrawables(null, null, mBtnRightDrawable, null);
                    mBtnRight.setCompoundDrawablePadding((int) mBtnRightDrawablePadding);
                }
                mBtnRightState = STATE_VISIBLE;
                mBtnRightType = TYPE_TEXTVIEW;
                mBtnRight.setPadding((int) mPadding, 0, (int) mPadding, 0);
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
        } else {
            if (mBtnRightState != STATE_GONE) {
                if (mBtnRightType == TYPE_TEXTVIEW) {
                    mTvTitle.setVisibility(GONE);
                } else if (mBtnRightType == TYPE_IMAGEVIEW) {
                    mImgTitle.setVisibility(GONE);
                }
            }

            if (view.getVisibility() == GONE) {
                mBtnRightState = STATE_GONE;
            } else if (view.getVisibility() == INVISIBLE) {
                mBtnRightState = STATE_INVISIBLE;
            } else if (view.getVisibility() == VISIBLE) {
                mBtnRightState = STATE_VISIBLE;
            }

            mBtnRightType = TYPE_SELFVIEW;

            addView(view, rightParams);
        }
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

    /**
     * 设置标题栏属性
     *
     * @param singleLine bool值,是否显示为单行
     * @param maxEms 显示的最大字符数,如果是-1，则默认
     * @param minEms 显示的最小字符数,如果是-1,则默认
     * @param where 超出最大字符数,从何处截断,当#where为null时,默认
     */
    public void setTitleTextAttribute(boolean singleLine, int maxEms, int minEms, TextUtils.TruncateAt where) {
        if (singleLine) {
            mTvTitle.setSingleLine();
        }
        if (maxEms != -1) {
            mTvTitle.setMaxEms(maxEms);
        }
        if (minEms != -1) {
            mTvTitle.setMinEms(minEms);
        }
        if (where != null) {
            mTvTitle.setEllipsize(where);
        }
    }

    /**
     * 设置标题的左上右下drawable
     *
     * @param leftId leftDrawable resource's id
     * @param topId topDrawable resource's id
     * @param rightId rightDrawable resource's id
     * @param bottomId bottomDrawable resource's id
     */
    public void setTitleTextDrawable(int leftId, int topId, int rightId, int bottomId) {
        if (mTvTitle == null
                || mTvTitle.getVisibility() != VISIBLE) {
            return;
        }

        Drawable leftDrawable = null;
        Drawable topDrawable = null;
        Drawable rightDrawable = null;
        Drawable bottomDrawable = null;

        if (leftId != -1) {
            leftDrawable = getResources().getDrawable(leftId);
        }
        if (topId != -1) {
            topDrawable = getResources().getDrawable(topId);
        }
        if (rightId != -1) {
            rightDrawable = getResources().getDrawable(rightId);
        }
        if (bottomId != -1) {
            bottomDrawable = getResources().getDrawable(bottomId);
        }

        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        }
        if (topDrawable != null) {
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        }
        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        }
        if (bottomDrawable != null) {
            bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
        }

        mTvTitle.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
    }

    /**
     * 设置标题栏的drawable边距
     * 如果标题被隐藏或者标题不存在,则设置无效
     *
     * @param padding 需要设置的边距padding
     */
    public void setTitleDrawablePadding(int padding) {
        if (mTvTitle == null || mTvTitle.getVisibility() != VISIBLE) {
            return;
        }

        mTvTitle.setCompoundDrawablePadding(padding);
    }
}
