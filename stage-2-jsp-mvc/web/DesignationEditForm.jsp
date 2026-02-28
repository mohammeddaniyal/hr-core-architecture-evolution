<%@taglib uri='WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='DESIGNATION' />
<jsp:useBean id='designationBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.DesignationBean' />
<jsp:useBean id='errorBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.ErrorBean' />
<jsp:include page='MasterPageTopSection.jsp' />
<script src='${pageContext.request.contextPath}/js/DesignationEditForm.js'></script>
<h2>Designation (Edit Module)</h2>
<span class='error'>
<jsp:getProperty name='errorBean' property='error' />
</span>
<form method='post' action='${pageContext.request.contextPath}/updateDesignation' onsubmit='return validateForm(this)'>
Designation
<input type='hidden' id='code' name='code' value='${designationBean.code}'>
<input type='text' id='title' name='title' maxlength='35' size='36' value='${designationBean.title}'>
<span id='titleErrorSection' class='error'></span>
<br>
<button type='submit' >Update</button>
<button type='button' onclick='cancelUpdate()'>Cancel</button>
</form>
<form id='cancelUpdateForm' action='${pageContext.request.contextPath}/Designations.jsp'></form>
<jsp:include page='MasterPageBottomSection.jsp' />