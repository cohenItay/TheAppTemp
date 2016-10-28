package com.itayc14.theapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.itayc14.theapp.purchaseStuff.LockSeatsActivity;

public class EventsDetailsActivity
  extends AppCompatActivity
  implements View.OnClickListener
{
  private TextView body;
  private TextView buyBtn;
  private TextView date;
  private Event event;
  private TextView headline;
  private TextView placesLeft;
  private int positionInAdapter;
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 4324))
    {
      setResult(-1, paramIntent);
      finish();
    }
  }
  
  public void onClick(View paramView)
  {
    if (paramView == this.buyBtn)
    {
      Intent localIntent = new Intent(this, LockSeatsActivity.class);
      localIntent.putExtra("POSITION", this.positionInAdapter);
      localIntent.putExtra("PRESSED_EVENT", this.event);
      startActivityForResult(localIntent, 4324);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968603);
    Intent localIntent = getIntent();
    this.event = ((Event)localIntent.getSerializableExtra("EVENT"));
    this.buyBtn = ((TextView)findViewById(2131558520));
    this.buyBtn.setOnClickListener(this);
    if (this.event.getTicketPrice().isEmpty()) {
      this.buyBtn.setText("שריין מקום");
    }
    this.body = ((TextView)findViewById(2131558519));
    this.headline = ((TextView)findViewById(2131558517));
    this.date = ((TextView)findViewById(2131558518));
    this.placesLeft = ((TextView)findViewById(2131558521));
    switch (localIntent.getIntExtra("COLOR", -1))
    {
    }
    for (;;)
    {
      this.positionInAdapter = localIntent.getIntExtra("POSITION", -1);
      this.body.setText(this.event.getBody());
      this.date.setText(this.event.getEventDate());
      this.headline.setText(this.event.getHeadline());
      this.placesLeft.setText("נותרו " + this.event.getPlacesLeft() + " מקומות פנויים");
      return;
      this.headline.setBackground(ContextCompat.getDrawable(this, 2130837636));
      continue;
      this.headline.setBackground(ContextCompat.getDrawable(this, 2130837637));
      continue;
      this.headline.setBackground(ContextCompat.getDrawable(this, 2130837638));
    }
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.EventsDetailsActivity
 * JD-Core Version:    0.7.0.1
 */