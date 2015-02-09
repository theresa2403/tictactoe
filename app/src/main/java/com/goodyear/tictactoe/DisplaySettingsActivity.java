package com.goodyear.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;


public class DisplaySettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);

        SharedPreferences sharedPref = getApplication().getSharedPreferences(getString(R.string.preference_settings), Context.MODE_PRIVATE);

        Switch switchNecessaryClicks = (Switch) findViewById(R.id.switch_necessaryClicks);
        // set check of switch
        switchNecessaryClicks.setChecked(sharedPref.getBoolean(getString(R.string.settings_necessaryClicks), false));
        switchNecessaryClicks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplication();
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_settings), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.settings_necessaryClicks), isChecked);
                editor.commit();
            }
        });

        Switch switchMultiplayer = (Switch) findViewById(R.id.switch_multiplayer);
        switchMultiplayer.setChecked(sharedPref.getBoolean(getString(R.string.settings_multiplayer), true));
        RadioGroup radioGroup_multiplayerMode = (RadioGroup) findViewById(R.id.radioGroup_multiplayerMode);
        if(sharedPref.getBoolean(getString(R.string.settings_multiplayer), true)) {
            for (int i = 0; i < radioGroup_multiplayerMode.getChildCount(); i++) {
                radioGroup_multiplayerMode.getChildAt(i).setEnabled(false);
            }
        } else {
            for (int i = 0; i < radioGroup_multiplayerMode.getChildCount(); i++) {
                radioGroup_multiplayerMode.getChildAt(i).setEnabled(true);
            }
        }

        switchMultiplayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplication();
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_settings), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.settings_multiplayer), isChecked);
                editor.commit();
                RadioGroup radioGroup_multiplayerMode = (RadioGroup) findViewById(R.id.radioGroup_multiplayerMode);
                if(isChecked) {
                    for (int i = 0; i < radioGroup_multiplayerMode.getChildCount(); i++) {
                        radioGroup_multiplayerMode.getChildAt(i).setEnabled(false);
                    }
                } else {
                    for (int i = 0; i < radioGroup_multiplayerMode.getChildCount(); i++) {
                        radioGroup_multiplayerMode.getChildAt(i).setEnabled(true);
                    }
                }
            }
        });

        String mode = sharedPref.getString(getString(R.string.settings_mode), "EASY");
        RadioButton radioButton;
        if(mode.equals("EASY")) {
            radioButton = (RadioButton) findViewById(R.id.radioButton_easy);
            radioButton.setChecked(true);
        } else if(mode.equals("MIDDLE")) {
            radioButton = (RadioButton) findViewById(R.id.radioButton_middle);
            radioButton.setChecked(true);
        } else if(mode.equals("DIFFICULT")) {
            radioButton = (RadioButton) findViewById(R.id.radioButton_difficult);
            radioButton.setChecked(true);
        }
    }

    public void setSingleplayerMode(View view) {
        Context context = getApplication();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_settings), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(R.id.radioButton_easy == view.getId()) {
            editor.putString(getString(R.string.settings_mode), "EASY");
        } else if(R.id.radioButton_middle == view.getId()) {
            editor.putString(getString(R.string.settings_mode), "MIDDLE");
        } else if(R.id.radioButton_difficult == view.getId()) {
            editor.putString(getString(R.string.settings_mode), "DIFFICULT");
        }
        editor.commit();
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
