package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.common.*;
import com.google.gson.*;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class GetUsernameInSession extends HttpServlet
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
HttpSession session=request.getSession();
String username=(String)session.getAttribute("username");
Gson gson=new Gson();
Response responseObject=new Response();
if(username==null)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setError(null);
pw.print(gson.toJson(responseObject));
pw.flush();
return;
}
responseObject.setSuccess(true);
responseObject.setResult(username);
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