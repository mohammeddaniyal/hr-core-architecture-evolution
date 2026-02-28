package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.common.*;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class GetEmployees extends HttpServlet
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
response.setContentType("text/plain");
response.setCharacterEncoding("utf-8");
EmployeeDAO employeeDAO=new EmployeeDAO();
List<EmployeeDTO> employees=null;
Gson gson=new Gson();
Response responseObject=new Response();
try
{
employees=employeeDAO.getAll();
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
responseObject.setResult(employees);
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