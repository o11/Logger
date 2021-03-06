/*
 *                    GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 ******************************************************************************/

package semiworld.org.logger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import semiworld.org.logger.R;
import semiworld.org.logger.adapters.NoteAdapter;
import semiworld.org.logger.models.Note;

public class MainActivity extends BaseActivity {

    /*
    Here we have initialize of view or component of ui
    ButterKnife save our times to initialize every ui component by writing findViewById and then casting
    This is standart definition for ButtonKnife
    Then after you HAVE TO initialize ButterKnife in onCreate method or else it will return null
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Note> noteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Init();
    }

    private void Init() {
        adapter = new NoteAdapter(noteList);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /*
    * Here we get our data from database and put it in a temp list then add them all to master list
      */
    @Override
    protected void onResume() {
        super.onResume();
        List<Note> notes = new Select().from(Note.class).orderBy("id DESC").execute();
        noteList.clear();
        noteList.addAll(notes);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab)
    void fab_clicked() {
        startActivity(new Intent(getApplicationContext(), SecondActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_info:
                Info();
                break;
            case R.id.action_whoami:
                WhoAmI();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void WhoAmI() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("Who am I?")
                .content("\n\nDesigned by @ozcaan11 | 2017")
                .iconRes(android.R.drawable.ic_menu_help)
                .show();
    }


    private void Info() {
        String totalRecord = String.valueOf(new Select().from(Note.class).count());
        new MaterialDialog.Builder(MainActivity.this)
                .title("Info")
                .content("\nTotal record: "+totalRecord)
                .iconRes(android.R.drawable.ic_menu_info_details)
                .show();
    }
}
