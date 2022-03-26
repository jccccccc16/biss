function myRemoveHoverDom(treeId, treeNode) {
    // 拼接按钮组的 id
    var btnGroupId = treeNode.tId + "_btnGrp";
// 移除对应的元素
    $("#" + btnGroupId).remove();
}

// 当生成每一个节点时都会调用这个函数
// 改变节点的图标
function myAddDiyDom(treeId, treeNode) {
    // treeId 是整个树形结构附着的 ul 标签的 id
    console.log("treeId="+treeId);
    // 当前树形节点的全部的数据，包括从后端查询得到的 Menu 对象的全部属性
    console.log(treeNode);
    // zTree 生成 id 的规则
    // 例子：treeDemo_7_ico
    // 解析：ul 标签的 id_当前节点的序号_功能
    // 提示：“ul 标签的 id_当前节点的序号”部分可以通过访问 treeNode 的 tId 属性得到
    // 根据 id 的生成规则拼接出来 span 标签的 id
    var spanId = treeNode.tId + "_ico";
    // 根据控制图标的 span 标签的 id 找到这个 span 标签 // 删除旧的 class // 添加新的 class
    $("#"+spanId) .removeClass() .addClass(treeNode.icon);
}


// 在鼠标移入节点范围时添加按钮组，用来显示对节点的修改
function myAddHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";
    if ($("#" + btnGroupId).length > 0) {
        return;
    }
    var addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var level = treeNode.level;
    var btnHTML = "";
    if (level == 0) {
        btnHTML = addBtn;
    }
    if (level == 1) {
        btnHTML = addBtn + " " + editBtn;
        var length = treeNode.children.length;
        // 当没有子节点时，可以显示删除操作
        if (length == 0) {
            btnHTML = btnHTML + " " + removeBtn;
        }
    }
    if (level == 2) {
        btnHTML = editBtn + " " + removeBtn;
    }
    var anchorId = treeNode.tId + "_a";
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");
}


function generateMenu() {
    // 使用json形成属性结构的json数据
    $.ajax({
        "url": "menu/get/menu/tree.json",
        "type": "post",
        "dataType": "json",
        "success": function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                // 2.创建 JSON 对象用于存储对 zTree 所做的设置
                // 3.从响应体中获取用来生成树形结构的 JSON 数据
                // 存储对zTree的设置
                var setting = {
                    "view":{
                        "addDiyDom":myAddDiyDom, //自定义菜单图标
                        "addHoverDom":myAddHoverDom, // 鼠标移入元素中
                        "removeHoverDom": myRemoveHoverDom // 鼠标移除元素
                    },
                    "data":{
                        "key":{
                            "url":"cjc"  // 不跳转，具体看zTree的文档
                        }
                    }
                };
                var zNodes = []
                var zNodes = response.data;
                // 4.初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result == "FAILED") {
                layer.msg(response.message);
            }
        }
    })
}