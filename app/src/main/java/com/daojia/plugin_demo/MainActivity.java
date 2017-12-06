package com.daojia.plugin_demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daojia.plugin_demo.activity.TargetActivity;
import com.daojia.plugin_demo.hook.HookHelper;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
                //必须加上 FLAG NEW_TASK
                /**
                 *  if ((intent.getFlags()&Intent.FLAG_ACTIVITY_NEW_TASK) == 0
                 && options != null && ActivityOptions.fromBundle(options).getLaunchTaskId() == -1) {
                 throw new AndroidRuntimeException(
                 "Calling startActivity() from outside of an Activity "
                 + " context requires the FLAG_ACTIVITY_NEW_TASK flag."
                 + " Is this really what you want?");
                 }
                 */

//
                getApplicationContext().startActivity(replaceTargetIntent());
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//

    }


    public Intent replaceIntentUrl(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public Intent replaceTargetIntent(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TargetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            HookHelper.attachContext();

            // targetActivity 替换 stubActivity打开 记得在Evilinstrumentation做修改
            com.daojia.plugin_demo.intercept_activity.AMSHookHelper.hookActivityThreadHandler();
        } catch (Exception e) {
            e.printStackTrace();
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
