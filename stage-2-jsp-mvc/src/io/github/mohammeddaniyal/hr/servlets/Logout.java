package io.github.mohammeddaniyal.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
public class Logout extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher;
try
{
HttpSession session=request.getSession();
session.removeAttribute("username");
/*
other way to remove, destroying the sesssion compeletly
session.invalidate();
*/
//requestDispatcher=request.getRequestDispatcher("/LoginForm.jsp");
//requestDispatcher.forward(request,response); it's wrong
response.sendRedirect("LoginForm.jsp");
}catch(Exception exception)
{
requestDispatcher=request.getRequestDispatcher("/ErrorPage.jsp");
try
{
requestDispatcher.forward(request,response);
}catch(Exception e)
{
//do nothing
}
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
}
