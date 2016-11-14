package panda.com.pandaview.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import panda.com.pandaview.R;
import panda.com.pandaview.util.Util;

/**
 * Created by panda on 16/3/26.
 *
 * 一个从底部弹出的选择框,选择项的个数由开发者自己定义
 */
public class PandaBottomSelector extends PopupWindow {

    private ArrayList<String> mSelectorArray;
    private ArrayList<View> mSelectViews;

    private Context mContext;


    public PandaBottomSelector(Context context, ArrayList<String> selectorStrings) {
        super(context);
        this.mContext = context;
        this.mSelectorArray = selectorStrings;

        initWindowView();
    }

    private void initWindowView() {
        LinearLayout dialogView = new LinearLayout(mContext);
        dialogView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        dialogView.setLayoutParams(params);

        View view1 = new View(mContext);
        view1.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        view1.setAlpha(0.1f);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        viewParams.weight = 1;
        view1.setLayoutParams(viewParams);
        dialogView.addView(view1, 0);

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2px(mContext, 40));
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;

        LinearLayout.LayoutParams lineViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < mSelectorArray.size(); i ++) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(textViewParams);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            textView.setTextColor(mContext.getResources().getColor(R.color.black));
            textView.setText(mSelectorArray.get(i));

            dialogView.addView(textView);

            if (i >= 0 && i < (mSelectorArray.size()-1)) {
                View lineView = new View(mContext);
                lineView.setLayoutParams(lineViewParams);
                lineView.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                dialogView.addView(lineView);
            }
        }

        setContentView(dialogView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable drawable = new ColorDrawable(0x000000);

        setBackgroundDrawable(drawable);
        setAnimationStyle(R.style.AnimBottom);
        setOutsideTouchable(true);
    }
}
