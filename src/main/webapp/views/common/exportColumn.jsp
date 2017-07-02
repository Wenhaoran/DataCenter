<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">

	var dataGrid3;
    $(function() {     	
    	 dataGrid3 = $('#dataGrid3').datagrid({
             url : '${path}/common/showColumnDataGrid?beanName='+$("#beanName").val()+"&type='export'",
             striped : true,
             rownumbers : true,
             pagination : false,
             singleSelect : false,
             selectOnCheck: true,
             checkOnSelect: false,
             idField : 'fieldName',//很重要，
             sortName : '',
             sortOrder : 'asc',
             fit : true,                       
             columns: [[
             { 
             	field:'ck',
             	checkbox:true
             },{
                 width : '150',
                 title : '列属性名称',
                 field : 'propName',
                 sortable : false
             },{
          	   width : '150',
               title : '是否必导项',
               field : 'isNull',
               sortable : false,
               hidden : false,
               formatter : function(value, row, index) {
                   if(value=="false") {                     	
                       return '是';
                   }else{
                       return '否';
                   }
               }
         }] ],
             onLoadSuccess:function(data){
            	  var allrows = dataGrid3.datagrid('getRows');
            	  for(var i=0;i<allrows.length;i++){            		 
            		 //必须导出
            		 if(allrows[i].isNull=='false'){            			 
            		 	 dataGrid3.datagrid('checkRow',i);
            		 	 $('#zz input:checkbox').eq(i+1).attr("disabled",'disabled');
            		 }
            	 } 
             },
             toolbar : '#toolbars'
         });    	
    });
    
    
   
    function submitData(cid){
    	progressLoad();
		var checkedItems = dataGrid3.datagrid('getChecked');
    	//alert(checkedItems.length);
    	var fns = [];//字段名称
    	var pns = [];//属性名称
    	//var pls = [];//长度
    	//var idc = [];//是否为字典
    	//var dcc = [];//字典代码
    	$.each(checkedItems, function(index, item){    
    		fns.push(item.fieldName);
    		pns.push(item.propName);
    		//pls.push(item.propLength);
    		//idc.push(item.dicFlag);
    		//dcc.push(item.dicClassCode);
    	});   
    	if(fns.length==0){
    		easyui_alert("请选择要导出的列！");
    		progressClose(); 
    		return ;
    	}
    	var url ='${path }/common/exportSelectCls';
    	
    	//提交
         $.ajax({ 
            url : url,            
            type: "POST",  
            async: false,  
            data:{'fns':fns,'pns':pns,'beanName':$("#beanName").val(),'ids':$("#ids").val(),'isCommon':$("#isCommon").val()},            
            dataType: "json",              
            cache: false,  
            success : function(result) {
                progressClose();                
                if (result.success) {  
                	$("#"+cid).dialog("close");
	   				$("#"+cid).dialog("destroy");     
                    window.location.href = basePath+'/common/download?filename='+result.msg;
                } else {
                	easyui_error(result.msg);
                }
            }
        });      
    }
    
</script>
<div class="easyui-layout" data-options="fit:true,border:true,title:'属性列表'" >
	<input type="hidden" id="beanName" value="${beanName}"/>
	<input type="hidden" id="ids" value="${ids}"/>
	<input type="hidden" id="isCommon" value="${isCommon}"/>
   	<div data-options="region:'center',border:false"  id="zz">
       	<table id="dataGrid3" data-options="fit:true,border:false"></table>
   	</div>
</div>