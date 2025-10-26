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
    private EditText taskInput;
    private Button assignButton;
    private String userRole;
    private SharedPreferences prefs;
    private static final String PREFS_KEY = "task_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        userRole = getIntent().getStringExtra("userRole");
        prefs = getSharedPreferences("TasksPrefs", MODE_PRIVATE);

        Button backButton = findViewById(R.id.backButtonTasks);
        backButton.setOnClickListener(v -> finish());

        taskContainer = findViewById(R.id.taskContainer);

        if ("manager".equals(userRole)) {
            taskInput = new EditText(this);
            taskInput.setHint("Enter task description");

            assignButton = new Button(this);
            assignButton.setText("Assign Task");

            assignButton.setOnClickListener(v -> {
                String task = taskInput.getText().toString().trim();
                if (!task.isEmpty()) {
                    addTask(task, false);
                    saveTask(task, false);
                    taskInput.setText("");
                }
            });

            taskContainer.addView(taskInput, 0);
            taskContainer.addView(assignButton, 1);
        }

        loadTasks();
    }

    private void addTask(String description, boolean completed) {
        LinearLayout taskRow = new LinearLayout(this);
        taskRow.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox taskCheckBox = new CheckBox(this);
        taskCheckBox.setText(description);
        taskCheckBox.setChecked(completed);

        taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateTaskStatus(description, isChecked);
            Toast.makeText(this,
                    isChecked ? "Task marked complete" : "Task marked incomplete",
                    Toast.LENGTH_SHORT).show();
        });

        taskRow.addView(taskCheckBox);

        if ("manager".equals(userRole)) {
            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(v -> {
                taskContainer.removeView(taskRow);
                deleteTask(description);
                Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
            });
            taskRow.addView(deleteButton);
        }

        taskContainer.addView(taskRow);
    }


    private void saveTask(String description, boolean completed) {
        try {
            JSONArray taskArray = new JSONArray(prefs.getString(PREFS_KEY, "[]"));
            JSONArray newTask = new JSONArray();
            newTask.put(description);
            newTask.put(completed);
            taskArray.put(newTask);
            prefs.edit().putString(PREFS_KEY, taskArray.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        try {
            JSONArray taskArray = new JSONArray(prefs.getString(PREFS_KEY, "[]"));
            for (int i = 0; i < taskArray.length(); i++) {
                JSONArray task = taskArray.getJSONArray(i);
                String description = task.getString(0);
                boolean completed = task.getBoolean(1);
                addTask(description, completed);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateTaskStatus(String description, boolean completed) {
        try {
            JSONArray taskArray = new JSONArray(prefs.getString(PREFS_KEY, "[]"));
            for (int i = 0; i < taskArray.length(); i++) {
                JSONArray task = taskArray.getJSONArray(i);
                if (task.getString(0).equals(description)) {
                    task.put(1, completed);
                    break;
                }
            }
            prefs.edit().putString(PREFS_KEY, taskArray.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void deleteTask(String description) {
        try {
            JSONArray taskArray = new JSONArray(prefs.getString(PREFS_KEY, "[]"));
            JSONArray newArray = new JSONArray();
            for (int i = 0; i < taskArray.length(); i++) {
                JSONArray task = taskArray.getJSONArray(i);
                if (!task.getString(0).equals(description)) {
                    newArray.put(task);
                }
            }
            prefs.edit().putString(PREFS_KEY, newArray.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
