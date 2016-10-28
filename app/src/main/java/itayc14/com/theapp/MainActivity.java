package com.itayc14.theapp;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.itayc14.theapp.AddEventWizard.AddEventActivity;
import com.itayc14.theapp.LoginStuff.Login;
import java.util.ArrayList;

public class MainActivity
  extends AppCompatActivity
  implements AdapterView.OnItemClickListener
{
  public static final int ADD_EVENT = 2323;
  public static final int BUY_TICKETS = 4324;
  public static final int EDIT_EVENT = 2324;
  private MyAdapter adapter;
  private SharedPreferences.Editor editor;
  private ArrayList<Event> eventsList;
  private FireBaseManager fireManager;
  private boolean isUserManager;
  private ListView listView;
  private TextView noUpcommingEvents;
  private ProgressBar progressBar;
  private SharedPreferences settings;
  
  private void EditEvent(Event paramEvent, int paramInt)
  {
    Intent localIntent = new Intent(this, AddEventActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("headline", paramEvent.getHeadline());
    localBundle.putString("body", paramEvent.getBody());
    localBundle.putInt("columns", paramEvent.getHallColumns());
    localBundle.putString("date", paramEvent.getEventDate());
    localBundle.putString("image", paramEvent.getEventImageName());
    localBundle.putInt("rows", paramEvent.getHallRows());
    localBundle.putInt("totalPlaces", paramEvent.getHallAmountOfPlaces());
    localBundle.putString("price", paramEvent.getTicketPrice());
    localBundle.putBoolean("isEdit", true);
    localBundle.putString("seatscode", paramEvent.getSeatsCode());
    localBundle.putString("eventID", paramEvent.getEventID());
    localBundle.putInt("eventPositionInArray", paramInt);
    localBundle.putSerializable("EventPressed", paramEvent);
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 2324);
  }
  
  private void addAndDisplayEvent(final Event paramEvent)
  {
    EventAdder local4 = new EventAdder()
    {
      public void addTheEvent(String paramAnonymousString)
      {
        paramEvent.setEventID(paramAnonymousString);
        MainActivity.this.adapter.add(paramEvent);
        if (MainActivity.this.adapter.getCount() != 0) {
          MainActivity.this.noUpcommingEvents.setVisibility(8);
        }
      }
    };
    this.fireManager.addSingleEvent(paramEvent, local4, this);
  }
  
  private void deleteEvent(final Event paramEvent)
  {
    final EventDeleter local5 = new EventDeleter()
    {
      public void deleteTheEvent()
      {
        MainActivity.this.adapter.remove(paramEvent);
        if (MainActivity.this.adapter.getCount() == 0) {
          MainActivity.this.noUpcommingEvents.setVisibility(0);
        }
      }
    };
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setMessage("למחוק את האירוע?");
    localBuilder.setPositiveButton("כן", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        MainActivity.this.fireManager.deleteSingleEvent(paramEvent.getEventID(), MainActivity.this, local5);
      }
    });
    localBuilder.setNegativeButton("לא", null);
    localBuilder.show();
  }
  
  private Event initializeEvent(Bundle paramBundle)
  {
    Event localEvent1 = new Event(paramBundle.getString("headline"), paramBundle.getString("image"), paramBundle.getString("body"), paramBundle.getInt("columns", -1), paramBundle.getInt("rows", -1), paramBundle.getInt("totalPlaces"), paramBundle.getString("date"), paramBundle.getString("price"), paramBundle.getString("seatscode"), paramBundle.getString("eventID"));
    Event localEvent2 = (Event)paramBundle.getSerializable("EventPressed");
    if (localEvent2 != null)
    {
      localEvent1.setTotalTicketsBought(localEvent2.getTotalTicketsBought());
      localEvent1.setGuestsList(localEvent2.getGuestsList());
    }
    return localEvent1;
  }
  
  private void initializeManagerPreferences()
  {
    CheckIfManager local2 = new CheckIfManager()
    {
      public void DefineIfManager(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          MainActivity.this.editor.putBoolean("IS_MANAGER", true);
          MainActivity.this.editor.commit();
          MainActivity.this.registerForContextMenu(MainActivity.this.listView);
          MainActivity.access$602(MainActivity.this, true);
          jdField_this.invalidateOptionsMenu();
          return;
        }
        MainActivity.this.editor.putBoolean("IS_MANAGER", false);
        MainActivity.this.editor.commit();
      }
    };
    if (!this.settings.contains("IS_MANAGER")) {
      this.fireManager.checkIfCurrentUserIsManager(local2);
    }
    while (this.settings.getBoolean("IS_MANAGER", false) != true) {
      return;
    }
    this.isUserManager = true;
    registerForContextMenu(this.listView);
  }
  
  public void loadExistingEvents()
  {
    this.progressBar.setVisibility(0);
    EventsLoad local1 = new EventsLoad()
    {
      public void eventsLoader(ArrayList<Event> paramAnonymousArrayList)
      {
        MainActivity.access$002(MainActivity.this, paramAnonymousArrayList);
        if (paramAnonymousArrayList.size() == 0) {
          MainActivity.this.noUpcommingEvents.setVisibility(0);
        }
        MainActivity.access$202(MainActivity.this, new MyAdapter(MainActivity.this, 2130968627, paramAnonymousArrayList));
        MainActivity.this.listView.setAdapter(MainActivity.this.adapter);
        MainActivity.this.progressBar.setVisibility(8);
      }
    };
    this.fireManager.loadEvents(local1);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      if (paramInt1 != 2323) {
        break label25;
      }
      addAndDisplayEvent(initializeEvent(paramIntent.getExtras()));
    }
    label25:
    do
    {
      return;
      if (paramInt1 == 2324)
      {
        Bundle localBundle = paramIntent.getExtras();
        final Event localEvent3 = initializeEvent(localBundle);
        EventEditCompleted local3 = new EventEditCompleted()
        {
          public void eventEditCompleted()
          {
            MainActivity.this.adapter.remove(MainActivity.this.adapter.getItem(this.val$editedEventPosition));
            MainActivity.this.adapter.insert(localEvent3, this.val$editedEventPosition);
          }
        };
        this.fireManager.editSingleEvent(localEvent3, local3, this);
        return;
      }
    } while (paramInt1 != 4324);
    Event localEvent1 = (Event)paramIntent.getSerializableExtra("EVENT");
    Event localEvent2 = (Event)this.eventsList.get(paramIntent.getIntExtra("POSITION", -1));
    int i = localEvent2.getTotalTicketsBought() + paramIntent.getIntExtra("TICKETS_AMOUNT", -1);
    Log.d("tag", "updated tatoal tickets bought " + i);
    localEvent2.setTotalTicketsBought(i);
    localEvent2.setGuestsList(localEvent1.getGuestsList());
    this.adapter.notifyDataSetChanged();
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
    Event localEvent = (Event)this.adapter.getItem(localAdapterContextMenuInfo.position);
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return false;
      deleteEvent(localEvent);
      continue;
      EditEvent(localEvent, localAdapterContextMenuInfo.position);
      continue;
      Intent localIntent = new Intent(this, GuestListActivity.class);
      localIntent.putExtra("GUEST_LIST", localEvent);
      startActivity(localIntent);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968607);
    Toolbar localToolbar = (Toolbar)findViewById(2131558537);
    localToolbar.setTitle("תפוס לי מקום");
    setSupportActionBar(localToolbar);
    this.listView = ((ListView)findViewById(2131558538));
    this.progressBar = ((ProgressBar)findViewById(2131558539));
    this.noUpcommingEvents = ((TextView)findViewById(2131558540));
    this.listView.setOnItemClickListener(this);
    this.fireManager = new FireBaseManager();
    this.settings = getSharedPreferences("user_entry_details", 0);
    this.editor = this.settings.edit();
    initializeManagerPreferences();
    loadExistingEvents();
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
    getMenuInflater().inflate(2131623936, paramContextMenu);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    if (this.isUserManager) {
      getMenuInflater().inflate(2131623938, paramMenu);
    }
    for (;;)
    {
      return true;
      getMenuInflater().inflate(2131623937, paramMenu);
    }
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Event localEvent = (Event)this.eventsList.get(paramInt);
    Intent localIntent = new Intent(this, EventsDetailsActivity.class);
    localIntent.putExtra("COLOR", ((MyAdapter.PointersToFieldsHolder)paramView.getTag()).color);
    localIntent.putExtra("EVENT", localEvent);
    localIntent.putExtra("POSITION", paramInt);
    startActivityForResult(localIntent, 4324);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return super.onOptionsItemSelected(paramMenuItem);
      Intent localIntent = new Intent(this, Login.class);
      this.fireManager.logOut();
      this.editor.clear();
      this.editor.commit();
      startActivity(localIntent);
      finish();
      continue;
      startActivityForResult(new Intent(this, AddEventActivity.class), 2323);
    }
  }
  
  public static abstract interface CheckIfManager
  {
    public abstract void DefineIfManager(boolean paramBoolean);
  }
  
  public static abstract interface EventAdder
  {
    public abstract void addTheEvent(String paramString);
  }
  
  public static abstract interface EventDeleter
  {
    public abstract void deleteTheEvent();
  }
  
  public static abstract interface EventEditCompleted
  {
    public abstract void eventEditCompleted();
  }
  
  public static abstract interface EventsLoad
  {
    public abstract void eventsLoader(ArrayList<Event> paramArrayList);
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.MainActivity
 * JD-Core Version:    0.7.0.1
 */