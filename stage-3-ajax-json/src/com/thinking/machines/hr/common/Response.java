package com.thinking.machines.hr.common;
import java.util.*;
public class Response implements java.io.Serializable
{
private boolean success;
private Object result;
private String error;
private Map<String,String> errors;
public Response()
{
this.success=false;
this.result=null;
this.error=null;
this.errors=new HashMap<>();
}
public void setSuccess(boolean success)
{
this.success=success;
}
public boolean getSuccess()
{
return this.success;
}
public void setResult(Object result)
{
this.result=result;
}
public Object getResult()
{
return result;
}
public void setError(String error)
{
this.error=error;
}
public String getError()
{
return this.error;
}
public void setPropertyError(String property,String error)
{
this.errors.put(property,error);
}
public String getPropertyError(String property)
{
return this.errors.get(property);
}
}