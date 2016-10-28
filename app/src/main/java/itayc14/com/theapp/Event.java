package com.itayc14.theapp;

import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class Event
  implements Serializable
{
  private String body;
  private String eventDate;
  private String eventID;
  private String eventImageName;
  private HashMap<String, GuestListAttendee> guestsList;
  private int hallAmountOfPlaces;
  private int hallColumns;
  private int hallRows;
  private String headline;
  private String seatsCode;
  private String ticketPrice;
  private int totalTicketsBought;
  
  private Event() {}
  
  public Event(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, String paramString4, @Nullable String paramString5, @Nullable String paramString6)
  {
    this.headline = paramString1;
    this.eventImageName = paramString2;
    this.body = paramString3;
    this.hallColumns = paramInt1;
    this.hallRows = paramInt2;
    this.hallAmountOfPlaces = paramInt3;
    this.ticketPrice = paramString5;
    this.eventDate = paramString4;
    this.seatsCode = paramString6;
    this.guestsList = new HashMap();
  }
  
  public Event(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, String paramString4, @Nullable String paramString5, @Nullable String paramString6, String paramString7)
  {
    this.headline = paramString1;
    this.eventImageName = paramString2;
    this.body = paramString3;
    this.hallColumns = paramInt1;
    this.hallRows = paramInt2;
    this.hallAmountOfPlaces = paramInt3;
    this.ticketPrice = paramString5;
    this.eventDate = paramString4;
    this.seatsCode = paramString6;
    this.eventID = paramString7;
    this.guestsList = new HashMap();
  }
  
  public Event(JSONObject paramJSONObject)
  {
    try
    {
      this.headline = paramJSONObject.getString("headline");
      this.body = paramJSONObject.getString("body");
      this.hallAmountOfPlaces = paramJSONObject.getInt("hallAmountOfPlaces");
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }
  
  public String getBody()
  {
    return this.body;
  }
  
  public String getEventDate()
  {
    return this.eventDate;
  }
  
  public String getEventID()
  {
    return this.eventID;
  }
  
  public String getEventImageName()
  {
    return this.eventImageName;
  }
  
  public HashMap<String, GuestListAttendee> getGuestsList()
  {
    return this.guestsList;
  }
  
  public int getHallAmountOfPlaces()
  {
    return this.hallAmountOfPlaces;
  }
  
  public int getHallColumns()
  {
    return this.hallColumns;
  }
  
  public int getHallRows()
  {
    return this.hallRows;
  }
  
  public String getHeadline()
  {
    return this.headline;
  }
  
  public int getPlacesLeft()
  {
    return this.hallAmountOfPlaces - this.totalTicketsBought;
  }
  
  public String getSeatsCode()
  {
    return this.seatsCode;
  }
  
  public String getTicketPrice()
  {
    return this.ticketPrice;
  }
  
  public int getTotalTicketsBought()
  {
    return this.totalTicketsBought;
  }
  
  public void setBody(String paramString)
  {
    this.body = paramString;
  }
  
  public void setEventDate(String paramString)
  {
    this.eventDate = paramString;
  }
  
  public void setEventID(String paramString)
  {
    this.eventID = paramString;
  }
  
  public void setEventImageName(String paramString)
  {
    this.eventImageName = paramString;
  }
  
  public void setGuestsList(HashMap<String, GuestListAttendee> paramHashMap)
  {
    this.guestsList = paramHashMap;
  }
  
  public void setHallColumns(int paramInt)
  {
    this.hallColumns = paramInt;
  }
  
  public void setHallRows(int paramInt)
  {
    this.hallRows = paramInt;
  }
  
  public void setHeadline(String paramString)
  {
    this.headline = paramString;
  }
  
  public void setSeatsCode(String paramString)
  {
    this.seatsCode = paramString;
  }
  
  public void setTicketPrice(String paramString)
  {
    this.ticketPrice = paramString;
  }
  
  public void setTotalTicketsBought(int paramInt)
  {
    this.totalTicketsBought = paramInt;
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.Event
 * JD-Core Version:    0.7.0.1
 */