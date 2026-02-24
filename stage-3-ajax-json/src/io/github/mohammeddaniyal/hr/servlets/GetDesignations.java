package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.common.*;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
public class GetDesignations extends HttpServlet
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
Gson gson=new Gson();
DesignationDAO designationDAO=new DesignationDAO();
List<DesignationDTO> designations=null;

try
{
designations=designationDAO.getAll();
}catch(DAOException daoException)
{
}
Response responseObject=new Response();
responseObject.setSuccess(true);
responseObject.setResult(designations);
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