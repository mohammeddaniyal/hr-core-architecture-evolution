package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.common.*;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.*;
public class AddDesignation extends HttpServlet
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
DesignationDTO designation=gson.fromJson(rawData,DesignationDTO.class);
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
DesignationDAO designationDAO=new DesignationDAO();
String responseJSONString;
Response responseObject=new Response();
try
{
designationDAO.add(designation);
}catch(DAOException daoException)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError(daoException.getMessage());
System.out.println("error1");
responseJSONString=gson.toJson(responseObject);
System.out.println(responseJSONString);
pw.print(responseJSONString);
pw.flush();
return;
}
responseObject.setSuccess(true);
responseObject.setResult(null);
responseObject.setError(null);
System.out.println("error2");
responseJSONString=gson.toJson(responseObject);
System.out.println(responseJSONString);
pw.print(responseJSONString);
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