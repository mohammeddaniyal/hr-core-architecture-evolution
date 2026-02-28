<jsp:include page='/MasterPageTopSection.jsp' />

<script>
function getModule()
{
return 'DESIGNATION';
}
function populateDesignations()
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function()
{
if(this.readyState==4)
{
if(this.status==200)
{
var response=JSON.parse(this.responseText);
var designations=response.result;
var designationTable=document.getElementById('designationTable');

var designationTableBody=designationTable.getElementsByTagName('tbody')[0];

var designationTableBodyRowTemplate=designationTableBody.getElementsByTagName("tr")[0];

designationTableBodyRowTemplate.remove();
var designationTableColumnsTemplateCollection=designationTableBodyRowTemplate.getElementsByTagName("td");


var cellTemplate;
var dynamicRow;
var dynamicRowCells;
var placeHolderFor;
var k;
var sno;
for(k=0,sno=0;k<designations.length;k++,sno++)
{
dynamicRow=designationTableBodyRowTemplate.cloneNode(true);
designationTableBody.appendChild(dynamicRow);
dynamicRowCells=dynamicRow.getElementsByTagName("td");
for(var i=0;i<dynamicRowCells.length;i++)
{
cellTemplate=dynamicRowCells[i];
placeHolderFor=cellTemplate.getAttribute("placeHolderId");
if(placeHolderFor==null) continue;
if(placeHolderFor=='serialNumber') cellTemplate.innerHTML=(sno+1)+'.';
if(placeHolderFor=='designation') cellTemplate.innerHTML=designations[k].title;
if(placeHolderFor=='editOption') cellTemplate.innerHTML="<a href='${pageContext.request.contextPath}/DesignationEditForm.jsp?code="+designations[k].code+"'>Edit</a>"
if(placeHolderFor=='deleteOption') cellTemplate.innerHTML="<a href='${pageContext.request.contextPath}/ConfirmDeleteDesignation.jsp?code="+designations[k].code+"'>Delete</a>"
}
}

}
else
{
alert('some problem');
}
}
};
xmlHttpRequest.open('GET','getDesignations',true);
xmlHttpRequest.send();
}
		          
window.addEventListener('load',populateDesignations);
</script>

<h2>Designations</h2>
<table border='1' id='designationTable'>
<thead>
<tr>
<th colspan='4' style='text-align:right;padding:5px'>
<a href='${pageContext.request.contextPath}/DesignationAddForm.jsp'>Add new Designation</a>
</th>
</tr>
<tr>
<th style='width:60px;text-align:center'>S.No</th>
<th style='width:200px;text-align:center'>Designation</th>
<th style='width:100px;text-align:center'>Edit</th>
<th style='width:100px;text-align:center'>Delete</th>
</tr>
</thead>
<tbody>
<tr>
<td placeHolderId='serialNumber' style='text-align:right'></td>
<td placeHolderId='designation'></td>
<td placeHolderId='editOption' style='text-align:center'></td>
<td placeHolderId='deleteOption' style='text-align:center'></td>
</tr>
</tbody>
</table>
<script>
XRay.init({
    title: "Stage 3: AJAX + JSON (Client-Side Rendering)",
    module: "View Designations (API-Driven Data Rendering)",

    impact: [
        "API-Based Data Retrieval: Designations fetched asynchronously via JSON endpoint.",
        "Client-Side Rendering: Table rows generated dynamically using DOM cloning.",
        "Decoupled Presentation: JSP provides layout shell only; data rendering handled by JavaScript.",
        "REST-Oriented Endpoint: Dedicated servlet returns structured JSON response.",
        "Reduced Server HTML Generation: No JSP iteration or custom tag required."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. Browser loads Designations.jsp layout shell." },
        { location: "client", type: "request", message: "2. JavaScript sends GET request to /getDesignations endpoint." },
        { location: "server", type: "process", message: "3. Servlet invokes DAO to fetch designation list." },
        { location: "database", type: "process", message: "4. DAO executes SELECT query and returns DTO list." },
        { location: "server", type: "response", message: "5. JSON response returned with success flag and result array." },
        { location: "client", type: "process", message: "6. JavaScript parses JSON and dynamically generates table rows." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. JavaScript sends GET request to API." },
        { location: "database", type: "error", message: "2. DAO throws exception due to database failure." },
        { location: "server", type: "response", message: "3. JSON response returned with success=false and error message." },
        { location: "client", type: "response", message: "4. JavaScript detects failure and alerts user." }
    ]
});
</script>
<jsp:include page='/MasterPageBottomSection.jsp' />