<jsp:useBean id='errorBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.ErrorBean' />
<!DOCTYPE HTML>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>HR Core | Stage 2 (JSP)</title>
<link rel='stylesheet' type='text/css' href='/stage2/css/styles.css'>
</head>
<body>
<!-- Main container starts here-->
<div class='main-container'>
<!-- header starts here -->
<div class='header'>
<a href='/stage2/index.jsp'><img src='/stage2/images/logo.png' class='logo'></a><div class='brand-name'>&nbspHR Core</div>
</div>
<!-- header ends here -->
<!-- content-section starts here -->
<div class='content'>
<!-- login form section starts here -->
<div class='loginForm'>
<form method='post' action='/stage2/Login.jsp' />
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
