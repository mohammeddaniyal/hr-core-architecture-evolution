const mainContent=document.getElementById('main-content');


const ACTIVE_CLASS='nav-link.active';
const HIDDEN_CLASS='nav-link-hidden';

async function loadModule(moduleName,code=null)
{
    console.log(`loading module ${moduleName}`);
    if(moduleName==='HOME')
    {
        mainContent.innerHTML="<h1>Welcome</h1>"
        updateNavigation(moduleName);
        return;
    }
    try
    {
        const response=await fetch(`/modules/${moduleName}.html`);

        if(response.ok)
        {
        const html=await response.text();
        mainContent.innerHTML=html;

       if(moduleName==='designations')
       {
        if(window.pages && window.pages.designations)
        {
            window.pages.designations.load();
        }
       }
       else if(moduleName==='designation-edit-form')
       {
        if(window.pages && window.pages.designationEditForm)
        {
         if(code!=null)
          {
            console.log(`loading designation of this code ${code}`)
            window.pages.designationEditForm.initEdit(code);
          }
        }
       }
       else if(moduleName==='designation-delete-confirm')
       {
        if(window.pages && window.pages.designationDeleteConfirm)
        {
         if(code!=null)
          {
            console.log(`loading designation of this code ${code}`)
            window.pages.designationDeleteConfirm.initDelete(code);
          }
        }
       }


        updateNavigation('designations');
        }else
        {
            mainContent.innerHTML=`<h3 style="color:red">Error module '${moduleName}' not found</h3>`
        }


    }catch(error)
    {
    console.error("Error loading module:", error);
            mainContent.innerHTML = `<h3 style="color:red">Connection Failed</h3>`;
    }

}

function updateNavigation(activeModule)
{
const linkHome=document.getElementById('link-home');
const linkDesignations=document.getElementById('link-designations');
const linkEmployees=document.getElementById('link-employees');

[linkHome,linkDesignations,linkEmployees].forEach(link=>{
if(link)
{
link.classList.remove(ACTIVE_CLASS);
link.classList.remove(HIDDEN_CLASS);
}
});

if(activeModule==='HOME')
{
if(linkHome)linkHome.classList.add(HIDDEN_CLASS);
}else if(activeModule==='designations')
{
if(linkDesignations)linkDesignations.classList.add(ACTIVE_CLASS);
}else if(activeModule==='employees')
 {
 if(linkEmployees)linkEmployees.classList.add(ACTIVE_CLASS);
 }




}

