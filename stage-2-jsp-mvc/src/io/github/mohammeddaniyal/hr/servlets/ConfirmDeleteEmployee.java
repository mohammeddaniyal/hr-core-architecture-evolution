package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.beans.*;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
public class ConfirmDeleteEmployee extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher=null;
try
{
String employeeId=request.getParameter("employeeId");
if(employeeId==null)
{
requestDispatcher=request.getRequestDispatcher("/Employees.jsp");
requestDispatcher.forward(request,response);
return;
}
EmployeeDAO employeeDAO=new EmployeeDAO();
EmployeeDTO employeeDTO=null;
try
{
employeeDTO=employeeDAO.getByEmployeeId(employeeId);
}catch(DAOException daoException)
{
requestDispatcher=request.getRequestDispatcher("/Employees.jsp");
requestDispatcher.forward(request,response);
return;
}

SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeBean employeeBean=new EmployeeBean();
employeeBean.setEmployeeId(employeeId);
employeeBean.setName(employeeDTO.getName());
employeeBean.setDesignation(employeeDTO.getDesignation());
employeeBean.setBasicSalary(employeeDTO.getBasicSalary().toPlainString());
employeeBean.setDateOfBirth(sdf.format(employeeDTO.getDateOfBirth()));
employeeBean.setIsIndian(employeeDTO.getIsIndian());
employeeBean.setGender(employeeDTO.getGender());
employeeBean.setAadharCardNumber(employeeDTO.getAadharCardNumber());
employeeBean.setPANNumber(employeeDTO.getPANNumber());

request.setAttribute("employeeBean",employeeBean);
requestDispatcher=request.getRequestDispatcher("/ConfirmDeleteEmployee.jsp");

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