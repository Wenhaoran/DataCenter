package cn.digitalpublishing.springmvc.controller.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.export.DataExport;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.imp.ImportRoleForm;
import cn.digitalpublishing.util.DateUtils;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.FileUtils;
import cn.digitalpublishing.util.FreeMarkers;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/pages/export")
public class DataExportController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, ImportRoleForm form){
		String forwardString = "export/data/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setIsSuccess(SUCCESS);
		} catch (Exception e) {
			form.setIsSuccess(FAILURE);
			form.setMsg((e instanceof DesignException) ? ((DesignException) e).getMessage() : e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGrid(HttpServletRequest request, DataExport export,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(export.getCode())){
				condition.put("code", export.getCode());
			}
			if(!Strings.isNullOrEmpty(export.getName())){
				condition.put("name", export.getName());
			}
			pageInfo.setCondition(condition);
			dataExportService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
    @RequestMapping("/readSQL")
    public ModelAndView readSQL() {
    	String forward = "export/data/readSQL";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("status", 1);
    		List<DeDBConnect> connList =deDBConnectService.getDBConnectList(condition); 
    		List<DataExport> exportList = dataExportService.getDataExportList(null);
    		
			model.put("connList", connList);
			model.put("exportList", exportList);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model); 
    }
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, DataExport export) throws Exception {
    	String forward = "export/data/add";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		model.put("sql", export.getSql());
    		model.put("dbId", export.getDbId());
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,DataExport export){
    	try {
    		export.setStatus(0);
    		export.setCreateId((String) request.getSession().getAttribute("accountId"));
    		dataExportService.insertDataExport(export);
   		} catch(Exception e) {
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping(value="/viewReportPage", method = RequestMethod.GET)   //, method = RequestMethod.POST
    public ModelAndView viewReportPage(HttpServletRequest request, DataExport export) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
    	String forward = "";
    	String projectPath = getServletContext().getRealPath("/");
    	try{
    		if(!Strings.isNullOrEmpty(export.getId())){
    			DataExport exportDB = dataExportService.getDataExportById(export.getId());
    			if(exportDB!=null){
    				export = exportDB;
    			}
    		}else{
    			export.setId(UUID.randomUUID().toString().replace("-", ""));
    		}
    		
		    DeDBConnect conn = deDBConnectService.getDBConnectById(export.getDbId());
		    String sql = export.getSql();
		    if(sql.toLowerCase().indexOf("drop")!=-1||sql.toLowerCase().indexOf("truncate")!=-1||sql.toLowerCase().indexOf("insert")!=-1||sql.toLowerCase().indexOf("update")!=-1||sql.toLowerCase().indexOf("delete")!=-1){
		    	throw new Exception("SQL语句中只允许出现select查询语句！");
		    }
	        List<Map<String, Object>> _list = DBUtil.execute(conn,export.getSql());
	        if(_list.size()==0){
	        	model.put("errInfo", "当前查询结果集为空");
	        	forward = "templateJsp/exportError";
	    		return new ModelAndView(forward, model);
	        }
	        StringBuffer strs = new StringBuffer("");
	        if(_list!=null&&_list.size()>0){
	        	Map<String, Object> map = _list.get(0);
	        	map.remove("R");
	        	int size = map.size();
	        	int width = 0;
	        	if(size<=10){
	        		width = 95/size;
	        	}else{
	        		width =10;
	        	}
	        	
	        	for(Entry<String,Object> entry : map.entrySet()){
	        		strs.append("{field:'").append(entry.getKey()).
	        		append("',title:'").append(entry.getKey()).
	        		append("',width:'"+width+"%',sortable:true},");	
	        	}
	        }
	        
	        strs.append("");
	    	String columnJson = strs.toString();  
	    	columnJson = columnJson.substring(0,columnJson.lastIndexOf(","));
	        // 获取文件路径
	 		String separator = File.separator;
	 		String classPath = new DefaultResourceLoader().getResource("").getFile().getPath();
	 		String templatePath = classPath.replace("classes","classes"+separator+"cn"+separator+"digitalpublishing"+separator+"template");
	 		
	 		// 代码模板配置
	 		Configuration cfg = new Configuration();
	 		cfg.setDefaultEncoding("UTF-8");
	 		String _templatePath = templatePath.replace("modules", "generate"+separator+"template");
	 		cfg.setDirectoryForTemplateLoading(new File(_templatePath));
	 		
	 		// 定义模板变量
	 		Map<String, String> models = Maps.newHashMap();
	 		models.put("columns", columnJson);
	 		Template template = cfg.getTemplate("sql_report_format_list.ftl");
	 		template.setEncoding("UTF-8");
	 		String content = FreeMarkers.renderTemplate(template, models);
	 		//命名规则，templateJsp/admin_id_list.jsp
	 		String filePath = projectPath+"views"+separator+"templateJsp"+separator+"_"+export.getId()+"_list.jsp";
	 		FileUtils.writeFile(content, filePath);
	 		model.put("exportId", export.getId());
	 		model.put("sql", export.getSql());
	 		model.put("dbId", export.getDbId());
	 		forward = "templateJsp/_"+export.getId()+"_list";
	 		return new ModelAndView(forward, model);	
    	}catch(MySQLSyntaxErrorException e){
    		e.printStackTrace();
    		model.put("errInfo", "当前查询有误，可能sql 语法不对 或者 表不存在。");
    		forward = "templateJsp/exportError";
    		return new ModelAndView(forward, model);	
    	}catch(SQLException e){
    		e.printStackTrace();
    		model.put("errInfo", "当前查询有误，可能sql 语法不对 或者 表不存在。");
    		forward = "templateJsp/exportError";
    		return new ModelAndView(forward, model);	
    	}catch(Exception e){
    		e.printStackTrace();
    		model.put("errInfo", e.getMessage());
    		forward = "templateJsp/exportError";
    		return new ModelAndView(forward, model);	
    	}
    }
    
    
    @RequestMapping(value = "/reportDataGrid", method = RequestMethod.POST)
    @ResponseBody
    public Object reportDataGrid(HttpServletRequest request,DataExport export,Integer page, Integer rows, String sort, String order) {
    	PageInfo pageInfo = new PageInfo(page, rows);        
    	try{        
    		if(!Strings.isNullOrEmpty(export.getId())){
    			DataExport exportDB = dataExportService.getDataExportById(export.getId());
    			if(exportDB!=null){
    				export = exportDB;
    			}
    		}
    		String sqlStr = export.getSql();
    		if(Strings.isNullOrEmpty(sqlStr)){
    			pageInfo.setTotal(0);
    			pageInfo.setSize(0);
    			pageInfo.setRows(null);
    			return pageInfo;
    		}
    		sqlStr = sqlStr + "";
	        Map<String, Object> condition = new HashMap<String, Object>();	        
	        condition.put("sql", sqlStr);
	        pageInfo.setCondition(condition);
	        DeDBConnect conn = deDBConnectService.getDBConnectById(export.getDbId());
	        List<Map<String, Object>> _list = DBUtil.execute(conn,export.getSql());
	        pageInfo.setRows(_list);
	        //pageInfo.setTotal(_list.size());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return pageInfo;
    }
    
    
    @RequestMapping(value = "/getReportSql", method = RequestMethod.POST)
    @ResponseBody
    public Object getReportSql(HttpServletRequest request,String exportId) {
    	try{    		 
    		DataExport export = dataExportService.getDataExportById(exportId);
		    return renderSuccess(export.getSql()); 
    	}catch(Exception e){
    		e.printStackTrace();
    		return renderError(e.getMessage());
    	}
    	
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) throws Exception {
    	try {
    		dataExportService.deleteDataExportById(id);
        } catch(Exception e) {
        	e.printStackTrace();
        	return renderError(e.getMessage());
    	}
        return renderSuccess("删除成功！");
    }
    
    
    @RequestMapping(value = "/exportReport")
    public void exportReport(HttpServletRequest request,HttpServletResponse response,DataExport export) {
    	try{    		 
    		if(!Strings.isNullOrEmpty(export.getId())){
    			DataExport exportDB = dataExportService.getDataExportById(export.getId());
    			if(exportDB!=null){
    				export = exportDB;
    			}
    		}
		    DeDBConnect conn = deDBConnectService.getDBConnectById(export.getDbId());
		     List<Map<String, Object>> list = DBUtil.execute(conn,export.getSql());
		     
		     List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		     if(list!=null&&list.size()>0){
		    	 Map<String, Object> map = list.get(0);
		    	 for(Entry<String,Object> ent : map.entrySet()){
		    		 entity.add(new ExcelExportEntity(ent.getKey().toString(),ent.getKey()));
		    	 }
		     }
		     String name = "";
		    if(!Strings.isNullOrEmpty(export.getName())){
		    	name = export.getName().equals("")?"报表导出":export.getName()+"_";
		    }else{
		    	name = "打印报表_";
		    }
		    
	        HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(new ExportParams(name,"报表"), entity, list); 
	        FileOutputStream fos;
	        String filename = name+""+DateUtils.getDate("yyyyMMddHHmmss")+".xls";
	    	File dp = new File(filename);
	    	
			fos = new FileOutputStream(dp);
			workbook.write(fos);
	        fos.close();
	        
	        long fileLength = dp.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length);
			FileInputStream input = null;
			ServletOutputStream output = null;
			response.setHeader("Content-disposition", "attachment; filename="+ filename);// 组装附件名称和格式
			try {
				input = new FileInputStream(dp);
				output = response.getOutputStream();
				byte[] block = new byte[1024];
				int len = 0;
				while ((len = input.read(block)) != -1) {
					output.write(block, 0, len);
				}
			} catch (Exception e) {
				e.getMessage();
			} finally {
				input.close();
				output.flush();
				output.close();
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,String exportId) throws Exception {
		String jsonarray = "";
		try {
			DataExport export = dataExportService.getDataExportById(exportId);
			DeDBConnect db = deDBConnectService.getDBConnectById(export.getDbId());
			
			List<String> pageList = DBUtil.executeSqlReturnColumnList(db, export.getSql());
			List<Map<String,String>> returnDataList = new ArrayList<Map<String,String>>();
			
			for(String column :pageList){
				Map<String,String> map = new HashMap<String,String>();
				map.put("name", column);
				returnDataList.add(map);
			}
			
			jsonarray = JSONArray.toJSONString(returnDataList);
//			System.out.println(jsonarray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    
    
}