package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.common.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.math.*;
import com.google.gson.*;
public class AddEmployee extends HttpServlet
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
Response responseObject=new Response();
EmployeeDAO employeeDAO=new EmployeeDAO();
DesignationDAO designationDAO=new DesignationDAO();
DesignationDTO designation=null;
boolean designationCodeExists=true;
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
try
{
designation=designationDAO.getByCode(employee.getDesignationCode());
}catch(DAOException daoException)
{
designationCodeExists=false;
}
EmployeeDTO e;
try
{
e=employeeDAO.getByPANNumber(employee.getPANNumber());
panNumberExists=true;
}catch(DAOException daoException)
{
}
try
{
e=employeeDAO.getByAadharCardNumber(employee.getAadharCardNumber());

if(employee!=null)aadharCardNumberExists=true;
}catch(DAOException daoException)
{
}

if(designationCodeExists==false ||  panNumberExists || aadharCardNumberExists)
{
if(designationCodeExists==false)
{
responseObject.setPropertyError("designationCode","Invalid Designation Code");
}
if(panNumberExists)
{
responseObject.setPropertyError("panNumber","PAN number exists.");
}
if(aadharCardNumberExists)
{
responseObject.setPropertyError("aadharCardNumber","Aadhar card number exists.");
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
employeeDAO.add(employee);
}catch(DAOException daoException)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError(daoException.getMessage());
String responseJSONString=gson.toJson(responseObject);
pw.print(responseJSONString);
pw.flush();
return;
}
responseObject.setSuccess(true);
responseObject.setResult(null);
responseObject.setError(null);
pw.print(gson.toJson(responseObject));
pw.flush();
}catch(Exception exception)
{

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