package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.beans.*;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ConfirmDeleteDesignation extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher=null;
try
{
String codeString=request.getParameter("code");
if(codeString==null)
{
requestDispatcher=request.getRequestDispatcher("/Designations.jsp");
requestDispatcher.forward(request,response);
return;
}
int code=0;
try
{
code=Integer.parseInt(codeString);
if(code<=0)
{
requestDispatcher=request.getRequestDispatcher("/Designations.jsp");
requestDispatcher.forward(request,response);
return;
}
}catch(NumberFormatException nfe)
{
requestDispatcher=request.getRequestDispatcher("/Designations.jsp");
requestDispatcher.forward(request,response);
return;
}
DesignationDAO designationDAO=new DesignationDAO();
DesignationDTO designationDTO=null;
try
{
designationDTO=designationDAO.getByCode(code);
}catch(DAOException daoException)
{
requestDispatcher=request.getRequestDispatcher("/Designations.jsp");
requestDispatcher.forward(request,response);
return;
}
DesignationBean designationBean=new DesignationBean();
designationBean.setCode(code);
designationBean.setTitle(designationDTO.getTitle());
request.setAttribute("designationBean",designationBean);
requestDispatcher=request.getRequestDispatcher("/ConfirmDeleteDesignation.jsp");
requestDispatcher.forward(request,response);
return;
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