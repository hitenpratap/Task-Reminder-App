package com.TechSect.TaskReminderApp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        registerForContextMenu(getListView());
    }

    protected void onListItemClick(ListView listView,View view, int position, long id){
        super.onListItemClick(listView,view,position,id);
        Intent intent = new Intent(this,ReminderEditActivity.class);
        intent.putExtra("RowId",id);
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
    }


    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuDelete:
                //Delete the Task
                return true;
        }
        return super.onContextItemSelected(item);
    }

    


}
