function generatePage() {
    console.log("我进来了generatePage()")
    var pageInfo = getPageInfoRemote();
    // 填充表格
    fillTableBody(pageInfo);

}

function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    });
    console.log("ajaxResult: " + ajaxResult);
    // 获取状态码
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("失败！ 响应状态码=" + statusCode + " 说明信息" + ajaxResult.statusText)
    }
    // 如果响应成功，那么获取result
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return;
    }

    var pageInfo = resultEntity.data;
    return pageInfo;

}


function fillTableBody(pageInfo) {
    // 清空内容
    $("#rolePageBody").empty();
    // 没有内容时不显示导航条
    $("#Pagination").empty();
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜 索的数据！</td></tr>");
        return;
    }
    // 使用 pageInfo 的 list 属性填充 tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>" + (i + 1+(pageInfo.pageNum-1)*pageInfo.pageSize) + "</td>";
        var checkboxTd = "<td><input type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn' ><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);
        // 生成分页导航条
        generateNavigator(pageInfo);
    }
}

function generateNavigator(pageInfo){
    // 获取总记录数
    var totalRecord = pageInfo.total; // 声明相关属性
    var properties = { "num_edge_entries": 3, "num_display_entries": 5, "callback": paginationCallBack, "items_per_page": pageInfo.pageSize, "current_page": pageInfo.pageNum - 1, "prev_text": "pre", "next_text": "next" }
    // 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}
// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    // 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;
// 调用分页函数
    generatePage(); // 取消页码超链接的默认行//
    return false;
}