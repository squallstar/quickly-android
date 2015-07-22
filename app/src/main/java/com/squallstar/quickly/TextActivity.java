package com.squallstar.quickly;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.squallstar.quickly.adapters.AppsAdapter;

import java.util.Collections;
import java.util.List;

public class TextActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnShare = (Button) findViewById(R.id.btnShare);

        final EditText txtShare = (EditText) findViewById(R.id.txtShare);

        txtShare.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtShare, InputMethodManager.SHOW_IMPLICIT);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txtShare.getText().toString();

                if (text.length() == 0) {
                    return;
                }

                final Intent share = new Intent(android.content.Intent.ACTION_SEND);

                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title_note));
                share.putExtra(Intent.EXTRA_TEXT, text);

                final Dialog dialog = new Dialog(TextActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
                WMLP.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(WMLP);
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.activity_chooser);

                PackageManager pm =getPackageManager();
                List<ResolveInfo> appsList = pm.queryIntentActivities(share, 0);
                Collections.sort(appsList, new ResolveInfo.DisplayNameComparator(pm));

                ListView appsListView = (ListView) dialog.findViewById(R.id.appsList);
                final AppsAdapter appsAdapter = new AppsAdapter(getApplicationContext(), pm, appsList);

                appsListView.setAdapter(appsAdapter);

                appsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ResolveInfo app = appsAdapter.getItem(position);
                        ActivityInfo activity = app.activityInfo;
                        ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                                activity.name);

                        share.addCategory(Intent.CATEGORY_LAUNCHER);
                        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        share.setComponent(name);

                        startActivity(share);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
