package com.thinking.machines.hr.servlets;
import com.thinking.machines.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.math.*;
import com.thinking.machines.hr.common.*;
import com.google.gson.*;
public class GetEmployee extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception e)
{
//do nothing
}
}
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
String employeeId=request.getParameter("employeeId");
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
EmployeeDAO employeeDAO=new EmployeeDAO();
EmployeeDTO employee=null;
Gson gson=new Gson();
Response responseObject=new Response();
try
{
employee=employeeDAO.getByEmployeeId(employeeId);
}catch(DAOException daoException)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError(daoException.getMessage());
pw.print(gson.toJson(responseObject));
pw.flush();
return;
}
responseObject.setSuccess(true);
responseObject.setResult(employee);
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