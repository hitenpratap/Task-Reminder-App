package com.TechSect.TaskReminderApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.database.Cursor;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {
	
	private static final String TAG = ComponentInfo.class.getCanonicalName();  
	
	@Override
	public void onReceive(Context context, Intent intent) {

		ReminderManager reminderMgr = new ReminderManager(context);
		
		RemindersDbAdapter dbHelper = new RemindersDbAdapter(context);
		dbHelper.open();
			
		Cursor cursor = dbHelper.fetchAllReminders();
		
		if(cursor != null) {
			cursor.moveToFirst(); 
			
			int rowIdColumnIndex = cursor.getColumnIndex(RemindersDbAdapter.KEY_ROWID);
			int dateTimeColumnIndex = cursor.getColumnIndex(RemindersDbAdapter.KEY_DATE_TIME); 
			
			while(cursor.isAfterLast() == false) {

				Log.d(TAG, "Adding alarm from boot.");
				Log.d(TAG, "Row Id Column Index - " + rowIdColumnIndex);
				Log.d(TAG, "Date Time Column Index - " + dateTimeColumnIndex);
				
				Long rowId = cursor.getLong(rowIdColumnIndex); 
				String dateTime = cursor.getString(dateTimeColumnIndex); 

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat(ReminderEditActivity.DATE_TIME_FORMAT); 
				
				try {
					java.util.Date date = format.parse(dateTime);
					cal.setTime(date);
					
					reminderMgr.setReminder(rowId, cal); 
				} catch (java.text.ParseException e) {
					Log.e("OnBootReceiver", e.getMessage(), e);
				}
				
				cursor.moveToNext(); 
			}
			cursor.close() ;	
		}
		
		dbHelper.close(); 
	}
}

