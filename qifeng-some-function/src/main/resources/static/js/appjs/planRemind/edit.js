$().ready(function() {
	validateRule();
	selectPlan();
});

$(function () {
	//  序列化表单
	$.fn.serializeJSON = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	}
});


$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function update() {
	var params = serialParams();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/planRemind/update",
		data : jQuery.param(params),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
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
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}


function selectPlan(){
	var planType = $("#planType").val();
	if (planType == 0 ) {
		// 单次执行
		$("#cycleDateFlag").hide();
		$("#cycleNumberFlag").hide();
		$("#executeDateFlag").show();
		$("#executeTimeFlag").show();
		$("#weekFlag").hide();
	} else if (planType == 1 ) {
		// 每日执行
		$("#cycleDateFlag").hide();
		$("#cycleNumberFlag").hide();
		$("#executeDateFlag").hide();
		$("#executeTimeFlag").show();
		$("#weekFlag").hide();
	} else if (planType == 2 ) {
		// 周期执行
		$("#cycleDateFlag").show();
		$("#cycleNumberFlag").show();
		$("#executeDateFlag").hide();
		$("#executeTimeFlag").show();
		$("#weekFlag").hide();
	} else if (planType == 3 ) {
		// 星期执行
		$("#cycleDateFlag").hide();
		$("#cycleNumberFlag").hide();
		$("#executeDateFlag").hide();
		$("#executeTimeFlag").show();
		$("#weekFlag").show();
	} else if (planType == 4 ) {
		// 周期及星期执
		$("#cycleDateFlag").show();
		$("#cycleNumberFlag").hide();
		$("#executeDateFlag").hide();
		$("#executeTimeFlag").show();
		$("#weekFlag").show();
	}
}

function serialParams() {
	var params = $('#signupForm').serializeJSON();
	var planType = $("#planType").val();
	if (planType == 0 ) {
		// 单次执行
		params.startDate=null;
		params.endDate=null;
		params.cycleNumber=null;
		params.cycleUnit=null;
		params.mondayFlag=null;
		params.tuesdayFlag=null;
		params.wednesdayFlag=null;
		params.thursdayFlag=null;
		params.fridayFlag=null;
		params.saturdayFlag=null;
		params.sundayFlag=null;
	} else if (planType == 1 ) {
		// 每日执行
		params.startDate=null;
		params.endDate=null;
		params.cycleNumber=null;
		params.cycleUnit=null;
		params.executeDate=null;
		params.mondayFlag=null;
		params.tuesdayFlag=null;
		params.wednesdayFlag=null;
		params.thursdayFlag=null;
		params.fridayFlag=null;
		params.saturdayFlag=null;
		params.sundayFlag=null;
	} else if (planType == 2 ) {
		// 周期执行
		params.executeDate=null;
		params.mondayFlag=null;
		params.tuesdayFlag=null;
		params.wednesdayFlag=null;
		params.thursdayFlag=null;
		params.fridayFlag=null;
		params.saturdayFlag=null;
		params.sundayFlag=null;
	} else if (planType == 3 ) {
		// 星期执行
		params.startDate=null;
		params.endDate=null;
		params.cycleNumber=null;
		params.cycleUnit=null;
		params.executeDate=null;
	} else if (planType == 4 ) {
		// 周期及星期执
		params.cycleNumber=null;
		params.cycleUnit=null;
		params.executeDate=null;
	}
	return params;
}
