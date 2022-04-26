// 获取分页数据1
function getMySupportProjectInfo(memberId){
    var pageInfo={};
    $.ajax({
        "url":"http://localhost/get/my/support/project/by/"+memberId+".json",
        "dataType":"json",
        "data":JSON.stringify({
            "pageSize":window.pageSize,
            "pageNum":window.pageNum,
        }),
        "contentType":"json/application",
        "type":"post",
        async: false,
        "success":function(response){
            var result = response.result;

            if(result=="SUCCESS"){
                pageInfo = response.data;
                console.warn(pageInfo);
                return pageInfo;
            }else{
                alert("数据访问出现错误，请重试!");
                console.warn(response);
                pageInfo = null;
                return pageInfo;
            }
        },
        "error":function(response){
            alert("数据访问出现错误，请重试!"+response.message)
            console.warn(response);
            pageInfo =  null;
            return pageInfo;
        }
    })
    return pageInfo;

}
// 填充表格2
function filledTheTable(pageInfo) {

     // 清空内容
    $("#mySupportTbody").empty();
    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#pagination").empty();
    var html = "";
    // 判断pageInfo对象是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#mySupportTbody").append("<tr><td colspan='4' align='center' >抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }else{
        // 使用pageInfo的list属性填充tbody
        for(var i = 0; i < pageInfo.list.length; i++) {
            var orderNum = pageInfo.list[i].orderNum;
            var projectName = pageInfo.list[i].projectName;
            var percentage = pageInfo.list[i].percentage;
            var lastDays = pageInfo.list[i].lastDays;
            var projectStatus = pageInfo.list[i].projectStatus;
            var supportDate = pageInfo.list[i].supportDate;
            var freight = pageInfo.list[i].freight;
            var returnCount = pageInfo.list[i].returnCount;
            var supportAmount = pageInfo.list[i].supportAmount;
            // 订单状态
            var orderStatus = pageInfo.list[i].orderStatus;
            var orderStatusString = "";
            if(orderStatus==1){
                orderStatusString = "已支付";
            }else if(orderStatus==2){
                orderStatusString = "已发货";
            }else if(orderStatus==3){
                orderStatusString = "订单已确认";
            }else if(orderStatus==4){
                orderStatusString = "已退款";
            }else if(orderStatus==5){
                orderStatusString = "待退款";
            }else if(orderStatus==6){
                orderStatusString = "待发货";
            }
            var projectStatusString = "";
            if(projectStatus==1){
                projectStatusString="众筹中";
            }
            if(projectStatus==3){
                projectStatusString="众筹成功";
            }
            if(projectStatus==4){
                projectStatusString="众筹失败";
            }
            var projectDetailTD = " <td style=\"vertical-align:middle;\">\n" +
                "                                                    <div class=\"thumbnail\">\n" +
                "                                                        <div class=\"caption\">\n" +
                "                                                            <h3>\n" +
                "                                                                "+projectName+"\n" +
                "                                                            </h3>\n" +
                "                                                            <p>\n" +
                "                                                                \n" +
                "                                                            </p>\n" +
                "                                                            <p>\n" +
                "                                                                <div style=\"float:left;\"><i class=\"glyphicon glyphicon-screenshot\" title=\"目标金额\" ></i> 已完成 "+percentage+"% </div>\n" +
                "                                                                <div style=\"float:right;\"><i title=\"截至日期\" class=\"glyphicon glyphicon-calendar\"></i> 剩余"+lastDays+"天 </div>\n" +
                "                                                            </p>\n" +
                "                                                            <br>\n" +
                "                                                                <div class=\"progress\" style=\"margin-bottom: 4px;\">\n" +
                "                                                                  <div class=\"progress-bar progress-bar-danger\" role=\"progressbar\" aria-valuenow=\"40\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: "+percentage+"%\">\n" +
                "                                                                    <span >"+projectStatusString+"</span>\n" +
                "                                                                  </div>\n" +
                "                                                                </div>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                  </td>"
        };
        var supportDateTD = "<td style=\"vertical-align:middle;\">"+supportDate+"</td>";
        var freightTD = " <td style=\"vertical-align:middle;\">"+supportAmount+"<br>(运费："+freight+" )</td>";
        var returnTd = " <td style=\"vertical-align:middle;\">"+returnCount+"</td>";
        var statusTD = "<td style=\"vertical-align:middle;\">"+orderStatusString+"</td>";
        var operationTD = "<td style=\"vertical-align:middle;\">\n" +
            "                                                    <div class=\"btn-group-vertical\" role=\"group\" aria-label=\"Vertical button group\">\n" +
            "                                                          <a type=\"button\" class=\"btn btn-default\">删除订单</a>\n" +
            "                                                          <a type=\"button\"  href=\"http://localhost/order/get/detail/"+orderNum+"\" class=\"btn btn-default\">交易详情</button>\n" +
            "                                                    </div>\n" +
            "                                                  </td>";
        var tr = "<tr>"+projectDetailTD+supportDateTD+freightTD+returnTd+statusTD+operationTD+"</tr>";
        $("#mySupportTbody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成我支持的众筹分页页码导航条3
function generateNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }

    // 调用pagination()函数
    $("#MySupportPagination").pagination(totalRecord, properties);
}



// 翻页时的回调函数4
function paginationCallBack(pageIndex, jQuery) {

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generateSupportPage(memberId);
    // 取消页码超链接的默认行为
    return false;
}

// 生成页面5
function generateSupportPage(memberId) {
    // 1.获取分页数据
    var pageInfo= getMySupportProjectInfo(memberId);
    console.info(pageInfo)
    // 2.填充表格
    filledTheTable(pageInfo);
}



