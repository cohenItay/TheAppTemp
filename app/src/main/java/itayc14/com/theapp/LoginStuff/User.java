package com.itayc14.theapp.LoginStuff;

public class User
{
  private String email;
  private boolean isManager;
  private String name;
  private String password;
  private String relative;
  private String toStudent;
  
  public User() {}
  
  public User(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    this.name = paramString1;
    this.email = paramString2;
    this.password = paramString3;
    this.relative = paramString4;
    this.toStudent = paramString5;
    this.isManager = false;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public String getRelative()
  {
    return this.relative;
  }
  
  public String getToStudent()
  {
    return this.toStudent;
  }
  
  public boolean isManager()
  {
    return this.isManager;
  }
  
  public void setEmail(String paramString)
  {
    this.email = paramString;
  }
  
  public void setManager(boolean paramBoolean)
  {
    this.isManager = paramBoolean;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public void setRelative(String paramString)
  {
    this.relative = paramString;
  }
  
  public void setToStudent(String paramString)
  {
    this.toStudent = paramString;
  }
}


/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar
 * Qualified Name:     com.itayc14.theapp.LoginStuff.User
 * JD-Core Version:    0.7.0.1
 */