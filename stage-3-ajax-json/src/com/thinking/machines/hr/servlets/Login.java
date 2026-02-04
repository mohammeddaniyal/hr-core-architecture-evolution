package com.thinking.machines.hr.servlets;
import com.thinking.machines.hr.common.*;
import com.google.gson.*;
import com.thinking.machines.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class Login extends HttpServlet
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
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
AdministratorDTO administrator=gson.fromJson(rawData,AdministratorDTO.class);
AdministratorDAO administratorDAO=new AdministratorDAO();
Response responseObject=new Response();
String username=administrator.getUsername();
String password=administrator.getPassword();
try
{
administrator=administratorDAO.getByUsername(username);
}catch(DAOException daoException)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError("Invalid username/password");
pw.print(gson.toJson(responseObject));
pw.flush();
return;
}
if(administrator.getPassword().equals(password)==false)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError("Invalid username/password");
pw.print(gson.toJson(responseObject));
pw.flush();
return;
}
HttpSession session=request.getSession();
session.setAttribute("username",username);
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