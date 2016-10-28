package com.itayc14.theapp.purchaseStuff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.itayc14.theapp.Event;
import com.itayc14.theapp.FireBaseManager;
import com.itayc14.theapp.Threads.LockSeatsForFewMinThread;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LockSeatsActivity
  extends AppCompatActivity
  implements View.OnClickListener, Serializable
{
  private static final String LOCK_SEAT = "LockSeat";
  public static Activity lockSeatActivity;
  private boolean alreadyChecked;
  private ImageView continueBtn;
  private Event event;
  private String eventId;
  private FireBaseManager fireManager;
  private int firstSeatInLastRowID;
  private HorizontalScrollView hScroll;
  private ArrayList<Integer> indexOfLockedSeats;
  private boolean isEntranceLeft;
  private int lastSeatID;
  private int lastSeatOfFirstRowID;
  private ArrayList<Integer> lockedSeats;
  private LinearLayout mainLinear;
  private int positionInMainActivityList;
  private ProgressBar progressBar;
  private ImageView seatImage;
  private JSONArray seatsCode;
  private LockSeatsForFewMinThread thread;
  
  private void buildHallView()
  {
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    LinearLayout localLinearLayout;
    try
    {
      if (this.seatsCode.getJSONObject(0).getInt("HallEntrance") == 888854) {}
      for (this.isEntranceLeft = true;; this.isEntranceLeft = false)
      {
        this.seatsCode.remove(0);
        int i = 200;
        localLinearLayout = new LinearLayout(this);
        localLinearLayout.setLayoutParams(localLayoutParams);
        for (int j = 0; j < this.seatsCode.length(); j++)
        {
          JSONObject localJSONObject = this.seatsCode.getJSONObject(j);
          int k = localJSONObject.getInt("seatID");
          if (k >= i)
          {
            i += 100;
            this.mainLinear.addView(localLinearLayout);
            if (!this.alreadyChecked) {
              checkLastSeatOfFirstRowID(localLinearLayout);
            }
            localLinearLayout = new LinearLayout(this);
            localLinearLayout.setLayoutParams(localLayoutParams);
          }
          localLinearLayout.addView(createNewImage(localJSONObject.getString("seatStatus"), k));
        }
      }
      this.mainLinear.setGravity(17);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    for (;;)
    {
      this.hScroll.postDelayed(new Runnable()
      {
        public void run()
        {
          DisplayMetrics localDisplayMetrics = new DisplayMetrics();
          LockSeatsActivity.this.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
          int i = localDisplayMetrics.widthPixels;
          LockSeatsActivity.this.hScroll.scrollTo(LockSeatsActivity.this.hScroll.getMaxScrollAmount() / 2 + i / 2, 0);
        }
      }, 350L);
      return;
      this.mainLinear.addView(localLinearLayout);
      if (this.isEntranceLeft)
      {
        this.lastSeatID = localLinearLayout.getChildAt(0).getId();
        this.firstSeatInLastRowID = localLinearLayout.getChildAt(-1 + localLinearLayout.getChildCount()).getId();
      }
      else
      {
        this.lastSeatID = localLinearLayout.getChildAt(-1 + localLinearLayout.getChildCount()).getId();
        this.firstSeatInLastRowID = localLinearLayout.getChildAt(0).getId();
      }
    }
  }
  
  private void checkLastSeatOfFirstRowID(LinearLayout paramLinearLayout)
  {
    if (this.isEntranceLeft) {}
    for (this.lastSeatOfFirstRowID = paramLinearLayout.getChildAt(0).getId();; this.lastSeatOfFirstRowID = paramLinearLayout.getChildAt(-1 + paramLinearLayout.getChildCount()).getId())
    {
      this.alreadyChecked = true;
      return;
    }
  }
  
  private boolean isLeavingSingleSeat()
  {
    Collections.sort(this.lockedSeats, new Comparator()
    {
      public int compare(Integer paramAnonymousInteger1, Integer paramAnonymousInteger2)
      {
        if (paramAnonymousInteger1.intValue() > paramAnonymousInteger2.intValue()) {
          return 1;
        }
        if (paramAnonymousInteger1 == paramAnonymousInteger2) {
          return 0;
        }
        return -1;
      }
    });
    int i = ((Integer)this.lockedSeats.get(0)).intValue();
    for (int j = 1; j < this.lockedSeats.size(); j++)
    {
      if (i + 1 != ((Integer)this.lockedSeats.get(j)).intValue()) {
        return false;
      }
      i = ((Integer)this.lockedSeats.get(j)).intValue();
    }
    for (;;)
    {
      int k;
      try
      {
        if ((this.lockedSeats.contains(Integer.valueOf(101))) || (this.lockedSeats.contains(Integer.valueOf(this.lastSeatOfFirstRowID))) || (this.lockedSeats.contains(Integer.valueOf(this.firstSeatInLastRowID)))) {
          break label693;
        }
        if (!this.lockedSeats.contains(Integer.valueOf(this.lastSeatID))) {
          break label695;
        }
      }
      catch (JSONException localJSONException)
      {
        int m;
        JSONObject localJSONObject1;
        JSONObject localJSONObject2;
        int n;
        String str1;
        int i1;
        String str2;
        int i2;
        int i3;
        int i4;
        String str3;
        String str4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean bool;
        localJSONException.printStackTrace();
      }
      m = this.seatsCode.length();
      localJSONObject1 = null;
      localJSONObject2 = null;
      JSONObject localJSONObject3 = null;
      JSONObject localJSONObject4 = null;
      if (k < m)
      {
        if (this.seatsCode.getJSONObject(k).getInt("seatID") != ((Integer)this.lockedSeats.get(0)).intValue()) {
          break label727;
        }
        if (this.isEntranceLeft)
        {
          localJSONObject1 = this.seatsCode.getJSONObject(k + 1);
          localJSONObject2 = this.seatsCode.getJSONObject(k - this.lockedSeats.size());
          if (localJSONObject1.getInt("seatID") == this.firstSeatInLastRowID) {
            break label703;
          }
          localJSONObject4 = this.seatsCode.getJSONObject(k + 2);
          if (localJSONObject2.getInt("seatID") == this.lastSeatOfFirstRowID) {
            break label709;
          }
          localJSONObject3 = this.seatsCode.getJSONObject(-1 + (k - this.lockedSeats.size()));
        }
      }
      else
      {
        n = localJSONObject1.getInt("seatID");
        str1 = localJSONObject1.getString("seatStatus");
        i1 = localJSONObject2.getInt("seatID");
        str2 = localJSONObject2.getString("seatStatus");
        i2 = ((Integer)this.lockedSeats.get(0)).intValue() / 100;
        i3 = n / 100;
        i4 = i1 / 100;
        if ((str1.equals("SeatTransparent")) || (str1.equals("SeatUnavailable")) || (str2.equals("SeatTransparent")) || (str2.equals("SeatUnavailable")) || (i3 != i2)) {
          break label701;
        }
        if (i4 == i2) {
          continue;
        }
        break label701;
      }
      localJSONObject1 = this.seatsCode.getJSONObject(k + this.lockedSeats.size());
      localJSONObject2 = this.seatsCode.getJSONObject(k - 1);
      if (localJSONObject1.getInt("seatID") != this.lastSeatID)
      {
        localJSONObject4 = this.seatsCode.getJSONObject(1 + (k + this.lockedSeats.size()));
        if (localJSONObject2.getInt("seatID") != 101)
        {
          localJSONObject3 = this.seatsCode.getJSONObject(k - 2);
          continue;
          str3 = "";
          str4 = "";
          i5 = 0;
          i6 = 0;
          if (localJSONObject4 != null)
          {
            i5 = localJSONObject4.getInt("seatID");
            str4 = localJSONObject4.getString("seatStatus");
            i6 = i5 / 100;
          }
          i7 = 0;
          i8 = 0;
          if (localJSONObject3 != null)
          {
            i7 = localJSONObject3.getInt("seatID");
            str3 = localJSONObject3.getString("seatStatus");
            i8 = i7 / 100;
          }
          if ((i5 != 0) && (i7 != 0) && (!str4.equals("SeatTransparent")) && (!str4.equals("SeatUnavailable")) && (!str3.equals("SeatTransparent"))) {
            bool = str3.equals("SeatUnavailable");
          }
          return (bool) || (i6 != i2) || (i8 != i2);
          label693:
          return false;
          label695:
          k = 0;
          continue;
          label701:
          return false;
          label703:
          localJSONObject4 = null;
          continue;
          label709:
          localJSONObject3 = null;
          continue;
        }
      }
      else
      {
        localJSONObject4 = null;
        continue;
      }
      localJSONObject3 = null;
      continue;
      label727:
      k++;
    }
  }
  
  public void continueToPurchaseActivity(String paramString, ArrayList<Integer> paramArrayList)
  {
    this.indexOfLockedSeats = paramArrayList;
    this.thread.setIndexOfLockedSeats(paramArrayList);
    Intent localIntent = new Intent(this, PurchaseActivity.class);
    localIntent.putExtra("POSITION", this.positionInMainActivityList);
    localIntent.putExtra("PRESSED_EVENT", this.event);
    localIntent.putExtra("LOCKED_SEATS_ID", this.lockedSeats.toString());
    localIntent.putExtra("AMOUNT_OF_LOCKED_SEATS", this.lockedSeats.size());
    this.progressBar.setVisibility(8);
    startActivity(localIntent);
  }
  
  public ImageView createNewImage(String paramString, int paramInt)
  {
    ImageView localImageView = new ImageView(this);
    localImageView.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
    localImageView.setOnClickListener(this);
    localImageView.setTag(paramString);
    localImageView.setId(paramInt);
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        return localImageView;
        if (paramString.equals("SeatAvailable"))
        {
          i = 0;
          continue;
          if (paramString.equals("SeatUnavailable"))
          {
            i = 1;
            continue;
            if (paramString.equals("SeatTransparent")) {
              i = 2;
            }
          }
        }
        break;
      }
    }
    localImageView.setImageResource(2130837642);
    return localImageView;
    localImageView.setImageResource(2130837641);
    localImageView.setOnClickListener(null);
    return localImageView;
    localImageView.setImageResource(17170445);
    localImageView.setOnClickListener(null);
    return localImageView;
  }
  
  public void onBackPressed()
  {
    if (this.indexOfLockedSeats != null) {
      this.fireManager.unlockSeats(this.indexOfLockedSeats, this.event);
    }
    this.thread.setIsKillingThreadNeeded(true);
    super.onBackPressed();
  }
  
  public void onClick(View paramView)
  {
    new JSONObject();
    if (paramView != this.continueBtn)
    {
      this.seatImage = ((ImageView)paramView);
      str = (String)this.seatImage.getTag();
      switch (str.hashCode())
      {
      default: 
        i = -1;
        switch (i)
        {
        }
        break;
      }
    }
    while (paramView != this.continueBtn)
    {
      for (;;)
      {
        String str;
        int i;
        return;
        if (str.equals("SeatAvailable"))
        {
          i = 0;
          continue;
          if (str.equals("LockSeat")) {
            i = 1;
          }
        }
      }
      this.seatImage.setImageResource(2130837643);
      this.seatImage.setTag("LockSeat");
      this.lockedSeats.add(Integer.valueOf(this.seatImage.getId()));
      return;
      this.seatImage.setImageResource(2130837642);
      this.seatImage.setTag("SeatAvailable");
      this.lockedSeats.remove(Integer.valueOf(this.seatImage.getId()));
      return;
    }
    if (this.lockedSeats.size() > 0)
    {
      if (isLeavingSingleSeat())
      {
        Toast.makeText(this, "נא הצמד את הכסאות ימינה או שמאלה", 0).show();
        return;
      }
      this.progressBar.setVisibility(0);
      this.mainLinear.setAlpha(0.3F);
      this.fireManager.fromAvailableSeatsToUnavailableSeats(this.lockedSeats, this.event, this);
      return;
    }
    Toast.makeText(this, "חובה לבחור בלפחות כסא אחד", 0).show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968609);
    setRequestedOrientation(0);
    getWindow().addFlags(1024);
    this.fireManager = new FireBaseManager();
    this.event = ((Event)getIntent().getSerializableExtra("PRESSED_EVENT"));
    this.eventId = this.event.getEventID();
    lockSeatActivity = this;
    try
    {
      this.seatsCode = new JSONArray(this.event.getSeatsCode());
      this.progressBar = ((ProgressBar)findViewById(2131558558));
      this.positionInMainActivityList = getIntent().getIntExtra("POSITION", -1);
      this.hScroll = ((HorizontalScrollView)findViewById(2131558524));
      this.mainLinear = ((LinearLayout)findViewById(2131558525));
      this.continueBtn = ((ImageView)findViewById(2131558557));
      this.continueBtn.setOnClickListener(this);
      this.continueBtn.bringToFront();
      this.lockedSeats = new ArrayList();
      this.thread = new LockSeatsForFewMinThread(this, this.event);
      this.thread.start();
      this.fireManager.getSingleEventSeatsCode(this.eventId, new HallBuild()
      {
        public void startBuildHall(String paramAnonymousString)
        {
          LockSeatsActivity.this.event.setSeatsCode(paramAnonymousString);
          try
          {
            LockSeatsActivity.access$102(LockSeatsActivity.this, new JSONArray(paramAnonymousString));
            LockSeatsActivity.this.buildHallView();
            LockSeatsActivity.this.progressBar.setVisibility(8);
            return;
          }
          catch (JSONException localJSONException)
          {
            for (;;)
            {
              localJSONException.printStackTrace();
            }
          }
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mainLinear.setAlpha(1.0F);
  }
  
  public void reloadSeats()
  {
    this.mainLinear.removeViews(1, -1 + this.mainLinear.getChildCount());
    this.fireManager.getSingleEventSeatsCode(this.eventId, new HallBuild()
    {
      public void startBuildHall(String paramAnonymousString)
      {
        LockSeatsActivity.this.event.setSeatsCode(paramAnonymousString);
        try
        {
          LockSeatsActivity.access$102(LockSeatsActivity.this, new JSONArray(paramAnonymousString));
          LockSeatsActivity.this.buildHallView();
          return;
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            localJSONException.printStackTrace();
          }
        }
      }
    });
  }
  
  public static abstract interface HallBuild
  {
    public abstract void startBuildHall(String paramString);
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.purchaseStuff.LockSeatsActivity
 * JD-Core Version:    0.7.0.1
 */