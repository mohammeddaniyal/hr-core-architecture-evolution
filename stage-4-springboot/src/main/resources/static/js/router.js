const mainContent=document.getElementById('main-content');
const currentModuleCode=null


const ACTIVE_CLASS='nav-link.active';
const HIDDEN_CLASS='nav-link-hidden';

async function loadModule(moduleName,code=null)
{
    console.log(`loading module ${moduleName}`);
    if(code) currentModuleCode=code;
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
//       if(moduleName==='designation-add-form')
//       {
//        if(window.pages && window.pages.designations)
//        {
//            window.pages.designationAddForm.save();
//        }
//       }

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

