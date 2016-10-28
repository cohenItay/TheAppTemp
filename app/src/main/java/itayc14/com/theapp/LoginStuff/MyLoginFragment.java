package com.itayc14.theapp.LoginStuff;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.itayc14.theapp.FireBaseManager;

public class MyLoginFragment
  extends Fragment
  implements View.OnClickListener
{
  public static SharedPreferences.Editor editor;
  public static SharedPreferences settings;
  Activity act;
  CheckBox checkBox;
  EditText emailField;
  FireBaseManager fireManager;
  TextView forgotPassword;
  EditText passwordField;
  private ProgressBar progressBar;
  private userEntreyDetailsRemover remover;
  View view;
  
  private void removeUserEntryDetails()
  {
    if ((settings.contains("USEREMAIL")) || (settings.contains("USERPASSWORD")))
    {
      editor.remove("USEREMAIL");
      editor.remove("USERPASSWORD");
      editor.commit();
    }
  }
  
  private boolean validateInputs()
  {
    boolean bool = true;
    String str1 = this.passwordField.getText().toString().trim();
    String str2 = this.emailField.getText().toString().trim();
    if (str1.length() < 6)
    {
      this.passwordField.setError("סיסמה קצרה מידיי");
      bool = false;
    }
    if ((!str2.contains("@")) || (!str2.contains(".")))
    {
      this.emailField.setError("אימייל לא תקני");
      bool = false;
    }
    return bool;
  }
  
  public void onClick(View paramView)
  {
    String str1 = this.emailField.getText().toString().trim();
    String str2 = this.passwordField.getText().toString().trim();
    if (paramView.getId() == 2131558593)
    {
      if (validateInputs())
      {
        this.progressBar.setVisibility(0);
        this.fireManager.loginWithListener(str1, str2, this.act, this.remover);
      }
      return;
    }
    if (paramView == this.forgotPassword)
    {
      if ((str1.isEmpty()) || (str1.equals("")) || (str1 == null))
      {
        this.emailField.setError("ציין email אליו תישלח סיסמת איפוס");
        this.emailField.requestFocus();
        return;
      }
      this.fireManager.resetUserPassword(str1, this.act);
      return;
    }
    if (this.checkBox.isChecked())
    {
      editor.putString("USEREMAIL", str1);
      editor.putString("USERPASSWORD", str2);
      editor.commit();
      return;
    }
    removeUserEntryDetails();
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.fireManager = new FireBaseManager();
    this.view = paramLayoutInflater.inflate(2130968625, null);
    this.act = getActivity();
    this.view.findViewById(2131558593).setOnClickListener(this);
    this.view.findViewById(2131558596).setVisibility(4);
    this.checkBox = ((CheckBox)this.view.findViewById(2131558595));
    this.checkBox.setOnClickListener(this);
    this.forgotPassword = ((TextView)this.view.findViewById(2131558597));
    this.forgotPassword.setOnClickListener(this);
    this.emailField = ((EditText)this.view.findViewById(2131558592));
    this.passwordField = ((EditText)this.view.findViewById(2131558594));
    this.progressBar = ((ProgressBar)this.view.findViewById(2131558596));
    this.remover = new userEntreyDetailsRemover()
    {
      public void removeEntryDetails()
      {
        MyLoginFragment.this.removeUserEntryDetails();
        MyLoginFragment.this.progressBar.setVisibility(4);
      }
    };
    settings = this.act.getSharedPreferences("user_entry_details", 0);
    editor = settings.edit();
    return this.view;
  }
  
  public static abstract interface userEntreyDetailsRemover
  {
    public abstract void removeEntryDetails();
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.LoginStuff.MyLoginFragment
 * JD-Core Version:    0.7.0.1
 */