window.pages["employeeForm"]={
mode:null,
employeeId:null,
load: function(params)
{
    if(!params.mode)
    {
        throw new Error("employeeForm.load(): mode is required");
    }
    this.mode=params.mode;
    this.employeeId=params.id || null;

    this.clearNotifications();
    this.clearErrors();
    this.populateDesignationComboBox();

    if(this.mode==="ADD")
    {
        this.setupAdd();
    }
    else if(this.mode==='EDIT')
    {
        if(!this.employeeId)
        {
            throw new Error('EDIT mode requires employeeId');
        }
        this.setupEdit();
    }
    else {
           throw new Error(`Unknown mode: ${this.mode}`);
         }
},
// Mode Setups
setupAdd: function()
{
    XRay.init({
        title: "Stage 4: Complex Asynchronous Mutation",
        module: "Add Employee (Multi-Layer Validation & REST POST)",

        impact: [
            "Concurrent API Fetching: Form dynamically populates the Designation dropdown via a secondary async GET call.",
            "Dual-Layer Validation: Fast client-side JS validation prevents bad requests; strict server-side Service validation ensures data integrity.",
            "RESTful POST: Data transmitted as application/json to a strictly mapped @PostMapping endpoint.",
            "Semantic Error Mapping: Business exceptions (like duplicate PAN) mapped to HTTP 422 Unprocessable Entity.",
            "DOM-Only Navigation: Success flows clear the form module and inject confirmation HTML without manipulating window history."
        ],

        successFlow: [
            { location: "client",   type: "request",  message: "1. User submits form. JS employeeValidator checks fields (e.g., regex for basicSalary)." },
            { location: "client",   type: "request",  message: "2. JS extracts form data, applies CSRF header, and issues POST /api/employees." },
            { location: "server",   type: "process",  message: "3. EmployeeServiceImpl validates Designation existence and PAN/Aadhar uniqueness." },
            { location: "database", type: "process",  message: "4. JpaRepository executes INSERT operation." },
            { location: "server",   type: "response", message: "5. Controller returns HTTP 201 Created with saved EmployeeDTO." },
            { location: "client",   type: "response", message: "6. JS wipes the form UI and renders an inline success notification." }
        ],

        errorFlow: [
            { location: "client",   type: "request",  message: "1. User submits a valid form, but with a PAN already used by another employee." },
            { location: "server",   type: "error",    message: "2. EmployeeServiceImpl throws BusinessException containing a field-specific error map." },
            { location: "server",   type: "response", message: "3. GlobalExceptionHandler maps exception to HTTP 422 JSON response." },
            { location: "client",   type: "process",  message: "4. EmployeeService detects 422 status and throws {type: 'BUSINESS', errors: ...}." },
            { location: "client",   type: "response", message: "5. JS showError() iterates map and injects red text precisely under the PAN input field." }
        ]
    });
    document.getElementById('formTitle').innerText='Employee (Add Form)';
    const btn=document.getElementById('submitBtn');
    btn.textContent='Add';
    btn.onclick=(e)=>{
    e.preventDefault();
    this.save();
    }
    this.clearForm();
},

setupEdit: async function()
{
    document.getElementById('formTitle').innerText='Employee (Edit Form)';
    const btn=document.getElementById('submitBtn');
    btn.textContent='Update';
    btn.onclick=(e)=>{
    e.preventDefault();
    this.update();
    }
    try
    {
        const employee=await employeeService.getByEmployeeId(this.employeeId);
        this.fillForm(employee);
    }catch(error)
    {
        console.log(error);
    }
},


// actions

save: async function()
{
    const employeeAddForm=document.getElementById('employeeForm');
    const employeeAddModule=document.getElementById('employeeModule');
    const notification=document.getElementById('notificationSection');
    const employee=this.getEmployeeFromForm(employeeAddForm);

    const result=employeeValidator.validate(employee);
    if(!result.valid)
    {
        this.showError(result.errors);
        return;
    }
    try
    {
    const response=await employeeService.add(employee);

    employeeAddModule.innerHTML='';
            notification.innerHTML=`
            <h3>Notification</h3><br>
            Employee added, add more ?<br>
            <table>
            <tr>
            <td>
            <button type="button" onclick="loadModule('employee-form',{mode: 'ADD'}) ; return false;">Yes</button>
            </td>
            <td>
            <button type="button" onclick="loadModule('employees'); return false;">No</button>
            </td>
            </tr>
            </table>

    `;
    }catch(error)
    {
        this.showError(error.errors);
    }
},
update: async function()
{
    const employeeEditForm=document.getElementById('employeeForm');
    const notification=document.getElementById('notificationSection');
    const employeeEditModule=document.getElementById('employeeModule');

    const employee=this.getEmployeeFromForm(employeeEditForm);

    const result=employeeValidator.validate(employee);
    if(!result.valid)
    {
        this.showError(result.errors);
        return;
    }
    try
    {
    const response=await employeeService.update(this.employeeId,employee);

    employeeEditModule.innerHTML='';
            notification.innerHTML=`
            <h3>Notification</h3><br>
            Employee updated<br>
            <button type="button" onclick="loadModule('employees'); return false;">Ok</button>
    `;
    }catch(error)
    {
        this.showError(error.errors);
    }
},



// Common utils
clearNotifications: function()
{
      const notification = document.getElementById('notificationSection');
      if (notification) notification.textContent = '';
},
clearErrors: function()
 {
   document.querySelectorAll('.error').forEach(span=>span.textContent='');
 },
getEmployeeFromForm: function(form)
{
    const formData=new FormData(form);
    const employee={};
    employee.isIndian=false;
    for(const [key,value] of formData.entries())
    {
        if(key==='isIndian')
        {
            employee[key]= value==='Y';
        }else
        {
        employee[key]=value;
        }
    }
    return employee;

},
fillForm: function(employee)
{
    Object.entries(employee).forEach(([key,value])=>{
    const field=document.querySelector(`[name="${key}"]`);
    if(!field) return;
    if(field.type==="radio")
    {
     const radio = document.querySelector(
        `input[type="radio"][name="${key}"][value="${value}"]`
      );
      if (radio) {
      radio.checked = true;
      }else
      {
      console.error('Radio not found for', key, value);
      }
    }
    else if(field.type==="checkbox")
    {
        field.checked=value===true || value==='Y';
    }
    else
    {
        field.value=value;
    }
    });
},

clearForm: function()
{
const form=document.getElementById('employeeForm');
if(!form) return;
form.reset();
},
populateDesignationComboBox: async function(designations)
{
try
{
const designations=await designationService.getAll();
if(designations.length===0)
{
return;
}
const select=document.getElementById('designationCode');

if(!select) return;
select.innerHTML='';

const defaultOption=document.createElement('option');
defaultOption.value='';
defaultOption.textContent='<Select Designation>';
select.appendChild(defaultOption);

designations.forEach(d=>{
const option=document.createElement('option');
option.value=d.code;
option.textContent=d.title;
select.appendChild(option);
});
}catch(error)
{
alert(error);
}
},
showError: function(errors)
{
    document.querySelectorAll('.error').forEach(span=>span.innerHTML='');

    for(const field in errors)
    {
        const errorMessage=errors[field];
        const errorElement=document.getElementById(`${field}ErrorSection`);
        if(errorElement)
        {
            errorElement.innerHTML=errorMessage;
        }
    }
}
};