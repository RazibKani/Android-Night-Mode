package info.razibkani.nightmodesample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String NIGHT_MODE = "night_mode";

    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // init shared preferences
        mSharedPref = getPreferences(Context.MODE_PRIVATE);

        if (isNightModeEnabled()) {
            setAppTheme(R.style.AppTheme_Base_Night);
        } else {
            setAppTheme(R.style.AppTheme_Base_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerAdapter adapter = new RecyclerAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter.setNewsList(getNewsList());
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String message = getString(R.string.bookmark);
                Toast.makeText(view.getContext(), String.format(message, position), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

    }

    private ArrayList<News> getNewsList() {
        ArrayList<News> newsList = new ArrayList<>();

        int index = 0;

        while (index < 10) {
            News news = new News();
            news.title = getString(R.string.str_item_title);
            news.description = getString(R.string.str_item_description);
            news.date = new Date().toString();

            newsList.add(index, news);

            index++;
        }

        return newsList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Inflate switch
        Switch mSwitchNightMode = (Switch) menu.findItem(R.id.action_night_mode)
                .getActionView().findViewById(R.id.item_switch);

        // Get state from preferences
        if (isNightModeEnabled()) {
            mSwitchNightMode.setChecked(true);
        } else {
            mSwitchNightMode.setChecked(false);
        }

        mSwitchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isNightModeEnabled()) {
                    setIsNightModeEnabled(false);
                    setAppTheme(R.style.AppTheme_Base_Light);
                } else {
                    setIsNightModeEnabled(true);
                    setAppTheme(R.style.AppTheme_Base_Night);
                }

                // Recreate activity
                recreate();
            }
        });

        return true;
    }

    private void setAppTheme(@StyleRes int style) {
        setTheme(style);
    }

    private boolean isNightModeEnabled() {
        return  mSharedPref.getBoolean(NIGHT_MODE, false);
    }

    private void setIsNightModeEnabled(boolean state) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putBoolean(NIGHT_MODE, state);
        mEditor.apply();
    }
}
