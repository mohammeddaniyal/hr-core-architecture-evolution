package com.thinking.machines.hr.servlets;
import com.thinking.machines.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class ConfirmDeleteDesignation extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
PrintWriter pw=null;
try
{
int code;
try
{
code=Integer.parseInt(request.getParameter("code"));
}catch(NumberFormatException nfe)
{
sendBackView(response);
return;
}
pw=response.getWriter();
response.setContentType("text/html");
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
DesignationDTO designation;
designation=designationDAO.getByCode(code);


pw.println("<!DOCTYPE HTML>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<meta charset='utf-8'>");
pw.println("<title>HR Application</title>");
pw.println("<script>");
pw.println("function cancelDeleting()");
pw.println("{");
pw.println("document.getElementById('cancelDeletingForm').submit();");
pw.println("}");
pw.println("</script>");
pw.println("</head>");
pw.println("<body>");
pw.println("<!-- Main container starts here-->");
pw.println("<div style='width:90hw;height:auto;border:1px solid black'>");
pw.println("<!-- header starts here -->");
pw.println("<div style='margin:5px;width:90hw;height:auto;border:1px solid black'>");
pw.println("<a href='/styleone'index.html'><img src='/styleone/images/logo.png' style='float:left'></a><div style='margin-top:6px;margin-bottom:6px;padding:5px;font-size:20pt'>&nbspThinking Machines</div>");
pw.println("</div>");
pw.println("<!-- header ends here -->");
pw.println("<!-- content-section starts here -->");
pw.println("<div style='width:90hw;height:70vh;margin:5px;border:1px solid white'>");
pw.println("<!-- left panel starts here -->");
pw.println("<div style='height:65vh;margin:5px;float:left;padding:5px;border:1px solid black'>");
pw.println("<b>Designations</b>");
pw.println("<br>");
pw.println("<a href='/styleone/employeesView'>Employees</a>");
pw.println("<br><br>");
pw.println("<a href='/styleone/index.html'>Home</a>");
pw.println("</div>");
pw.println("<!-- left panel ends here -->");
pw.println("<!-- right panel starts here -->");
pw.println("<div style='height:65vh;margin-left:105px;margin-right:5px;margin-bottom:px;margin-top:5px;padding:5px;border:1px solid black'>");
pw.println("<h2>Designation (Delete Module)</h2>");
pw.println("<form method='post' action='/styleone/deleteDesignation' onsubmit='return validateForm(this)'>");
pw.println("<input type='hidden' id='code' name='code' value='"+code+"'>");
pw.println("Designation : ");
pw.println("<b>"+designation.getTitle()+"</b><br><br>");
pw.println("Are you sure you want to delete this designation?<br><br>");
pw.println("<button type='submit' >Yes</button>");
pw.println("<button type='button' onclick='cancelDeleting()'>No</button>");
pw.println("</form>");
pw.println("</div>");
pw.println("<!-- right panel ends here -->");
pw.println("</div>");
pw.println("<!-- content-section ends here -->");
pw.println("<!-- footer starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;text-align:center;border:1px solid white'>");
pw.println("&copy; Thinking Machines 2024");
pw.println("<!-- footer ends here -->");
pw.println("</div>");
pw.println("</div>");
pw.println("<!-- Main container ends here-->");
pw.println("<form id='cancelDeletingForm' action='/styleone/designationsView'></form>");
pw.println("</body>");
pw.println("</html>");




}catch(DAOException daoException)
{
sendBackView(response);
}
catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
private void sendBackView(HttpServletResponse response)
{
try
{
List<DesignationDTO> designations;
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
designations=designationDAO.getAll();
PrintWriter pw;
pw=response.getWriter();
response.setContentType("text/html");




pw.println("<!DOCTYPE HTML>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<meta charset='utf-8'>");
pw.println("<title>HR Application</title>");
pw.println("</head>");
pw.println("<body>");
pw.println("<!-- Main container starts here-->");
pw.println("<div style='width:90hw;height:auto;border:1px solid black'>");
pw.println("<!-- header starts here -->");
pw.println("<div style='margin:5px;width:90hw;height:auto;border:1px solid black'>");
pw.println("<a href='/styleone'index.html'><img src='/styleone/images/logo.png' style='float:left'></a><div style='margin-top:6px;margin-bottom:6px;padding:5px;font-size:20pt'>&nbspThinking Machines</div>");
pw.println("</div>");

pw.println("<!-- header ends here -->");
pw.println("<!-- content-section starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;border:1px solid white'>");

pw.println("<!-- left panel starts here -->");
pw.println("<div style='height:65vh;margin:5px;float:left;padding:5px;border:1px solid black'>");
pw.println("<b>Designations</b>");
pw.println("<br>");
pw.println("<a href='/styleone/employeesView'>Employees</a>");
pw.println("<br><br>");
pw.println("<a href='/styleone/index.html'>Home</a>");

pw.println("</div>");
pw.println("<!-- left panel ends here -->");

pw.println("<!-- right panel starts here -->");
pw.println("<div style='height:65vh;margin-left:105px;margin-right:5px;margin-bottom:px;margin-top:5px;padding:5px;overflow:scroll;border:1px solid black'>");
pw.println("<h2>Designations</h2>");

pw.println("<table border='1'>");
pw.println("<thead>");
pw.println("<tr>");
pw.println("<th colspan='4' style='text-align:right;padding:5px'>");
pw.println("<a href='/styleone/AddDesignation.html'>Add new Designation</a>");
pw.println("</th>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<th style='width:60px;text-align:center'>S.No</th>");
pw.println("<th style='width:200px;text-align:center'>Designation</th>");
pw.println("<th style='width:100px;text-align:center'>Edit</th>");
pw.println("<th style='width:100px;text-align:center'>Delete</th>");
pw.println("</tr>");
pw.println("</thead>");
pw.println("<tbody>");

int x;
int code;
String title;
int sno=0;
DesignationDTO designationDTO;
for(x=0;x<designations.size();x++)
{
sno++;
designationDTO=designations.get(x);
code=designationDTO.getCode();
title=designationDTO.getTitle();
pw.println("<tr>");
pw.println("<td style='text-align:right'>"+sno+".</td>");
pw.println("<td>"+title+"</td>");
pw.println("<td style='text-align:center'><a href='/styleone/editDesignation?code="+code+"'>Edit</a></td>");
pw.println("<td style='text-align:center'><a href='/styleone/deleteDesignation?code="+code+"'>Delete</a></td>");
pw.println("</tr>");
}

pw.println("</tbody>");
pw.println("</table>");
pw.println("</div>");
pw.println("<!-- right panel ends here -->");
pw.println("</div>");
pw.println("<!-- content-section ends here -->");
pw.println("<!-- footer starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;text-align:center;border:1px solid white'>");
pw.println("&copy; Thinking Machines 2024");
pw.println("<!-- footer ends here -->");
pw.println("</div>");

pw.println("</div>");
pw.println("<!-- Main container ends here-->");
pw.println("</body>");
pw.println("</html>");

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
catch(Exception e)
{
System.out.println(e.getMessage());
}

}
}