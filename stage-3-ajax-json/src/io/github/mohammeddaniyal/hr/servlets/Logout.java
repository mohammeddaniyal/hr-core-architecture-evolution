package io.github.mohammeddaniyal.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
public class Logout extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception e)
{
//do nothing
}
}

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
HttpSession session=request.getSession();
session.removeAttribute("username");
/*
other way to remove, destroying the sesssion compeletly
session.invalidate();
*/
}catch(Exception exception)
{
exception.printStackTrace();
try
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception e)
{
//do nothing
}
}
}
}
