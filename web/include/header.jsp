<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/fore/style.css"  rel="stylesheet">
<script type="text/javascript">
function formatMoney(num){
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
		num = 0;
	sign = (num == (num = Math.abs(num))); 
	num = Math.floor(num*100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num/100).toString();
	if(cents<10)
		cents= "0" + cents;
	for(var i = 0; i < Math.floor((num.length - (1 + i))/3); i++)
		num = num.substring(0,num.length-(4*i+3))+',' + num.substring(num.length-(4*i+3));
	return (((sign)?'':"-") + num + '.' + cents);
}

function checkEmpty(id,name){
	var value = $("#" + id).val();
	if(value.length == 0){
		$("#" + id)[0].focus();
		return false;
	}
	return true;
}

$(function(){
	
	$("a.productDetailTopReviewLink").click(function(){
		$("div.productReviewDiv").show();
		$("div.productDetailDiv").hide();
	});
	
	$("a.productDetailTopPartSelectLink").click(function(){
		$("div.productReviewDiv").show();
		$("div.productDetailDiv").hide();
	});
	
	$("span.leaveMessageTextareaSpan").hide();
	
	$("img.leaveMessageImg").click(function(){
		$(this).hide;
		$("span.leaveMessageTextareaSpan").show();
		$("div.orderItemSumDiv").css("height","100px");
	});
	
	$("div#footer a[href$=nowhere]").click(function(){
		alert("模仿天猫的连接，并没有跳转到实际的页面");
	});
	
	$("a.wangwanglink").click(function(){
		alert("暂时做不出一个阿里旺旺");
	});
	
	$("a.notImplementLink").click(function(){
		alert("现在不懂做这个功能");
	});
	
});
</script>
</head>
<body>

</body>
</html>