<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>资源信息管理</title>
    <script type="text/javascript">
    var treeGrid;
    var organizationTree;
    
    $(function() {
    	var dbcommId = '${form.obj.dbconn.id}';
    	var dbName = '${form.obj.dbconn.name}';
		$("#dbId").val(dbcommId);
   	   	$('#dbId_query').combobox({
   	    	onSelect: function(record){
   	    		$("#dbId").val(record.value);
	   	    	searchObjectTreeByDBconnId(record.value);
	   	     	searchObjectByDBconnId(record.value);
   	    	}
   	    });
   	 	$('#dbId_query').combobox('setValue',dbcommId);
    });
    
    function searchObjectTreeByDBconnId(dbId){
    	organizationTree = $('#organizationTree').tree({
            url : '${path }/pages/obj/tree',
            queryParams: {
            	dbId: dbId,
            	islist:false
        	},
        	method : "post",
            parentField : 'pId',
            textField : 'name',
            lines : true,
            onClick : function(node) {
            	//alert(node);
            }
        });
    }
    
    function searchObjectByDBconnId(dbId){
    	
    	treeGrid = $('#treeGrid').treegrid({
            url : '${path }/pages/obj/findObjectByDBId?dbId='+dbId,
            idField : 'id',
            rownumbers : true,
            treeField : 'name',
            parentField : 'pId',
            fit : true,
            autoRowHeight :false,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {title : '编号',field : 'code',width : 80} ] ],
            columns : [ [ 
                {field : 'name',title : '目录名称',width : '13%'}, 
                {field : 'description',title : '描述',width : '13%'}, 
                /* {field : 'pid',title : '上级资源ID',width : '20%'}, */ 
                {field : 'tableName',title : '表名称',width : '13%',
                	formatter : function(value, row, index) {
			              if(value ==""||value ==0){
			            	  return "";
			              }else{
			            	  return value;
			              }
			          }
                },
                {field : 'type',title : '类型',width : '6%',
			          formatter : function(value, row, index) {
			              switch (value) {
			              case 1:
			                  return '目录';
			              case 2:
			                  return '<b style="color: red;">对象</b>';
			              }
			          }
			  },
  			  	{field : 'action',title : '基本操作',width : '19%',
		          formatter : function(value, row, index) {
		              var str = '';
		                      str += $.formatString('<a href="javascript:void(0)" class="object-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
		                      str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
		                      str += $.formatString('<a href="javascript:void(0)" class="object-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
		              return str;
		          }
  			  	},
  			  	{field : 'action2',title : '其他操作',width : '25%',
  		          formatter : function(value, row, index) {
  		              var str = '';
  		            	str += $.formatString('<a href="javascript:void(0)" class="object-easyui-linkbutton-export" data-options="plain:true,iconCls:\'icon-dc-list\'" onclick="exportSql(\'{0}\');" >导出建表sql</a>', row.id);
	                      	if(row.type != 1 ){
	                    	  	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
								str += $.formatString('<a href="javascript:void(0)" data-row="view_{0}" class="object-easyui-linkbutton-view" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="showField(\'{1}\',\'{2}\');" >查看表字段</a>', index, row.id, index);
								/* if(row.status == 0 ){
		                    	  	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
									str += $.formatString('<a href="javascript:void(0)" data-row="create_{0}" class="object-easyui-linkbutton-create" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="createTable(\'{1}\',\'{2}\');" >创建对象表</a>', index, row.id, index);
								} */
							}
	                      
  		              return str;
  		          }
    			  	}]],
  			  	
            onLoadSuccess:function(data){
            	$('#treeGrid').treegrid('collapseAll');
                $('.object-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-dc-edit'});
                $('.object-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-dc-del'});
                $('.object-easyui-linkbutton-export').linkbutton({text:'导出建表SQL',plain:true,iconCls:'icon-dc-export'});
                $('.object-easyui-linkbutton-view').linkbutton({text:'查看字段',plain:true,iconCls:'icon-dc-view'});
                $('.object-easyui-linkbutton-create').linkbutton({text:'创建表',plain:true,iconCls:'icon-dc-save'});
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

	function editFun(id) {
        var url='${path}/pages/obj/editPage?id=' + id;
        openEditTreeDialog(url, 450, 500, treeGrid, function(cid) {
			modify(cid);
		}).dialog('open').dialog('setTitle', '修改');
    }

    function deleteFun(id) {
    	$.messager.confirm('询问', '是否确定删除？', function(b) {
	        if (b) {
	        	progressLoad();
	        	$.post('${path}/pages/obj/delete', {
	            	id : id
	            }, function(result) {
	                if (result.success) {
	                	treeGrid.treegrid('reload');
	                	organizationTree.tree('reload');
	                	easyui_alert(result.msg);
	                }else{
	                	easyui_alert(result.msg);	
	                }
	                progressClose();
	            }, 'JSON');
	        }
	    });
    }

    function addFun(type) {
    	var dbId = $("#dbId").val();
    	var url="${path}/pages/obj/addPage?dbId="+dbId+"&type="+type;
    	openEditTreeDialogT(url, 450, 500, treeGrid,organizationTree, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    
    function searchFun() {
    	treeGrid.treegrid('load', $.serializeObject($('#searchForm')));
    }
    
    function cleanFun() {
        $('#searchForm input').val('');
        treeGrid.treegrid('load', {});
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
             <div class="easyui-layout" data-options="fit:true,border:true,title:'岗位列表'" >
             	
               	<div data-options="region:'center',border:false"  style="overflow: hidden;">
		            <table id="treeGrid"></table>
		        </div>
             </div>
            
        </div>
        <div data-options="region:'west',border:true,split:false,title:'数据库链接'"  style="width:240px;overflow: true; ">
        	<p>
	    		<b>选择链接：</b>
	    		<input id="dbId" name="dbId" type="hidden" />
	    		<select id="dbId_query" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px'">
	    			<c:forEach items="${dbconnList}" var="dbconn">
						<option value="${dbconn.id}">${dbconn.name}</option>
					</c:forEach>
				 </select>
  			</p>
		    <ul id="organizationTree"  style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
    </div>
    <div id="toolbar" style="display: none;">
		<a onclick="addFun(1);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">添加目录</a>
		<a onclick="addFun(2);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">添加对象</a>
    </div>
</body>
</html>