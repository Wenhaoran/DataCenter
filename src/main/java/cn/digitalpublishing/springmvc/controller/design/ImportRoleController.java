package cn.digitalpublishing.springmvc.controller.design;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;

import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.imp.ImportRoleForm;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import quartz.cn.digitalpublishing.model.ScheduleJob;

@Controller
@RequestMapping("/pages/importRole")
public class ImportRoleController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, ImportRoleForm form){
		//Map<String,String> accountInfoMap = (Map<String, String>) request.getSession().getAttribute("pt_accountInfo");
		String forwardString = "import/role/list";
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
	public Object dataGrid(HttpServletRequest request, ImportRole role,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(role.getCode())){
				condition.put("code", role.getCode());
			}
			if(!Strings.isNullOrEmpty(role.getName())){
				condition.put("name", role.getName());
			}
			pageInfo.setCondition(condition);
			importRoleService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request,ImportRole role){
    	String forward = "import/role/add";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		Map<String, Object> condition = new HashMap<String, Object>();
			List<FTPConnect> ftpList = ftpConnectService.getFTPConnectList(condition);
			model.put("ftpList", ftpList);
			model.put("type", role.getType());
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,ImportRole role){
    	try {
    		role.setCode("0");//code 字段，留作备用暂时不插入值
    		if(role.getType().equals("txt")){
    			TxtObject obj = txtObjectService.getTxtObjectById(role.getObjId());
    			role.setObjName(obj.getName());
    		}else{
    			XlsObject obj = xlsObjectService.getXlsObjectById(role.getObjId());
    			role.setObjName(obj.getName());
    		}
    		role.setImportType("0");
    		role.setStatus(0);
    		role.setCreateId((String) request.getSession().getAttribute("accountId"));
    		importRoleService.insertImportRole(role);
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping("/changeStatus")
    @ResponseBody
    public Object changeStatus(HttpServletRequest request,String roleId) throws Exception {
    	
    	ImportRole role = importRoleService.getImportRoleById(roleId);
    	if(role.getStatus()==0){
    		//如果设置当前 规则可用，则 ，当前对象  的所有规则，只有这个 可用，其他规则，设置不可用状态
    		role.setStatus(1);
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("objId", role.getObjId());
    		condition.put("status", 1);
    		List<ImportRole> roleList = importRoleService.getImportRoleList(condition);
    		if(roleList!=null&&roleList.size()>0){
    			ImportRole roleOK = roleList.get(0);
    			roleOK.setStatus(0);
    			importRoleService.updateImportRole(roleOK);
    		}
    	}else{
    		role.setStatus(0);
    	}
    	importRoleService.updateImportRole(role);
    	return renderSuccess("更改成功！");
    }
    
    
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,String type){
		String jsonarray = "";
		Map<String, Object> condition = new HashMap<String, Object>();
		try {
			
			if(type.equals("txt")){
				List<XlsObject> pageList = new ArrayList<>();
				pageList = xlsObjectService.getXlsObjectList(condition);
				jsonarray = JSONArray.toJSONString(pageList);
			}else{
				List<TxtObject> pageList = new ArrayList<>();
				pageList = txtObjectService.getTxtObjectList(condition);
				jsonarray = JSONArray.toJSONString(pageList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    @RequestMapping("/mappedOK")
    @ResponseBody
    public Object mappedOK(HttpServletRequest request,String roleId) throws Exception {
    	
    	ImportRole role = importRoleService.getImportRoleById(roleId);
    	String createId = (String) request.getSession().getAttribute("accountId");
    	importRTMappedService.createSql(roleId,createId);
    	
    	if(role.getImportType().equals("1")){ //已经配置 数据表，但是，映射未配置
			role.setImportType("2");
		}else if(role.getImportType().equals("4")){//数据表 已配置，映射 也已经配置
			role.setImportType("3");
		}
    	
    	importRoleService.updateImportRole(role);
    	return renderSuccess("更改成功！");
    }
    
    @RequestMapping(value = "/taskPage", method = RequestMethod.GET)
    public ModelAndView taskPage(HttpServletRequest request,String roleId){
    	String forward = "import/role/taskadd";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		ImportRole role = importRoleService.getImportRoleById(roleId);
			model.put("role", role);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }

    @RequestMapping(value = "/taskSave", method = RequestMethod.POST)
    @ResponseBody
    public Object taskSave(HttpServletRequest request,ImportRole role){
    	try {
    		String cornTask = role.getCronTask();
    		role = importRoleService.getImportRoleById(role.getId());
    		if(role.getImportType().equals("1")){ //已经配置 数据表，但是，映射未配置
    			role.setImportType("4");
    		}else if(role.getImportType().equals("2")){//数据表 已配置，映射 也已经配置
    			role.setImportType("3");
    		}
    		role.setCronTask(cornTask);
    		role.setUpdateId((String) request.getSession().getAttribute("accountId"));
    		role.setUpdateDate(new Date());
    		importRoleService.updateImportRole(role);
    		ScheduleJob  job = scheduleJobService.getScheduleJobByRoleId(role.getId());
    		if(job!=null){
    			job.setCronExpression(role.getCronTask());
    			scheduleJobService.update(job);
    		}
   		} catch(Exception e) {
			logger.error("设置出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("设置成功！");
    }
    
    
    @RequestMapping("/setCRON")
    @ResponseBody
    public Object setCRON(HttpServletRequest request,String roleId){
    	try {
    		ImportRole role = importRoleService.getImportRoleById(roleId);
    		ScheduleJob  job = scheduleJobService.getScheduleJobByRoleId(role.getId());
        	if(job == null){
            	String url = "http://127.0.0.1:8080/design/pages/import/index?roleId="+roleId;
            	job = getScheduleJob(role,url);
            	scheduleJobService.insert(job);
        	}else{
        		scheduleJobService.resumeJob(role.getId());
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("启动成功！");
		}
    	
    	return renderSuccess("启动成功！");
    }
    
    @RequestMapping("/pause")
    @ResponseBody
    public Object pause(HttpServletRequest request,String roleId){
    	try {
    		ImportRole role = importRoleService.getImportRoleById(roleId);
    		ScheduleJob  job = scheduleJobService.getScheduleJobByRoleId(role.getId());
    		if(job!=null){
    			scheduleJobService.pauseJob(roleId);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("启动成功！");
		}
    	
    	return renderSuccess("启动成功！");
    }
    
    private ScheduleJob getScheduleJob(ImportRole role ,String url){
    	ScheduleJob  job = new ScheduleJob();
    	job.setAliasName(role.getId());
    	job.setJobName(role.getName());
    	job.setJobGroup(role.getType());
    	job.setCronExpression(role.getCronTask());
    	if(!Strings.isNullOrEmpty(url)){
    		job.setUrl(url);
    	}
    	job.setIsSync(false);
    	return job;
    }
}