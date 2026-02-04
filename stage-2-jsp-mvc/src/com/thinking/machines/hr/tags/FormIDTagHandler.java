package com.thinking.machines.hr.tags;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.*;
public class FormIDTagHandler extends TagSupport
{
public FormIDTagHandler()
{
}
public int doStartTag()
{
String formID=UUID.randomUUID().toString();
pageContext.setAttribute(formID,formID,PageContext.SESSION_SCOPE);
JspWriter writer=pageContext.getOut();
try
{
writer.print("<input type='hidden' id='formID' name='formID' value='"+formID+"'>");
}catch(IOException ioe)
{
//do nothing
}
return super.SKIP_BODY;
}
public int doEndTag()
{
return super.EVAL_PAGE;
}
}
