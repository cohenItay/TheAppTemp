package com.itayc14.theapp.AddEventWizard;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HallDesignActivity
  extends AppCompatActivity
  implements View.OnClickListener
{
  public static final String HALL_ENTRANCE = "HallEntrance";
  public static final int HALL_ENTRANCE_LEFT = 888854;
  public static final int HALL_ENTRANCE_RIGHT = 888855;
  public static final String SEAT_AVAILABLE = "SeatAvailable";
  public static final String SEAT_ID = "seatID";
  public static final String SEAT_STATUS = "seatStatus";
  public static final String SEAT_TRANSPARENT = "SeatTransparent";
  public static final String SEAT_UNAVAILABLE = "SeatUnavailable";
  private Button btnSave;
  private Bundle bundle;
  private LinearLayout chosenLinear;
  private CountDownTimer countDownTimer;
  private HorizontalScrollView hScroll;
  private boolean isEntranceLeft;
  private ImageView leftEntrance;
  private LinearLayout mainLinear;
  private ImageButton minusLeft;
  private ImageButton minusRight;
  private ImageButton plusLeft;
  private ImageButton plusRight;
  private ImageView rightEntrance;
  private LinearLayout rowWhichWasPaintedBefore;
  private float scaleFactor;
  private ScaleGestureDetector scaleGestureDetector;
  int totalPlacesCounter;
  private String userReDesigningSeats;
  
  private void loadSeats(@Nullable JSONArray paramJSONArray)
  {
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    if (paramJSONArray == null)
    {
      int k = this.bundle.getInt("rows");
      int m = this.bundle.getInt("columns");
      for (int n = 0; n < k; n++)
      {
        LinearLayout localLinearLayout2 = new LinearLayout(this);
        localLinearLayout2.setLayoutParams(localLayoutParams);
        for (int i1 = 1; i1 <= m; i1++) {
          localLinearLayout2.addView(createNewImage("SeatAvailable"));
        }
        this.mainLinear.addView(localLinearLayout2);
      }
    }
    try
    {
      if (paramJSONArray.getJSONObject(0).getInt("HallEntrance") == 888854)
      {
        this.isEntranceLeft = true;
        this.leftEntrance.setVisibility(0);
        this.rightEntrance.setVisibility(8);
      }
      paramJSONArray.remove(0);
      int i = 200;
      LinearLayout localLinearLayout1 = new LinearLayout(this);
      localLinearLayout1.setLayoutParams(localLayoutParams);
      for (int j = 0; j < paramJSONArray.length(); j++)
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
        if (localJSONObject.getInt("seatID") >= i)
        {
          i += 100;
          this.mainLinear.addView(localLinearLayout1);
          localLinearLayout1 = new LinearLayout(this);
          localLinearLayout1.setLayoutParams(localLayoutParams);
        }
        localLinearLayout1.addView(createNewImage(localJSONObject.getString("seatStatus")));
      }
      this.mainLinear.addView(localLinearLayout1);
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
    this.mainLinear.setGravity(17);
    this.hScroll.postDelayed(new Runnable()
    {
      public void run()
      {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        HallDesignActivity.this.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        HallDesignActivity.this.hScroll.scrollTo(HallDesignActivity.this.hScroll.getMaxScrollAmount() / 2 + i / 2, 0);
      }
    }, 350L);
  }
  
  private void paintChosenRow(ImageView paramImageView)
  {
    if (this.rowWhichWasPaintedBefore != null)
    {
      this.rowWhichWasPaintedBefore.setBackgroundResource(17170445);
      this.rowWhichWasPaintedBefore.setTag(Boolean.valueOf(false));
    }
    this.chosenLinear = ((LinearLayout)paramImageView.getParent());
    this.chosenLinear.setBackgroundResource(17170459);
    this.chosenLinear.setTag(Boolean.valueOf(true));
    this.rowWhichWasPaintedBefore = this.chosenLinear;
  }
  
  private JSONArray saveSeatsDesign()
  {
    JSONArray localJSONArray = new JSONArray();
    Object localObject1 = new JSONObject();
    for (int i = 1; i < this.mainLinear.getChildCount(); i++)
    {
      int i1 = i * 100;
      this.chosenLinear = ((LinearLayout)this.mainLinear.getChildAt(i));
      if (this.isEntranceLeft)
      {
        int i3 = -1 + this.chosenLinear.getChildCount();
        if (i3 >= 0)
        {
          ImageView localImageView3 = (ImageView)this.chosenLinear.getChildAt(i3);
          String str3 = localImageView3.getTag().toString();
          if (str3.equals("SeatAvailable"))
          {
            localImageView3.setId(i1 + 1);
            i1++;
          }
          for (;;)
          {
            i3--;
            break;
            if (str3.equals("SeatTransparent")) {
              localImageView3.setId(100 * (i1 / 100));
            }
          }
        }
      }
      else
      {
        int i2 = 0;
        if (i2 < this.chosenLinear.getChildCount())
        {
          ImageView localImageView2 = (ImageView)this.chosenLinear.getChildAt(i2);
          String str2 = localImageView2.getTag().toString();
          if (str2.equals("SeatAvailable"))
          {
            localImageView2.setId(i1 + 1);
            i1++;
          }
          for (;;)
          {
            i2++;
            break;
            if (str2.equals("SeatTransparent")) {
              localImageView2.setId(100 * (i1 / 100));
            }
          }
        }
      }
    }
    label427:
    label577:
    label594:
    label624:
    for (;;)
    {
      try
      {
        if (this.isEntranceLeft)
        {
          ((JSONObject)localObject1).put("HallEntrance", 888854);
          this.totalPlacesCounter = 0;
          localJSONArray.put(localObject1);
          j = 1;
          if (j >= this.mainLinear.getChildCount()) {
            break label427;
          }
          this.chosenLinear = ((LinearLayout)this.mainLinear.getChildAt(j));
          k = 0;
          localObject2 = localObject1;
        }
      }
      catch (JSONException localJSONException1)
      {
        try
        {
          if (k >= this.chosenLinear.getChildCount()) {
            break label577;
          }
          localJSONObject = new JSONObject();
          ImageView localImageView1 = (ImageView)this.chosenLinear.getChildAt(k);
          m = localImageView1.getId();
          str1 = (String)localImageView1.getTag();
          n = -1;
          switch (str1.hashCode())
          {
          case -986447324: 
            ((JSONObject)localObject1).put("HallEntrance", 888855);
          }
        }
        catch (JSONException localJSONException2)
        {
          int j;
          int k;
          JSONObject localJSONObject;
          int m;
          String str1;
          int n;
          continue;
          switch (n)
          {
          }
          k++;
          Object localObject2 = localJSONObject;
        }
        localJSONException1 = localJSONException1;
        localJSONException1.printStackTrace();
      }
      return localJSONArray;
      if (str1.equals("SeatAvailable"))
      {
        n = 0;
        break label594;
        if (str1.equals("SeatUnavailable"))
        {
          n = 1;
          break label594;
          if (str1.equals("SeatTransparent"))
          {
            n = 2;
            break label594;
            localJSONObject.put("seatStatus", "SeatAvailable");
            localJSONObject.put("seatID", m);
            localJSONArray.put(localJSONObject);
            this.totalPlacesCounter = (1 + this.totalPlacesCounter);
            break label624;
            localJSONObject.put("seatStatus", "SeatUnavailable");
            localJSONObject.put("seatID", m);
            localJSONArray.put(localJSONObject);
            break label624;
            localJSONObject.put("seatStatus", "SeatTransparent");
            localJSONObject.put("seatID", m);
            localJSONArray.put(localJSONObject);
            break label624;
            j++;
            localObject1 = localObject2;
            continue;
          }
        }
      }
    }
  }
  
  private CountDownTimer setCounterAndStart()
  {
    new CountDownTimer(3500L, 3300L)
    {
      public void onFinish()
      {
        HallDesignActivity.this.plusLeft.setVisibility(8);
        HallDesignActivity.this.plusRight.setVisibility(8);
        HallDesignActivity.this.minusLeft.setVisibility(8);
        HallDesignActivity.this.minusRight.setVisibility(8);
        HallDesignActivity.this.btnSave.setVisibility(8);
        HallDesignActivity.this.chosenLinear.setBackgroundResource(17170445);
      }
      
      public void onTick(long paramAnonymousLong) {}
    }.start();
  }
  
  private boolean setEditBtnsCommands(View paramView)
  {
    if (paramView == this.rightEntrance)
    {
      this.rightEntrance.setVisibility(8);
      this.leftEntrance.setVisibility(0);
      Toast.makeText(this, "כסא מספר 1 יתחיל בצד שמאל", 0).show();
      this.isEntranceLeft = true;
      return true;
    }
    if (paramView == this.leftEntrance)
    {
      this.leftEntrance.setVisibility(8);
      this.rightEntrance.setVisibility(0);
      Toast.makeText(this, "כסא מספר 1 יתחיל בצד ימין", 0).show();
      this.isEntranceLeft = false;
      return true;
    }
    if (this.countDownTimer != null) {
      this.countDownTimer.cancel();
    }
    this.countDownTimer = setCounterAndStart();
    this.plusLeft.setVisibility(0);
    this.plusRight.setVisibility(0);
    this.minusLeft.setVisibility(0);
    this.minusRight.setVisibility(0);
    this.btnSave.setVisibility(0);
    if (paramView == this.plusLeft)
    {
      this.chosenLinear.addView(createNewImage("SeatAvailable"));
      return true;
    }
    if (paramView == this.minusLeft)
    {
      this.chosenLinear.removeView(this.chosenLinear.getChildAt(-1 + this.chosenLinear.getChildCount()));
      return true;
    }
    if (paramView == this.plusRight)
    {
      this.chosenLinear.addView(createNewImage("SeatAvailable"), 0);
      return true;
    }
    if (paramView == this.minusRight)
    {
      this.chosenLinear.removeView(this.chosenLinear.getChildAt(0));
      return true;
    }
    if (paramView == this.btnSave)
    {
      JSONArray localJSONArray = saveSeatsDesign();
      Intent localIntent = new Intent();
      localIntent.putExtra("seatscode", localJSONArray.toString());
      localIntent.putExtra("TOTAL_PLACES", this.totalPlacesCounter);
      setResult(-1, localIntent);
      finish();
      return true;
    }
    return false;
  }
  
  public ImageView createNewImage(String paramString)
  {
    ImageView localImageView = new ImageView(this);
    localImageView.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
    localImageView.setOnClickListener(this);
    localImageView.setTag(paramString);
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
    return localImageView;
    localImageView.setImageResource(17170445);
    return localImageView;
  }
  
  public void onBackPressed()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("בטוח שאתה מעוניין לצאת?");
    localBuilder.setMessage("עיצוב האולם לא ישמר ללא לחיצה על כתפור 'שמור'");
    localBuilder.setPositiveButton("צא ואפס עיצוב", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Intent localIntent = new Intent();
        HallDesignActivity.this.setResult(0, localIntent);
        HallDesignActivity.this.finish();
      }
    });
    localBuilder.setNegativeButton("שמור", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        JSONArray localJSONArray = HallDesignActivity.this.saveSeatsDesign();
        Intent localIntent = new Intent();
        localIntent.putExtra("seatscode", localJSONArray.toString());
        localIntent.putExtra("TOTAL_PLACES", HallDesignActivity.this.totalPlacesCounter);
        HallDesignActivity.this.setResult(-1, localIntent);
        HallDesignActivity.this.finish();
      }
    });
    localBuilder.show();
  }
  
  public void onClick(View paramView)
  {
    if (setEditBtnsCommands(paramView)) {
      return;
    }
    ImageView localImageView = (ImageView)paramView;
    paintChosenRow(localImageView);
    String str = (String)localImageView.getTag();
    label64:
    int i;
    switch (str.hashCode())
    {
    default: 
      i = -1;
    }
    for (;;)
    {
      switch (i)
      {
      case 1: 
      default: 
        return;
      case 0: 
        localImageView.setImageResource(17170445);
        localImageView.setTag("SeatTransparent");
        return;
        if (!str.equals("SeatAvailable")) {
          break label64;
        }
        i = 0;
        continue;
        if (!str.equals("SeatUnavailable")) {
          break label64;
        }
        i = 1;
        continue;
        if (!str.equals("SeatTransparent")) {
          break label64;
        }
        i = 2;
      }
    }
    localImageView.setImageResource(2130837641);
    localImageView.setTag("SeatAvailable");
    Log.d("tag", "sit id is: " + localImageView.getId());
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968605);
    setRequestedOrientation(0);
    this.bundle = getIntent().getExtras();
    this.isEntranceLeft = false;
    this.plusLeft = ((ImageButton)findViewById(2131558529));
    this.plusLeft.setOnClickListener(this);
    this.plusRight = ((ImageButton)findViewById(2131558527));
    this.plusRight.setOnClickListener(this);
    this.minusLeft = ((ImageButton)findViewById(2131558528));
    this.minusLeft.setOnClickListener(this);
    this.minusRight = ((ImageButton)findViewById(2131558526));
    this.minusRight.setOnClickListener(this);
    this.btnSave = ((Button)findViewById(2131558532));
    this.btnSave.setOnClickListener(this);
    this.leftEntrance = ((ImageView)findViewById(2131558531));
    this.leftEntrance.setOnClickListener(this);
    this.rightEntrance = ((ImageView)findViewById(2131558530));
    this.rightEntrance.setOnClickListener(this);
    this.hScroll = ((HorizontalScrollView)findViewById(2131558524));
    this.mainLinear = ((LinearLayout)findViewById(2131558525));
    this.userReDesigningSeats = getIntent().getStringExtra("seatscode");
    if ((this.userReDesigningSeats == null) || (this.userReDesigningSeats.isEmpty())) {
      loadSeats(null);
    }
    for (;;)
    {
      this.scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener(null)) {};
      return;
      try
      {
        Log.d("tag", "before loading code: " + this.userReDesigningSeats.toString());
        loadSeats(new JSONArray(this.userReDesigningSeats));
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.scaleGestureDetector.onTouchEvent(paramMotionEvent);
    return true;
  }
  
  private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener
  {
    private ScaleListener() {}
    
    public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
    {
      HallDesignActivity.access$202(HallDesignActivity.this, paramScaleGestureDetector.getScaleFactor());
      Log.d("tag", "factor: " + HallDesignActivity.this.scaleFactor);
      return true;
    }
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.AddEventWizard.HallDesignActivity
 * JD-Core Version:    0.7.0.1
 */