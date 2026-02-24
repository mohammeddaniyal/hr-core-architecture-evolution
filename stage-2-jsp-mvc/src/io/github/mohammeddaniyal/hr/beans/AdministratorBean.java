package io.github.mohammeddaniyal.hr.beans;
public class AdministratorBean implements java.io.Serializable,Comparable<AdministratorBean>
{
private String username;
private String password;
public AdministratorBean()
{
this.username="";
this.password="";
}
public void setUsername(String username)
{
this.username=username;
}
public void setPassword(String password)
{
this.password=password;
}
public String getUsername()
{
return username;
}
public String getPassword()
{
return password;
}
public boolean equals(Object object)
{
if(! (object instanceof AdministratorBean)) return false;
AdministratorBean other=(AdministratorBean)object;
return this.username.equals(other.username);
}
public int compareTo(AdministratorBean other)
{
return this.username.compareToIgnoreCase(other.username);
}
public int hashCode()
{
return this.username.hashCode();
}
}