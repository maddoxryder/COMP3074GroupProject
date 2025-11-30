package com.example.comp3074uiwireframe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class TasksActivity extends AppCompatActivity {

    private LinearLayout taskContainer;
    private EditText inputTask;
    private Button btnAddTask;
    private SharedPreferences prefs;

    private String userRole;
    private static final String PREF_KEY = "task_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        userRole = getIntent().getStringExtra("userRole");
        prefs = getSharedPreferences("TasksPrefs", MODE_PRIVATE);

        Button back = findViewById(R.id.backButtonTasks);
        taskContainer = findViewById(R.id.taskContainer);
        inputTask = findViewById(R.id.inputTask);
        btnAddTask = findViewById(R.id.btnAddTask);

        back.setOnClickListener(v -> finish());

        // manager can add tasks
        if ("manager".equals(userRole)) {
            inputTask.setVisibility(View.VISIBLE);
            btnAddTask.setVisibility(View.VISIBLE);

            btnAddTask.setOnClickListener(v -> {
                String text = inputTask.getText().toString().trim();
                if (text.isEmpty()) return;
                addTask(text, false);
                saveTask(text, false);
                inputTask.setText("");
            });
        }

        loadTasks();
    }

    private void loadTasks() {
        try {
            JSONArray arr = new JSONArray(prefs.getString(PREF_KEY, "[]"));
            for (int i = 0; i < arr.length(); i++) {
                JSONArray t = arr.getJSONArray(i);
                String desc = t.getString(0);
                boolean done = t.getBoolean(1);
                addTask(desc, done);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void addTask(String text, boolean completed) {
        // inflate task row xml
        View row = getLayoutInflater().inflate(R.layout.task_item, null);

        CheckBox chk = row.findViewById(R.id.checkTask);
        Button btnDelete = row.findViewById(R.id.btnDeleteTask);

        chk.setText(text);
        chk.setChecked(completed);

        // staff cannot delete tasks
        if ("manager".equals(userRole)) {
            btnDelete.setVisibility(View.VISIBLE);
        }

        chk.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateTask(text, isChecked));

        btnDelete.setOnClickListener(v -> {
            taskContainer.removeView(row);
            deleteTask(text);
        });

        taskContainer.addView(row);
    }

    private void saveTask(String text, boolean completed) {
        try {
            JSONArray arr = new JSONArray(prefs.getString(PREF_KEY, "[]"));
            JSONArray t = new JSONArray();
            t.put(text);
            t.put(completed);
            arr.put(t);
            prefs.edit().putString(PREF_KEY, arr.toString()).apply();
        } catch (JSONException e) { e.printStackTrace(); }
    }

    private void updateTask(String text, boolean completed) {
        try {
            JSONArray arr = new JSONArray(prefs.getString(PREF_KEY, "[]"));
            for (int i = 0; i < arr.length(); i++) {
                JSONArray t = arr.getJSONArray(i);
                if (t.getString(0).equals(text)) {
                    t.put(1, completed);
                    break;
                }
            }
            prefs.edit().putString(PREF_KEY, arr.toString()).apply();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void deleteTask(String text) {
        try {
            JSONArray arr = new JSONArray(prefs.getString(PREF_KEY, "[]"));
            JSONArray newArr = new JSONArray();

            for (int i = 0; i < arr.length(); i++) {
                JSONArray t = arr.getJSONArray(i);
                if (!t.getString(0).equals(text)) {
                    newArr.put(t);
                }
            }
            prefs.edit().putString(PREF_KEY, newArr.toString()).apply();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
