package com.itayc14.theapp.LoginStuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Spinner;
import com.google.firebase.auth.FirebaseAuth;
import com.itayc14.theapp.FireBaseManager;
import com.itayc14.theapp.MainActivity;

public class Login
  extends AppCompatActivity
  implements View.OnClickListener, MoveToMainActivityListener
{
  public static final String USER_EMAIL = "USEREMAIL";
  public static final String USER_PASSWORD = "USERPASSWORD";
  public static SharedPreferences.Editor editor;
  public static SharedPreferences settings;
  String chosenRelative;
  FireBaseManager fireManager;
  String[] relatives;
  Spinner spinner;
  
  private boolean checkIfUserAlreadyConnected()
  {
    if ((settings.contains("USEREMAIL")) && (settings.contains("USERPASSWORD")))
    {
      String str1 = settings.getString("USEREMAIL", "null");
      String str2 = settings.getString("USERPASSWORD", "null");
      this.fireManager.loginWithListener(str1, str2, this, null);
      return true;
    }
    return false;
  }
  
  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131558534)
    {
      MyNewUserFragment localMyNewUserFragment = new MyNewUserFragment();
      getSupportFragmentManager().beginTransaction().setCustomAnimations(17432578, 17432579).replace(2131558536, localMyNewUserFragment).commit();
    }
    if (paramView.getId() == 2131558533)
    {
      MyLoginFragment localMyLoginFragment = new MyLoginFragment();
      getSupportFragmentManager().beginTransaction().setCustomAnimations(2131034129, 2131034130).replace(2131558536, localMyLoginFragment).commit();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968606);
    this.fireManager = new FireBaseManager();
    settings = getSharedPreferences("user_entry_details", 0);
    editor = settings.edit();
    ProgressBar localProgressBar = (ProgressBar)findViewById(2131558535);
    localProgressBar.setVisibility(0);
    if (!checkIfUserAlreadyConnected())
    {
      localProgressBar.setVisibility(4);
      MyLoginFragment localMyLoginFragment = new MyLoginFragment();
      getSupportFragmentManager().beginTransaction().add(2131558536, localMyLoginFragment).commit();
      findViewById(2131558534).setOnClickListener(this);
      findViewById(2131558533).setOnClickListener(this);
    }
  }
  
  protected void onStart()
  {
    super.onStart();
    this.fireManager.auth.addAuthStateListener(this.fireManager.authListener);
  }
  
  protected void onStop()
  {
    super.onStop();
    if (this.fireManager.authListener != null) {
      this.fireManager.auth.removeAuthStateListener(this.fireManager.authListener);
    }
  }
  
  public void toMainActivityListener()
  {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.LoginStuff.Login
 * JD-Core Version:    0.7.0.1
 */