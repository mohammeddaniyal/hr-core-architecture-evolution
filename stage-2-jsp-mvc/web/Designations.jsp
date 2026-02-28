<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='DESIGNATION' />
<jsp:include page='/MasterPageTopSection.jsp' />
<h2>Designations</h2>
<table border='1'>
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
<tm:EntityList populatorClass='io.github.mohammeddaniyal.hr.bl.DesignationBL' populatorMethod='getAll' name='cool'>
<tr>
<td style='text-align:right'>${serialNumber}</td>
<td>${cool.title}</td>
<td style='text-align:center'><a href='${pageContext.request.contextPath}/editDesignation?code=${cool.code}'>Edit</a></td>
<td style='text-align:center'><a href='${pageContext.request.contextPath}/confirmDeleteDesignation?code=${cool.code}'>Delete</a></td>
</tr>
</tm:EntityList>
</tbody>
</table>
<script>
XRay.init({
    title: "Stage 2: JSP + Custom Tags (Layered Architecture)",
    module: "View Designations (JSP + BL + Custom Tag Rendering)",

    impact: [
        "Presentation Layer Separation: HTML rendering moved from servlet to JSP.",
        "Custom Tag Abstraction: EntityList tag handles iteration logic.",
        "Business Layer Introduced: DesignationBL mediates between JSP and DAO.",
        "Reduced Controller Responsibility: No HTML generation in servlet.",
        "Reflection-Based Populator: Dynamic method invocation via custom tag."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. User clicks Designations from index.jsp." },
        { location: "server", type: "request", message: "2. HTML rendering is handled by JSP instead of manual pw.println() in servlet." },
        { location: "server", type: "process", message: "3. EntityList custom tag instantiates DesignationBL via reflection." },
        { location: "server", type: "process", message: "4. BL calls DAO to retrieve designation list." },
        { location: "database", type: "process", message: "5. DAO executes SELECT query and returns DTO list." },
        { location: "server", type: "process", message: "6. BL converts DTO to Bean objects for presentation." },
        { location: "server", type: "response", message: "7. JSP renders rows using EntityList expressions inside custom tag iteration." },
        { location: "client", type: "response", message: "8. Browser receives fully rendered HTML page." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. User requests Designations page." },
        { location: "server", type: "process", message: "2. EntityList tag attempts to invoke BL method via reflection." },
        { location: "database", type: "error", message: "3. DAO throws DAOException due to DB failure." },
        { location: "server", type: "error", message: "4. BL logs exception; tag skips body rendering." },
        { location: "client", type: "response", message: "5. Page loads with empty table or fallback layout." }
    ]
});
</script>
<jsp:include page='/MasterPageBottomSection.jsp' />