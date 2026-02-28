<jsp:include page='MasterPageTopSection.jsp' />
<script>
function getModule()
{
return 'HOME';
}
</script>
<h1>Welcome</h1>
<script>
XRay.init({
    title: "Stage 3: Client-Driven Layout + Session API",
    module: "Application Entry (AJAX-Based Guard + Dynamic Navigation)",

    impact: [
        "Client-Side Guard: Session validation performed via AJAX call to server API.",
        "JSON Session Verification: Username fetched asynchronously from server.",
        "Dynamic Navigation Rendering: Navigation panel constructed using DOM APIs.",
        "API-Based Logout: Logout handled via asynchronous POST request.",
        "Hybrid Architecture: Server provides layout shell, client initializes state."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. Browser loads index.jsp layout shell." },
        { location: "client", type: "request", message: "2. guard() triggers GET request to session validation endpoint." },
        { location: "server", type: "process", message: "3. Servlet checks HttpSession and returns JSON with username." },
        { location: "client", type: "response", message: "4. Username injected dynamically into header." },
        { location: "client", type: "process", message: "5. Navigation panel generated based on module context." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. Unauthenticated user loads protected page." },
        { location: "server", type: "process", message: "2. Session validation endpoint returns success=false." },
        { location: "client", type: "response", message: "3. JavaScript redirects browser to LoginForm.jsp." }
    ]
});
</script>
<jsp:include page='MasterPageBottomSection.jsp' />