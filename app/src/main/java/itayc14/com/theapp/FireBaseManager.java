package com.itayc14.theapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask.StreamProcessor;
import com.google.firebase.storage.StreamDownloadTask.TaskSnapshot;
import com.itayc14.theapp.LoginStuff.Login;
import com.itayc14.theapp.LoginStuff.MoveToMainActivityListener;
import com.itayc14.theapp.LoginStuff.MyLoginFragment.userEntreyDetailsRemover;
import com.itayc14.theapp.LoginStuff.MyNewUserFragment;
import com.itayc14.theapp.LoginStuff.User;
import com.itayc14.theapp.purchaseStuff.LockSeatsActivity;
import com.itayc14.theapp.purchaseStuff.LockSeatsActivity.HallBuild;
import com.itayc14.theapp.purchaseStuff.PurchaseActivity.SuccessPoper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FireBaseManager
{
  public FirebaseAuth auth = FirebaseAuth.getInstance();
  public FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener()
  {
    public void onAuthStateChanged(@NonNull FirebaseAuth paramAnonymousFirebaseAuth)
    {
      FireBaseManager.access$002(FireBaseManager.this, FireBaseManager.this.auth.getCurrentUser());
    }
  };
  private FirebaseDatabase database = FirebaseDatabase.getInstance();
  private DatabaseReference dbRef = this.database.getReference();
  private Handler handle = new Handler();
  private FirebaseStorage storage = FirebaseStorage.getInstance();
  private StorageReference storageRef = this.storage.getReference();
  private FirebaseUser user = this.auth.getCurrentUser();
  
  private void updateUserDetails(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("name", paramString1);
    localHashMap.put("toStudent", paramString2);
    localHashMap.put("relative", paramString3);
    localHashMap.put("isManager", Boolean.valueOf(paramBoolean));
    this.dbRef.child("users").child(this.auth.getCurrentUser().getUid()).updateChildren(localHashMap);
  }
  
  public void addSingleEvent(Event paramEvent, final MainActivity.EventAdder paramEventAdder, final MainActivity paramMainActivity)
  {
    final String str = this.dbRef.child("events").push().getKey();
    this.dbRef.child("events").child(str).setValue(paramEvent).addOnCompleteListener(new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<Void> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          HashMap localHashMap = new HashMap();
          localHashMap.put("eventID", str);
          FireBaseManager.this.dbRef.child("events").child(str).updateChildren(localHashMap);
          paramEventAdder.addTheEvent(str);
          return;
        }
        Toast.makeText(paramMainActivity, "הייתה בעייה ביצירת האירוע..", 0).show();
      }
    });
  }
  
  public void addToEventGuestList(final int paramInt, final String paramString, final Activity paramActivity, final PurchaseActivity.SuccessPoper paramSuccessPoper)
  {
    this.dbRef.child("users/" + this.user.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError)
      {
        Log.d("tag", "cannot retrieve currentBuyingUser: " + paramAnonymousDatabaseError.toString());
      }
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        final User localUser = (User)paramAnonymousDataSnapshot.getValue(User.class);
        FireBaseManager.this.dbRef.child("events/" + paramString).runTransaction(new Transaction.Handler()
        {
          public Transaction.Result doTransaction(MutableData paramAnonymous2MutableData)
          {
            Event localEvent = (Event)paramAnonymous2MutableData.getValue(Event.class);
            if (localEvent == null) {
              return Transaction.success(paramAnonymous2MutableData);
            }
            String str = FireBaseManager.this.user.getUid().toString();
            Integer localInteger = Integer.valueOf(FireBaseManager.10.this.val$ticketsAmount);
            GuestListAttendee localGuestListAttendee;
            if (paramAnonymous2MutableData.child("guestsList").hasChild(str))
            {
              localGuestListAttendee = (GuestListAttendee)paramAnonymous2MutableData.child("guestsList/" + str).getValue(GuestListAttendee.class);
              localInteger = Integer.valueOf(Integer.valueOf(localGuestListAttendee.getTicketsBought()).intValue() + FireBaseManager.10.this.val$ticketsAmount);
              localGuestListAttendee.setTicketsBought(localInteger.intValue());
            }
            HashMap localHashMap;
            for (;;)
            {
              localGuestListAttendee.setTicketsBought(localInteger.intValue());
              localGuestListAttendee.setRelative(localUser.getRelative() + " של " + localUser.getToStudent());
              localGuestListAttendee.setName(localUser.getName());
              localHashMap = new HashMap();
              Iterator localIterator = paramAnonymous2MutableData.child("guestsList").getChildren().iterator();
              while (localIterator.hasNext())
              {
                MutableData localMutableData = (MutableData)localIterator.next();
                localHashMap.put(localMutableData.getKey(), localMutableData.getValue(GuestListAttendee.class));
              }
              localGuestListAttendee = new GuestListAttendee();
            }
            localHashMap.put(str, localGuestListAttendee);
            localEvent.setGuestsList(localHashMap);
            localEvent.setTotalTicketsBought(localEvent.getTotalTicketsBought() + FireBaseManager.10.this.val$ticketsAmount);
            paramAnonymous2MutableData.setValue(localEvent);
            return Transaction.success(paramAnonymous2MutableData);
          }
          
          public void onComplete(DatabaseError paramAnonymous2DatabaseError, boolean paramAnonymous2Boolean, DataSnapshot paramAnonymous2DataSnapshot)
          {
            if (!paramAnonymous2Boolean) {
              Toast.makeText(FireBaseManager.10.this.val$activity, "יש בעיה: לא עודכן במסד הנתונים הרכישה שלך", 0).show();
            }
            for (;;)
            {
              if (paramAnonymous2DatabaseError != null) {
                Log.d("tag", paramAnonymous2DatabaseError.toString());
              }
              return;
              Event localEvent = (Event)paramAnonymous2DataSnapshot.getValue(Event.class);
              Toast.makeText(FireBaseManager.10.this.val$activity, "מסד הנתונים עדכן את רכישת בהצלחה", 0).show();
              FireBaseManager.10.this.val$poper.popSuccess(FireBaseManager.10.this.val$activity, localEvent);
            }
          }
        });
      }
    });
  }
  
  public void checkIfCurrentUserIsManager(final MainActivity.CheckIfManager paramCheckIfManager)
  {
    this.dbRef.child("users/" + this.auth.getCurrentUser().getUid() + "/isManager").addListenerForSingleValueEvent(new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        boolean bool = ((Boolean)paramAnonymousDataSnapshot.getValue()).booleanValue();
        paramCheckIfManager.DefineIfManager(bool);
      }
    });
  }
  
  public void createNewUser(final User paramUser, final MyNewUserFragment paramMyNewUserFragment)
  {
    this.auth.createUserWithEmailAndPassword(paramUser.getEmail(), paramUser.getPassword()).addOnCompleteListener(new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<AuthResult> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          FireBaseManager.this.updateUserDetails(paramUser.getName(), paramUser.getToStudent(), paramUser.getRelative(), paramUser.isManager());
          paramMyNewUserFragment.toMainActivityListener();
          return;
        }
        Log.d("tag", "failed to create new user: " + paramAnonymousTask.getException().toString());
        Toast.makeText(paramMyNewUserFragment.getActivity(), "Fail to create user", 0).show();
      }
    });
  }
  
  public void deleteSingleEvent(String paramString, final Activity paramActivity, final MainActivity.EventDeleter paramEventDeleter)
  {
    this.dbRef.child("events").child(paramString).removeValue().addOnCompleteListener(new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<Void> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          paramEventDeleter.deleteTheEvent();
          return;
        }
        Toast.makeText(paramActivity, "הייתה בעיה במחיקת האירוע", 0).show();
      }
    });
  }
  
  public void downloadSingleImage(final ImageView paramImageView, final String paramString, final Resources paramResources, final View paramView, final HashMap<String, RoundedBitmapDrawable> paramHashMap)
  {
    this.storageRef.child("listview_images/" + paramString).getStream(new StreamDownloadTask.StreamProcessor()
    {
      public void doInBackground(StreamDownloadTask.TaskSnapshot paramAnonymousTaskSnapshot, InputStream paramAnonymousInputStream)
        throws IOException
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(paramAnonymousInputStream);
        paramAnonymousInputStream.close();
        final RoundedBitmapDrawable localRoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(paramResources, localBitmap);
        localRoundedBitmapDrawable.setCornerRadius(25.0F);
        localRoundedBitmapDrawable.setAntiAlias(true);
        paramHashMap.put(paramString, localRoundedBitmapDrawable);
        FireBaseManager.this.handle.post(new Runnable()
        {
          public void run()
          {
            FireBaseManager.15.this.val$eventImageViewer.setImageDrawable(localRoundedBitmapDrawable);
            FireBaseManager.15.this.val$convertView.animate().alpha(1.0F).setDuration(400L).setListener(null);
          }
        });
      }
    });
  }
  
  public void editSingleEvent(Event paramEvent, final MainActivity.EventEditCompleted paramEventEditCompleted, final MainActivity paramMainActivity)
  {
    this.dbRef.child("events").child(paramEvent.getEventID()).setValue(paramEvent).addOnCompleteListener(new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<Void> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          paramEventEditCompleted.eventEditCompleted();
          return;
        }
        Toast.makeText(paramMainActivity, "הייתה בעיה בעריכת האירוע", 0).show();
      }
    });
  }
  
  public void fromAvailableSeatsToUnavailableSeats(final ArrayList<Integer> paramArrayList, Event paramEvent, final LockSeatsActivity paramLockSeatsActivity)
  {
    final ArrayList localArrayList = new ArrayList();
    this.dbRef.child("events/" + paramEvent.getEventID() + "/seatsCode").runTransaction(new Transaction.Handler()
    {
      public Transaction.Result doTransaction(MutableData paramAnonymousMutableData)
      {
        String str1 = (String)paramAnonymousMutableData.getValue(String.class);
        Object localObject = str1;
        if (str1 == null) {
          return Transaction.success(paramAnonymousMutableData);
        }
        try
        {
          localJSONArray = new JSONArray(str1);
          i = 1;
        }
        catch (JSONException localJSONException)
        {
          JSONArray localJSONArray;
          int i;
          JSONObject localJSONObject1;
          Iterator localIterator;
          localJSONException.printStackTrace();
          for (;;)
          {
            if (!((String)localObject).equals(str1)) {
              break label192;
            }
            return Transaction.abort();
            i++;
            break;
            String str2 = localJSONArray.toString();
            localObject = str2;
          }
          paramAnonymousMutableData.setValue(localObject);
        }
        if (i < localJSONArray.length())
        {
          localJSONObject1 = localJSONArray.getJSONObject(i);
          localIterator = paramArrayList.iterator();
          while (localIterator.hasNext())
          {
            int j = ((Integer)localIterator.next()).intValue();
            if (j == localJSONObject1.getInt("seatID"))
            {
              localArrayList.add(Integer.valueOf(i));
              JSONObject localJSONObject2 = new JSONObject();
              localJSONObject2.put("seatStatus", "SeatUnavailable");
              localJSONObject2.put("seatID", j);
              localJSONArray.put(i, localJSONObject2);
            }
          }
        }
        label192:
        return Transaction.success(paramAnonymousMutableData);
      }
      
      public void onComplete(DatabaseError paramAnonymousDatabaseError, boolean paramAnonymousBoolean, DataSnapshot paramAnonymousDataSnapshot)
      {
        if (!paramAnonymousBoolean)
        {
          Toast.makeText(paramLockSeatsActivity, "איזה מעצבן, הם תפסו לנו את המקומות! אין ברירה, נבחר חדשים..", 1).show();
          paramLockSeatsActivity.reloadSeats();
        }
        for (;;)
        {
          if (paramAnonymousDatabaseError != null) {
            Log.d("tag", paramAnonymousDatabaseError.toString());
          }
          return;
          Toast.makeText(paramLockSeatsActivity, "המקומות נעולים לזמן מוגבל עד לרכישה סופית", 0).show();
          paramLockSeatsActivity.continueToPurchaseActivity(paramAnonymousDataSnapshot.getValue().toString(), localArrayList);
        }
      }
    });
  }
  
  public DatabaseReference getDbRef()
  {
    return this.dbRef;
  }
  
  public void getEventGuestList(String paramString, final GuestListActivity.GuestListLoad paramGuestListLoad)
  {
    this.dbRef.child("events/" + paramString + "/guestsList").orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError)
      {
        Log.d("tag", "cant get event guest list: " + paramAnonymousDatabaseError.toString());
      }
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
        while (localIterator.hasNext()) {
          localArrayList.add((GuestListAttendee)((DataSnapshot)localIterator.next()).getValue(GuestListAttendee.class));
        }
        paramGuestListLoad.loadGuestList(localArrayList);
      }
    });
  }
  
  public void getSingleEventSeatsCode(String paramString, final LockSeatsActivity.HallBuild paramHallBuild)
  {
    this.dbRef.child("events/" + paramString + "/seatsCode").addListenerForSingleValueEvent(new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError)
      {
        Log.d("tag", paramAnonymousDatabaseError.toString());
      }
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        String str = paramAnonymousDataSnapshot.getValue().toString();
        paramHallBuild.startBuildHall(str);
      }
    });
  }
  
  public void loadEvents(final MainActivity.EventsLoad paramEventsLoad)
  {
    this.dbRef.child("events").orderByKey().addListenerForSingleValueEvent(new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
        while (localIterator.hasNext()) {
          localArrayList.add((Event)((DataSnapshot)localIterator.next()).getValue(Event.class));
        }
        paramEventsLoad.eventsLoader(localArrayList);
      }
    });
  }
  
  public void logOut()
  {
    this.auth.signOut();
  }
  
  public void loginWithListener(String paramString1, String paramString2, final Activity paramActivity, @Nullable final MyLoginFragment.userEntreyDetailsRemover paramuserEntreyDetailsRemover)
  {
    final Login localLogin = (Login)paramActivity;
    this.auth.signInWithEmailAndPassword(paramString1, paramString2).addOnCompleteListener(new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<AuthResult> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          localLogin.toMainActivityListener();
          return;
        }
        Toast.makeText(paramActivity, "Fail to sign in", 0).show();
        paramuserEntreyDetailsRemover.removeEntryDetails();
      }
    });
  }
  
  public void moveToMainActivityIfUserConnected(Context paramContext)
  {
    MoveToMainActivityListener localMoveToMainActivityListener = (MoveToMainActivityListener)paramContext;
    if (this.user != null) {
      localMoveToMainActivityListener.toMainActivityListener();
    }
  }
  
  public void resetUserPassword(String paramString, final Activity paramActivity)
  {
    this.auth.sendPasswordResetEmail(paramString).addOnCompleteListener(new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<Void> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          Toast.makeText(paramActivity, "הemail נשלח בהצלחה", 0).show();
          return;
        }
        Toast.makeText(paramActivity, "יש בעיה, נא בדוק שהemail תקין", 0).show();
      }
    });
  }
  
  public void unlockSeats(final ArrayList<Integer> paramArrayList, Event paramEvent)
  {
    this.dbRef.child("events/" + paramEvent.getEventID() + "/seatsCode").runTransaction(new Transaction.Handler()
    {
      public Transaction.Result doTransaction(MutableData paramAnonymousMutableData)
      {
        String str1 = "b";
        String str2 = (String)paramAnonymousMutableData.getValue(String.class);
        if (str2 == null) {
          return Transaction.success(paramAnonymousMutableData);
        }
        try
        {
          JSONArray localJSONArray = new JSONArray(str2);
          Log.d("tag", "old seats code:" + localJSONArray.toString());
          for (int i = 0; i < paramArrayList.size(); i++)
          {
            JSONObject localJSONObject1 = localJSONArray.getJSONObject(((Integer)paramArrayList.get(i)).intValue());
            Log.d("tag", "currentSeatStatus: " + localJSONObject1.toString());
            JSONObject localJSONObject2 = new JSONObject();
            localJSONObject2.put("seatStatus", "SeatAvailable");
            localJSONObject2.put("seatID", localJSONObject1.getInt("seatID"));
            Log.d("tag", "updatedSeatsCode: " + localJSONObject2.toString());
            localJSONArray.put(((Integer)paramArrayList.get(i)).intValue(), localJSONObject2);
          }
          str1 = localJSONArray.toString();
          Log.d("tag", "updated seatsCode" + str1.toString());
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            localJSONException.printStackTrace();
          }
          paramAnonymousMutableData.setValue(str1);
        }
        if (str2.equals(str1)) {
          return Transaction.abort();
        }
        return Transaction.success(paramAnonymousMutableData);
      }
      
      public void onComplete(DatabaseError paramAnonymousDatabaseError, boolean paramAnonymousBoolean, DataSnapshot paramAnonymousDataSnapshot)
      {
        if (paramAnonymousDatabaseError != null) {
          Log.d("tag", paramAnonymousDatabaseError.toString());
        }
      }
    });
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.FireBaseManager
 * JD-Core Version:    0.7.0.1
 */