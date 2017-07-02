<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">

	var dataGrid2;
    $(function() {     	
    	 dataGrid2 = $('#dataGrid2').datagrid({
             url : '${path}/common/showColumnDataGrid?beanName='+$("#beanName").val()+"&type=show",
             striped : true,
             rownumbers : true,
             pagination : false,
             singleSelect : false,
             selectOnCheck: true,
             checkOnSelect: true,
             idField : 'fieldName',//很重要，
             sortName : '',
             sortOrder : 'asc',
             fit : true,                       
             columns: [[
             {
            	   width : '100',
             	   field:'ck',
             	   checkbox:true
             } /* , {
                 width : '120',
                 title : '列字段名称',
                 field : 'fieldName',
                 sortable : true
             }  */, {
                 width : '220',
                 title : '列属性名称',
                 field : 'propName',
                 sortable : true
             }/* , {
                 width : '60',
                 title : '展示长度',
                 field : 'propLength',
                 sortable : true
             } , {
                 width : '50',
                 title : '字典值',
                 field : 'dicFlag',
                 sortable : true,
                 formatter : function(value, row, index) {
                     if(value=="Y") {                     	
                         return '是';
                     }else{
                         return '否';
                     }
                 }
             }  */] ],
             onLoadSuccess:function(data){
                
             },
             toolbar : '#toolbars'
         });    	
    });
    
    
   
    function submitData(cid){
    	
		var checkedItems = dataGrid2.datagrid('getChecked');
    	//alert(checkedItems.length);
    	var fns = [];//字段名称
    	var pns = [];//属性名称
    	var pls = [];//长度
    	var idc = [];
    	$.each(checkedItems, function(index, item){    
    		fns.push(item.fieldName);
    		pns.push(item.propName);
    		pls.push(item.propLength);
    		idc.push(item.dicFlag);
    	});   
    	if(fns.length==0){
    		easyui_alert("请选择要展示的列！");
    		return false;
    	}
    	//alert(pls);
    	var url ='${path}/common/setupSelect';
    	//alert(url);
    	//提交
         $.ajax({ 
            url : url,            
            type: "POST", 
            data:{'fns':fns,'pns':pns,'pls':pls,'idc':idc,'beanName':$("#beanName").val(),'ftlName':$("#ftlName").val()},
            async: false,             
            dataType: "json",              
            cache: false,  
            success : function(result) {
                progressClose();                
                if (result.success) {                	
                	$("#"+cid).dialog("close");
	   				$("#"+cid).dialog("destroy");     				
                } else {
                	easyui_error(result.msg);
                }
            }
        });      
    }
    
</script>
<div class="easyui-layout" data-options="fit:true,border:true,title:'属性列表'">
	<input type="hidden" id="beanName" value="${beanName}"/>
	<input type="hidden" id="ftlName" value="${ftlName}"/>
  	<div data-options="region:'center',border:false">
      	<table id="dataGrid2" data-options="fit:true,border:false"></table>
  	</div>
</div>