<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div>
				<a class="navbar-brand" style="font-size: 32px;" href="">众筹平台 -
					控制面板</a>
			</div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li style="padding-top: 8px;">
					<div class="btn-group">
						<button type="button"
							class="btn btn-default btn-primary dropdown-toggle"
							data-toggle="dropdown">
							<i class="glyphicon glyphicon-user"></i>
							<security:authentication property="principal.originalAdmin.userName"/>
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li class="divider"></li>
							<li><a href="seucrity/do/logout.html"><i
									class="glyphicon glyphicon-off"></i> 退出系统</a></li>
						</ul>
					</div>
				</li>
				<li style="margin-left: 10px; padding-top: 8px;">

				</li>
			</ul>

		</div>
	</div>
</nav>