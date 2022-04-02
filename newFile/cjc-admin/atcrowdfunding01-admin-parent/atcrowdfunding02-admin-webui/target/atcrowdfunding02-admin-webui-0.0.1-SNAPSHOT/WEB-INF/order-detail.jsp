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


    <%--    <div>--%>
    <%--        <c:if test="${orderDetailVO==null}">--%>
    <%--            <h3>没有查到任何数据</h3>--%>
    <%--        </c:if>--%>
    <%--    </div>--%>

    <div>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="jumbotron nofollow" style="padding-top: 10px;">
                        <h2>
                            <span>订单信息</span>
                        </h2>
                        <br>
                        <address>
                            <strong>收货地址：</strong>

                            ${orderDetailVO.addressPO.address}

                        </address>
                        <br>
                        <address>
                            <strong>收货人：</strong>

                            ${orderDetailVO.addressPO.receiveName}

                        </address>
                        <br>
                        <address>
                            <strong>电话号码: </strong>

                            ${orderDetailVO.addressPO.phoneNum}

                        </address>
                        <br>
                        <address>
                            <strong>订单备注: </strong>

                            <c:if test="${orderDetailVO.orderPO.orderRemark}==null"> - </c:if>
                            <c:if test="${orderDetailVO.orderPO.orderRemark}!=null">${orderDetailVO.orderPO.orderRemark}</c:if>

                        </address>
                        <br>
                        <address>
                            <strong>订单编号: </strong>

                            ${orderDetailVO.orderPO.orderNum}

                        </address>
                        <br>
                        <address>
                            <strong>订单创建时间：</strong>

                            ${orderDetailVO.orderPO.createTime}

                        </address>

                        <c:if test="${orderDetailVO.orderPO.status==3}">
                            <br>
                            <address>
                                <strong>确认订单时间：</strong>

                                    ${orderDetailVO.orderPO.confirmTime}

                            </address>
                        </c:if>
                        <c:if test="${orderDetailVO.orderPO.status==5}">
                            <br>
                            <address>
                                <strong>订单退款时间：</strong>

                                    ${orderDetailVO.orderPO.refundTime}

                            </address>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div>
            <div class="container">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                            <table class="table" width="300" height="200">
                                <thead>
                                <tr>
                                    <th>众筹项目</th>
                                    <th>回报数量</th>
                                    <th>回报单价</th>
                                    <th>订单状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${orderDetailVO.orderPO.status==4}">
                                <tr class="danger">
                                </c:if>
                                <c:if test="${orderDetailVO.orderPO.status!=4}">
                                <tr>
                                </c:if>
                                <td>${orderDetailVO.orderProjectPO.projectName}</td>
                                <td>${orderDetailVO.orderProjectPO.returnCount}</td>
                                <td>${orderDetailVO.orderProjectPO.supportPrice}</td>
                                <td>
                                    <c:if test="${orderDetailVO.orderPO.status==1}">已支付</c:if>
                                    <c:if test="${orderDetailVO.orderPO.status==2}">已发货</c:if>
                                    <c:if test="${orderDetailVO.orderPO.status==3}">订单已确认</c:if>
                                    <c:if test="${orderDetailVO.orderPO.status==4}">待退款</c:if>
                                </td>
                            </table>

                    </div>
                </div>
            </div>
        </div>

            <div>
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <div class="jumbotron nofollow" style="    padding-top: 10px;">
                                <div class="text-right">
                                    <br>
                                    商品总价：￥ ${orderDetailVO.orderProjectPO.returnCount * orderDetailVO.orderProjectPO.supportPrice}
                                    <br>
                                    运费(快递)：￥ ${orderDetailVO.orderProjectPO.freight}
                                    <br>

                                    <h3>实付款： ￥ ${orderDetailVO.orderPO.orderAmount}</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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