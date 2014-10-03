package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by hitenpratap on 28/9/14.
 */
public class ReminderEditActivity extends Activity {

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.reminder_edit);
        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            int rowId = extras!=null?extras.getInt("RowId"): -1;
        }
    }

}
