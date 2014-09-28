package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ReminderListActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);
        String[] list = new String[]{"Task Element1","Task Element2","Task Element3","Task Element4","Task Element5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.reminder_row,R.id.text1,list);
        setListAdapter(adapter);
    }
}
