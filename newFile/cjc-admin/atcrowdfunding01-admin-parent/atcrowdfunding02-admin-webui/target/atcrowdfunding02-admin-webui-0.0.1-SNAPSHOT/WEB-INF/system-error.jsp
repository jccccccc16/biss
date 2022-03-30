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
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("button").click(function(){
			// 相当于浏览器的后退按钮
			window.history.back(-1);
		});
	});
</script>
<style>
</style>
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
	
		<h2 class="form-signin-heading" style="text-align: center;">
			 众筹平台系统消息提示
		</h2>
		<!-- 
			requestScope对应的是存放request域数据的Map
			requestScope.exception相当于request.getAttribute("exception")
			requestScope.exception.message相当于exception.getMessage()
		 -->
		<h3 style="text-align: center;">${requestScope.exception.message }</h3>
		<a href="admin/to/main/page.html" style="width: 150px;margin: 50px auto 0px auto;" class="btn btn-lg btn-primary btn-block">返回上一步</a>
	</div>
</body>
</html>