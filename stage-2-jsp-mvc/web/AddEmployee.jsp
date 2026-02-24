<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Guard>
<jsp:forward page='/LoginForm.jsp' />
</tm:Guard>
<tm:FormResubmitted>
<jsp:forward page='/notifyFormResubmission' />
</tm:FormResubmitted>
<jsp:useBean id='employeeBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.EmployeeBean' />
<jsp:setProperty name='employeeBean' property='*' />
<jsp:setProperty name='employeeBean' property='PANNumber' />
<jsp:forward page='/addEmployee' />