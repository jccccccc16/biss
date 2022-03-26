<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--<script type="text/javascript">--%>
<%--    $(function () {--%>
<%--        // generateInitMenu()--%>
<%--    })--%>

<%--    function generateInitMenu() {--%>
<%--        $.ajax({--%>
<%--            "type": "post",--%>
<%--            "url": "menu/get/menu/to/side/bar.json",--%>
<%--            "dataType": "json",--%>
<%--            "async": "false",--%>
<%--            "success": function (response) {--%>
<%--                var result = response.result;--%>
<%--                if (result == "SUCCESS") {--%>
<%--                    var data = response.data;--%>
<%--                    console.info(data)--%>
<%--                    var menuHtml = ""--%>
<%--                    for (var i = 0; i < data.length; i++) {--%>
<%--                        var children = data[i].children;--%>
<%--// 如果没有子节点--%>
<%--                        var secondNodeHtml = "";--%>
<%--                        if (children == null || children.length === 0) {--%>
<%--                            secondNodeHtml = "<li class=\"list-group-item tree\"><a href=\"" + data[i].url + "\"><i\n" +--%>
<%--                                "                    class=\"" + data[i].icon + "\"></i>" + data[i].name + "</a></li>"--%>
<%--                        } else { // 如果有子节点--%>
<%--                            var secondNodeHeadHtml = "<li class=\"list-group-item tree\"><span><i\n" +--%>
<%--                                "\t\t\t\t\tclass=\"" + data[i].icon + "\"></i> " + data[i].name + " <span\n" +--%>
<%--                                "\t\t\t\t\tclass=\"badge\" style=\"float: right\">" + children.length + "</span></span>\n" +--%>
<%--                                "\t\t\t\t<ul style=\"margin-top: 10px; display: none;\">"--%>
<%--                            var secondNodeTailHtml = "</ul></li>"--%>
<%--                            var childrenHtml = "";--%>
<%--                            for (var j = 0; j < children.length; j++) {--%>
<%--                                childrenHtml = childrenHtml + "<li style=\"height: 30px;\"><a href=\"" + children[j].url + "\"><i\n" +--%>
<%--                                    "\t\t\t\t\t\t\tclass=\"" + children[j].icon + "\"></i> " + children[j].name + "</a></li>"--%>
<%--                            }--%>
<%--                            secondNodeHtml = secondNodeHeadHtml + childrenHtml + secondNodeTailHtml;--%>
<%--                        }--%>
<%--                        menuHtml = menuHtml + secondNodeHtml;--%>
<%--                    }--%>
<%--                    $("#menusUl").append(menuHtml);--%>
<%--                } else {--%>
<%--                    alert("菜单初始化失败，请刷新");--%>
<%--                }--%>
<%--            },--%>
<%--            "error": function (response) {--%>
<%--                var message = response.message;--%>
<%--                alert("菜单初始化失败," + message)--%>
<%--            }--%>
<%--        })--%>
<%--    }--%>
<%--</script>--%>

<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul id="menusUl" style="padding-left:0px;" class="list-group">
            <c:forEach items="${sessionScope.menus}" var="menu">
                <c:if test="${menu.children==null || fn:length(menu.children)==0}">
                    <li class="list-group-item tree-closed"><a href="${pageContext.request.contextPath }${menu.url}"><i
                            class="${menu.icon}"></i> ${menu.name}</a></li>
                </c:if>
                <c:if test="${menu.children!=null || fn:length(menu.children)!=0}">
                    <li class="list-group-item tree-closed"><span><i
                            class="${menu.icon}"></i> ${menu.name} <span
                            class="badge" style="float: right">${fn:length(menu.children)}</span></span>
                        <ul style="margin-top: 10px; display: none;">
                            <c:forEach items="${menu.children}" var="child">
                                <li style="height: 30px;"><a href="${pageContext.request.contextPath }${child.url}"><i
                                        class="${child.icon}"></i> ${child.name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
    </div>
</div>


