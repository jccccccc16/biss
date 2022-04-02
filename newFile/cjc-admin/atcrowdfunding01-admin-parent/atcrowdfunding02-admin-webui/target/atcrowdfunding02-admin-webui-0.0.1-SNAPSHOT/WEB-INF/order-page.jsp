<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">

    $(function () {

        // 调用后面声明的函数对页码导航条进行初始化操作
        initPagination();

    });

    // 生成页码导航条的函数
    function initPagination() {

        // 获取总记录数
        var totalRecord = ${requestScope.pageInfo.total};

        // 声明一个JSON对象存储Pagination要设置的属性
        var properties = {
            num_edge_entries: 3,								// 边缘页数
            num_display_entries: 5,								// 主体页数
            callback: pageSelectCallback,						// 指定用户点击“翻页”的按钮时跳转页面的回调函数
            items_per_page: ${requestScope.pageInfo.pageSize},	// 每页要显示的数据的数量
            current_page: ${requestScope.pageInfo.pageNum - 1},	// Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减一
            prev_text: "上一页",									// 上一页按钮上显示的文本
            next_text: "下一页"									// 下一页按钮上显示的文本
        };

        // 生成页码导航条
        $("#Pagination").pagination(totalRecord, properties);

    }

    // 回调函数的含义：声明出来以后不是自己调用，而是交给系统或框架调用
    // 用户点击“上一页、下一页、1、2、3……”这样的页码时调用这个函数实现页面跳转
    // pageIndex是Pagination传给我们的那个“从0开始”的页码
    function pageSelectCallback(pageIndex, jQuery) {

        // 根据pageIndex计算得到pageNum
        var pageNum = pageIndex + 1;


        window.location.href = "order/get/order/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}"

        // 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
        return false;
    }

</script>
<body>

<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表
                    </h3>
                </div>
                <div class="panel-body">

                    <form action="order/get/order/page.html" method="post" class="form-inline" role="form"
                          style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入订单编号">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <%--										<th width="30"><input type="checkbox"></th>--%>
                                <th>订单编号</th>
                                <th>支付宝流水号</th>
                                <th>订单金额</th>
                                <th>发票抬头</th>
                                <th>订单备注</th>
                                <th>订单状态</th>
                                <th>创建时间</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list }">
                                <tr>
                                    <td colspan="9" align="center">抱歉！没有查询到您要的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list }">
                                <c:forEach items="${requestScope.pageInfo.list }" var="order" varStatus="myStatus">
                                    <c:if test="${order.status==4}">
                                        <tr class="danger">
                                    </c:if>
                                    <c:if test="${order.status!=4}">
                                        <tr>
                                    </c:if>
                                        <td>${myStatus.count+pageInfo.pageSize*(pageInfo.pageNum-1)}</td>
                                            <%--												<td><input type="checkbox"></td>--%>
                                        <td>${order.orderNum }</td>
                                        <td>${order.payOrderNum }</td>
                                        <td>${order.orderAmount }</td>
                                        <c:if test="${order.invoice==0}">
                                            <td>无</td>
                                        </c:if>
                                        <c:if test="${order.invoice!=0}">
                                            <td>${order.invoiceTitle}</td>
                                        </c:if>
                                        <td>${order.orderRemark }</td>
                                        <c:if test="${order.status==1}">
                                            <td>已支付，待发货</td>
                                        </c:if>
                                        <c:if test="${order.status==2}">
                                            <td>已发货</td>
                                        </c:if>
                                        <c:if test="${order.status==3}">
                                            <td>订单已确认</td>
                                        </c:if>
                                        <c:if test="${order.status==4}">
                                            <td>待退款</td>
                                        </c:if>
                                        <c:if test="${order.status==5}">
                                            <td>已退款</td>
                                        </c:if>
                                        <td>${order.createTime }</td>
                                        <td>
                                            <a title="查看账单"
                                               href="order/get/order/detail/page.html?orderNum=${order.orderNum}"
                                               class="btn btn-info btn-xs"><i
                                                    class=" glyphicon glyphicon-briefcase"></i></a>

                                            <c:if test="${projectReview.projectPO.status==4}">
                                                <a title="去退款"
                                                   href="assign/to/assign/role/page.html?adminId=${admin.id }&pageNum=${requestScope.pageInfo.pageNum }&keyword=${param.keyword }"
                                                   class="btn btn-warning btn-xs"><i
                                                        class=" glyphicon glyphicon-briefcase"></i></a>
                                            </c:if></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="9" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>