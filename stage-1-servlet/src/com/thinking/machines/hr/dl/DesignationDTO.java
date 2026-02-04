package com.thinking.machines.hr.dl;
public class DesignationDTO implements java.io.Serializable,Comparable<DesignationDTO>
{
private int code;
private String title;
public DesignationDTO()
{
code=0;
title="";
}
public void setCode(int code)
{
this.code=code;
}
public void setTitle(String title)
{
this.title=title;
}
public int getCode()
{
return this.code;
}
public String getTitle()
{
return this.title;
}
public boolean equals(Object other)
{
if(! (other instanceof DesignationDTO)) return false;
DesignationDTO d=(DesignationDTO)other;
return this.code==d.code;
}
public int compareTo(DesignationDTO other)
{
return this.title.compareToIgnoreCase(other.title);
}
public int hashCode()
{
return this.code;
}
}