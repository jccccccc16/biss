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
                            <span >${detailProject.projectName}</span>
                        </h3>
                        <div style="float:left;width:70%;">
                                ${detailProject.projectDescription}
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
                                                    <span if="${returnDetail.signalPurchase == 0}"
                                                          text=""
                                                          style="float:right;font-size:12px;">'无限额</span>
                                                    <span if="${returnDetail.signalPurchase != 0}"
                                                          text=""
                                                          style="float:right;font-size:12px;">限额</span>
                                                </h3>
                                            </div>
                                            <div class="panel-body">
                                                <p th:if="${returnDetail.freight == 0}">配送费用：包邮</p>
                                                <p>预计发放时间：项目筹款成功后的50天内</p>
                                                <a href="'http://localhost/order/confirm/return/info/'+${detailProject.projectId}+'/'+${returnDetail.returnId}"
                                                   class="btn  btn-primary btn-lg"
                                                >支持
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
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog " style="width:960px;height:400px;" role="document">
                    <div class="modal-content" data-spy="scroll" data-target="#myScrollspy">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabel">选择支持项</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row clearfix">
                                    <div class="col-sm-3 col-md-3 column" id="myScrollspy">
                                        <ul class="nav nav-tabs nav-stacked">
                                            <li class="active"><a href="#section-1">￥1.00</a></li>
                                            <li class="active"><a href="#section-2">￥149.00</a></li>
                                            <li class="active"><a href="#section-3">￥249.00</a></li>
                                            <li class="active"><a href="#section-4">￥549.00</a></li>
                                            <li class="active"><a href="#section-5">￥1999.00</a></li>
                                        </ul>
                                    </div>
                                    <div id="navList" class="col-sm-9 col-md-9 column"
                                         style="height:400px;overflow-y:auto;">
                                        <h2 id="section-1" style="border-bottom:1px dashed #ddd;"><span
                                                style="font-size:20px;font-weight:bold;">￥1.00</span><span
                                                style="font-size:12px;margin-left:60px;">无限额，223位支持者</span></h2>
                                        <p>配送费用：全国包邮</p>
                                        <p>预计发放时间：项目筹款成功后的30天内</p>
                                        <button type="button" class="btn  btn-warning btn-lg "
                                                onclick="window.location.href='pay-step-1.html'">支持
                                        </button>
                                        <br><br>
                                        <p>每满1750人抽取一台活性富氢净水直饮机，至少抽取一台。抽取名额（小数点后一位四舍五入）=参与人数÷1750人，由苏宁官方抽取。</p>
                                        <hr>
                                        <h2 id="section-2" style="border-bottom:1px dashed #ddd;"><span
                                                style="font-size:20px;font-weight:bold;">￥149.00</span><span
                                                style="font-size:12px;margin-left:60px;">无限额，223位支持者</span></h2>
                                        <p>配送费用：全国包邮</p>
                                        <p>预计发放时间：项目筹款成功后的30天内</p>
                                        <button type="button" class="btn  btn-warning btn-lg "
                                                onclick="window.location.href='pay-step-1.html'">支持
                                        </button>
                                        <br><br>
                                        <p>每满1750人抽取一台活性富氢净水直饮机，至少抽取一台。抽取名额（小数点后一位四舍五入）=参与人数÷1750人，由苏宁官方抽取。</p>
                                        <hr>
                                        <h2 id="section-3" style="border-bottom:1px dashed #ddd;"><span
                                                style="font-size:20px;font-weight:bold;">￥249.00</span><span
                                                style="font-size:12px;margin-left:60px;">无限额，223位支持者</span></h2>
                                        <p>配送费用：全国包邮</p>
                                        <p>预计发放时间：项目筹款成功后的30天内</p>
                                        <button type="button" class="btn  btn-warning btn-lg "
                                                onclick="window.location.href='pay-step-1.html'">支持
                                        </button>
                                        <br><br>
                                        <p>每满1750人抽取一台活性富氢净水直饮机，至少抽取一台。抽取名额（小数点后一位四舍五入）=参与人数÷1750人，由苏宁官方抽取。</p>
                                        <hr>
                                        <h2 id="section-4" style="border-bottom:1px dashed #ddd;"><span
                                                style="font-size:20px;font-weight:bold;">￥549.00</span><span
                                                style="font-size:12px;margin-left:60px;">无限额，223位支持者</span></h2>
                                        <p>配送费用：全国包邮</p>
                                        <p>预计发放时间：项目筹款成功后的30天内</p>
                                        <button type="button" class="btn  btn-warning btn-lg "
                                                onclick="window.location.href='pay-step-1.html'">支持
                                        </button>
                                        <br><br>
                                        <p>每满1750人抽取一台活性富氢净水直饮机，至少抽取一台。抽取名额（小数点后一位四舍五入）=参与人数÷1750人，由苏宁官方抽取。</p>
                                        <hr>
                                        <h2 id="section-5" style="border-bottom:1px dashed #ddd;"><span
                                                style="font-size:20px;font-weight:bold;">￥1999.00</span><span
                                                style="font-size:12px;margin-left:60px;">无限额，223位支持者</span></h2>
                                        <p>配送费用：全国包邮</p>
                                        <p>预计发放时间：项目筹款成功后的30天内</p>
                                        <button type="button" class="btn  btn-warning btn-lg "
                                                onclick="window.location.href='pay-step-1.html'">支持
                                        </button>
                                        <br><br>
                                        <p>每满1750人抽取一台活性富氢净水直饮机，至少抽取一台。抽取名额（小数点后一位四舍五入）=参与人数÷1750人，由苏宁官方抽取。</p>
                                    </div>
                                </div>
                            </div>
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
</script>
</body>
</html>