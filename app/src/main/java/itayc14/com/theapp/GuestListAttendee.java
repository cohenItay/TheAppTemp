package com.itayc14.theapp;

import java.io.Serializable;

public class GuestListAttendee
  implements Serializable
{
  private String name;
  private String relative;
  private int ticketsBought;
  
  public GuestListAttendee() {}
  
  public GuestListAttendee(String paramString1, String paramString2, int paramInt)
  {
    this.name = paramString1;
    this.relative = paramString2;
    this.ticketsBought = paramInt;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getRelative()
  {
    return this.relative;
  }
  
  public int getTicketsBought()
  {
    return this.ticketsBought;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setRelative(String paramString)
  {
    this.relative = paramString;
  }
  
  public void setTicketsBought(int paramInt)
  {
    this.ticketsBought = paramInt;
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.GuestListAttendee
 * JD-Core Version:    0.7.0.1
 */