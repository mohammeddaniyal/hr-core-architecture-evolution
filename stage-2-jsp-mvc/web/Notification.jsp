<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<jsp:useBean id='messageBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.MessageBean' />
<jsp:include page='/MasterPageTopSection.jsp' />
<h2>${messageBean.heading}</h2>
${messageBean.message}
<!-- as a designer -->
<tm:If condition='${messageBean.generateButtons}'>
<table>
<tr>
<td>
<button type="button"
        onclick="location.href='${pageContext.request.contextPath}/${messageBean.buttonOneAction}'">
${messageBean.buttonOneText}
</button>
</form>
</td>
<tm:If condition='${messageBean.generateTwoButtons}'>
<td>
<button type="button"
        onclick="location.href='${pageContext.request.contextPath}/${messageBean.buttonTwoAction}'">
    ${messageBean.buttonTwoText}
</button>
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
<form action='/stage2/${messageBean.buttonOneAction}'>
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
