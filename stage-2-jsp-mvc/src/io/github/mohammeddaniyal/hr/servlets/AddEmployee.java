package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;
import java.util.*;
public class AddEmployee extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
RequestDispatcher requestDispatcher;
try
{
EmployeeBean employeeBean=(EmployeeBean)request.getAttribute("employeeBean");
String name=employeeBean.getName();
int designationCode=employeeBean.getDesignationCode();
String gender=employeeBean.getGender();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
Date dateOfBirth=simpleDateFormat.parse(employeeBean.getDateOfBirth());
BigDecimal basicSalary=new BigDecimal(employeeBean.getBasicSalary());

boolean isIndian=false;
String isIndianString=request.getParameter("isIndian");
System.out.println(isIndianString);
if(isIndianString!=null && isIndianString.equals("Y"))
{
isIndian=true;
}
employeeBean.setIsIndian(isIndian);
System.out.println("Is indian : "+isIndian);

String panNumber=employeeBean.getPANNumber();
String aadharCardNumber=employeeBean.getAadharCardNumber();
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
panNumberExists=true;
request.setAttribute("panNumberError","PAN Number exists");
exists=true;
}catch(DAOException daoException)
{
//do nothing
}
try
{
employee=employeeDAO.getByAadharCardNumber(aadharCardNumber);
aadharCardNumberExists=true;
request.setAttribute("aadharCardNumberError","Aadhar card number exists");
exists=true;
}catch(DAOException daoException)
{
//do nothing
}
if(exists || designationCodeExists==false)
{
request.setAttribute("employeeBean",employeeBean);
requestDispatcher=request.getRequestDispatcher("/EmployeeAddForm.jsp");
requestDispatcher.forward(request,response);
return;
}

employee=new EmployeeDTO();
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
employeeDAO.add(employee);
MessageBean messageBean;
messageBean=new MessageBean();
messageBean.setHeading("Employee (Add Module)");
messageBean.setMessage("Employee added, add more ?");
messageBean.setGenerateButtons(true);
messageBean.setGenerateTwoButtons(true);
messageBean.setButtonOneText("Yes");
messageBean.setButtonOneAction("EmployeeAddForm.jsp");
messageBean.setButtonTwoText("No");
messageBean.setButtonTwoAction("Employees.jsp");
request.setAttribute("messageBean",messageBean);
requestDispatcher=request.getRequestDispatcher("/Notification.jsp");
requestDispatcher.forward(request,response);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

System.out.println(name+","+designationCode+","+gender+","+simpleDateFormat.format(dateOfBirth));
System.out.println(basicSalary.toPlainString()+","+panNumber+","+aadharCardNumber);
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