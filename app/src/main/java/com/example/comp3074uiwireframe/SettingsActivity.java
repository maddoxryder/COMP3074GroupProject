package com.example.comp3074uiwireframe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchDark, switchNotify;
    private Button btnClearTasks, btnBack;

    private SharedPreferences prefs;
    private static final String PREFS = "SettingsPrefs";
    private static final String DARK = "darkmode";
    private static final String NOTIFY = "notify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        switchDark = findViewById(R.id.switchDarkMode);
        switchNotify = findViewById(R.id.switchNotifications);
        btnClearTasks = findViewById(R.id.btnClearTasks);
        btnBack = findViewById(R.id.btnBackSettings);

        // load saved states
        switchDark.setChecked(prefs.getBoolean(DARK, false));
        switchNotify.setChecked(prefs.getBoolean(NOTIFY, true));

        switchDark.setOnCheckedChangeListener((v, checked) ->
                prefs.edit().putBoolean(DARK, checked).apply());

        switchNotify.setOnCheckedChangeListener((v, checked) ->
                prefs.edit().putBoolean(NOTIFY, checked).apply());

        btnClearTasks.setOnClickListener(v -> {
            getSharedPreferences("TasksPrefs", MODE_PRIVATE)
                    .edit().putString("task_list", "[]").apply();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
