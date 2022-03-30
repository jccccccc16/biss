<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<base
	href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/" />
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<style>
</style>
<title>大学生创新创业众筹平台</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" href="index.html" style="font-size: 32px;">大学生创新创业众筹平台</a>
				</div>
			</div>
		</div>
	</nav>

	<div class="container">
		<%--登录交由spring security操作--%>
		<form action="security/do/login.html" method="post" class="form-signin" role="form">
			<h2 class="form-signin-heading">
				众筹平台管理员登录
			</h2>
			<%-- 显示错误信息--%>
			<p>${requestScope.exception.message }</p>
			<p>${sessionScope.message }</p>
			<%--显示spring scurity的错误信息--%>
			<p>${SPRING_SECURITY_LAST_EXCEPTION.message }</p>
			<div class="form-group has-success has-feedback">
				<input type="text" name="loginAcct" value="${sessionScope.loginAcct}" class="form-control" id="inputSuccess4"
					placeholder="请输入登录账号" autofocus>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="password" name="userPswd" value="" class="form-control" id="inputSuccess4"
					placeholder="请输入登录密码" style="margin-top: 10px;">
			</div>
			<button type="submit" class="btn btn-lg btn-primary btn-block">登录</button>
		</form>
	</div>
</body>
</html>