//1
function getMyLaunchProjectInfo(memberId) {

    var pageInfo={};
    $.ajax({
        "url":"http://localhost/get/my/launch/project/by/"+memberId+".json",
        "dataType":"json",
        "data":JSON.stringify({
            "pageSize":window.pageSize,
            "pageNum":window.pageNum,
        }),
        "contentType":"json/application",
        "type":"post",
        async: false,
        "success":function(response){

            var result = response.result;
            if(result=="SUCCESS"){
                pageInfo = response.data;
                console.warn(pageInfo);
                return pageInfo;
            }else{
                console.warn(response);
                pageInfo = null;
                return pageInfo;
            }
        },
        "error":function(response){
            alert("数据访问出现错误，请重试!")
            console.warn(response);
            pageInfo =  null;
            return pageInfo;
        }
    })
    return pageInfo;
}




// 我的众筹2
function generateLaunchPage(memberId){
    // 1.获取分页数据
    var pageInfo= getMyLaunchProjectInfo(memberId);
    console.info(pageInfo)
    // 2.填充表格
    filledLaunchProjectTable(pageInfo);
}
//3
function filledLaunchProjectTable(pageInfo){
    console.info("生成我发起的表格")
    // 清空内容
    $("#myLaunchProjectTbody").empty();
    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#myLaunchProjectTbody").empty();

    // 判断pageInfo对象是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#myLaunchProjectTbody").append("<tr><td colspan='4' align='center' >抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }else{

        for(var i = 0; i < pageInfo.list.length; i++) {
            console.log("-------------------------"+i)
            var projectId = pageInfo.list[i].projectId;
            var projectName = pageInfo.list[i].projectName;
            var percentage = pageInfo.list[i].percentage;
            var money = pageInfo.list[i].money;
            var projectStatus = pageInfo.list[i].status;
            var lastDays = pageInfo.list[i].lastDays;
            // 项目状态
            var projectStatusString = "";
            if(projectStatus==0){
                projectStatusString="待审核";
            }
            if(projectStatus==1){
                projectStatusString="众筹中";
            }if(projectStatus==2){
                projectStatusString="审核失败";
            }
            if(projectStatus==3){
                projectStatusString="众筹成功";
            }
            if(projectStatus==4){
                projectStatusString="众筹失败";
            }
            // 看这里，做到这里
            var projectDetailTD = "<td style=\"vertical-align:middle;\">\n" +
                "                                                    <div class=\"thumbnail\">\n" +
                "                                                        <div class=\"caption\">\n" +
                "                                                            <p>\n" +
                "                                                                "+projectName+"\n" +
                "                                                            </p>\n" +
                "                                                            <p>\n" +
                "                                                            <div style=\"float:left;\"><i\n" +
                "                                                                    class=\"glyphicon glyphicon-screenshot\"\n" +
                "                                                                    title=\"目标金额\"></i> 已完成"+percentage+"%\n" +
                "                                                            </div>\n" +
                "                                                            <div style=\"float:right;\"><i title=\"截至日期\"\n" +
                "                                                                                         class=\"glyphicon glyphicon-calendar\"></i>\n" +
                "                                                                剩余"+lastDays+"天\n" +
                "                                                            </div>\n" +
                "                                                            </p>\n" +
                "                                                            <br>\n" +
                "                                                            <div class=\"progress\" style=\"margin-bottom: 4px;\">\n" +
                "                                                                <div class=\"progress-bar progress-bar-success\"\n" +
                "                                                                     role=\"progressbar\" aria-valuenow=\"40\"\n" +
                "                                                                     aria-valuemin=\"0\" aria-valuemax=\"100\"\n" +
                "                                                                     style=\"width: "+percentage+"%\">\n" +
                "                                                                    <span>"+projectStatusString+"</span>\n" +
                "                                                                </div>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                </td>"
        };
        var freightTD = "<td style=\"vertical-align:middle;\">"+money+"<br></td>";
        var currentStatusTD = "<td style=\"vertical-align:middle;\">"+projectStatusString+"</td>";
        var operationTD = "<td style=\"vertical-align:middle;\">\n" +
            "                                                    <div class=\"btn-group-vertical\" role=\"group\"\n" +
            "                                                         aria-label=\"Vertical button group\">\n" +
            "                                                        <a href=\"http://localhost/project/show/detail/project/"+projectId+ "\" type=\"button\" class=\"btn btn-default\">查看项目</a>\n" +
            "                                                        <button type=\"button\" class=\"btn btn-default\">修改项目</button>\n" +
            "                                                        <button type=\"button\" class=\"btn btn-default\">删除项目</button>\n"
            "                                                    </div>\n" +
            "                                                </td>";
        var tr = "<tr>"+projectDetailTD+freightTD+currentStatusTD+operationTD+"</tr>";
        console.log(projectDetailTD)
        $("#myLaunchProjectTbody").append(tr)
    }
    console.info("-----------------------------------")
    console.info(pageInfo.list[0])
    console.info(pageInfo.list[1])
    // 生成分页导航条
    generateMyLaunchNavigator(pageInfo);
}



// 生成我发起的众筹分页页码导航条4
function generateMyLaunchNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": MyLaunchPaginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }

    // 调用pagination()函数
    $("#myLaunchPagination").pagination(totalRecord, properties);
}


// 翻页时的回调函数4
function MyLaunchPaginationCallBack(pageIndex, jQuery) {


    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generateLaunchPage(memberId);
    // 取消页码超链接的默认行为
    return false;
}

