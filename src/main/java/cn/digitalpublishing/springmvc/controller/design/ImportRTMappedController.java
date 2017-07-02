package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.po.imp.ImportRTMapped;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.po.imp.ImportRoleDetail;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

@Controller
@RequestMapping("/pages/importRt")
public class ImportRTMappedController extends BaseController {

	@RequestMapping(value = "/fieldListManagerByRoleId", method = RequestMethod.GET)
    public ModelAndView accountGiveRole(HttpServletRequest request, String roleId) {
    	String forward = "import/roleRT/detailList";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		ImportRole role = importRoleService.getImportRoleById(roleId);
    		model.put("role", role);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    	}
    	return new ModelAndView(forward, model);
    }
	
  	@RequestMapping(value = "/dataGridByRoleId", method = RequestMethod.POST)
  	@ResponseBody
  	public Object dataGridByModuleId(HttpServletRequest request,ImportRTMapped rt,Integer page, Integer rows, String sort, String order) {
  		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
  		String roleId = request.getParameter("roleId");
  		try {
  			Map<String, Object> condition = new HashMap<String, Object>();
  			//当前 角色ID
  			if(!Strings.isNullOrEmpty(roleId)){
  				condition.put("roleId", roleId);
  			}
  			//查询条件：
  			if(!Strings.isNullOrEmpty(rt.getCode())){
  				condition.put("code", rt.getCode());
  			}
  			pageInfo.setCondition(condition);
  			importRTMappedService.findDataGrid(pageInfo);
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("查询列表出现异常，", e);
  		}
  		return pageInfo;
  	}
  	
  	@RequestMapping(value = "/viewSql", method = RequestMethod.GET)
	public ModelAndView viewSql(HttpServletRequest request,String rtId)throws Exception {
		String forward = "import/roleRT/viewSql";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ImportRTMapped rt = importRTMappedService.getImportRTMappedById(rtId);
			model.put("rt", rt);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
  	
  	@RequestMapping(value = "/updateSql", method = RequestMethod.POST)
	@ResponseBody
	public Object updateSql(HttpServletRequest request,ImportRTMapped rt) throws Exception {
		try {
			importRTMappedService.updateImportRTMapped(rt);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("更新成功！");
	}
  	
  	@RequestMapping(value = "/viewParam", method = RequestMethod.GET)
	public ModelAndView viewParam(HttpServletRequest request,String rtId)throws Exception {
		String forward = "import/roleRT/viewParam";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ImportRTMapped rt = importRTMappedService.getImportRTMappedById(rtId);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("rtId", rtId);
			List<ImportRoleDetail> roleDetailList = importRoleDetailService.getImportRoleDetailList(condition);
			model.put("roleDetailList", roleDetailList);
			model.put("rt", rt);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
  	
  	@RequestMapping(value = "/dataGridByRtId", method = RequestMethod.POST)
  	@ResponseBody
  	public Object dataGridByRtId(HttpServletRequest request,String rtId,Integer page, Integer rows, String sort, String order) {
  		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
  		try {
  			Map<String, Object> condition = new HashMap<String, Object>();
  			//当前 角色ID
  			if(!Strings.isNullOrEmpty(rtId)){
  				condition.put("rtId", rtId);
  			}
  			//查询条件：
  			pageInfo.setCondition(condition);
  			importRoleDetailService.findDataGrid(pageInfo);
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("查询列表出现异常，", e);
  		}
  		return pageInfo;
  	}
  	
  	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request,ImportRTMapped rt)throws Exception {
		String forward = "import/roleRT/add";
		Map<String, Object> model = new HashMap<String, Object>();
		String roleId = request.getParameter("roleId");
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("status", 1);
			List<DeDBConnect> connList = deDBConnectService.getDBConnectList(condition);
			ImportRole role = importRoleService.getImportRoleById(roleId);
			model.put("connList", connList);
			model.put("role", role);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
  	
  	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request,ImportRTMapped rt) throws Exception {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("dbId", rt.getDbId());
			condition.put("dbTable", rt.getDbTable());
			condition.put("roleId", rt.getImportRoleId());
			List<ImportRTMapped> ifRT = importRTMappedService.getImportRTMappedList(condition);
			if(!(ifRT!=null&&ifRT.size()>0)){
				rt.setInsertSort("0");
				rt.setCreateId((String)request.getSession().getAttribute("accountId"));
				rt.setCreateDate(new Date());
				rt.setStatus(0);
				importRTMappedService.insertImportRTMapped(rt);
				
				ImportRole role = importRoleService.getImportRoleById(rt.getImportRoleId());
				role.setImportType("1");
				importRoleService.updateImportRole(role);
			}
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,String[] ids) throws Exception {
		try {
			importRTMappedService.deleteListByIds(ids);
			
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
	
	@RequestMapping(value = "/getTableColumnList", method = RequestMethod.POST)
	@ResponseBody
	public String getTableColumnList(HttpServletRequest request,String rtId){
		String jsonarray = "";
		try {
			ImportRTMapped rt = importRTMappedService.getImportRTMappedById(rtId);
			DeDBConnect conn = deDBConnectService.getDBConnectById(rt.getDbId());
			DesignTable table = new DesignTable();
			table.setTableName(rt.getDbTable());
			table.setDbconn(conn);
			List<DesignTableColumn> tableColumnList = DBUtil.getColumnList(table);
			jsonarray = JSONArray.toJSONString(tableColumnList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
    
}