

function importExcel() {
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,
        url: "/importExcel",
        data: new FormData($('#importPlan')[0]),
        success: function (r) {
            if (r.code == 0) {
                alert("导入成功");
            } else {
                $("#data").val(JSON.stringify(r.map));
                $("#exportForm").submit();
            }
        }
    });
}