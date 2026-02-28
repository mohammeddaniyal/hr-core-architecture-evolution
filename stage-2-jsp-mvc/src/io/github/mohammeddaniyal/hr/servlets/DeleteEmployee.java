package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.beans.*;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class DeleteEmployee extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
RequestDispatcher requestDispatcher=request.getRequestDispatcher("/Designations.jsp");
requestDispatcher.forward(request,response);
}catch(Exception exception)
{
//do nothing
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher=null;
try
{
    EmployeeBean employeeBean=(EmployeeBean)request.getAttribute("employeeBean");
if(employeeBean==null)
{
requestDispatcher=request.getRequestDispatcher("/Employees.jsp");
requestDispatcher.forward(request,response);
return;
}
String employeeId=employeeBean.getEmployeeId();
EmployeeDAO employeeDAO=new EmployeeDAO();
try
{
employeeDAO.deleteByEmployeeId(employeeId);
}catch(DAOException daoException)
{
MessageBean messageBean=new MessageBean();
messageBean.setHeading("Notification!");
messageBean.setMessage("Unable to delete employee, "+daoException.getMessage());
messageBean.setGenerateButtons(true);
messageBean.setButtonOneText("Ok");
messageBean.setButtonOneAction("Employees.jsp");
messageBean.setGenerateTwoButtons(false);
request.setAttribute("messageBean",messageBean);
requestDispatcher=request.getRequestDispatcher("/Notification.jsp");
requestDispatcher.forward(request,response);
}
MessageBean messageBean=new MessageBean();
messageBean.setHeading("Notification!");
messageBean.setMessage("Employee Deleted");
messageBean.setGenerateButtons(true);
messageBean.setButtonOneText("Ok");
messageBean.setButtonOneAction("Employees.jsp");
messageBean.setGenerateTwoButtons(false);
request.setAttribute("messageBean",messageBean);
requestDispatcher=request.getRequestDispatcher("/Notification.jsp");
requestDispatcher.forward(request,response);
}catch(Exception exception)
{
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
}