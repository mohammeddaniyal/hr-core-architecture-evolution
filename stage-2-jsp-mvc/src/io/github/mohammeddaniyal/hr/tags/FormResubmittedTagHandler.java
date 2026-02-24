package io.github.mohammeddaniyal.hr.tags;
import javax.servlet.jsp.tagext.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
public class FormResubmittedTagHandler extends TagSupport
{
public FormResubmittedTagHandler()
{
reset();
}
public void reset()
{
//do nothing
}
public int doStartTag()
{
HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
String formID=request.getParameter("formID");
if(formID==null)
{
return super.EVAL_BODY_INCLUDE;
}
String formIDInSession=(String)pageContext.getAttribute(formID,PageContext.SESSION_SCOPE);
if(formIDInSession==null)
{
System.out.println("Session id is null");
return super.EVAL_BODY_INCLUDE;
}
pageContext.removeAttribute(formID,PageContext.SESSION_SCOPE);
if(formID.equals(formIDInSession))
{
System.out.println("Session id is equal");
return super.SKIP_BODY;
}
else
{
System.out.println("Session id is not equal");
return super.EVAL_BODY_INCLUDE;
}
}
public int doEndTag()
{
reset();
return super.EVAL_PAGE;
}
}
