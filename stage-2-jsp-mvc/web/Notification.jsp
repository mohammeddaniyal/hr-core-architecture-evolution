<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<jsp:useBean id='messageBean' scope='request' class='com.thinking.machines.hr.beans.MessageBean' />
<jsp:include page='/MasterPageTopSection.jsp' />
<h2>${messageBean.heading}</h2>
${messageBean.message}
<!-- as a designer -->
<tm:If condition='${messageBean.generateButtons}'>
<table>
<tr>
<td>
<form action='/styletwo/${messageBean.buttonOneAction}'>
<button>${messageBean.buttonOneText}</button>
</form>
</td>
<tm:If condition='${messageBean.generateTwoButtons}'>
<td>
<form action='/styletwo/${messageBean.buttonTwoAction}'>
<button>${messageBean.buttonTwoText}</button>
</form>
</td>
</tm:If>
</tr>
</table>
</tm:If>
<%-- this is how as a programmer we'll do the programming for buttons 
<%
if(messageBean.getGenerateButtons())
{
%>
<table>
<tr>
<td>
<form action='/styletwo/${messageBean.buttonOneAction}'>
<button>${messageBean.buttonOneText}</button>
</form>
</td>
<%
if(messageBean.getGenerateTwoButtons())
{
%>
<td>
<form action='/styleone/${messageBean.buttonTwoAction}'>
<button>${messageBean.buttonTwoText}</button>
</form>
</td>
<%
}
%>
</tr>
</table>
<%
}
%>
--%>
<jsp:include page='/MasterPageBottomSection.jsp' />
