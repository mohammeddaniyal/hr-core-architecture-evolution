package com.thinking.machines.hr.tags;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;
public class GenerateUUIDTagHandler extends TagSupport
{
private String uuId;
public GenerateUUIDTagHandler()
{
reset();
}
public void reset()
{
this.uuId="";
}
public int doStartTag()
{
this.uuId=UUID.randomUUID().toString();
HttpSession session;
session=pageContext.getSession();
session.setAttribute("session_uuId",uuId);
pageContext.setAttribute("uuId",uuId,PageContext.REQUEST_SCOPE);
return super.SKIP_BODY;
}
public int doEndTag()
{
this.reset();
return super.EVAL_PAGE;
}
}