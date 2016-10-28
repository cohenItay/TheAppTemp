package com.itayc14.theapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itayc14.theapp.purchaseStuff.LockSeatsActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter
  extends ArrayAdapter
  implements View.OnClickListener
{
  private int colorPicker;
  private Context context;
  private ArrayList<Event> eventsList;
  private FireBaseManager fireManager;
  private HashMap<String, RoundedBitmapDrawable> imagesMap;
  private String thisEventTicketPrice;
  
  public MyAdapter(Context paramContext, int paramInt, ArrayList<Event> paramArrayList)
  {
    super(paramContext, paramInt, paramArrayList);
    this.context = paramContext;
    this.eventsList = paramArrayList;
    this.colorPicker = 1;
    this.fireManager = new FireBaseManager();
    this.imagesMap = new HashMap();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    this.thisEventTicketPrice = ((Event)this.eventsList.get(paramInt)).getTicketPrice();
    RelativeLayout localRelativeLayout1;
    PointersToFieldsHolder localPointersToFieldsHolder;
    label218:
    String str;
    if (paramView == null)
    {
      paramView = LayoutInflater.from(this.context).inflate(2130968627, null);
      TextView localTextView1 = (TextView)paramView.findViewById(2131558608);
      TextView localTextView2 = (TextView)paramView.findViewById(2131558609);
      ImageView localImageView2 = (ImageView)paramView.findViewById(2131558607);
      TextView localTextView3 = (TextView)paramView.findViewById(2131558613);
      TextView localTextView4 = (TextView)paramView.findViewById(2131558610);
      localRelativeLayout1 = (RelativeLayout)paramView.findViewById(2131558605);
      RelativeLayout localRelativeLayout2 = (RelativeLayout)paramView.findViewById(2131558611);
      TextView localTextView5 = (TextView)paramView.findViewById(2131558612);
      switch (this.colorPicker)
      {
      default: 
        localPointersToFieldsHolder = new PointersToFieldsHolder(localTextView1, localTextView4, localImageView2, localTextView2, localTextView3, localRelativeLayout2, localTextView5, this.colorPicker);
        this.colorPicker = (1 + this.colorPicker);
        if (this.colorPicker > 3) {
          this.colorPicker = 1;
        }
        paramView.setTag(localPointersToFieldsHolder);
        paramView.setAlpha(0.05F);
        if (!this.thisEventTicketPrice.isEmpty())
        {
          localPointersToFieldsHolder.displayCostMoney.setText("לקנייה");
          localPointersToFieldsHolder.hallPlacesLeft.setText(((Event)this.eventsList.get(paramInt)).getPlacesLeft() + " מקומות נותרו");
          label278:
          localPointersToFieldsHolder.buyBtn.setOnClickListener(this);
          localPointersToFieldsHolder.buyBtn.setId(paramInt);
          localPointersToFieldsHolder.date.setText(((Event)this.eventsList.get(paramInt)).getEventDate());
          localPointersToFieldsHolder.headLine.setText(((Event)this.eventsList.get(paramInt)).getHeadline());
          str = ((Event)this.eventsList.get(paramInt)).getEventImageName();
          if (this.imagesMap.containsKey(str)) {
            break label520;
          }
          FireBaseManager localFireBaseManager = this.fireManager;
          ImageView localImageView1 = localPointersToFieldsHolder.eventImageViewer;
          Resources localResources = this.context.getResources();
          HashMap localHashMap = this.imagesMap;
          localFireBaseManager.downloadSingleImage(localImageView1, str, localResources, paramView, localHashMap);
        }
        break;
      }
    }
    for (;;)
    {
      localPointersToFieldsHolder.body.setText(((Event)this.eventsList.get(paramInt)).getBody());
      return paramView;
      localRelativeLayout1.setBackground(ContextCompat.getDrawable(this.context, 2130837636));
      break;
      localRelativeLayout1.setBackground(ContextCompat.getDrawable(this.context, 2130837637));
      break;
      localRelativeLayout1.setBackground(ContextCompat.getDrawable(this.context, 2130837638));
      break;
      localPointersToFieldsHolder = (PointersToFieldsHolder)paramView.getTag();
      break label218;
      localPointersToFieldsHolder.displayCostMoney.setText("שריין מקום");
      localPointersToFieldsHolder.hallPlacesLeft.setText("");
      break label278;
      label520:
      localPointersToFieldsHolder.eventImageViewer.setImageDrawable((Drawable)this.imagesMap.get(str));
    }
  }
  
  public void onClick(View paramView)
  {
    int i = paramView.getId();
    Intent localIntent = new Intent(this.context, LockSeatsActivity.class);
    Event localEvent = (Event)this.eventsList.get(i);
    localIntent.putExtra("POSITION", i);
    localIntent.putExtra("PRESSED_EVENT", localEvent);
    ((Activity)this.context).startActivityForResult(localIntent, 4324);
  }
  
  public static class PointersToFieldsHolder
  {
    TextView body;
    RelativeLayout buyBtn;
    int color;
    TextView date;
    TextView displayCostMoney;
    ImageView eventImageViewer;
    TextView hallPlacesLeft;
    TextView headLine;
    
    public PointersToFieldsHolder(TextView paramTextView1, TextView paramTextView2, ImageView paramImageView, TextView paramTextView3, TextView paramTextView4, RelativeLayout paramRelativeLayout, TextView paramTextView5, int paramInt)
    {
      this.headLine = paramTextView1;
      this.date = paramTextView2;
      this.eventImageViewer = paramImageView;
      this.body = paramTextView3;
      this.hallPlacesLeft = paramTextView4;
      this.buyBtn = paramRelativeLayout;
      this.displayCostMoney = paramTextView5;
      this.color = paramInt;
    }
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.MyAdapter
 * JD-Core Version:    0.7.0.1
 */