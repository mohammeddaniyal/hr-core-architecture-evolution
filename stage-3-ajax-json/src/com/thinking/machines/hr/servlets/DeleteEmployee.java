package com.thinking.machines.hr.servlets;
import com.thinking.machines.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.thinking.machines.hr.common.*;
import com.google.gson.*;
public class DeleteEmployee extends HttpServlet
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
String employeeId=gson.fromJson(rawData,String.class);
System.out.println(employeeId);
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
Response responseObject=new Response();
EmployeeDAO employeeDAO=new EmployeeDAO();
try
{
employeeDAO.deleteByEmployeeId(employeeId);
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