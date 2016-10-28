package com.itayc14.theapp.AddEventWizard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentEventBody
  extends Fragment
  implements View.OnClickListener
{
  EditText bodyField;
  Bundle bundle;
  
  public void onClick(View paramView)
  {
    this.bundle.putString("body", this.bodyField.getText().toString().trim());
    FragmentHallBuilder localFragmentHallBuilder = new FragmentHallBuilder();
    localFragmentHallBuilder.setArguments(this.bundle);
    getFragmentManager().beginTransaction().addToBackStack(null).replace(2131558516, localFragmentHallBuilder).commit();
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130968623, null);
    localView.findViewById(2131558579).setOnClickListener(this);
    this.bodyField = ((EditText)localView.findViewById(2131558578));
    this.bundle = getArguments();
    if (this.bundle.getBoolean("isEdit")) {
      this.bodyField.setText(this.bundle.getString("body"));
    }
    return localView;
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.AddEventWizard.FragmentEventBody
 * JD-Core Version:    0.7.0.1
 */