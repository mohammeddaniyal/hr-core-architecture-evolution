<%@taglib uri='WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='DESIGNATION' />
<jsp:useBean id='designationBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.DesignationBean' />
<jsp:useBean id='errorBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.ErrorBean' />

<jsp:include page='/MasterPageTopSection.jsp' />
<script src='${pageContext.request.contextPath}/js/DesignationAddForm.js'></script>
<h2>Designation (Add Module)</h2>
<span class='error'>
<jsp:getProperty name='errorBean' property='error' />
</span>
<form method='post' action='${pageContext.request.contextPath}/AddDesignation.jsp' autocomplete='off' onsubmit='return validateForm(this)'>
<tm:FormID />
Designation
<input type='text' id='title' name='title' maxlength='35' size='36' value='${designationBean.title}'>
<span id='titleErrorSection' class='error'></span>
<br>

<button type='submit'>Add</button>
<button type='button' onclick='cancelAddition()'>Cancel</button>
</form>
<form id='cancelAdditionForm' action='${pageContext.request.contextPath}/Designations.jsp'></form>
<script>
XRay.init({
    title: "Stage 2: JSP + MVC Pattern",
    module: "Add Designation (Guarded MVC Flow with Bean Binding)",

    impact: [
        "Presentation Layer Separation: Form rendering handled by JSP instead of servlet.",
        "Authentication Guard: Custom Guard tag prevents unauthorized access.",
        "Bean Binding: Automatic request-to-bean mapping via jsp:setProperty.",
        "Form Resubmission Control: Custom tag detects duplicate submissions.",
        "Controller Responsibility: Servlet handles business logic and routing only.",
        "Structured Feedback: Notification and error handling abstracted via beans."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. User submits designation form." },
        { location: "server", type: "process", message: "2. AddDesignation.jsp binds request parameters to DesignationBean." },
        { location: "server", type: "process", message: "3. Request forwarded to AddDesignation servlet." },
        { location: "database", type: "process", message: "4. DAO validates uniqueness and executes INSERT." },
        { location: "server", type: "response", message: "5. Servlet creates MessageBean and forwards to Notification.jsp." },
        { location: "client", type: "response", message: "6. Browser receives rendered confirmation page." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. User submits duplicate designation." },
        { location: "database", type: "error", message: "2. DAO detects existing title and throws DAOException." },
        { location: "server", type: "process", message: "3. Servlet wraps message into ErrorBean." },
        { location: "server", type: "response", message: "4. Request forwarded back to DesignationAddForm.jsp." },
        { location: "client", type: "response", message: "5. Form reloads with validation error displayed." }
    ]
});
</script>
<jsp:include page='/MasterPageBottomSection.jsp' />