package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hitenpratap on 28/9/14.
 */
public class ReminderEditActivity extends Activity {

    private Button mDateButton;
    private Button mTimeButton;
    private static final int DATE_PICKER_DIALOG = 0;
    private static final int TIME_PICKER_DIALOG = 1;
    private Calendar mCalendar;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";

    private EditText titleText;
    private EditText bodyText;
    private Button confirmBtn;

    private Long rowId;

    private ReminderDBAdapter dbAdapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_edit);

        rowId = savedInstanceState!=null?savedInstanceState.getLong(ReminderDBAdapter.KEY_ROWID) : null;

        mCalendar = Calendar.getInstance();
        mDateButton = (Button)findViewById(R.id.date);
        mTimeButton = (Button) findViewById(R.id.time);
        dbAdapter = new ReminderDBAdapter(this);

        titleText = (EditText)findViewById(R.id.title);
        bodyText = (EditText)findViewById(R.id.body);
        confirmBtn = (Button)findViewById(R.id.confirm);

        registerButtonListenersAndSetDefaultText();
    }

    private void setRowIdFromIntent(){
        if(rowId == null){
           Bundle extras = getIntent().getExtras();
            rowId = extras!=null?extras.getLong(ReminderDBAdapter.KEY_ROWID):null;
        }
    }

    protected void onPause(){
        super.onPause();
        dbAdapter.close();
    }

    protected void onResume(){
        super.onResume();
        dbAdapter.open();
        setRowIdFromIntent();
        populateFields();
    }

    private void populateFields(){
        if(rowId!=null){
            Cursor reminder = dbAdapter.fetchReminder(rowId);
            startManagingCursor(reminder);
            titleText.setText(reminder.getString(reminder.getColumnIndexOrThrow(ReminderDBAdapter.KEY_TITLE)));
            bodyText.setText(reminder.getString(reminder.getColumnIndexOrThrow(ReminderDBAdapter.KEY_BODY)));

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = null;
            try{
                String dateString = reminder.getString(reminder.getColumnIndexOrThrow(ReminderDBAdapter.KEY_DATE_TIME));
                date = dateFormat.parse(dateString);
                mCalendar.setTime(date);
            }catch (ParseException pe){
                Log.e("ReminderEditActivity", pe.getMessage(), pe);
            }
        }
        updateTimeButtonText();
        updateDateButtonText();
    }

    protected void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        savedState.putLong(ReminderDBAdapter.KEY_ROWID,rowId);
    }

    private void registerButtonListenersAndSetDefaultText() {
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_DIALOG);
            }
        });
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_PICKER_DIALOG);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveState();
                setResult(RESULT_OK);
                Toast.makeText(ReminderEditActivity.this,getString(R.string.taskSavedMessage),Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateDateButtonText();
        updateTimeButtonText();
    }

    public void saveState(){
        String title = titleText.getText().toString();
        String body = bodyText.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String reminderDateTime = dateFormat.format(mCalendar.getTime());

        if(rowId == null){
            long id= dbAdapter.createReminder(title,body,reminderDateTime);
            if(id > 0)
                rowId = id;
        }else{
            dbAdapter.updateReminder(rowId,title,body,reminderDateTime);
        }

    }

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_PICKER_DIALOG:
                return showDatePicker();
            case TIME_PICKER_DIALOG:
                return showTimePicker();
        }
        return super.onCreateDialog(id);
    }

    private DatePickerDialog showDatePicker(){
        return new DatePickerDialog(ReminderEditActivity.this,new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth){
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateButtonText();
            }
        },mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void updateDateButtonText(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(mCalendar.getTime());
        mDateButton.setText(dateForButton);
    }

    private TimePickerDialog showTimePicker(){
        return new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                mCalendar.set(Calendar.MINUTE,minute);
                updateTimeButtonText();
            }
        },mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE),true);
    }

    private void updateTimeButtonText(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        String timeForButton = dateFormat.format(mCalendar.getTime());
        mTimeButton.setText(timeForButton);
    }


}
