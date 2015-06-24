package com.squallstar.quickly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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

                Intent share = new Intent(android.content.Intent.ACTION_SEND);

                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title_note));
                share.putExtra(Intent.EXTRA_TEXT, text);

                startActivity(Intent.createChooser(share, getString(R.string.btn_share)));
            }
        });
    }
}
