window.pages = window.pages || {}

window.pages['designations']={
   load: async function()
    {

        XRay.init({
            title: "Stage 4: REST Data Layer",
            module: "View Designations (API Fetch + Client Rendering)",

            impact: [
                "Pure JSON Endpoint: @RestController returns List<DesignationDTO>; zero server-side HTML generation.",
                "Method-Level Security: Endpoint guarded by @PreAuthorize(\"hasAnyAuthority('ADMIN', 'USER')\").",
                "Dynamic Table Generation: JavaScript iterates over JSON array and constructs HTML rows using template literals.",
                "Role-Aware UI: JavaScript evaluates sessionStorage('userRole') to render 'Edit/Delete' buttons or 'View Only' text.",
                "Layered Backend Architecture: Controller -> ServiceImpl -> JpaRepository cleanly separates business logic from data access."
            ],

            successFlow: [
                { location: "client",   type: "request",  message: "1. User clicks 'Designations'. Router loads HTML fragment and calls designations.load()." },
                { location: "client",   type: "request",  message: "2. DesignationService executes fetch('/api/designations')." },
                { location: "server",   type: "process",  message: "3. Spring Boot Controller passes request to DesignationServiceImpl." },
                { location: "database", type: "process",  message: "4. DesignationRepository performs SELECT query via Hibernate/JPA." },
                { location: "server",   type: "response", message: "5. Controller serializes DTO list and returns HTTP 200 OK." },
                { location: "client",   type: "process",  message: "6. JS parses JSON, evaluates user permissions, and injects rows into the DOM." }
            ],

            errorFlow: [
                { location: "client",   type: "request",  message: "1. JS requests /api/designations." },
                { location: "server",   type: "error",    message: "2. Backend exception occurs (e.g., Database timeout)." },
                { location: "server",   type: "response", message: "3. GlobalExceptionHandler catches exception and returns HTTP 500 JSON." },
                { location: "client",   type: "process",  message: "4. JS designationService throws error object." },
                { location: "client",   type: "response", message: "5. JS catch block updates table body with inline red error message." }
            ]
        });


        const tableBody=document.getElementById('designationTableBody');
        const userRole=sessionStorage.getItem('userRole');
        let actionButtonsHTML='';
        try
        {
            const designations=await designationService.getAll();

            tableBody.innerHTML='';

            if(designations.length===0)
            {
                tableBody.innerHTML='<tr><td colspan="4">No Records Found</td></tr>';
                return;
            }

            let rowsHTML='';

            designations.forEach((designation,index)=>{
            let actionButtonsHTML='';

            if(userRole==='ADMIN')
            {
                actionButtonsHTML=`
                        <td class="col-action">
                            <a href="#" onclick="loadModule('designation-form', {id: '${designation.code}', mode:'EDIT'}); return false;">Edit</a>
                        </td>
                        <td class="col-action">
                            <a href="#" onclick="loadModule('designation-delete-confirm', {id: '${designation.code}'}); return false;">Delete</a>
                        </td>
                `;
            }else{
                actionButtonsHTML = `
                    <td colspan="2" style="color: gray; font-style: italic;">View Only</td>
                `;
            }

                rowsHTML+=`
                    <tr>
                        <td class="col-serial">${index+1}</td>
                        <td class="col-designation">${designation.title}</td>
                        ${actionButtonsHTML}
                    </tr>
                `;
            });

            tableBody.innerHTML=rowsHTML;

        }catch(error)
        {
            console.log(error);
            tableBody.innerHTML = `<tr><td colspan="4" style="color:red">Error: ${error.message}</td></tr>`;
        }


    }


}