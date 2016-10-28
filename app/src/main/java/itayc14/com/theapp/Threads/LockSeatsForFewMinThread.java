package com.itayc14.theapp.Threads;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.itayc14.theapp.Event;
import com.itayc14.theapp.FireBaseManager;
import com.itayc14.theapp.purchaseStuff.LockSeatsActivity;
import java.io.Serializable;
import java.util.ArrayList;

public class LockSeatsForFewMinThread
  extends Thread
  implements Serializable
{
  private static LockSeatsForFewMinThread currentThread;
  private Context context;
  private Event event;
  private FireBaseManager fireManager;
  private Handler handler = new Handler();
  private ArrayList<Integer> indexOfLockedSeats;
  private boolean isKillingThreadNeeded;
  
  public LockSeatsForFewMinThread(Context paramContext, Event paramEvent)
  {
    this.context = paramContext;
    this.event = paramEvent;
    this.isKillingThreadNeeded = false;
    this.fireManager = new FireBaseManager();
    this.indexOfLockedSeats = new ArrayList();
  }
  
  public static LockSeatsForFewMinThread getCurrentThread()
  {
    return currentThread;
  }
  
  public void run()
  {
    currentThread = this;
    int i = 0;
    if (i < 14) {}
    do
    {
      try
      {
        sleep(30000L);
        if (this.isKillingThreadNeeded)
        {
          currentThread = null;
          Log.d("tag", "thread killed");
          return;
        }
        i++;
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      if (!this.indexOfLockedSeats.isEmpty()) {
        this.fireManager.unlockSeats(this.indexOfLockedSeats, this.event);
      }
    } while (this.context == null);
    this.handler.post(new Runnable()
    {
      public void run()
      {
        final Activity localActivity = (Activity)LockSeatsForFewMinThread.this.context;
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(LockSeatsForFewMinThread.this.context);
        localBuilder.setCancelable(false);
        localBuilder.setTitle("זמן הרכישה תם");
        localBuilder.setMessage("לא ניתן לשמור מקומות באולם מעל ל7 דקות. מה תרצה לעשות?");
        localBuilder.setPositiveButton("להתחיל מחדש", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            Intent localIntent = new Intent(LockSeatsForFewMinThread.this.context, LockSeatsActivity.class);
            localIntent.putExtra("PRESSED_EVENT", LockSeatsForFewMinThread.this.event);
            localActivity.startActivityForResult(localIntent, 4324);
            LockSeatsActivity.lockSeatActivity.finish();
            localActivity.finish();
          }
        });
        localBuilder.setNegativeButton("לסיים את התהליך", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            localActivity.setResult(0);
            LockSeatsActivity.lockSeatActivity.setResult(0);
            LockSeatsActivity.lockSeatActivity.finish();
            localActivity.finish();
          }
        });
        localBuilder.show();
      }
    });
    currentThread = null;
  }
  
  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public void setIndexOfLockedSeats(ArrayList<Integer> paramArrayList)
  {
    this.indexOfLockedSeats = paramArrayList;
  }
  
  public void setIsKillingThreadNeeded(boolean paramBoolean)
  {
    this.isKillingThreadNeeded = paramBoolean;
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.Threads.LockSeatsForFewMinThread
 * JD-Core Version:    0.7.0.1
 */