window.pages=window.pages || {};

window.pages['employees']={

employeeList:[],
selectedRow:null,
load: async function()
{
    XRay.init({
        title: "Stage 4: SPA State Management",
        module: "View Employees (Master-Detail API Hydration)",

        impact: [
            "Client-Side State Caching: Employee array is cached in JS memory (this.employeeList) upon initial fetch.",
            "Zero-Latency Detail View: Clicking a row updates the detail panel instantly from local memory, bypassing the server entirely.",
            "Role-Based Rendering: Action column dynamically injects Edit/Delete links or 'View Only' text based on sessionStorage.",
            "Client-Side Formatting: Dates are transformed from ISO format (yyyy-mm-dd) to localized strings natively in the browser.",
            "Decoupled Presentation: Backend serves pure JSON; frontend manages all DOM creation and interaction."
        ],

        successFlow: [
            { location: "client",   type: "request",  message: "1. Router executes employees.load(). JS calls fetch('/api/employees')." },
            { location: "server",   type: "process",  message: "2. EmployeeController delegates to EmployeeServiceImpl.getAll()." },
            { location: "database", type: "process",  message: "3. JpaRepository executes SELECT query and fetches entities." },
            { location: "server",   type: "response", message: "4. Entities mapped to DTOs. API returns HTTP 200 with JSON array." },
            { location: "client",   type: "process",  message: "5. JS caches array locally and renders the master table." },
            { location: "client",   type: "process",  message: "6. User clicks row. JS retrieves object from cache and hydrates Detail Panel." }
        ],

        errorFlow: [
            { location: "client",   type: "request",  message: "1. JS requests employee list." },
            { location: "server",   type: "error",    message: "2. Database connection fails. Spring throws SQLException." },
            { location: "server",   type: "response", message: "3. GlobalExceptionHandler traps error and returns HTTP 500." },
            { location: "client",   type: "process",  message: "4. JS catch block intercepts failure." },
            { location: "client",   type: "response", message: "5. Table body is replaced with a localized error message." }
        ]
    });

    const tableBody=document.getElementById('employeeTableBody');
    const userRole=sessionStorage.getItem('userRole');
    try
    {
       const data=await employeeService.getAll();
       this.employeeList=data;
       tableBody.innerHTML='';
       if(data.length===0)
       {
           tableBody.innerHTML='<tr><td colspan="6">No Records Found</td></tr>';
           return;
       }

        let rowsHTML='';
        data.forEach((employee,index)=>{
        // Formatting Date manually to match logic (dd/mm/yyyy) in employee grid box
        // API sends "yyyy-mm-dd"
            const dateParts=employee.dateOfBirth.split('-');
            const formattedDob=`dateParts[2]/dateParts[1]/dateParts[0]`;
            let actionButtonsHTML='';

            if(userRole==='ADMIN')
            {
                actionButtonsHTML=`
                <td>
                    <a href='#' onclick="loadModule('employee-form',{ id: '${employee.employeeId}', mode: 'EDIT'}); return false;">Edit</a>
                </td>
                <td>
                    <a href='#' onclick="loadModule('employee-delete-confirm',{ id: '${employee.employeeId}'}); return false;">Delete</a>
                </td>
                `;
            }else{
                actionButtonsHTML = `
                    <td colspan="2" class="view-only-cell">View Only</td>
                `;
            }

            rowsHTML+=`
            <tr style='cursor:pointer' onclick="window.pages.employees.selectEmployee(this,'${employee.employeeId}'); return false;">
            <td>${index+1}</td>
            <td>${employee.employeeId}</td>
            <td>${employee.name}</td>
            <td>${employee.designation}</td>
             ${actionButtonsHTML}
            </tr>
            `;

        });
        tableBody.innerHTML=rowsHTML;
        return;
    }catch(error)
    {
        console.error(error);
        tableBody.innerHTML = `<tr><td colspan="6" style="color:red">Error: ${error.message}</td></tr>`;
    }
}
,
selectEmployee: function(row,employeeId)
{

    if(this.selectedRow===row) return;
    if(this.selectedRow!=null)
    {
        this.selectedRow.classList.remove('selected-row');
    }
    row.classList.add('selected-row');
    this.selectedRow=row;
    const employee=this.employeeList.find(function(e)
    {
        if(e.employeeId==employeeId) return e; // also can use arrow function (e=> e.employeeId==employeeId)
    });
    if(employee)
    {
        document.getElementById('detailPanel_employeeId').innerHTML=employee.employeeId;
        document.getElementById('detailPanel_name').innerHTML=employee.name;
        document.getElementById('detailPanel_designation').innerHTML=employee.designation;
        const dateParts=employee.dateOfBirth.split('-');
        const formattedDob=`${dateParts[2]}/${dateParts[1]}/${dateParts[0]}`;
        document.getElementById('detailPanel_dateOfBirth').innerHTML=formattedDob;
        document.getElementById('detailPanel_gender').innerHTML=employee.gender;
        document.getElementById('detailPanel_isIndian').innerHTML=employee.isIndian?'Yes': 'No';
        document.getElementById('detailPanel_basicSalary').innerHTML=employee.basicSalary;
        document.getElementById('detailPanel_panNumber').innerHTML=employee.panNumber;
        document.getElementById('detailPanel_aadharCardNumber').innerHTML=employee.aadharCardNumber;
    }
}
};