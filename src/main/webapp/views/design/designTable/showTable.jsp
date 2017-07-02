<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>资源信息管理</title>
    <script type="text/javascript">
    var dataGrid;
    
    $(function() {
    	var dbcommId = '${form.obj.dbconn.id}';
    	var dbName = '${form.obj.dbconn.name}';
		$("#dbId").val(dbcommId);
   	   	$('#dbId_query').combobox({
   	    	onSelect: function(record){
   	    		$("#dbId").val(record.value);
	   	     	searchTableByDBconnId(record.value);
   	    	}
   	    });
   	 	$('#dbId_query').combobox('setValue',dbcommId);
    });
    
    function searchTableByDBconnId(dbId){
    	
    	 dataGrid = $('#dataGrid').datagrid({
             url : '${path}/pages/des/dataGrid?dbId='+dbId,
             striped : true,
             rownumbers : true,
             pagination : true,
             singleSelect : true,
             idField : 'id',
             sortName : 'createDate',//id
             sortOrder : 'desc',
             fit : true,
             fitColumns : true,
             fix : false,
             autoRowHeight : false,
             pageSize : 20,
             pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
             columns: [[
            /*  { 
            	   field : 'ck', checkbox : false
            	}, */
 			{
                 width : '10%',
                 title : '主键',
                 field : 'id',
                 sortable : true,
                 align : 'center',
             	hidden : true
             } , 
 			{
                 width : '10%',
                 title : '方案名称',
                 field : 'name',
                 sortable : false,
                 align : 'center',
                 hidden : false,
             } , 
             {
                 width : '10%',
                 title : '表名称',
                 field : 'tableName',
                 sortable : false,
                 align : 'center',
                 hidden : false,
             } , 
             {
                 width : '10%',
                 title : '描述',
                 field : 'comments',
                 sortable : false,
                 align : 'center',
                 hidden : false,
             } , 
             {
                 width : '10%',
                 title : '类名称',
                 field : 'className',
                 sortable : false,
                 align : 'center',
                 hidden : false,
             } , 
             {
                 field : 'action',
                 title : '基本操作',
                 width : '25%',
                 align : 'center',
                 formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-edit" data-row="edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="edit(\'{1}\',\'{2}\');" >删除</a>', index, row.id, index);
                   	str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-del" data-row="del_{0}" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="del(\'{1}\',\'{2}\');" >删除</a>', index, row.id, index);
                   	if(row.type != '1' ){
                   		str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-edit_attr" data-row="edit_attr_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="editAttr(\'{1}\',\'{2}\');" >方案属性|查看or修改</a>', index, row.id, index);
                   		if(row.type != '2' ){
                   			str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-edit_sche" data-row="edit_sche_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="editSche(\'{1}\',\'{2}\');" >业务方案|查看or修改</a>', index, row.id, index);
                   		}
                   	}
 					return str;
                 }
             }, 
             {
                 field : 'action2',
                 title : '其他操作',
                 width : '25%',
                 align : 'center',
                 formatter : function(value, row, index) {
                     var str = '';
                     if(row.type == '1' ){
                    	 str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-attr" data-row="attr_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="attr(\'{1}\',\'{2}\');" >配置方案属性</a>', index, row.id, index);
                     }else if(row.type == '2'){
                    	 str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-sche" data-row="sche_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="sche(\'{1}\',\'{2}\');" >设置业务方案</a>', index, row.id, index); 
                     }else if(row.type == '3'){
                    	 str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-export-ftp" data-row="ftp_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="ftp(\'{1}\',\'{2}\');" >代码上传ftp服务器</a>', index, row.id, index);
                    	 str += $.formatString('<a hidden href="javascript:void(0)" class="desTable-easyui-linkbutton-export-download" data-row="download_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="download(\'{1}\',\'{2}\');" >下载代码</a>', index, row.id, index);
                     }
 					return str;
                 }
             } 
 			]],
             onLoadSuccess:function(data){
            	 $('.desTable-easyui-linkbutton-edit').linkbutton({text:'修改',plain:true,iconCls:'icon-dc-edit'});
                 $('.desTable-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-dc-del'});
//                 $('.desTable-easyui-linkbutton-edit_inf').linkbutton({text:'基本信息|查看or修改',plain:true,iconCls:'icon-dc-edit'});
                 $('.desTable-easyui-linkbutton-edit_attr').linkbutton({text:'方案属性',plain:true,iconCls:'icon-dc-edit'});
                 $('.desTable-easyui-linkbutton-edit_sche').linkbutton({text:'方案配置',plain:true,iconCls:'icon-dc-edit'});
//                 $('.desTable-easyui-linkbutton-inf').linkbutton({text:'设置基本信息',plain:true,iconCls:'icon-dc-add'});
                 $('.desTable-easyui-linkbutton-attr').linkbutton({text:'配置方案属性',plain:true,iconCls:'icon-dc-add'});
                 $('.desTable-easyui-linkbutton-sche').linkbutton({text:'设置业务方案',plain:true,iconCls:'icon-dc-ok'});
                 $('.desTable-easyui-linkbutton-export-ftp').linkbutton({text:'代码上传ftp',plain:true,iconCls:'icon-dc-import'});
                 $('.desTable-easyui-linkbutton-export-download').linkbutton({text:'下载代码',plain:true,iconCls:'icon-dc-export'});
             },
             toolbar : '#toolbar'
         });
    }
    
    function exportSql(id){
    	window.location="${path}/pages/obj/exportSql?id="+id;
    	/* $.ajax({
	        type : "get",  
	    	url : "${path}/pages/obj/exportSql",  
	    	data:{id:id},
	     }); */
    }

    function edit(id,index) {
        var url='${path}/pages/des/editPage?tableId='+id;
        openEditDialog(url, 450, 500, dataGrid, function(cid) {
			modify(cid);
		}).dialog('open').dialog('setTitle', '业务方案修改');
    }
    
    function editSche(id,index) {
        var url='${path}/pages/config/editPage?tableId='+id;
        openEditDialog(url, 450, 750, dataGrid, function(cid) {
			modify(cid);
		}).dialog('open').dialog('setTitle', '业务方案配置修改');
    }

    function del(id,index) {
    	$.messager.confirm('询问', '是否确定删除？', function(b) {
	        if (b) {
	        	progressLoad();
	        	$.post('${path}/pages/des/delete', {
	            	id : id
	            }, function(result) {
	                if (result.success) {
	                	dataGrid.datagrid('reload');
	                	easyui_alert(result.msg);
	                }else{
	                	//dataGrid.datagrid('reload');
	                	easyui_alert(result.msg);	
	                }
	                
	                progressClose();
	            }, 'JSON');
	        }
	    });
    	
    }

    function addFun() {
    	var dbId = $("#dbId").val();
    	var url="${path}/pages/des/addPage?dbId="+dbId;
    	openEditDialog(url, 450, 500, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    function attr(id,index){
    	var url="${path}/pages/column/addAttr?tableId="+id;
    	openViewDialog(url, 500, 750, dataGrid).dialog('open').dialog('setTitle', '关联');
    }
    
    function sche(id,index){
    	var url="${path}/pages/config/addConfigPage?tableId="+id;
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    function editAttr(id) {
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/column/columnListManagerByTableId?tableId='+id,
    	    title: '当前关联属性信息',    
    	    split: true
    	}); 
    
    }
    
    
    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
    
    function showField(id,index) {
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/field/fieldListManagerByObjectId?objectId='+id,
    	    title: '表字段信息',    
    	    split: true
    	}); 
    }    
    
    </script>
</head>
<body>	
    <div id="ListLay" class="easyui-layout" data-options="fit:true,border:true,title:'岗位列表'" >
    	<!-- 中部区域重新布局 -->
        <div data-options="region:'center',border:false"  >
        	<p>
	    		<b>选择链接：</b>
	    		<input id="dbId" name="dbId" type="hidden" />
	    		<select id="dbId_query" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px'">
	    			<c:forEach items="${dbconnList}" var="dbconn">
						<option value="${dbconn.id}">${dbconn.name}</option>
					</c:forEach>
				 </select>
  			</p>
             <div class="easyui-layout" data-options="fit:true,border:true,title:'岗位列表'" >
             	
               	<div data-options="region:'center',border:false"  style="overflow: hidden;">
		            <table id="dataGrid" data-options="fit:true,border:false"></table>
		        </div>
             </div>
        </div>
   </div>
   <div id="toolbar" style="display: none;">
	<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">添加方案</a>
   </div>
</body>
</html>