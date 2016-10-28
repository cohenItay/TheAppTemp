package com.itayc14.theapp.AddEventWizard;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddEventActivity
  extends AppCompatActivity
{
  public static final String BODY_TAG = "body";
  public static final String DATE_TAG = "date";
  public static final String EVENT_IMAGE = "image";
  public static final String EVENT_PRICE_TAG = "price";
  public static final String HALL_COLUMNS_TAG = "columns";
  public static final String HALL_ROWS_TAG = "rows";
  public static final String HALL_TOTALPLACES_TAG = "totalPlaces";
  public static final String HEADLINE_TAG = "headline";
  public static final String IS_BEING_EDIT_TAG = "isEdit";
  public static final String SEATS_CODE = "seatscode";
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968602);
    Bundle localBundle = getIntent().getExtras();
    FragmentEventHeadline localFragmentEventHeadline = new FragmentEventHeadline();
    localFragmentEventHeadline.setArguments(localBundle);
    getFragmentManager().beginTransaction().replace(2131558516, localFragmentEventHeadline).commit();
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.AddEventWizard.AddEventActivity
 * JD-Core Version:    0.7.0.1
 */