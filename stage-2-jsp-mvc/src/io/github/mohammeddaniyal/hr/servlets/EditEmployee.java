package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;
import java.util.*;
public class EditEmployee extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher;
try
{
String employeeId=request.getParameter("employeeId");
EmployeeDAO employeeDAO=new EmployeeDAO();
EmployeeDTO employee;
try
{
employee=employeeDAO.getByEmployeeId(employeeId);
}catch(DAOException daoException)
{
requestDispatcher=request.getRequestDispatcher("/Employees.jsp");
requestDispatcher.forward(request,response);
return;
}
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
EmployeeBean employeeBean=new EmployeeBean();
employeeBean.setEmployeeId(employee.getEmployeeId());
employeeBean.setName(employee.getName());
employeeBean.setDesignationCode(employee.getDesignationCode());
employeeBean.setDesignation(employee.getDesignation());
employeeBean.setDateOfBirth(simpleDateFormat.format(employee.getDateOfBirth()));
employeeBean.setGender(employee.getGender());
employeeBean.setIsIndian(employee.getIsIndian());
employeeBean.setBasicSalary(employee.getBasicSalary().toPlainString());
employeeBean.setPANNumber(employee.getPANNumber());
employeeBean.setAadharCardNumber(employee.getAadharCardNumber());
request.setAttribute("employeeBean",employeeBean);
requestDispatcher=request.getRequestDispatcher("/EmployeeEditForm.jsp");
requestDispatcher.forward(request,response);
return;
}catch(Exception exception)
{
System.out.println(exception.getMessage());
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