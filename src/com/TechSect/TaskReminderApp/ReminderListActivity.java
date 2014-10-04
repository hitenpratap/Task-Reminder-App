package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ReminderListActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */

    private ReminderDBAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);
        dbAdapter = new ReminderDBAdapter(this);
        dbAdapter.open();
        fillData();
        registerForContextMenu(getListView());
    }

    public void fillData(){
        Cursor reminderCursors = dbAdapter.fetchAllReminders();
        startManagingCursor(reminderCursors);
        String[] from = new String[]{ReminderDBAdapter.KEY_TITLE};

        int[] to = new int[]{R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.reminder_list,reminderCursors,from,to);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView listView,View view, int position, long id){
        super.onListItemClick(listView,view,position,id);
        Intent intent = new Intent(this,ReminderEditActivity.class);
        intent.putExtra(ReminderDBAdapter.KEY_ROWID,id);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view,ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(contextMenu,view,contextMenuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu_item_longpress,contextMenu);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu,menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId,MenuItem item){
        switch (item.getItemId()){
            case R.id.menuInsert:
                createReminder();
                return true;
        }
        return super.onMenuItemSelected(featureId,item);
    }

    private static final int ACTIVITY_CREATE = 0;

    private void createReminder(){
        Intent intent = new Intent(this,ReminderEditActivity.class);
        startActivityForResult(intent,ACTIVITY_CREATE);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        fillData();
    }


    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuDelete:
                //Delete the Task
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                dbAdapter.deleteReminder(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    


}
