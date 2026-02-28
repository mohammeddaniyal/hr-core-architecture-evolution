<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='DESIGNATION' />
<jsp:useBean id='designationBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.DesignationBean' />
<jsp:include page='MasterPageTopSection.jsp' />
<script src='${pageContext.request.contextPath}/js/ConfirmDeleteDesignation.js'></script>
<h2>Designation (Delete Module)</h2>
<form method='post' action='${pageContext.request.contextPath}/DeleteDesignation.jsp' onsubmit='return validateForm(this)'>
<tm:FormID />
<b>Designation : ${designationBean.title}</b>
<br><br>
<input type='hidden' id='code' name='code' value='${designationBean.code}'>
Are you sure you want to delete designation ?
<br>
<button type='submit' >Yes</button>
<button type='button' onclick='cancelDeletion()'>No</button>
</form>
<form id='cancelDeletionForm' action='${pageContext.request.contextPath}/Designations.jsp'></form>
<jsp:include page='MasterPageBottomSection.jsp' />