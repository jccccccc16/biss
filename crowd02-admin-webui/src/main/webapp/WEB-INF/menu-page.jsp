<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <base
            href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="ztree/zTreeStyle.css"/>
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        .tree-closed {
            height: 40px;
        }

        .tree-expanded {
            height: auto;
        }
    </style>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="script/docs.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script src="myjs/my-menu.js" charset="gb312"></script>
    <script type="text/javascript">
        $(function () {
            $(".list-group-item").click(function () {
                if ($(this).find("ul")) {
                    $(this).toggleClass("tree-closed");
                    if ($(this).hasClass("tree-closed")) {
                        $("ul", this).hide("fast");
                    } else {
                        $("ul", this).show("fast");
                    }
                }
            });
        });
    </script>
    <title>大学生创新创业众筹平台</title>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">大学生创新创业众筹平台 - 后台
            </a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle"
                                data-toggle="dropdown">
                            <i class="glyphicon glyphicon-user"></i> 张三 <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                            <li class="divider"></li>
                            <li><a href="index.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                        </ul>
                    </div>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
<%--<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span--%>
<%--                        class="sr-only">Close</span></button>--%>
<%--                <h4 class="modal-title" id="myModalLabel">帮助</h4>--%>
<%--            </div>--%>
<%--            <div class="modal-body">--%>
<%--                <div class="bs-callout bs-callout-info">--%>
<%--                    <h4>没有默认类</h4>--%>
<%--                    <p>警告框没有默认类，只有基类和修饰类。默认的灰色警告框并没有多少意义。所以您要使用一种有意义的警告类。目前提供了成功、消息、警告或危险。</p>--%>
<%--                </div>--%>
<%--                <div class="bs-callout bs-callout-info">--%>
<%--                    <h4>没有默认类</h4>--%>
<%--                    <p>警告框没有默认类，只有基类和修饰类。默认的灰色警告框并没有多少意义。所以您要使用一种有意义的警告类。目前提供了成功、消息、警告或危险。</p>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <!----%>
<%--            <div class="modal-footer">--%>
<%--              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
<%--              <button type="button" class="btn btn-primary">Save changes</button>--%>
<%--            </div>--%>
<%--            -->--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<script type="text/javascript">


    // 菜单修改
    $(function () {
        generateMenu()

        // 添加
        $("#treeDemo").on("click", ".addBtn", function () {
            var pid = this.id;
            // 显示模态框
            $("#menuAddModal").modal("show");
            $("#menuSaveBtn").click(function () {
                var name = $("#menuAddModal [name=name]").val();
                var url = $("#menuAddModal [name=url]").val();
                var icon = $("#menuAddModal [name=icon]:checked").val();

                if ((name == null || name == "") || (url == null || url == "") || (icon == null || icon == "")) {
                    alert("请填写完整信息！")
                    return false;
                }
                $.ajax({
                    "url": "save/menu.json",
                    "type": "post",
                    "data": JSON.stringify({
                        "name": name,
                        "url": url,
                        "icon": icon,
                        "pid": pid
                    }),
                    "contentType": "application/json",
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if (result == "SUCCESS") {
                            alert("添加成功")
                            generateMenu()
                        } else {
                            var message = response.message;
                            alert(message)
                        }
                    },
                    "error": function (response) {
                        alert(response.message);
                    }
                })
                $("#menuEditModal").modal("hide");

            })


            return false;
        })

        // 编辑
        $("#treeDemo").on("click", ".editBtn", function () {

            // 将当前节点的id保存到全局变量
            window.id = this.id;
            // 打开模态框
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            $("#menuEditModal").modal("show");
            // // 根据id属性查询节点对象
            var key = "id";
            // // 用来搜索节点的属性值
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key, value);
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);

            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });
        // 编辑
        $("#menuEditBtn").click(function () {
            var id = window.id;
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();
            var data = JSON.stringify({
                "id": id,
                "name": name,
                "url": url,
                "icon": icon
            })
            alert(data)
            $.ajax({
                "url": "edit/menu/by/id.json",
                "contentType": "application/json",
                "type": "post",
                "data": data,
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        alert("修改成功");
                        generateMenu()
                    } else {
                        alert("修改失败");
                    }

                },
                "error": function (response) {
                    var message = reponse.meesage;
                    alert(message)
                }
            })
            $("#menuEditModal").modal("hide");

        })

        // 删除
        $("#treeDemo").on("click", ".removeBtn", function () {
            var id = this.id;
            alert(id);
            var isConfirm = confirm("确定要删除该菜单吗？");
            if (isConfirm) {
                $.ajax({
                    "url": "remove/menu/by/id.json",
                    "type": "post",
                    "data": {
                        "id": id
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if (result == "SUCCESS") {
                            generateMenu();
                            alert("删除成功")

                        } else {
                            alert("删除失败，请重试!")
                        }
                    },
                    "error": function (response) {
                        var message = response.message;
                        alert(message);
                    }
                })
            }
            // $.confirm({
            //     title: '确认',
            //     content: '确定要删除该菜单吗？',
            //     type: 'green',
            //     icon: 'glyphicon glyphicon-question-sign',
            //     buttons: {
            //         ok: {
            //             text: '确认',
            //             btnClass: 'btn-primary',
            //             action: function() {
            //                 $.ajax({
            //                     "url":"remove/menu/by/id.json",
            //                     "type":"post",
            //                     "data":{
            //                         "id":id
            //                     },
            //                     "dataType":"json",
            //                     "success":function(response){
            //                         var result = response.result;
            //                         if(result=="SUCCESS"){
            //                             alert("删除成功")
            //                              generateMenu();
            //                         }else{
            //                             alert("删除失败，请重试!")
            //                         }
            //                     },
            //                     "error":function(response){
            //                         var message = response.message;
            //                         alert(message);
            //                     }
            //                 })
            //             }
            //         },
            //         cancel: {
            //             text: '取消',
            //             btnClass: 'btn-primary'
            //         }
            //     }
            // });
            return false;
        })


    })


    // $(function () {
    //     $(".list-group-item").click(function () {
    //         if ($(this).find("ul")) {
    //             $(this).toggleClass("tree-closed");
    //             if ($(this).hasClass("tree-closed")) {
    //                 $("ul", this).hide("fast");
    //             } else {
    //                 $("ul", this).show("fast");
    //             }
    //         }
    //     });
    //
    //     var setting = {
    //         view: {
    //             selectedMulti: false,
    //             addDiyDom: function (treeId, treeNode) {
    //                 var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
    //                 if (treeNode.icon) {
    //                     icoObj.removeClass("button ico_docu ico_open").addClass("fa fa-fw " + treeNode.icon).css("background", "");
    //                 }
    //             },
    //             addHoverDom: function (treeId, treeNode) {
    //                 var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
    //                 aObj.attr("href", "javascript:;");
    //                 if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) return;
    //                 var s = '<span id="btnGroup' + treeNode.tId + '">';
    //                 if (treeNode.level == 0) {
    //                     s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    //                 } else if (treeNode.level == 1) {
    //                     s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    //                     if (treeNode.children.length == 0) {
    //                         s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    //                     }
    //                     s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    //                 } else if (treeNode.level == 2) {
    //                     s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    //                     s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    //                 }
    //
    //                 s += '</span>';
    //                 aObj.after(s);
    //             },
    //             removeHoverDom: function (treeId, treeNode) {
    //                 $("#btnGroup" + treeNode.tId).remove();
    //             }
    //         },
    //         async: {
    //             enable: true,
    //             url: "tree.txt",
    //             autoParam: ["id", "name=n", "level=lv"]
    //         },
    //         callback: {
    //             onClick: function (event, treeId, json) {
    //
    //             }
    //         }
    //     };
    //     //$.fn.zTree.init($("#treeDemo"), setting); //异步访问数据
    //
    //     var d = [{
    //         "id": 1,
    //         "pid": 0,
    //         "seqno": 0,
    //         "name": "系统权限菜单",
    //         "url": null,
    //         "icon": "fa fa-sitemap",
    //         "open": true,
    //         "checked": false,
    //         "children": [{
    //             "id": 2,
    //             "pid": 1,
    //             "seqno": 0,
    //             "name": "控制面板",
    //             "url": "dashboard.htm",
    //             "icon": "fa fa-desktop",
    //             "open": true,
    //             "checked": false,
    //             "children": []
    //         }, {
    //             "id": 6,
    //             "pid": 1,
    //             "seqno": 1,
    //             "name": "消息管理",
    //             "url": "message/index.htm",
    //             "icon": "fa fa-weixin",
    //             "open": true,
    //             "checked": false,
    //             "children": []
    //         }, {
    //             "id": 7,
    //             "pid": 1,
    //             "seqno": 1,
    //             "name": "权限管理",
    //             "url": "",
    //             "icon": "fa fa-cogs",
    //             "open": true,
    //             "checked": false,
    //             "children": [{
    //                 "id": 8,
    //                 "pid": 7,
    //                 "seqno": 1,
    //                 "name": "用户管理",
    //                 "url": "user/index.htm",
    //                 "icon": "fa fa-user",
    //                 "open": true,
    //                 "checked": false,
    //                 "children": []
    //             }, {
    //                 "id": 9,
    //                 "pid": 7,
    //                 "seqno": 1,
    //                 "name": "角色管理",
    //                 "url": "role/index.htm",
    //                 "icon": "fa fa-graduation-cap",
    //                 "open": true,
    //                 "checked": false,
    //                 "children": []
    //             }, {
    //                 "id": 10,
    //                 "pid": 7,
    //                 "seqno": 1,
    //                 "name": "许可管理",
    //                 "url": "permission/index.htm",
    //                 "icon": "fa fa-check-square-o",
    //                 "open": true,
    //                 "checked": false,
    //                 "children": []
    //             }]
    //         }, {
    //             "id": 11,
    //             "pid": 1,
    //             "seqno": 1,
    //             "name": "资质管理",
    //             "url": "",
    //             "icon": "fa fa-certificate",
    //             "open": true,
    //             "checked": false,
    //             "children": [{
    //                 "id": 12,
    //                 "pid": 11,
    //                 "seqno": 1,
    //                 "name": "分类管理",
    //                 "url": "cert/type.htm",
    //                 "icon": "fa fa-th-list",
    //                 "open": true,
    //                 "checked": false,
    //                 "children": []
    //             }, {
    //                 "id": 13,
    //                 "pid": 11,
    //                 "seqno": 1,
    //                 "name": "资质管理",
    //                 "url": "cert/index.htm",
    //                 "icon": "fa fa-certificate",
    //                 "open": true,
    //                 "checked": false,
    //                 "children": []
    //             }]
    //         }, {
    //             "id": 15,
    //             "pid": 1,
    //             "seqno": 1,
    //             "name": "流程管理",
    //             "url": "process/index.htm",
    //             "icon": "fa fa-random",
    //             "open": true,
    //             "checked": false,
    //             "children": []
    //         }, {
    //             "id": 16,
    //             "pid": 1,
    //             "seqno": 1,
    //             "name": "审核管理",
    //             "url": "",
    //             "icon": "fa fa-check-square",
    //             "open": true,
    //             "checked": false,
    //             "children": [{
    //                 "id": 17,
    //                 "pid": 16,
    //                 "seqno": 1,
    //                 "name": "实名认证人工审核",
    //                 "url": "process/cert.htm",
    //                 "icon": "fa fa-check-circle-o",
    //                 "open": true,
    //                 "checked": false,
    //                 "children": []
    //             }]
    //         }]
    //     }];
    //     $.fn.zTree.init($("#treeDemo"), setting, d);
    // });
</script>
</body>
</html>
