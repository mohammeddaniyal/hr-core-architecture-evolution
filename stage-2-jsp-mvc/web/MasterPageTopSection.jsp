<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Guard>
<jsp:forward page='/LoginForm.jsp' />
</tm:Guard>
<!DOCTYPE HTML>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>HR Core | Stage 2 (JSP)</title>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/styles.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/xray.css'>
<script src="${pageContext.request.contextPath}/js/xray.js"></script>
</head>
<body>
<!-- Main container starts here-->
<div class='main-container'>
<!-- header starts here -->
<div class='header'>
<a href='${pageContext.request.contextPath}/index.jsp'><img src='${pageContext.request.contextPath}/images/logo.png' class='logo'></a>
<div class='brand-name'>&nbsp;HR Core</div>
<div class='username'>
<img src='${pageContext.request.contextPath}/images/user.png'/>
${username}&nbsp;
<a href='${pageContext.request.contextPath}/logout'>Logout</a>
</div>
</div><!-- header ends here -->
<!-- content-section starts here -->
<div class='content'>
<!-- left panel starts here -->
<div class='content-left-panel'>
<tm:If condition='${module==DESIGNATION}'>
<b>Designations</b><br>
</tm:If>
<tm:If condition='${module!=DESIGNATION}'>
<a href='${pageContext.request.contextPath}/Designations.jsp'>Designations</a><br>
</tm:If>
<tm:If condition='${module==EMPLOYEE}'>
<b>Employees</b><br>
</tm:If>
<tm:If condition='${module!=EMPLOYEE}'>
<a href='${pageContext.request.contextPath}/Employees.jsp'>Employees</a><br>
</tm:If>

<tm:If condition='${module!=HOME}'>
<a href='${pageContext.request.contextPath}/index.jsp'>Home</a>
</tm:If>
</div>
<!-- left panel ends here -->
<!-- right panel starts here -->
<div class='content-right-panel'>