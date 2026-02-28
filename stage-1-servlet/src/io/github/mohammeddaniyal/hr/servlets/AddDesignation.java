package io.github.mohammeddaniyal.hr.servlets;
import io.github.mohammeddaniyal.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class AddDesignation extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
PrintWriter pw=null;
String title="";
try
{
title=request.getParameter("title");
pw=response.getWriter();
response.setContentType("text/html");
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
DesignationDTO designation;
designation=new DesignationDTO();
designation.setTitle(title);
designationDAO.add(designation);




pw.println("<!DOCTYPE HTML>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<meta charset='utf-8'>");
pw.println("<title>HR Core | Stage 1 (Servlets)</title>");
pw.println("</head>");
pw.println("<body>");
pw.println("<!-- Main container starts here-->");

pw.println("<div style='width:90hw;height:auto;border:1px solid black'>");
pw.println("<!-- header starts here -->");
pw.println("<div style='margin:5px;width:90hw;height:auto;border:1px solid black'>");
pw.println("<a href='index.html'><img src='images/logo.png' style='float:left'></a><div style='margin-top:6px;margin-bottom:6px;padding:5px;font-size:20pt'>&nbsp;HR Core</div>");
pw.println("</div>");

pw.println("<!-- header ends here -->");
pw.println("<!-- content-section starts here -->");
pw.println("<div style='width:90hw;height:70vh;margin:5px;border:1px solid white'>");

pw.println("<!-- left panel starts here -->");
pw.println("<div style='height:65vh;margin:5px;float:left;padding:5px;border:1px solid black'>");
pw.println("<b>Designations</b>");
pw.println("<br>");
pw.println("<a href='employeesView'>Employees</a>");
pw.println("<br><br>");
pw.println("<a href='index.html'>Home</a>");
pw.println("</div>");
pw.println("<!-- left panel ends here -->");

pw.println("<!-- right panel starts here -->");
pw.println("<div style='height:65vh;margin-left:105px;margin-right:5px;margin-bottom:px;margin-top:5px;padding:5px;border:1px solid black'>");
pw.println("<h3>Notification!</h3>");
pw.println("<b>Designation Added</b>");
pw.println("<br><br>");
pw.println("Do you want to add more?");
pw.println("<table>");
pw.println("<tr>");
pw.println("<td>");
pw.println("<form action='AddDesignation.html'>");
pw.println("<button type='submit'>Yes</button>");
pw.println("</form>");
pw.println("</td>");
pw.println("<td>");
pw.println("<form action='designationsView'>");
pw.println("<button type='submit'>No</button>");
pw.println("</form>");
pw.println("</td>");
pw.println("</tr>");
pw.println("</table>");
pw.println("</div>");
pw.println("<!-- right panel ends here -->");
pw.println("</div>");
pw.println("<!-- content-section ends here -->");
pw.println("<!-- footer starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;text-align:center;border:1px solid white'>");
pw.println("&copy; HR Core 2026");
pw.println("<!-- footer ends here -->");
pw.println("</div>");

pw.println("</div>");
pw.println("<!-- Main container ends here-->");
pw.println("</body>");
pw.println("</html>");
}catch(DAOException daoException)
{
pw.println("<!DOCTYPE HTML>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<meta charset='utf-8'>");
pw.println("<title>HR Core | Stage 1 (Servlets)</title>");
pw.println("<script>");
pw.println("function validateForm(frm)");
pw.println("{");
pw.println("var nn=frm.title.value.trim();");
pw.println("var titleErrorSection=document.getElementById('titleErrorSection');");
pw.println("titleErrorSection.innerHTML='';");
pw.println("if(nn.length==0)");
pw.println("{");
pw.println("titleErrorSection.innerHTML='required';");
pw.println("frm.title.focus();");
pw.println("return false;");
pw.println("}");
pw.println("return true;");
pw.println("}");
pw.println("function cancelAddition()");
pw.println("{");
pw.println("document.getElementById('cancelAdditionForm').submit();");
pw.println("}");
pw.println("</script>");
pw.println("</head>");
pw.println("<body>");
pw.println("<!-- Main container starts here-->");
pw.println("<div style='width:90hw;height:auto;border:1px solid black'>");
pw.println("<!-- header starts here -->");
pw.println("<div style='margin:5px;width:90hw;height:auto;border:1px solid black'>");
pw.println("<a href='index.html'><img src='images/logo.png' style='float:left'></a><div style='margin-top:6px;margin-bottom:6px;padding:5px;font-size:20pt'>&nbspHR Core</div>");
pw.println("</div>");

pw.println("<!-- header ends here -->");
pw.println("<!-- content-section starts here -->");
pw.println("<div style='width:90hw;height:70vh;margin:5px;border:1px solid white'>");

pw.println("<!-- left panel starts here -->");
pw.println("<div style='height:65vh;margin:5px;float:left;padding:5px;border:1px solid black'>");
pw.println("<b>Designations</b>");
pw.println("<br>");
pw.println("<a href='employeesView'>Employees</a>");
pw.println("<br><br>");
pw.println("<a href='index.html'>Home</a>");
pw.println("</div>");
pw.println("<!-- left panel ends here -->");

pw.println("<!-- right panel starts here -->");
pw.println("<div style='height:65vh;margin-left:105px;margin-right:5px;margin-bottom:px;margin-top:5px;padding:5px;border:1px solid black'>");
pw.println("<h2>Designation (Add Module)</h2>");
pw.println("<div style='color:red'>"+daoException.getMessage()+"</div>");

pw.println("<form action='addDesignation' onsubmit='return validateForm(this)'>");
pw.println("Designation");
pw.println("<input type='text' id='title' name='title' maxlength='35' size='36' value='"+title+"'>");
pw.println("<span id='titleErrorSection' style='color:red'></span>");
pw.println("<br>");
pw.println("<button type='submit' >Add</button>");
pw.println("<button type='button' onclick='cancelAddition()'>Cancel</button>");
pw.println("</form>");
pw.println("</div>");
pw.println("<!-- right panel ends here -->");
pw.println("</div>");
pw.println("<!-- content-section ends here -->");
pw.println("<!-- footer starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;text-align:center;border:1px solid white'>");
pw.println("&copy; HR Core 2026");
pw.println("<!-- footer ends here -->");
pw.println("</div>");

pw.println("</div>");
pw.println("<!-- Main container ends here-->");
pw.println("<form id='cancelAdditionForm' action='designationsView'></form>");
pw.println("</body>");
pw.println("</html>");


}catch(Exception exception)
{
  exception.printStackTrace();

    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    response.setContentType("text/html");


    pw.println("<h2>System Error</h2>");
    pw.println("<p>An unexpected error occurred. Please try again later.</p>");}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
}