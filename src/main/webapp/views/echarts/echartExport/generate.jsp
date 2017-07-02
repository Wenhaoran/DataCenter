<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript" src="${staticPath}/views/echarts/base/${echartCode}.js" charset="utf-8"></script>
<%-- <script type="text/javascript" src="${staticPath }/static/echarts3/echarts.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/echarts3/macarons.js" charset="utf-8"></script> --%>
<script type="text/javascript">
$(function() {
	
	var mustStr = '${mustJson}';
	var mustJson = JSON.parse(mustStr);
	
	var jsonStr = '${jsonData}';
	console.log(jsonStr);
	var jsonData = JSON.parse(jsonStr);
	console.log(jsonData[0].one);
	console.log(jsonData[1].two);
	var option = getOption(mustJson,jsonData);
	//console.log(option);
	var dom = document.getElementById('myChart');
	var myChart = echarts.init(dom);
	
    myChart.setOption(option); 
    
});

</script>

<div id="myChart" style="width: 800px;height:400px;">

</div>