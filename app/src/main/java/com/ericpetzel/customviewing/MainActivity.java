package com.ericpetzel.customviewing;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ericpetzel.customviewing.events.LogEvent;
import com.squareup.otto.Subscribe;


public class MainActivity extends ActionBarActivity {

    private static final String SAVED_STATE_LOGS = "ss_logs";

    private FrameLayout contentFrame;
    private TextView logTextView;
    private StringBuffer logBuffer;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logBuffer = new StringBuffer();
        TheBus bus = TheBus.getInstance();
        bus.register(this);

        setContentView(R.layout.activity_main);

        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        logTextView = (TextView) findViewById(R.id.log_tv);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        // use a buffer until we have a reference to logTextView
        if (!TextUtils.isEmpty(logBuffer)) {
            logTextView.append(logBuffer.toString());
            logBuffer.setLength(0);
        }

        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    protected void onDestroy() {
        TheBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_STATE_LOGS, logTextView.getText().toString());
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

    @Subscribe
    public void onLogEvent(LogEvent event) {
        // this is here since we register before setContentView so we can capture measure logs
        if (logTextView == null) {
            logBuffer.append(event.message + "\n");
            return;
        }

        logTextView.append(event.message + "\n");
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
