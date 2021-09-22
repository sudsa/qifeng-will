$().ready(function () {
    init();
});

function init() {
    let amount = generateAmount();
    $("#sumCount").val(1);
    $("#trueCount").val(0);
    $("#falseCount").val(0);
    $("#trueRate").val(0);
    $("#amountHidden").val(amount);
    $("#amount").val(formatMoney(amount,2));
    convertAmount();
}


function generateAmount() {

    return (Math.random() * (0 - 1000000000) + 1000000000).toFixed(2);
}


function convertAmount() {
    var amount = $("#amountHidden").val();
    if (amount == null || amount < 0) {
        return;
    }
    var bigAmount = smalltoBig(amount);
    $("#amountStr").val(bigAmount);
}



$('#inputAmount').bind('keyup', function(event) {
    if (event.keyCode == "13") {

        var inputAmount = $("#inputAmount").val();
        var amountHidden = $("#amountHidden").val();
        if(inputAmount==null || inputAmount==''){
            alert("请输入正确金额！");
            return;
        }
        let sumCount=parseInt($("#sumCount").val())+1;

        if(parseFloat(inputAmount)==parseFloat(amountHidden)){
            let amount = generateAmount();
            let trueCount= parseInt($("#trueCount").val())+ 1;
            let trueRate = floatDiv(trueCount,sumCount);
            $("#sumCount").val(sumCount);
            $("#trueCount").val(trueCount);
            $("#trueRate").val(keepTwoDecimalFull(trueRate));
            $("#amountHidden").val(amount);
            $("#amount").val(formatMoney(amount,2));
            $("#inputAmount").val();
            convertAmount();
        }else {
            let falseCount= parseInt($("#falseCount").val())+ 1;
            let trueRate = floatDiv(falseCount,sumCount);
            $("#sumCount").val(sumCount);
            $("#falseCount").val(falseCount);
            $("#trueRate").val(keepTwoDecimalFull(trueRate));
        }
    }
});


function keepTwoDecimalFull(num) {
    var result = parseFloat(num);
    if (isNaN(result)) {
        alert('传递参数错误，请检查！');
        return false;
    }
    result = Math.round(num * 100) / 100;
    var s_x = result.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}


function floatDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}

    r1=Number(arg1.toString().replace(".",""));

    r2=Number(arg2.toString().replace(".",""));
    return (r1/r2)*Math.pow(10,t2-t1);
}


function formatMoney(value, type) {
    if (value == '0' || value == '') {
        return '0';
    }
    if (value == null || value == '') {
        return '';
    }
    if (value == "-") {
        return '-';
    }

    if (type == null || type == '') {
        type = 2;
    }
    return parseFloat(value).toFixed(type).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
};


/** 数字金额大写转换(可以处理整数,小数,负数) */
function smalltoBig(n) {
    var fraction = ['角', '分'];
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    var unit = [['元', '万', '亿'], ['', '拾', '佰', '仟']];
    var head = n < 0 ? '欠' : '';
    n = Math.abs(n);

    var s = '';

    for (var i = 0; i < fraction.length; i++) {
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
    }
    s = s || '整';
    n = Math.floor(n);

    for (var i = 0; i < unit[0].length && n > 0; i++) {
        var p = '';
        for (var j = 0; j < unit[1].length && n > 0; j++) {
            p = digit[n % 10] + unit[1][j] + p;
            n = Math.floor(n / 10);
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
    }
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
}


$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});

function save() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/common/dict/save",
        data: $('#signupForm').serialize(), // 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("网络超时");
        },
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });

}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入名字"
            }
        }
    })
}