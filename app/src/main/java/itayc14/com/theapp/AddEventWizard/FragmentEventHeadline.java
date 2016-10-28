package com.itayc14.theapp.AddEventWizard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.StreamDownloadTask.StreamProcessor;
import com.google.firebase.storage.StreamDownloadTask.TaskSnapshot;
import java.io.IOException;
import java.io.InputStream;

public class FragmentEventHeadline
  extends Fragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  private int animationDuration;
  private Bitmap[] bitmapArray;
  private Bundle bundle;
  private ImageView chosenImage;
  private int downloadTaskCounter;
  private EditText eventDate;
  private GridView gridView;
  private EditText headlineField;
  private RelativeLayout imagePicker;
  private String[] imagesNameOnFirebase;
  private StorageReference imagesRef;
  private boolean isBeingEdited;
  private RelativeLayout majorLayout;
  private ProgressBar progressBar;
  private FirebaseStorage storage;
  
  private void downloadImages(final String paramString, @Nullable final int paramInt)
  {
    this.imagesRef.child(paramString).getStream(new StreamDownloadTask.StreamProcessor()
    {
      public void doInBackground(StreamDownloadTask.TaskSnapshot paramAnonymousTaskSnapshot, InputStream paramAnonymousInputStream)
        throws IOException
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(paramAnonymousInputStream);
        FragmentEventHeadline.this.bitmapArray[paramInt] = localBitmap;
        if (paramString == FragmentEventHeadline.this.bundle.getString("image")) {
          FragmentEventHeadline.this.chosenImage.setImageBitmap(localBitmap);
        }
        paramAnonymousInputStream.close();
      }
    }).addOnSuccessListener(new OnSuccessListener()
    {
      public void onSuccess(StreamDownloadTask.TaskSnapshot paramAnonymousTaskSnapshot)
      {
        FragmentEventHeadline.access$010(FragmentEventHeadline.this);
        Log.d("tag", "counter: " + FragmentEventHeadline.this.downloadTaskCounter);
        if (FragmentEventHeadline.this.downloadTaskCounter <= 0)
        {
          FragmentEventHeadline.this.gridView.setAdapter(new MyGridViewAdapter(FragmentEventHeadline.this.getActivity(), FragmentEventHeadline.this.bitmapArray));
          FragmentEventHeadline.this.progressBar.setVisibility(8);
        }
      }
    });
  }
  
  private void fadeInImagePicker()
  {
    this.imagePicker.setAlpha(0.0F);
    this.imagePicker.setVisibility(0);
    this.imagePicker.animate().alpha(1.0F).setDuration(this.animationDuration).setListener(null);
    this.majorLayout.animate().alpha(0.0F).setDuration(this.animationDuration).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        FragmentEventHeadline.this.majorLayout.setVisibility(8);
      }
    });
  }
  
  private void fadeOutImagePicker()
  {
    this.majorLayout.setAlpha(0.0F);
    this.majorLayout.setVisibility(0);
    this.majorLayout.animate().alpha(1.0F).setDuration(this.animationDuration).setListener(null);
    this.imagePicker.animate().alpha(0.0F).setDuration(this.animationDuration).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        FragmentEventHeadline.this.imagePicker.setVisibility(8);
      }
    });
  }
  
  private void initializeImagesNames()
  {
    this.imagesNameOnFirebase = new String[8];
    this.downloadTaskCounter = this.imagesNameOnFirebase.length;
    this.imagesNameOnFirebase[0] = "ballet.png";
    this.imagesNameOnFirebase[1] = "breakdance.png";
    this.imagesNameOnFirebase[2] = "conference.png";
    this.imagesNameOnFirebase[3] = "rock.png";
    this.imagesNameOnFirebase[4] = "hall1.png";
    this.imagesNameOnFirebase[5] = "hall2.png";
    this.imagesNameOnFirebase[6] = "hall3.png";
    this.imagesNameOnFirebase[7] = "hall4.png";
  }
  
  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131558577)
    {
      this.bundle.putString("headline", this.headlineField.getText().toString().trim());
      this.bundle.putString("date", this.eventDate.getText().toString().trim());
      FragmentEventBody localFragmentEventBody = new FragmentEventBody();
      localFragmentEventBody.setArguments(this.bundle);
      getFragmentManager().beginTransaction().addToBackStack(null).replace(2131558516, localFragmentEventBody).commit();
      return;
    }
    fadeInImagePicker();
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130968622, null);
    localView.findViewById(2131558577).setOnClickListener(this);
    this.headlineField = ((EditText)localView.findViewById(2131558573));
    this.chosenImage = ((ImageView)localView.findViewById(2131558575));
    this.chosenImage.setOnClickListener(this);
    this.eventDate = ((EditText)localView.findViewById(2131558572));
    this.animationDuration = getResources().getInteger(17694721);
    this.imagePicker = ((RelativeLayout)localView.findViewById(2131558568));
    this.majorLayout = ((RelativeLayout)localView.findViewById(2131558571));
    this.progressBar = ((ProgressBar)localView.findViewById(2131558570));
    this.gridView = ((GridView)localView.findViewById(2131558569));
    this.storage = FirebaseStorage.getInstance();
    this.imagesRef = this.storage.getReference().child("listview_images");
    this.bitmapArray = new Bitmap[8];
    initializeImagesNames();
    this.gridView.setOnItemClickListener(this);
    for (int i = 0; i < this.imagesNameOnFirebase.length; i++) {
      downloadImages(this.imagesNameOnFirebase[i], i);
    }
    this.bundle = getArguments();
    if (this.bundle != null)
    {
      this.headlineField.setText(this.bundle.getString("headline"));
      this.eventDate.setText(this.bundle.getString("date"));
      this.isBeingEdited = true;
      return localView;
    }
    this.bundle = new Bundle();
    return localView;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Log.d("tag", "imagename: " + this.imagesNameOnFirebase[paramInt] + "position num: " + paramInt);
    this.bundle.putString("image", this.imagesNameOnFirebase[paramInt]);
    fadeOutImagePicker();
    this.chosenImage.setImageBitmap(this.bitmapArray[paramInt]);
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.AddEventWizard.FragmentEventHeadline
 * JD-Core Version:    0.7.0.1
 */