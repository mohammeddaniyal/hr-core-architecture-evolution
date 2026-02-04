<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<jsp:useBean id='employeeBean' scope='request' class='com.thinking.machines.hr.beans.EmployeeBean' />
<tm:Module name='EMPLOYEE' />
<jsp:include page='/MasterPageTopSection.jsp' />
<script src='/styletwo/js/EmployeeEditForm.js'></script>
<h2>Employee (Edit Module)</h2>
<form method='post' action='/styletwo/AddEmployee.jsp' onsubmit='return validateForm(this)'>
<tm:FormID />
<table>
<tr>
<td>Name</td>
<td><input type='text' id='name' name='name' maxlength='50' size='51' value='${employeeBean.name}'>
<span id='nameErrorSection' style='color:red'></span></td>
</tr>
<tr>
<td>Designation</td>
<td><select id='designationCode' name='designationCode'>
<option value='-1'>&lt;Select Designation&gt;</option>
<tm:EntityList populatorClass='com.thinking.machines.hr.bl.DesignationBL' populatorMethod='getAll' name='designationBean'>
<option value='${designationBean.code}'>${designationBean.title}</option>
<tm:If condition='${designationBean.code==employeeBean.designationCode}'>
<option selected value='${designationBean.code}'>${designationBean.title}</option>
</tm:If>
</tm:EntityList>
</select><span id='designationCodeErrorSection' style='color:red'></span></td>
</td>
</tr>
<tr>
<td>Date Of Birth</td>
<td><input type='Date' id='dateOfBirth' name='dateOfBirth' value='${employeeBean.dateOfBirth}'>
<span id='dateOfBirthErrorSection' style='color:red'></span></td>
</tr>


<tr>
<td>Gender</td>
<td>
<tm:If condition='${employeeBean.isMale()}'>
<input type='radio' id='male' name='gender' value='M' checked>Male
<input type='radio' id='female' name='gender' value='F' unchecked>Female
</tm:If>
<tm:If condition='${employeeBean.isFemale()}'>
<input type='radio' id='male' name='gender' value='M' unchecked>Male
<input type='radio' id='female' name='gender' value='F' checked>Female
</tm:If>
<tm:If condition='${employeeBean.isMale()!=true}'>
<tm:If condition='${employeeBean.isFemale()!=true}'>
<input type='radio' id='male' name='gender' value='M'>Male
<input type='radio' id='female' name='gender' value='F'>Female
</tm:If>
</tm:If>
&nbsp;&nbsp;&nbsp;&nbsp;
<span id='genderErrorSection' style='color:red'></span>
</td>
</tr>
<tr>
<td>Indian ?</td>
<tm:If condition='${employeeBean.isIndian}' >
<td><input type='checkbox' id='isIndian' name='isIndian' value='Y' checked>
</tm:If>
<tm:If condition='${employeeBean.isIndian==false}' >
<td><input type='checkbox' id='isIndian' name='isIndian' value='Y' unchecked>
</tm:If>
<td><span id='isIndianErrorSection' style='color:red'></span></td>
</tr>

<td>Basic Salary</td>
<td><input type='text' style='text-align:right' id='basicSalary' name='basicSalary' value='${employeeBean.basicSalary}'>
<span id='basicSalaryErrorSection' style='color:red'></span></td>
</tr>

<td>PAN Number</td>
<td><input type='text' id='PANNumber' name='PANNumber' maxlength='10' size='11' value='${employeeBean.PANNumber}'>
<span id='panNumberErrorSection' style='color:red'>${panNumberError}</span></td>
</tr>

<td>Aadhar Card Number</td>
<td><input type='text' id='aadharCardNumber' name='aadharCardNumber' maxlength='10' size='11' value='${employeeBean.aadharCardNumber}'>
<span id='aadharCardNumberErrorSection' style='color:red'>${aadharCardNumberError}</span></td>
</tr>

<tr colspan='2'>
<td><button type='submit' >Add</button>
&nbsp;&nbsp;
<button type='button' onclick='cancelUpdation()'>Cancel</button></td>
</tr>
</table>
</form>
<form id='cancelUpdationForm' action='/styletwo/Employees.jsp'></form>
<jsp:include page='/MasterPageBottomSection.jsp' />