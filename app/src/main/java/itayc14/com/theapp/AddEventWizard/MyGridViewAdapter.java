package com.itayc14.theapp.AddEventWizard;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyGridViewAdapter
  extends BaseAdapter
{
  private Bitmap[] bitmapArray;
  private Context context;
  
  public MyGridViewAdapter(Context paramContext, Bitmap[] paramArrayOfBitmap)
  {
    this.context = paramContext;
    this.bitmapArray = paramArrayOfBitmap;
  }
  
  public int getCount()
  {
    return this.bitmapArray.length;
  }
  
  public Object getItem(int paramInt)
  {
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ImageView localImageView;
    if (paramView == null)
    {
      localImageView = new ImageView(this.context);
      localImageView.setLayoutParams(new AbsListView.LayoutParams(500, 279));
      localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
    for (;;)
    {
      localImageView.setImageBitmap(this.bitmapArray[paramInt]);
      return localImageView;
      localImageView = (ImageView)paramView;
    }
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.AddEventWizard.MyGridViewAdapter
 * JD-Core Version:    0.7.0.1
 */