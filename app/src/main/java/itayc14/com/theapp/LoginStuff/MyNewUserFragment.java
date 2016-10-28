package com.itayc14.theapp.LoginStuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.itayc14.theapp.FireBaseManager;
import com.itayc14.theapp.MainActivity;

public class MyNewUserFragment
  extends Fragment
  implements View.OnClickListener, MoveToMainActivityListener
{
  private String chosenRelative;
  private TextView emailField;
  private FireBaseManager fireManager;
  private TextView nameField;
  private TextView passwordField;
  private String[] relatives;
  private Spinner spinner;
  private TextView studentField;
  private View view;
  
  private void setSpinnerPrefs()
  {
    this.relatives = new String[] { "הורה", "דוד", "אח", "חבר", "בחר קרבה לתלמיד" };
    this.spinner = ((Spinner)this.view.findViewById(2131558599));
    ArrayAdapter localArrayAdapter = new ArrayAdapter(getActivity(), 17367043, this.relatives);
    localArrayAdapter.setDropDownViewResource(17367049);
    this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        MyNewUserFragment.access$002(MyNewUserFragment.this, MyNewUserFragment.this.relatives[paramAnonymousInt]);
      }
      
      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
    });
    this.spinner.setAdapter(localArrayAdapter);
    this.spinner.setSelection(4);
  }
  
  private boolean validateInputs()
  {
    boolean bool = true;
    String str1 = this.nameField.getText().toString().trim();
    String str2 = this.emailField.getText().toString().trim();
    String str3 = this.passwordField.getText().toString().trim();
    String str4 = this.spinner.getSelectedItem().toString();
    String str5 = this.studentField.getText().toString().trim();
    if (!str1.contains(" "))
    {
      this.nameField.setError("נא להזין שם מלא");
      bool = false;
    }
    if ((!str2.contains("@")) || (!str2.contains(".")))
    {
      this.emailField.setError("אימייל לא תקני");
      bool = false;
    }
    if (str3.length() < 6)
    {
      this.passwordField.setError("מינימום 6 תווים");
      bool = false;
    }
    if (str4.equals("בחר קרבה לתלמיד"))
    {
      TextView localTextView = (TextView)this.spinner.getSelectedView();
      localTextView.setError("");
      localTextView.setTextColor(-65536);
      localTextView.setText("בחר קרבה");
      bool = false;
    }
    if (!str5.contains(" "))
    {
      this.studentField.setError("נא להזין שם מלא");
      bool = false;
    }
    return bool;
  }
  
  public void onClick(View paramView)
  {
    if ((paramView.getId() == 2131558603) && (validateInputs()))
    {
      this.view.findViewById(2131558604).setVisibility(0);
      User localUser = new User(this.nameField.getText().toString().trim(), this.emailField.getText().toString().trim(), this.passwordField.getText().toString().trim(), this.spinner.getSelectedItem().toString(), this.studentField.getText().toString().trim(), true);
      this.fireManager.createNewUser(localUser, this);
    }
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.view = paramLayoutInflater.inflate(2130968626, null);
    this.view.findViewById(2131558604).setVisibility(4);
    setSpinnerPrefs();
    this.emailField = ((TextView)this.view.findViewById(2131558601));
    this.passwordField = ((TextView)this.view.findViewById(2131558602));
    this.nameField = ((TextView)this.view.findViewById(2131558598));
    this.spinner = ((Spinner)this.view.findViewById(2131558599));
    this.studentField = ((TextView)this.view.findViewById(2131558600));
    this.view.findViewById(2131558603).setOnClickListener(this);
    this.fireManager = new FireBaseManager();
    return this.view;
  }
  
  public void toMainActivityListener()
  {
    startActivity(new Intent(getActivity(), MainActivity.class));
    getActivity().finish();
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.LoginStuff.MyNewUserFragment
 * JD-Core Version:    0.7.0.1
 */