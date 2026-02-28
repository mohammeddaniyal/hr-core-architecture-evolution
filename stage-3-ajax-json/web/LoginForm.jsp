<!DOCTYPE HTML>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>HR Core | Stage 3 (AJAX & JSON)</title>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/styles.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/xray.css'>
<script src='${pageContext.request.contextPath}/js/xray.js'></script>

<script>
XRay.init({
    title: "Stage 3: AJAX + JSON API Architecture",
    module: "Login (Asynchronous Authentication via REST Endpoint)",

    impact: [
        "Asynchronous Communication: Login handled via XMLHttpRequest instead of form submission.",
        "JSON Payload Exchange: Credentials transmitted as application/json.",
        "REST-Oriented Endpoint: Servlet acts as JSON API returning structured response object.",
        "Client-Controlled Navigation: Browser redirect triggered conditionally via JavaScript.",
        "Session Persistence: HttpSession still used for server-side authentication state."
    ],

    successFlow: [
        { location: "client", type: "request", message: "1. User clicks Login button. JavaScript constructs JSON object." },
        { location: "client", type: "request", message: "2. XMLHttpRequest sends POST /login with application/json body." },
        { location: "server", type: "process", message: "3. Servlet parses JSON using Gson into AdministratorDTO." },
        { location: "database", type: "process", message: "4. DAO validates username and password." },
        { location: "server", type: "process", message: "5. HttpSession created and JSON success response generated." },
        { location: "client", type: "response", message: "6. JavaScript parses JSON and redirects to index.jsp without full form resubmission." }
    ],

    errorFlow: [
        { location: "client", type: "request", message: "1. User submits invalid credentials." },
        { location: "server", type: "process", message: "2. DAO lookup fails or password mismatch detected." },
        { location: "server", type: "response", message: "3. JSON response returned with success=false and error message." },
        { location: "client", type: "response", message: "4. JavaScript updates error section dynamically without page reload." }
    ]
});
</script>
<script>
function authentication()
{
var username=document.getElementById('username').value.trim();
var password=document.getElementById('password').value.trim();
var administrator={
"username" : username,
"password" : password
};
var errorSection=document.getElementById('errorSection');
errorSection.innerHTML='';

var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var response=JSON.parse(this.responseText);
if(response.success==true)
{
window.location.href='${pageContext.request.contextPath}/index.jsp';
return;
}
else if(response.success==false)
{
errorSection.innerHTML=response.error;
}
}else
{
alert('some problem');
}
}
};
xmlHttpRequest.open('POST','login',true);
xmlHttpRequest.setRequestHeader("Content-Type","application/json");
xmlHttpRequest.send(JSON.stringify(administrator));
}
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
<table border='0'>
<tr>
<td colspan='2' align='center'>
<span id='errorSection' class='error'></span><br>
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
<button type='button' onclick='authentication()'>Login</button>
</td>
</tr>
</table>
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
