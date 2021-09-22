var filePrefix = "/common/file";
$(function () {
    initFileTable('1','A','','fileTable','1');
    fileUpload('A','A01','fileTable');
});

/**
 * 初始化文件表格
 *
 * @param belongId 所属ID （必传）
 * @param type  文件总类型 （必传）
 * @param fileTag 文件分类型
 * @param tableId 表格id （必传）
 * @param delFlag 删除表示 （必传）
 */
function initFileTable(belongId,type,fileTag,tableId,delFlag) {
    $("#"+tableId).bootstrapTable({
        striped: true, // 设置为true会有隔行变色效果
        cache: false,
        sortable: false,
        url: filePrefix+"/listByBelongIdAndTypeAndFileTag",
        method: "get",
        dataType: "json", // 服务器返回的数据类型
        queryParams: {"belongId": belongId,"type":type,"fileTag":fileTag},
        onLoadSuccess: function(data) {
        },
        columns: [
            {
                checkbox: true
            },
            {
                class: 'uid',
                title: '序号',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                field: "id",
                visible: false
            },
            {
                field: "uuid",
                visible: false
            },
            {
                title: '上传文件',
                align: 'center',
                formatter: function (value, row, index) {
                    return uploadFileBtn(row,tableId);
                }
            },
            {
                field: "typeName",
                align: 'center',
                title: "文件总类型",
                visible: false
            },
            {
                field: "fileTagName",
                align: 'center',
                title: "文件类型"
            },
            {
                field: "fileName",
                align: 'center',
                title: "文件名"
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var id = row.id;
                    if(id==null){
                        return '-';
                    }
                    var p = '<a class="btn btn-warning btn-xs' + '"  title="预览"  mce_href="#" onclick="previewFile(\''
                        + row.url
                        + '\')">预览</a> ';
                    var e = '<a class="btn btn-success btn-xs' + '"  title="下载"  mce_href="#" onclick="downloadFile(\''
                        + id
                        + '\')">下载</a> ';
                    var d = '<a class="btn btn-danger btn-xs' + '" title="删除"  mce_href="#" onclick="deleteFile(\''+ id+'\',\''+tableId+ '\')">删除</a> ';


                    if(delFlag=='1'){
                        return p + e + d;
                    }else {
                        return p + e;
                    }
                }
            }
        ]
    })

}

/**
 * 上传文件页面
 *
 * @param type
 * @param tableId
 */
function uploadFilePage(type,tableId) {
    var index = layer.open({
        type: 2,
        title: '上传文件',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: filePrefix+'/uploadFilePage/' + type+'/'+tableId
    });
    layer.full(index);
}

/**
 * 上传
 *
 * @param type
 * @param fileTag
 * @param tableId
 */
function fileUpload(type,fileTag,tableId) {
    layui.use('upload', function () {
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#uploadFile', //绑定元素
            url: filePrefix+'/uploadFile1/'+type+'/'+fileTag, //上传接口
            size: 102400,
            accept: 'file',
            done: function (data) {
                if (data.code == 0) {
                    var busFile = data.busFile;
                    //插入一条数据
                    insertFile(busFile,tableId);
                }
                layer.msg(data.msg);
            },
            error: function (data) {
                layer.msg(data.msg);
            }
        });
    });
}

/**
 * 上传文件按钮
 *
 * @param row
 * @param tableId
 * @returns {string}
 */
function uploadFileBtn(row,tableId) {
    var formId=row.uuid;
    return  '<button type="button" class="btn  btn-primary" style="width: 82px;height: 25px;position: relative">'+
        '<form  id="'+formId+'" method="post" enctype="multipart/form-data">' +
        '<input type="hidden"  name="type" value="'+row.type+'"/>'+
        '<input type="hidden"  name="fileTag" value="'+row.fileTag+'"/>'+
        '<input type="file"  name="file"  onchange="uploadFile(\''+formId+'\',\''+tableId+'\')" style="width: 100%;height: 25px;padding:0;position: absolute;font-size: 10px;left:0;top: 0;opacity: 0;z-index:2;filter:alpha(opacity=0);cursor: pointer;"/>'+
        '</form>'+
        '<span style="position: absolute;left: 10%;top: 3px;z-index: 1;" >上传文件3</span>'+
        '</button>';
}


