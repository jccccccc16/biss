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
                        <div style="float:left;width:70%;">
                            目标金额：￥${detailProject.money}
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
                                <%--如果是审核不通过查看项目详情--%>
                                <%--显示不通过的理由--%>
                            <c:if test="${requestScope.type=='DISREVIEW_DETAIL_PROJECT_PAGE'}">
                                <h3>
                                    <span>审核不通过</span>
                                </h3>
                                <div style="float:left;width:70%;">
                                        ${detailProject.message}
                                </div>
                                <div style="float:right;">
                                </div>
                            </c:if>
                                <%--如果是审核详情，显示审核按钮--%>
                            <c:if test="${requestScope.type=='REVIEW_DETAIL_PROJECT_PAGE'}">
                                <div class="panel-footer" style="text-align:center;">
                                    <a id="reviewBtn" type="button"
                                       href="project/do/review/project.html?projectId=${detailProject.projectId}"
                                       class="btn  btn-success btn-lg">审核通过</a>
                                    <a id="disReviewBtn" type="button" class="btn  btn-danger btn-lg">审核不通过</a>
                                </div>
                            </c:if>



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
            <c:if test="${detailProject.status==1}">
                众筹中
            </c:if>
            <c:if test="${detailProject.status==3}">
                众筹成功，待转账
            </c:if>
            <c:if test="${detailProject.status==4}">
                众筹失败，待退款
            </c:if>
            <c:if test="${detailProject.status==5}">
                众筹失败，资金已退回
            </c:if>
            <c:if test="${detailProject.status==6}">
                众筹成功，已转账
            </c:if>

                                    </span>
                                </div>

                                <div class="panel-body">
                                    <h3>
                                        已筹资金：￥ ${detailProject.supportMoney}
                                    </h3>
                                    <p><span>目标金额 ：￥  ${detailProject.money}</span><span
                                            style="float:right;">达成 ： ${detailProject.percentage}%</span>
                                    </p>
                                    <div class="progress" style="height:10px; margin-bottom: 5px;">
                                        <div class="progress-bar progress-bar-success" role="progressbar"
                                             aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                                             style="'width: '+${detailProject.percentage}+'%;'"
                                             style="width: 60%;"></div>
                                    </div>
                                    <p>剩余 ${detailProject.lastDay} 天</p>
                                    <div>
                                        <p><span>已有${detailProject.supporterCount}人支持该项目</span>
                                        </p>
                                    </div>
                                </div>
                            </div>

                            <div>
                                <h3>回报类型</h3>
                                <c:forEach items="${detailProject.returnDetailVOList}" var="returnVO">
                                <div class="panel panel-default" style="border-radius: 0px;">
                                    <div class="panel-heading">
                                        <h3>
                                            <span>￥ ${returnVO.supportMoney}.00</span>
                                                <%--                                                <c:if test="${returnVO.signalPurchase == 0}">无限额，${returnVO.supporterCount}--%>
                                                <%--                                                位支持者</c:if>--%>
                                                <%--                                                <c:if test="${returnVO.signalPurchase != 0}">限额，${returnVO.supporterCount}--%>
                                                <%--                                                位支持者</c:if>--%>
                                    </div>
                                    <div class="panel-body">
                                        <c:if test="${returnVO.freight == 0}"><p>配送费用：包邮</p></c:if>
                                        <c:if test="${returnVO.freight != 0}"><p>配送费用：${returnVO.freight}</p></c:if>
                                        <p>预计发放时间：项目筹款成功后的 ${returnVO.returnDate }天内</p>
                                        </a>
                                        <br><br>
                                        <p>汇报描述：${returnVO.content}</p>
                                    </div>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                    </div>

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
                                    <form method="post"
                                          action="project/do/disReview/project.html?projectId=${detailProject.projectId}"
                                          class="form-signin" role="form">
                                        <div class="form-group has-success has-feedback">
                                            <input id="messageInput"
                                                   type="text" name="message"
                                                   class="form-control" placeholder="请输入审核不通过的理由"
                                                   autofocus>
                                        </div>
                                        <div class="modal-footer">
                                            <button id="submitDisReviewBtn" type="submit" class="btn btn-primary">确定
                                            </button>
                                        </div>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                    </c:if></div>


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
            $(function () {


                // 点击审核不通过,显示模态框
                $("#disReviewBtn").click(function () {
                    $("#disReviewModal").modal("show");
                })

            })
        </script>
</body>
</html>