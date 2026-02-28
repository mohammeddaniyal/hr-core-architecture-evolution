package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class NotifyFormResubmission extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
MessageBean messageBean;
messageBean=new MessageBean();
messageBean.setHeading("Notification");
messageBean.setMessage("Forms are not to be resubmitted");
messageBean.setGenerateButtons(true);
messageBean.setGenerateTwoButtons(false);
messageBean.setButtonOneText("Ok");
messageBean.setButtonOneAction("index.jsp");
request.setAttribute("messageBean",messageBean);
RequestDispatcher requestDispatcher;
requestDispatcher=request.getRequestDispatcher("/Notification.jsp");
requestDispatcher.forward(request,response);
}catch(Exception e)
{
RequestDispatcher requestDispatcher;
response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
requestDispatcher=request.getRequestDispatcher("/ErrorPage.jsp");
try
{
requestDispatcher.forward(request,response);
}catch(Exception exception)
{
//do nothing
}
}
}
}