<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<%@include file="/WEB-INF/modal-role-add.jsp"%>
<%@include file="/WEB-INF/modal-role-update.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" charset="UTF-8" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" charset="UTF-8" src="myjs/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        // 分页初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        generatePage();
    })
    $(function () {
        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();
            generatePage();
        })

        // 打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        })


        // 点击更新按钮
        $("#rolePageBody").on("click",".pencilBtn",function(){
            console.log("点击rolePage")
            $("#updateModel").modal("show");
            var roleName = $(this).parent().prev().text();
            window.roleId = this.id;
            // 回显
            $("#updateModel [name=roleName]").val(roleName);
        })

        // 删除
        $("#rolepagebody").on("click",".")

        // 更新role
        $("#updateRoleBtn").click(function () {
            var roleName = $("#updateModel [name=roleName]").val();
            console.log("roleName"+roleName)
            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {
                    "id":window.roleId,
                    "name": roleName,
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功!");
                        // 重新分页
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
                // 关闭模态框
                $("#updateModel").modal("hide");
            })
        })




        //
        $("#saveRoleBtn").click(function(){
            var roleName = $.trim($("#addModal [name=roleName]").val());
            $.ajax({
                "url":"role/save.json",
                "type":"post",
                "data":{
                    "name":roleName,
                },
                "dataType":"json",
                "success":function(response){
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg("操作成功");
                        // 设置为最后一夜
                        window.pageNum =9999999;
                        generatePage();
                    }
                    if(result=="FAILED"){
                        layer.msg("操作失败！"+response.message)
                    }
                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            })
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
        })





</script>
<body>

<%@ include file="/WEB-INF/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表
                    </h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float: right; margin-left: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button
                            type="button"
                            id="showAddModalBtn" class="btn btn-primary"
                            style="float: right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
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