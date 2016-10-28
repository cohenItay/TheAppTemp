package com.itayc14.theapp.purchaseStuff;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.itayc14.theapp.Event;
import com.itayc14.theapp.FireBaseManager;
import com.itayc14.theapp.MainActivity;
import com.itayc14.theapp.Threads.LockSeatsForFewMinThread;
import com.itayc14.theapp.Threads.PurchaseThread;
import java.util.HashMap;

public class PurchaseActivity
  extends AppCompatActivity
  implements View.OnClickListener
{
  private EditText IDField;
  private EditText costumerEmailField;
  private EditText creditCardNumField;
  private EditText cvvField;
  private buyBtnEnabler enabler;
  private String eventID;
  private EditText ex_mmField;
  private EditText ex_yyField;
  private FireBaseManager fireManager;
  private boolean isFreeTicket;
  private String lockedSeatsIds;
  private HashMap<String, String> params;
  private int placesLeft;
  private SuccessPoper poper;
  private int position;
  private TextView priceField;
  private Button purchaseBtn;
  private EditText receiptNameField;
  private LockSeatsForFewMinThread thread;
  private int ticketPrice;
  private String ticketPriceString;
  private int ticketsAmount;
  private TextView ticketsAmountField;
  
  private void initializeSuccessDialog()
  {
    this.poper = new SuccessPoper()
    {
      public void popSuccess(Activity paramAnonymousActivity, final Event paramAnonymousEvent)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramAnonymousActivity);
        localBuilder.setTitle("הרכישה בוצעה בהצלחה");
        localBuilder.setIcon(2130837633);
        localBuilder.setMessage("    ");
        final Intent localIntent = new Intent(paramAnonymousActivity, MainActivity.class);
        localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            localIntent.putExtra("TICKETS_AMOUNT", PurchaseActivity.this.ticketsAmount);
            localIntent.putExtra("POSITION", PurchaseActivity.this.position);
            localIntent.putExtra("EVENT", paramAnonymousEvent);
            LockSeatsActivity.lockSeatActivity.setResult(-1, localIntent);
            LockSeatsActivity.lockSeatActivity.finish();
            PurchaseActivity.this.finish();
          }
        });
        AlertDialog localAlertDialog = localBuilder.create();
        localAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
          public void onDismiss(DialogInterface paramAnonymous2DialogInterface)
          {
            localIntent.putExtra("TICKETS_AMOUNT", PurchaseActivity.this.ticketsAmount);
            localIntent.putExtra("POSITION", PurchaseActivity.this.position);
            localIntent.putExtra("EVENT", paramAnonymousEvent);
            LockSeatsActivity.lockSeatActivity.setResult(-1, localIntent);
            LockSeatsActivity.lockSeatActivity.finish();
            PurchaseActivity.this.finish();
          }
        });
        localAlertDialog.show();
      }
    };
  }
  
  private boolean validateInputs(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    boolean bool = true;
    if (paramString1.isEmpty())
    {
      this.receiptNameField.setError("נא להזין שם מלא");
      bool = false;
    }
    if ((!paramString2.contains("@")) || (!paramString2.contains(".")))
    {
      this.costumerEmailField.setError("אימייל לא תקני");
      bool = false;
    }
    if (!this.isFreeTicket)
    {
      if (paramString3.isEmpty())
      {
        this.creditCardNumField.setError("נא להזין מספר כרטיס");
        bool = false;
      }
      if ((paramString4.length() > 4) || (paramString4.length() < 4))
      {
        this.ex_mmField.setError("נא להזין 2 ספרות בלבד");
        bool = false;
      }
      if ((paramString5.length() < 3) || (paramString5.length() > 3))
      {
        this.cvvField.setError("נא להזין 3 ספרות אבטחה");
        bool = false;
      }
      if ((paramString6.length() < 9) || (paramString6.length() > 9))
      {
        this.IDField.setError("נא להזין 9 ספרות");
        bool = false;
      }
    }
    return bool;
  }
  
  public void initializeParams()
  {
    this.params.clear();
    this.params.put("supplier", "itaytest");
    this.params.put("tranmode", "A");
    this.params.put("currency", "1");
    this.params.put("cred_type", "1");
  }
  
  public void onBackPressed()
  {
    this.thread.setContext(LockSeatsActivity.lockSeatActivity);
    super.onBackPressed();
  }
  
  public void onClick(View paramView)
  {
    if ((paramView == this.purchaseBtn) && (!this.isFreeTicket))
    {
      int i = this.ticketsAmount * this.ticketPrice;
      String str1 = this.receiptNameField.getText().toString().trim();
      String str2 = this.costumerEmailField.getText().toString().trim();
      String str3 = this.creditCardNumField.getText().toString().trim();
      String str4 = this.ex_mmField.getText().toString().trim() + this.ex_yyField.getText().toString().trim();
      String str5 = this.cvvField.getText().toString().trim();
      String str6 = this.IDField.getText().toString().trim();
      if ((validateInputs(str1, str2, str3, str4, str5, str6)) && (this.placesLeft >= this.ticketsAmount) && (this.ticketsAmount > 0))
      {
        this.purchaseBtn.setOnClickListener(null);
        this.params.put("sum", i + "");
        this.params.put("ccno", str3);
        this.params.put("expdate", str4);
        this.params.put("mycvv", str5);
        this.params.put("myid", str6);
        new PurchaseThread(this.params, this.ticketsAmount, this.eventID, this, this.poper, this.enabler).start();
      }
      return;
    }
    LockSeatsForFewMinThread.getCurrentThread().setIsKillingThreadNeeded(true);
    this.fireManager.addToEventGuestList(this.ticketsAmount, this.eventID, this, this.poper);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968608);
    this.thread = LockSeatsForFewMinThread.getCurrentThread();
    this.thread.setContext(this);
    this.fireManager = new FireBaseManager();
    initializeSuccessDialog();
    this.enabler = new buyBtnEnabler()
    {
      public void enableBuyBtn()
      {
        PurchaseActivity.this.purchaseBtn.setOnClickListener(PurchaseActivity.this);
      }
    };
    Event localEvent = (Event)getIntent().getSerializableExtra("PRESSED_EVENT");
    this.isFreeTicket = localEvent.getTicketPrice().isEmpty();
    if (!this.isFreeTicket) {
      this.ticketPrice = Integer.parseInt(localEvent.getTicketPrice());
    }
    this.eventID = localEvent.getEventID();
    this.position = getIntent().getIntExtra("POSITION", -1);
    this.placesLeft = localEvent.getPlacesLeft();
    this.ticketsAmountField = ((TextView)findViewById(2131558553));
    this.purchaseBtn = ((Button)findViewById(2131558554));
    this.purchaseBtn.setOnClickListener(this);
    this.priceField = ((TextView)findViewById(2131558555));
    this.creditCardNumField = ((EditText)findViewById(2131558545));
    this.ex_mmField = ((EditText)findViewById(2131558548));
    this.ex_yyField = ((EditText)findViewById(2131558549));
    this.cvvField = ((EditText)findViewById(2131558550));
    this.IDField = ((EditText)findViewById(2131558552));
    this.receiptNameField = ((EditText)findViewById(2131558544));
    this.costumerEmailField = ((EditText)findViewById(2131558543));
    this.lockedSeatsIds = getIntent().getStringExtra("LOCKED_SEATS_ID");
    this.ticketsAmount = getIntent().getIntExtra("AMOUNT_OF_LOCKED_SEATS", -1);
    this.ticketsAmountField.setText("כמות כרטיסים: " + this.ticketsAmount);
    this.priceField.setText(this.ticketPrice * this.ticketsAmount + "₪");
    this.params = new HashMap();
    initializeParams();
    if (this.isFreeTicket)
    {
      findViewById(2131558547).setAlpha(0.15F);
      ((TextView)findViewById(2131558546)).setVisibility(0);
      this.costumerEmailField.setImeOptions(6);
    }
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  public static abstract interface SuccessPoper
  {
    public abstract void popSuccess(Activity paramActivity, Event paramEvent);
  }
  
  public static abstract interface buyBtnEnabler
  {
    public abstract void enableBuyBtn();
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.purchaseStuff.PurchaseActivity
 * JD-Core Version:    0.7.0.1
 */