package com.itayc14.theapp.AddEventWizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentHallBuilder
  extends Fragment
  implements View.OnClickListener
{
  public static final int REQUEST_TO_DESIGN_HALL_CODE = 144141;
  Bundle bundle;
  int columns;
  EditText columnsField;
  private Button designBtn;
  boolean doneDesigningHall;
  private ImageView greenV;
  TextView greenV_text;
  boolean isBeingEditNow;
  private CheckBox isDesignNeededField;
  TextView placesLeftField;
  int rows;
  EditText rowsField;
  private JSONArray savedSeatsCode;
  int score;
  private JSONArray seatsCode;
  EditText ticketPriceField;
  
  private void setRowsAndColumnsListenersWithCalculations()
  {
    this.rowsField.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        String str = FragmentHallBuilder.this.rowsField.getText().toString().trim();
        FragmentHallBuilder.this.rows = Integer.parseInt(0 + str);
        if (FragmentHallBuilder.this.rows == 0) {
          FragmentHallBuilder.this.rows = 1;
        }
        FragmentHallBuilder.this.score = (FragmentHallBuilder.this.rows * FragmentHallBuilder.this.columns);
        FragmentHallBuilder.this.placesLeftField.setText("סך כל המקומות בהופעה: " + FragmentHallBuilder.this.score);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
    this.columnsField.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        String str = FragmentHallBuilder.this.columnsField.getText().toString().trim();
        FragmentHallBuilder.this.columns = Integer.parseInt(0 + str);
        if (FragmentHallBuilder.this.columns == 0) {
          FragmentHallBuilder.this.columns = 1;
        }
        FragmentHallBuilder.this.score = (FragmentHallBuilder.this.rows * FragmentHallBuilder.this.columns);
        FragmentHallBuilder.this.placesLeftField.setText("סך כל המקומות בהופעה: " + FragmentHallBuilder.this.score);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    getActivity();
    if (paramInt2 == -1) {
      if (paramInt1 == 144141) {
        this.doneDesigningHall = true;
      }
    }
    do
    {
      try
      {
        this.seatsCode = new JSONArray(paramIntent.getStringExtra("seatscode"));
        this.greenV.setVisibility(0);
        this.greenV_text.setVisibility(0);
        this.score = paramIntent.getIntExtra("TOTAL_PLACES", -1);
        this.placesLeftField.setText("סך כל המקומות בהופעה: " + this.score);
        return;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          localJSONException.printStackTrace();
        }
      }
      getActivity();
    } while (paramInt2 != 0);
    this.seatsCode = new JSONArray();
    this.savedSeatsCode = null;
    this.doneDesigningHall = false;
    this.greenV_text.setVisibility(4);
    this.greenV.setVisibility(4);
  }
  
  public void onClick(View paramView)
  {
    Activity localActivity = getActivity();
    boolean bool = this.isDesignNeededField.isChecked();
    String str = this.ticketPriceField.getText().toString().trim();
    if (paramView == this.designBtn)
    {
      if (!bool)
      {
        this.isDesignNeededField.setError("חייב לאשר שאתה מעוניין בעיצוב אולם");
        return;
      }
      if (this.rows < 2)
      {
        this.rowsField.setError("כמה שורות יהיו באולם?, מינימום 2");
        return;
      }
      if ((this.columns < 2) || (this.columns > 99))
      {
        this.columnsField.setError("כמה כסאות בכל שורה?, בין 2 ל 99");
        return;
      }
      Intent localIntent2 = new Intent(localActivity, HallDesignActivity.class);
      if (this.doneDesigningHall) {
        localIntent2.putExtra("seatscode", this.seatsCode.toString());
      }
      for (;;)
      {
        localIntent2.putExtra("rows", this.rows);
        localIntent2.putExtra("columns", this.columns);
        startActivityForResult(localIntent2, 144141);
        return;
        if (this.savedSeatsCode != null)
        {
          localIntent2.putExtra("seatscode", this.savedSeatsCode.toString());
          this.bundle.remove("seatscode");
        }
      }
    }
    if ((bool) && (!this.doneDesigningHall))
    {
      Toast.makeText(localActivity, "עוד לא עיצבת את האולם", 0).show();
      return;
    }
    if ((this.isBeingEditNow) && (!this.doneDesigningHall))
    {
      Toast.makeText(localActivity, "נא היכנס ושמור את עיצוב האולם מחדש", 0).show();
      return;
    }
    if (this.seatsCode.isNull(0)) {
      try
      {
        JSONObject localJSONObject1 = new JSONObject();
        localJSONObject1.put("HallEntrance", 888855);
        this.seatsCode.put(localJSONObject1);
        for (int i = 1; i <= this.rows; i++)
        {
          int j = i * 100;
          for (int k = 1; k <= this.columns; k++)
          {
            JSONObject localJSONObject2 = new JSONObject();
            localJSONObject2.put("seatStatus", "SeatAvailable");
            localJSONObject2.put("seatID", j + k);
            this.seatsCode.put(localJSONObject2);
          }
        }
        this.bundle.putString("seatscode", this.seatsCode.toString());
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    this.bundle.putInt("rows", this.rows);
    this.bundle.putInt("columns", this.columns);
    this.bundle.putInt("totalPlaces", this.score);
    this.bundle.putString("price", str);
    Intent localIntent1 = new Intent();
    localIntent1.putExtras(this.bundle);
    localActivity.setResult(-1, localIntent1);
    localActivity.finish();
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130968624, null);
    this.rows = 1;
    this.columns = 1;
    this.score = 1;
    this.doneDesigningHall = false;
    this.seatsCode = new JSONArray();
    this.placesLeftField = ((TextView)localView.findViewById(2131558584));
    localView.findViewById(2131558591).setOnClickListener(this);
    this.ticketPriceField = ((EditText)localView.findViewById(2131558583));
    this.rowsField = ((EditText)localView.findViewById(2131558581));
    this.columnsField = ((EditText)localView.findViewById(2131558582));
    this.isDesignNeededField = ((CheckBox)localView.findViewById(2131558586));
    this.designBtn = ((Button)localView.findViewById(2131558587));
    this.designBtn.setOnClickListener(this);
    this.greenV = ((ImageView)localView.findViewById(2131558588));
    this.greenV_text = ((TextView)localView.findViewById(2131558589));
    setRowsAndColumnsListenersWithCalculations();
    this.bundle = getArguments();
    this.isBeingEditNow = this.bundle.getBoolean("isEdit");
    if (this.isBeingEditNow)
    {
      this.columnsField.setText(this.bundle.getInt("columns") + "");
      this.rowsField.setText(this.bundle.getInt("rows") + "");
      this.ticketPriceField.setText(this.bundle.getString("price"));
    }
    try
    {
      this.savedSeatsCode = new JSONArray(this.bundle.getString("seatscode"));
      if (!this.savedSeatsCode.isNull(0))
      {
        this.greenV.setVisibility(0);
        this.greenV_text.setVisibility(0);
      }
      return localView;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.AddEventWizard.FragmentHallBuilder
 * JD-Core Version:    0.7.0.1
 */