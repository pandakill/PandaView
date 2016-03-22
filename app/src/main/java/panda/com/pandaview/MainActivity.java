package panda.com.pandaview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import panda.com.pandaview.util.Util;
import panda.com.pandaview.view.MLYSegmentView;
import panda.com.pandaview.view.PandaTopHeader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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
        header.setTitleDrawablePadding(Util.dp2px(MainActivity.this, 8));
        header.setTitleTextDrawable(R.mipmap.back, -1, -1, -1);

        header.setButtonLeftVisible(View.GONE);
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
                } else if (position == PandaTopHeader.HEADER_RIGHT) {
                    Toast.makeText(MainActivity.this, "点击右侧", Toast.LENGTH_SHORT).show();
                }
            }
        };
        header.setOnClickHeaderListener(listener);
        header.setRightButtonBackground(getResources().getDrawable(R.drawable.header_button_selector));
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
