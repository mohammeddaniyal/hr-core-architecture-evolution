<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='Employee' />
<jsp:useBean id='employeeBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.EmployeeBean' />
<jsp:include page='MasterPageTopSection.jsp' />
<script src='/stage2/js/ConfirmDeleteEmployee.js'></script>
<h2>Employee (Delete Module)</h2>
<form method='post' action='/stage2/DeleteEmployee.jsp'>
<tm:FormID />
<b>Name : ${employeeBean.name}</b><br>
<b>Designation : ${employeeBean.designation}</b><br>
<b>Gender : ${employeeBean.gender}</b><br>
<b>Date Of Birth : ${employeeBean.dateOfBirth}</b><br>

<tm:If condition='${employeeBean.isIndian==true}'>
<b> Nationality : Indian</b><br>
</tm:If>
<tm:If condition='${employeeBean.isIndian!=true}'>
<b> Nationality : Non-Indian</b><br>
</tm:If>



<b>Basic Salary : ${employeeBean.basicSalary}</b><br>
<b>PAN Number : ${employeeBean.PANNumber}</b><br>
<b>Aadhar Card Number : ${employeeBean.aadharCardNumber}</b><br>

<br><br>
<input type='hidden' id='employeeId' name='employeeId' value='${employeeBean.employeeId}'>
Are you sure you want to delete Employee ?
<br>
<button type='submit' >Yes</button>
<button type='button' onclick='cancelDeletion()'>No</button>
</form>
<form id='cancelDeletionForm' action='/stage2/Employees.jsp'></form>
<jsp:include page='MasterPageBottomSection.jsp' />