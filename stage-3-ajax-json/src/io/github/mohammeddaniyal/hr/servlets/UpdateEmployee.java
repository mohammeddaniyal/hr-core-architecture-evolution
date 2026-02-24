package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.math.*;
import com.google.gson.*;
import io.github.mohammeddaniyal.hr.common.*;
public class UpdateEmployee extends HttpServlet
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
BufferedReader br=request.getReader();
StringBuffer sb=new StringBuffer();
String d;
while(true)
{
d=br.readLine();
if(d==null) break;
sb.append(d);
}
String rawData=sb.toString();
Gson gson=new Gson();
EmployeeDTO employee=gson.fromJson(rawData,EmployeeDTO.class);
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
EmployeeDAO employeeDAO=new EmployeeDAO();
EmployeeDTO e;
DesignationDAO designationDAO=new DesignationDAO();
DesignationDTO designation=null;
boolean designationCodeExists=true;
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
int designationCode=employee.getDesignationCode();
String employeeId=employee.getEmployeeId();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
Response responseObject=new Response();
try
{
designation=designationDAO.getByCode(designationCode);
}catch(DAOException daoException)
{
designationCodeExists=false;
}

try
{
e=employeeDAO.getByPANNumber(panNumber);
if(e.getEmployeeId().equals(employeeId)==false)panNumberExists=true;
}catch(DAOException daoException)
{
}
try
{
e=employeeDAO.getByAadharCardNumber(aadharCardNumber);
if(e.getEmployeeId().equals(employeeId)==false) aadharCardNumberExists=true;
}catch(DAOException daoException)
{
}
if(designationCodeExists==false ||  panNumberExists || aadharCardNumberExists)
{
if(designationCodeExists==false)
{
responseObject.setPropertyError("designationCode","Invalid designation code");
}
if(panNumberExists)
{
responseObject.setPropertyError("panNumber","PAN number exists");
}
if(aadharCardNumberExists)
{
responseObject.setPropertyError("aadharCardNumber","Aadhar card number exists");
}
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError(null);
pw.print(gson.toJson(responseObject));
pw.flush();
return;
}
employee.setDesignation(designation.getTitle());
try
{
employeeDAO.update(employee);
}catch(DAOException daoException)
{
}
responseObject.setSuccess(true);
responseObject.setResult(null);
responseObject.setError(null);
pw.print(gson.toJson(responseObject));
pw.flush();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
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