/**
 * 上传文件按钮-上传方法
 *
 * @param formId
 * @param tableId
 */
function uploadFile(formId,tableId) {
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,
        url: filePrefix+"/uploadFile2",
        data: new FormData($('#'+formId)[0]),
        success: function (data) {
            if (data.code == 0) {
                layer.msg("上传成功");
                insertFile(data.busFile,tableId);
            } else {
                layer.alert(data.msg)
            }
        }
    });
}

/**
 * 插入文件
 *
 * @param row
 * @param tableId
 */
function insertFile(row,tableId) {
    var table = $("#"+tableId);
    var rows = table.bootstrapTable('getData');
    var type = row.type;
    var fileTag = row.fileTag;
    var uuid=null;
    var insertIndex = rows.length-1;
    //寻找插入文件文件位置和可能需要删除的行
    $.each(rows, function (i, oldRow) {
        if(type==oldRow.type && fileTag==oldRow.fileTag){
            if(null == oldRow.id){
                uuid=oldRow.uuid;
                insertIndex=i;
            }else {
                insertIndex=i+1;
            }
        }
    });
    // 删除表格空的数据
    if(uuid!=null){
        var values = [uuid];
        table.bootstrapTable('remove', {
            field: 'uuid',
            values: values
        });
    }
    // 表格插入一条
    table.bootstrapTable('insertRow', {
        index: insertIndex,
        row: row
    });
}

/**
 * 预览文件
 *
 * @param url
 */
function previewFile(url) {
    //获取最后一个.的位置
    var index= url.lastIndexOf(".");
    //获取后缀
    var ext = url.substr(index+1);
    if(ext=='docx'||ext=='doc'||ext=='txt'||
        ext=='zip'||ext=='xlsx'||ext=='xls'||
        ext=='ppt'||ext=='pptx'){
        layer.msg("不支持此"+ext+"格式文件预览");
        return;
    }
    var index = layer.open({
        type: 2,
        title: '预览文件',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: filePrefix+'/previewFile?url=' + url
    });
    layer.full(index);
}


/**
 * 下载文件
 *
 * @param id
 */
function downloadFile(id) {
    window.location.href = filePrefix+"/downloadFile/" + id;
}

/**
 * 删除文件
 *
 * @param id
 * @param tableId
 */
function deleteFile(id,tableId) {
    layer.confirm("确认要删除此文件吗？",
        {btn: ['确定', '取消']}, function () {
            $.ajax({
                cache: true,
                type: "get",
                url: filePrefix+"/deleteFile/" + id,
                async: false,
                error: function (request) {
                    parent.layer.alert("Connection error");
                },
                success: function (data) {
                    if (data.code == 0) {
                        var table = $("#"+tableId);
                        var values = [parseInt(id)];
                        table.bootstrapTable('remove', {
                            field: 'id',
                            values: values
                        });
                        layer.closeAll('dialog');
                        parent.layer.msg("文件删除成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }

                }
            });
        })
}


/**
 * 打包下载1
 *
 * @param belongId
 * @param type
 * @param fileTag
 */
function downloadZip1(belongId,type,fileTag) {
    window.location.href = filePrefix+"/downloadZip1?belongId=" + belongId+"&type="+type+"&fileTag"+fileTag;
}

/**
 * 打包下载2
 */
function downloadZip2() {
    var params={};
    params.belongIds=[1,2];
    params.zipFilename='测试';
    var param = jQuery.param(params);
    window.location.href = filePrefix+"/downloadZip2?" + param;
}
