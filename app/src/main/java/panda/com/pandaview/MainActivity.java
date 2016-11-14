package panda.com.pandaview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import panda.com.pandaview.util.Util;
import panda.com.pandaview.view.MLYSegmentView;
import panda.com.pandaview.view.PandaBottomSelector;
import panda.com.pandaview.view.PandaTopHeader;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();

    PandaBottomSelector bottomSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        arrayList.add("第一个选项");
        arrayList.add("第二个选项");
        arrayList.add("第三个选项");

        bottomSelector = new PandaBottomSelector(MainActivity.this, arrayList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PandaTopHeader header = (PandaTopHeader) findViewById(R.id.header1);
        header.setLeftButtonId(R.id.button_back);
        header.setLeftButtonBackground(getResources().getDrawable(R.drawable.header_button_selector));
        header.setTitleDrawablePadding(Util.dp2px(MainActivity.this, 8));
        header.setTitleTextDrawable(R.mipmap.back, -1, -1, -1);

        MLYSegmentView segmentView = new MLYSegmentView(this);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(Util.dp2px(MainActivity.this, 170),Util.dp2px(MainActivity.this, 30));
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        segmentView.setSegmentText("测试", 0);
        segmentView.setOnSegmentViewClickListener(new MLYSegmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View v, int position) {
                if (position == 0) {
                    Util.displayToastShort(MainActivity.this, "选中了第一个segmentView");
                } else if (position == 1) {
                    Util.displayToastShort(MainActivity.this, "选中了第二个segmentView");
                }
            }
        });
        header.addTitleView(segmentView, params);

        PandaTopHeader.OnClickHeaderListener listener = new PandaTopHeader.OnClickHeaderListener() {
            @Override
            public void onClick(View view, int position, int viewType) {
                if (position == PandaTopHeader.HEADER_LEFT) {
                    Toast.makeText(MainActivity.this, "点击左侧", Toast.LENGTH_SHORT).show();
                    if (bottomSelector.isShowing()) {
                        bottomSelector.dismiss();
                    } else {
                        bottomSelector.showAtLocation(MainActivity.this.findViewById(R.id.header1), Gravity.BOTTOM, 0, 0);
                    }

                    bottomSelector.setWidth(WindowManager.LayoutParams.FILL_PARENT);
                    bottomSelector.setHeight(WindowManager.LayoutParams.FILL_PARENT);

                    bottomSelector.setFocusable(true);

                    Log.d(MainActivity.class.getSimpleName(), "bottomSelector.isShowing = " + bottomSelector.isShowing());
                    Log.d(MainActivity.class.getSimpleName(), "bottomSelector.getHeight = " + bottomSelector.getHeight());
                    Log.d(MainActivity.class.getSimpleName(), "bottomSelector.getWidth = " + bottomSelector.getWidth());
                } else if (position == PandaTopHeader.HEADER_RIGHT) {
                    Toast.makeText(MainActivity.this, "点击右侧", Toast.LENGTH_SHORT).show();
                }
            }
        };
        header.setOnClickHeaderListener(listener);
        header.setRightButtonBackground(getResources().getDrawable(R.drawable.header_button_selector));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!bottomSelector.isShowing()) {
            bottomSelector.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
