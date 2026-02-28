<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<jsp:useBean id='employeeBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.EmployeeBean' />
<jsp:useBean id='errorBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.ErrorBean' />
<tm:If condition='${employeeBean.dateOfBirth==""}'>
<jsp:setProperty name='employeeBean' property='dateOfBirth' value='1970-01-01' />
</tm:If>
<tm:Module name='EMPLOYEE' />
<jsp:include page='/MasterPageTopSection.jsp' />
<script src='${pageContext.request.contextPath}/js/EmployeeAddForm.js'></script>
<h2>Employee (Add Module)</h2>
<form method='post' action='${pageContext.request.contextPath}/AddEmployee.jsp' onsubmit='return validateForm(this)'>
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
<tm:EntityList populatorClass='io.github.mohammeddaniyal.hr.bl.DesignationBL' populatorMethod='getAll' name='designationBean'>
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
<tm:If condition='${employeeBean.isIndian==null}' >
<td><input type='checkbox' id='isIndian' name='isIndian' value='Y'>
</tm:If>
<tm:If condition='${employeeBean.isIndian}' >
<td><input type='checkbox' id='isIndian' name='isIndian' value='Y' checked>
</tm:If>
<tm:If condition='${employeeBean.isIndian==false}' >
<td><input type='checkbox' id='isIndian' name='isIndian' value='N' unchecked>
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
<button type='button' onclick='cancelAddition()'>Cancel</button></td>
</tr>
</table>
</form>
<form id='cancelAdditionForm' action='${pageContext.request.contextPath}/Employees.jsp'></form>
<script>
XRay.init({
    title: "Stage 2: JSP + MVC + Guarded Flow",
    module: "Add Employee (Layered Validation + Controlled Routing)",

    impact: [
        "MVC Separation: JSP handles view, servlet handles control flow.",
        "Authentication Guard: Custom tag prevents unauthorized form access.",
        "Bean Binding: Request parameters automatically mapped to EmployeeBean.",
        "Structured Validation: Errors passed as request attributes instead of rebuilding HTML.",
        "Centralized Notification: Success responses rendered via reusable Notification.jsp.",
        "Still Forward-Based Flow: No redirect-based PRG yet."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. User submits employee form." },
        { location: "server", type: "process", message: "2. AddEmployee.jsp binds parameters to EmployeeBean." },
        { location: "server", type: "process", message: "3. Request forwarded to AddEmployee servlet." },
        { location: "server", type: "process", message: "4. Servlet performs business validations (designation, PAN, Aadhaar)." },
        { location: "database", type: "process", message: "5. DAO executes INSERT after validation passes." },
        { location: "server", type: "response", message: "6. Servlet forwards to Notification.jsp with MessageBean." },
        { location: "client", type: "response", message: "7. Browser renders confirmation page." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. User submits invalid or duplicate data." },
        { location: "server", type: "process", message: "2. Validation detects duplicate PAN, Aadhaar, or invalid designation." },
        { location: "server", type: "response", message: "3. Servlet forwards back to EmployeeAddForm.jsp with error attributes." },
        { location: "client", type: "response", message: "4. Form reloads with field-level error messages preserved." }
    ]
});
</script>
<jsp:include page='/MasterPageBottomSection.jsp' />