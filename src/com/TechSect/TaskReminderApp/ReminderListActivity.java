package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

public class ReminderListActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);
    }
}
