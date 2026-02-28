<jsp:useBean id='errorBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.ErrorBean' />
<!DOCTYPE HTML>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>HR Core | Stage 2 (JSP)</title>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/styles.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/xray.css'>
<script src="${pageContext.request.contextPath}/js/xray.js"></script>
<script>
XRay.init({
    title: "Stage 2: Authentication + Session Management",
    module: "Login (Credential Validation + Session Establishment)",

    impact: [
        "Bean Binding: Login credentials mapped to AdministratorBean via JSP.",
        "Controller-Based Authentication: Servlet validates credentials against DAO.",
        "Session Establishment: Username stored in HttpSession after successful login.",
        "Redirect Strategy: sendRedirect used to avoid form resubmission.",
        "Guard Integration: Subsequent pages protected using custom Guard tag."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. User submits login form." },
        { location: "server", type: "process", message: "2. Login.jsp binds parameters to AdministratorBean." },
        { location: "server", type: "process", message: "3. Login servlet validates credentials via AdministratorDAO." },
        { location: "server", type: "process", message: "4. HttpSession created and username attribute stored." },
        { location: "server", type: "response", message: "5. Servlet issues sendRedirect to index.jsp." },
        { location: "client", type: "response", message: "6. Browser initiates new request and loads protected home page." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. User submits invalid credentials." },
        { location: "server", type: "process", message: "2. DAO lookup fails or password mismatch detected." },
        { location: "server", type: "response", message: "3. ErrorBean set and request forwarded back to LoginForm.jsp." },
        { location: "client", type: "response", message: "4. Login page reloads displaying error message." }
    ]
});
</script>
</head>
<body>
<!-- Main container starts here-->
<div class='main-container'>
<!-- header starts here -->
<div class='header'>
<a href='${pageContext.request.contextPath}/index.jsp'><img src='${pageContext.request.contextPath}/images/logo.png' class='logo'></a><div class='brand-name'>&nbspHR Core</div>
</div>
<!-- header ends here -->
<!-- content-section starts here -->
<div class='content'>
<!-- login form section starts here -->
<div class='loginForm'>
<form method='post' action='${pageContext.request.contextPath}/Login.jsp' />
<table border='0'>
<tr>
<td colspan='2' align='center'>
<span class='error'>${errorBean.error}</span><br>
</td>
</tr>
<tr>
<td>Username</td>
<td><input type='text' id='username' name='username' maxlength='15'></td>
</tr>
<tr>
<td>Password</td>
<td><input type='password' id='password' name='password' maxlength='15'>
</td>
</tr>
<tr>
<td colspan='2' align='center'>
<button type='submit'>Login</button>
</td>
</tr>
</table>
</form>
</div>
<!-- login form section ends here -->
</div>
<!-- content-section ends here -->
<!-- footer starts here -->
<div class='footer'>
&copy; HR Core 2026
<!-- footer ends here -->
</div>
</div>
<!-- Main container ends here-->
</body>
</html>
