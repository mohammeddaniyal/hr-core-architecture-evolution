package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;
import java.util.*;
public class UpdateEmployee extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher;
try
{
    String employeeId=request.getParameter("employeeId");
    String name = request.getParameter("name");
    int designationCode = Integer.parseInt(request.getParameter("designationCode"));
    String dateOfBirthStr = request.getParameter("dateOfBirth");
    String gender = request.getParameter("gender");
    String isIndianStr = request.getParameter("isIndian");
    String basicSalaryStr = request.getParameter("basicSalary");
    String panNumber = request.getParameter("PANNumber");
    String aadharCardNumber = request.getParameter("aadharCardNumber");

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
Date dateOfBirth=simpleDateFormat.parse(dateOfBirthStr);
BigDecimal basicSalary=new BigDecimal(basicSalaryStr);

boolean isIndian=false;
if(isIndianStr!=null && isIndianStr.equals("Y"))
{
isIndian=true;
}

boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
boolean designationCodeExists=false;
boolean exists=false;
EmployeeDAO employeeDAO=new EmployeeDAO();
EmployeeDTO employee;
DesignationDAO designationDAO=new DesignationDAO();
String panNumberError="";
String aadharCardNumberError="";
String designationCodeError="";

try{
if(employeeDAO.employeeIdExists(employeeId)==false)
{
    response.sendRedirect("Employees.jsp");
    return;
}
}catch(DAOException daoException)
{
    response.sendRedirect("Employees.jsp");
    return;
}

try
{
designationCodeExists=designationDAO.designationCodeExists(designationCode);
if(designationCodeExists==false)
{
request.setAttribute("designationCodeError","Invalid Designation Code");
}
}catch(DAOException daoException)
{
designationCodeExists=false;
}
try
{

employee=employeeDAO.getByPANNumber(panNumber);
if(employee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
    {

        panNumberExists=true;
        exists=true;
    request.setAttribute("panNumberError","PAN Number exists");
    }

}catch(DAOException daoException)
{
//do nothing
}
try
{
employee=employeeDAO.getByAadharCardNumber(aadharCardNumber);
if(employee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{

aadharCardNumberExists=true;
request.setAttribute("aadharCardNumberError","Aadhar card number exists");
exists=true;
}
}catch(DAOException daoException)
{
//do nothing
}
if(exists || designationCodeExists==false)
{
EmployeeBean employeeBean=new EmployeeBean();
employeeBean.setEmployeeId(employeeId);
employeeBean.setName(name);
employeeBean.setDesignationCode(designationCode);
employeeBean.setDesignation(designationCodeError);
employeeBean.setDateOfBirth(dateOfBirthStr);
employeeBean.setIsIndian(isIndian);
employeeBean.setGender(gender);
employeeBean.setBasicSalary(basicSalaryStr);
employeeBean.setPANNumber(panNumber);
employeeBean.setAadharCardNumber(aadharCardNumber);
request.setAttribute("employeeBean",employeeBean);
requestDispatcher=request.getRequestDispatcher("/EmployeeEditForm.jsp");
requestDispatcher.forward(request,response);
return;
}

employee=new EmployeeDTO();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignationCode(designationCode);
employee.setDesignation(designationDAO.getByCode(designationCode).getTitle());
employee.setDateOfBirth(dateOfBirth);
employee.setGender(gender);
employee.setBasicSalary(basicSalary);
employee.setIsIndian(isIndian);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);

try
{

employeeDAO.update(employee);

MessageBean messageBean;
messageBean=new MessageBean();
messageBean.setHeading("Employee (Update Module)");
messageBean.setMessage("Employee Updated");
messageBean.setGenerateButtons(true);
messageBean.setButtonOneText("Ok");
messageBean.setButtonOneAction("Employees.jsp");
request.setAttribute("messageBean",messageBean);
requestDispatcher=request.getRequestDispatcher("/Notification.jsp");
requestDispatcher.forward(request,response);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

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