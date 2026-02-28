<%@taglib uri='WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='EMPLOYEE' />
<jsp:include page='/MasterPageTopSection.jsp' />
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/employees.css'>
<script src='${pageContext.request.contextPath}/js/Employees.js'></script>
<script>
    // so that it can be used in the Employees.js
    const APP_CONTEXT = "${pageContext.request.contextPath}";
</script>
<h4>Employees</h4>
<div class='employeeGrid'>
<table border='1' id='employeesGridTable'>
<thead>
<tr>
<th colspan='6' class='employeeGridHeader'>
<a href='${pageContext.request.contextPath}/EmployeeAddForm.jsp'>Add Employee</a>
</th>
</tr>
<tr>
<th class='employeeGridSNOColumnTitle'>S.No</th>
<th class='employeeGridIdColumnTitle'>Id.</th>
<th class='employeeGridNameColumnTitle'>Name</th>
<th class='employeeGridDesignationColumnTitle'>Designation</th>
<th class='employeeGridEditOptionColumnTitle'>Edit</th>
<th class='employeeGridDeleteOptionColumnTitle'>Delete</th>
</tr>
</thead>
<tbody>
<tr style='cursor:pointer'>
<td style='text-align:right' placeHolderId='serialNumber'></td>
<td placeHolderId='employeeId'></td>
<td placeHolderId='name'></td>
<td placeHolderId='designation'></td>
<td style='text-align:center' placeHolderId='editOption'></td>
<td style='text-align:center' placeHolderId='deleteOption'></td>
</tr>
</tbody>
</table>
</div>
<div style='height:19vh;margin-left:5.25px;margin-right:5px;margin-bottom:px;margin-top:5px;padding:5px;border:1px solid black'>
<label style='background:gray;color:white;padding-left:5px;padding-right:5px'>Details</label>

<table border='0' width='100%'>
<tr>
<td>Employee Id : <span id='detailPanel_employeeId' style='margin-right:30px'></span></td>
<td>Name : <span id='detailPanel_name' style='margin-right:30px'></span></td>
<td>Designation : <span id='detailPanel_designation' style='margin-right:30px'><span></td>
</tr>
<tr>
<td>Date of birth : <span id='detailPanel_dateOfBirth' style='margin-right:30px'></span></td>
<td>Gender : <span id='detailPanel_gender' style='margin-right:30px'></span></td>
<td>Is Indian : <span id='detailPanel_isIndian' style='margin-right:30px'></span></td>
</tr>
<tr>
<td>Basic Salary : <span id='detailPanel_basicSalary' style='margin-right:30px'></span></td>
<td>Pan Number : <span id='detailPanel_panNumber' style='margin-right:30px'></span></td>
<td>Aadhar Card Number : <span id='detailPanel_aadharCardNumber' style='margin-right:30px'></span></td>
</tr>
</table>
</div>

<script>
XRay.init({
    title: "Stage 2: JSP + Business Layer + JS Rendering",
    module: "View Employees (Template + Client-Side Grid Population)",

    impact: [
        "Separation of Concerns: JSP defines layout while JavaScript handles dynamic grid population.",
        "Business Layer Introduced: EmployeesJS servlet delegates to EmployeeBL instead of directly calling DAO.",
        "External Script Delivery: Employee data injected via dedicated JavaScript endpoint.",
        "Client-Side Rendering: Table rows generated in browser using DOM manipulation.",
        "Still Full Hydration: All employee records transferred at page load."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. User navigates to Employees.jsp." },
        { location: "client", type: "request", message: "2. Browser requests external script /js/Employees.js." },
        { location: "server", type: "process", message: "3. EmployeesJS servlet loads static JS template." },
        { location: "server", type: "process", message: "4. Servlet calls EmployeeBL.getAll()." },
        { location: "database", type: "process", message: "5. DAO executes JOIN query and returns employee data." },
        { location: "server", type: "response", message: "6. Servlet serializes employees into JavaScript array." },
        { location: "client", type: "response", message: "7. Browser executes script and dynamically populates grid." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. Browser requests Employees.js." },
        { location: "server", type: "process", message: "2. EmployeesJS servlet attempts BL/DAO access." },
        { location: "database", type: "error", message: "3. Database failure occurs during data retrieval." },
        { location: "server", type: "error", message: "4. Servlet forwards to ErrorPage.jsp with 500 status." },
        { location: "client", type: "response", message: "5. Grid fails to load due to script error or error page response." }
    ]
});
</script>
<jsp:include page='MasterPageBottomSection.jsp' />