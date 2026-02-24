<%@ taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Guard>
<jsp:forward page='/LoginForm.jsp' />
</tm:Guard>
<tm:FormResubmitted>
<tm:Module name='HOME' />
<jsp:forward page='/notifyFormResubmission' />
</tm:FormResubmitted>
<jsp:useBean id='designationBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.DesignationBean' />
<jsp:setProperty name='designationBean' property='*'/>
<jsp:forward page='/deleteDesignation' />
