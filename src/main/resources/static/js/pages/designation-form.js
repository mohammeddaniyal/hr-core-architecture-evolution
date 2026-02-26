window.pages["designationForm"]={
mode:null,
code:null,
load: function(params)
{
    if(!params.mode)
    {
        throw new Error("employeeForm.load(): mode is required");
    }
    this.mode=params.mode;
    this.code=params.id || null;


    if(this.mode==="ADD")
    {
        this.setupAdd();
    }
    else if(this.mode==='EDIT')
    {
        if(!this.code)
        {
            throw new Error('EDIT mode requires code');
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
        title: "Stage 4: Asynchronous Mutation",
        module: "Add Designation (REST POST + Global Exception Handling)",

        impact: [
            "RESTful POST: Data transmitted as application/json to a strictly mapped @PostMapping endpoint.",
            "Header-Based CSRF: X-XSRF-TOKEN extracted from cookies and attached to fetch() headers for secure API mutation.",
            "JPA Validation: Duplicate checks performed cleanly via repository.existsByTitle().",
            "Centralized Error Mapping: GlobalExceptionHandler translates DataConflictException into HTTP 409 and Validation errors into HTTP 400.",
            "DOM-Only State Updates: Success and error notifications replace the form container instantly without any page navigation."
        ],

        successFlow: [
            { location: "client",   type: "request",  message: "1. User submits form. JS intercepts and validates via designationValidator." },
            { location: "client",   type: "request",  message: "2. Fetch API sends POST /api/designations with JSON body and CSRF header." },
            { location: "server",   type: "process",  message: "3. Spring Controller validates DTO (@Valid). Passes to Service layer." },
            { location: "database", type: "process",  message: "4. JPA Repository executes INSERT after confirming title uniqueness." },
            { location: "server",   type: "response", message: "5. Controller returns HTTP 201 Created with saved DTO." },
            { location: "client",   type: "response", message: "6. JS clears the form module and renders the Success Notification inline." }
        ],

        errorFlow: [
            { location: "client",   type: "request",  message: "1. User submits a designation title that already exists." },
            { location: "server",   type: "error",    message: "2. DesignationServiceImpl throws DataConflictException." },
            { location: "server",   type: "process",  message: "3. GlobalExceptionHandler intercepts exception and maps it to a Map<String, String>." },
            { location: "server",   type: "response", message: "4. API returns HTTP 409 Conflict with JSON error payload." },
            { location: "client",   type: "process",  message: "5. JS designationService evaluates status code and throws {type: 'BUSINESS'}." },
            { location: "client",   type: "response", message: "6. JS catch block routes error to showError(), displaying inline red text." }
        ]
    });

    document.getElementById('formTitle').innerText='Designation (Add Form)';
    const btn=document.getElementById('submitBtn');
    btn.textContent='Add';
    btn.onclick=(e)=>{
    e.preventDefault(); // to tell browser STOP! Don't submit the form or follow the link
    this.save();
    }
},

setupEdit: async function()
{
    document.getElementById('formTitle').innerText='Designation (Edit Form)';
    const btn=document.getElementById('submitBtn');
    btn.textContent='Update';
    btn.onclick=(e)=>
    {
    e.preventDefault();// to tell browser STOP! Don't submit the form or follow the link
    this.update();
    }
    try
    {
        const designation=await designationService.getByCode(this.code);
        document.getElementById('title').value=designation.title;
    }catch(error)
    {
        console.log(error);
    }
},
save: async function()
    {
        const frm=document.getElementById('designationForm');
        const notification=document.getElementById('notification');
        const errorSection=document.getElementById('errorSection');
        const designationAddModule=document.getElementById('designationModule');
        notification.innerHTML='';
        errorSection.innerHTML='';

        const formData={
        title: frm.title.value
        };

        const result= designationValidator.validate(formData);

        if(!result.valid)
        {
            this.showError(result.errors);
            return;
        }

        const title=formData.title.trim();

        const designation={
        'title':title
        };
        try
        {
            const response=await designationService.add(designation);
            designationAddModule.innerHTML='';
            notification.innerHTML=`
            <h3>Notification</h3><br>
            Designation added, add more ?<br>
            <table>
            <tr>
            <td>
            <button type="button" onclick="loadModule('designation-form',{mode:'ADD'}); return false;">Yes</button>
            </td>
            <td>
            <button type="button" onclick="loadModule('designations'); return false;">No</button>
            </td>
            </tr>
            </table>
            `;
        }catch(error)
        {
            console.log(error);
            if(error.type==='VALIDATION')
            {
                this.showError(error.errors);
            }else if(error.type==='BUSINESS')
            {
                this.showError(error.errors);
            }else
            {
                alert('Critical error '+error.message)
            }
        }

    },
    update: async function()
        {
            const frm=document.getElementById('designationForm');
            const notification=document.getElementById('notification');
            const errorSection=document.getElementById('errorSection');
            const designationEditModule=document.getElementById('designationModule');
            notification.innerHTML='';
            errorSection.innerHTML='';

            const formData={
            title: frm.title.value
            };

            const result= designationValidator.validate(formData);

            if(!result.valid)
            {
                this.showError(result.errors);
                return;
            }

            const title=formData.title.trim();

            const designation={
            'title':title
            };
            try
            {
                const response=await designationService.update(this. code,designation);
                designationEditModule.innerHTML='';
                notification.innerHTML=`
                <h3>Notification</h3><br>
                Designation Updated <br>
                <button type="button" onclick="loadModule('designations'); return false;">Ok</button>
                `;
            }catch(error)
            {
                console.log(error);
                if(error.type==='VALIDATION')
                {
                    this.showError(error.errors);
                }else if(error.type==='BUSINESS')
                {
                    this.showError(error.errors);
                }else
                {
                    alert('Critical error '+error.message)
                }
            }

        },
        showError: function(errors)
        {
            const titleErrorSection=document.getElementById('titleErrorSection');
            const errorSection=document.getElementById('errorSection');

            titleErrorSection.innerHTML='';
            errorSection.innerHTML='';

            if(errors.title)
            {
                titleErrorSection.innerHTML=errors.title;
                document.getElementById('designationForm').title.focus();
            }else if(errors.message)
            {
                errorSection.innerHTML=errors.message;
            }else
            {
                alert(errors)
            }
        }
    }