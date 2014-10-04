package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.reminder_edit);
        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            int rowId = extras!=null?extras.getInt("RowId"): -1;
        }
        mDateButton = (Button)findViewById(R.id.date);
        mTimeButton = (Button) findViewById(R.id.time);
        registerButtonListenersAndSetDefaultText();
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
        mCalendar = Calendar.getInstance();
        updateDateButtonText();
        updateTimeButtonText();
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
        return new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
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
        mDateButton.setText(timeForButton);
    }


}
