package com.example.kenneth.hiit;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import org.json.JSONObject;

import java.util.ArrayList;

public class frdActivity extends AppCompatActivity {
    TabHost tabHost;
    Global global;
    ArrayList<JSONObject> frdList, suggestList, addList;
    private ListView fdView, suggestView, addView;
    private Context context = this;
    public frdAdapter ad1, ad2, ad3;
    public int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();
        TabHost.TabSpec spec1 = tabHost.newTabSpec("tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Friends");
        tabHost.addTab(spec1);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Nearly By");
        spec2.setIndicator("Nearly By");
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec2);
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Friends Request");
        spec3.setIndicator("Friends Request");
        spec3.setContent(R.id.tab3);
        tabHost.addTab(spec3);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                global.SetFrdList();
                global.SetFrdRequestList();
                ad1.notifyDataSetChanged();
                ad2.notifyDataSetChanged();
                ad3.notifyDataSetChanged();
            }
        });
        fdView = (ListView) findViewById(R.id.fdView);
        suggestView = (ListView) findViewById(R.id.suggestView);
        addView = (ListView) findViewById(R.id.addView);

        global = (Global) getApplicationContext();

        global.SetFrdList();
        global.SetFrdRequestList();
        frdList = global.fdList;
        suggestList = global.fdList;
        addList = global.fdRequestList;

        fdView = (ListView) findViewById(R.id.fdView);
        suggestView = (ListView) findViewById(R.id.suggestView);
        addView = (ListView) findViewById(R.id.addView);


        ad1 = new frdAdapter(this, frdList, global, 1);
        ad2 = new frdAdapter(this, frdList, global, 2);
        ad3 = new frdAdapter(this, addList, global, 3);
        fdView.setAdapter(ad1);
        suggestView.setAdapter(ad2);
        addView.setAdapter(ad3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final AlertDialog.Builder inputAlert = new AlertDialog.Builder(context);
                inputAlert.setTitle("Add Freiend");
                inputAlert.setMessage("Please enter user name:");
                final EditText userInput = new EditText(context);
                inputAlert.setView(userInput);
                inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInputValue = userInput.getText().toString();
                        boolean chk = false;
                        if (!global.ChkFrdExit(userInputValue)) {
                            chk = global.AddFrd(userInputValue.toLowerCase());
                        } else {
                            chk = true;
                        }
                        if (chk) {
                            Snackbar.make(view, "Successful", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(view, "User Name not found", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
                inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = inputAlert.create();
                alertDialog.show();
            }
        });

    }
}
