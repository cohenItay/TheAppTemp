package com.itayc14.theapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

public class GuestListActivity
  extends AppCompatActivity
{
  Drawable cellShape;
  ArrayList<GuestListAttendee> guestListAttendees;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968604);
    Resources localResources = getResources();
    try
    {
      this.cellShape = Drawable.createFromXml(localResources, localResources.getXml(2131099648));
      FireBaseManager localFireBaseManager = new FireBaseManager();
      final TableLayout localTableLayout = (TableLayout)findViewById(2131558523);
      TextView localTextView = (TextView)findViewById(2131558522);
      Event localEvent = (Event)getIntent().getSerializableExtra("GUEST_LIST");
      localTextView.setText("סך הכול מוזמנים: " + (localEvent.getHallAmountOfPlaces() - localEvent.getPlacesLeft()));
      GuestListLoad local1 = new GuestListLoad()
      {
        public void loadGuestList(ArrayList<GuestListAttendee> paramAnonymousArrayList)
        {
          GuestListActivity.this.guestListAttendees = paramAnonymousArrayList;
          for (int i = 0; i < GuestListActivity.this.guestListAttendees.size(); i++)
          {
            TableRow localTableRow = new TableRow(GuestListActivity.this);
            TextView localTextView1 = new TextView(GuestListActivity.this);
            localTextView1.setText(((GuestListAttendee)GuestListActivity.this.guestListAttendees.get(i)).getName());
            localTextView1.setGravity(17);
            localTextView1.setBackground(GuestListActivity.this.cellShape);
            TextView localTextView2 = new TextView(GuestListActivity.this);
            localTextView2.setText(((GuestListAttendee)GuestListActivity.this.guestListAttendees.get(i)).getRelative());
            localTextView2.setGravity(17);
            localTextView2.setBackground(GuestListActivity.this.cellShape);
            TextView localTextView3 = new TextView(GuestListActivity.this);
            localTextView3.setText(((GuestListAttendee)GuestListActivity.this.guestListAttendees.get(i)).getTicketsBought() + "");
            localTextView3.setGravity(17);
            localTextView3.setBackground(GuestListActivity.this.cellShape);
            localTableRow.addView(localTextView1, this.val$params);
            localTableRow.addView(localTextView2, this.val$params);
            localTableRow.addView(localTextView3, this.val$params);
            localTableLayout.addView(localTableRow);
          }
        }
      };
      localFireBaseManager.getEventGuestList(localEvent.getEventID(), local1);
      return;
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      for (;;)
      {
        localXmlPullParserException.printStackTrace();
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
  }
  
  public static abstract interface GuestListLoad
  {
    public abstract void loadGuestList(ArrayList<GuestListAttendee> paramArrayList);
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.GuestListActivity
 * JD-Core Version:    0.7.0.1
 */