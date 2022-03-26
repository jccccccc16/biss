<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cjc
  Date: 2020/9/20
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<%@include file="include-head.jsp" %>
<body>
<%@include file="include-nav.jsp" %>

<div class="container theme-showcase" role="main">


    <div>
        <c:if test="${detailProject==null}">
            <h3>没有查到任何数据</h3>
        </c:if>
    </div>

    <div>
        <c:if test="${detailProject!=null}">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="jumbotron nofollow" style="    padding-top: 10px;">
                        <h3>
                            <span>项目名称：${detailProject.projectName}</span>
                        </h3>
                        <div style="float:left;width:70%;">
                                项目描述：${detailProject.projectDescription}
                        </div>
                        <div style="float:right;">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="row clearfix">
                        <div class="col-md-8 column">
                            <img alt="140x140" src="${detailProject.headerPicturePath}" width="740"
                                 src="img/product_detail_head.jpg"/>
                            <div>
                                <c:forEach items="${detailProject.detailPicturePathList}" var="picturePath">
                                    <img alt="140x140" width="740" src="${picturePath}"
                                         src="img/product_detail_body.jpg"/>
                                </c:forEach>
                            </div>
                            <div class="panel-footer" style="text-align:center;">
                                <a id="reviewBtn" type="button" href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/project/do/review/project.html?projectId=${detailProject.projectId}" class="btn  btn-success btn-lg" >审核通过</a>
                                <a id="disReviewBtn" type="button" class="btn  btn-danger btn-lg">审核不通过</a>
                            </div>
                        </div>
                        <div class="col-md-4 column">
                            <div class="panel panel-default" style="border-radius: 0px;">
                                <div class="panel-heading" style="background-color: #fff;border-color: #fff;">
                                    <span class="label label-success"><i class="glyphicon glyphicon-tag"></i>
                                        <c:if test="${detailProject.status == 0}">
                                            <span>待审核</span>
                                        </c:if>
                                        <c:if test="${detailProject.status == 2}">
                                            <span>审核不通过</span>
                                        </c:if>
                                    </span>
                                </div>
                                <div>
                                    <h3>回报类型</h3>
                                    <c:forEach items="${detailProject.returnDetailVOList}" var="returnDetail">
                                        <div class="panel panel-default" style="border-radius: 0px;">
                                            <div class="panel-heading">
                                                <h3>
                                                    <span>￥${returnDetail.supportMoney}.00</span>
                                                    <c:if test="${returnDetail.signalPurchase == 0}">
                                                        <span style="float:right;font-size:12px;">无限额</span>
                                                    </c:if>
                                                    <c:if test="${returnDetail.signalPurchase == 0}">
                                                        <span style="float:right;font-size:12px;">限额</span>
                                                    </c:if>

                                                </h3>
                                            </div>
                                            <div class="panel-body">
                                                <p th:if="${returnDetail.freight == 0}">配送费用：包邮</p>
                                                <p>预计发放时间：项目筹款成功后的50天内</p>
                                                </a>
                                                <br><br>
                                                <p>
                                                        ${returnDetail.content}</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
            <%@ page language="java" contentType="text/html; charset=UTF-8"
                     pageEncoding="UTF-8"%>
            <div id="disReviewModal" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">众筹平台系统弹窗</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" action="project/do/disReview/project.html?projectId=${detailProject.projectId}" class="form-signin" role="form">
                                <div class="form-group has-success has-feedback">
                                    <input id="messageInput"
                                            type="text" name="message"
                                            class="form-control" placeholder="请输入审核不通过的理由"
                                            autofocus>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button   type="submit" class="btn btn-primary">确定</button>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>


        </div>


    </div> <!-- /container -->


</div>

<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script src="script/back-to-top.js"></script>
<script>
    $(".prjtip img").css("cursor", "pointer");
    $(".prjtip img").click(function () {
        window.location.href = 'project.html';
    });
    $(function(){



        // 点击审核不通过,显示模态框
        $("#disReviewBtn").click(function(){
            $("#disReviewModal").modal("show");
        })

    })
</script>
</body>
</html>