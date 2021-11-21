var filePrefix = "/common/file";
$(function () {
});

$("#excelImport").click(function() {
    $('#fileupload').click();
});
$('#fileupload').change(function(){
    $('#textfield').val( document.getElementById("fileupload").files[0].name);
})

function uploadFile() {
    var fileBtnId='signupForm';
    var type=$('#type').val();
    var fileTag= $('#fileTag option:selected').val();
    var tableId=$('#tableId').val();
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,
        url: filePrefix+"/uploadFile1/"+type+"/"+fileTag,
        data: new FormData($('#'+fileBtnId)[0]),
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.insertFile(data.busFile,tableId);
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg)
            }
        }
    });
}