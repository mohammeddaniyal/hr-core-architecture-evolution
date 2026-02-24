package io.github.mohammeddaniyal.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
public class Login extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
doPost(request,response);
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher;
try
{
AdministratorBean administratorBean=(AdministratorBean)request.getAttribute("administratorBean");
if(administratorBean==null)
{
requestDispatcher=request.getRequestDispatcher("/LoginForm.jsp");
requestDispatcher.forward(request,response);
return;
}
String username=administratorBean.getUsername();
String password=administratorBean.getPassword();
AdministratorDAO administratorDAO;
administratorDAO=new AdministratorDAO();
AdministratorDTO administratorDTO=null;
try
{
administratorDTO=administratorDAO.getByUsername(username);
if(!password.equals(administratorDTO.getPassword()))
{
ErrorBean errorBean=new ErrorBean();
errorBean.setError("Invalid username / password");
request.setAttribute("errorBean",errorBean);
requestDispatcher=request.getRequestDispatcher("/LoginForm.jsp");
requestDispatcher.forward(request,response);
return;
}
}catch(DAOException daoException)
{
ErrorBean errorBean=new ErrorBean();
errorBean.setError("Invalid username / password");
request.setAttribute("errorBean",errorBean);
requestDispatcher=request.getRequestDispatcher("/LoginForm.jsp");
requestDispatcher.forward(request,response);
}
HttpSession session=request.getSession();
session.setAttribute("username",username);
//requestDispatcher=request.getRequestDispatcher("/index.jsp");
// reason not to use, this happens inside the server, browser is unaware so URL doesn't change
// in our case /stage2/LoginForm.jsp remains as it but it should become /stage2/index.jsp
//requestDispatcher.forward(request,response); 
// solution 
response.sendRedirect("index.jsp");

}catch(Exception exception)
{
System.out.println(exception.getMessage());
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