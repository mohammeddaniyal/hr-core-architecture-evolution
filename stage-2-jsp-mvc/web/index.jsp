<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='HOME' />
<jsp:include page='MasterPageTopSection.jsp' />
<h1>Welcome</h1>
<script>
XRay.init({
    title: "Stage 2: JSP + Session + Layout Reuse",
    module: "Application Entry (Guarded Layout with Session Awareness)",

    impact: [
        "Authentication Guard: Entry page protected via custom Guard tag.",
        "Layout Reuse: Header and footer abstracted into reusable JSP fragments.",
        "Session-Aware UI: Logged-in username rendered dynamically.",
        "Dynamic Navigation State: Module highlighting controlled via custom tag.",
        "Logout Mechanism: Session attribute cleared and redirect issued."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. Authenticated user requests index.jsp." },
        { location: "server", type: "process", message: "2. Guard tag verifies session validity." },
        { location: "server", type: "process", message: "3. MasterPageTopSection.jsp renders header and navigation." },
        { location: "server", type: "response", message: "4. Full layout composed via JSP includes." },
        { location: "client", type: "response", message: "5. Browser renders unified layout without servlet HTML generation." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. Unauthenticated user accesses index.jsp." },
        { location: "server", type: "process", message: "2. Guard tag detects missing session attribute." },
        { location: "server", type: "response", message: "3. Request forwarded to LoginForm.jsp." },
        { location: "client", type: "response", message: "4. Browser displays login page instead of protected content." }
    ]
});
</script>
<jsp:include page='MasterPageBottomSection.jsp' />
