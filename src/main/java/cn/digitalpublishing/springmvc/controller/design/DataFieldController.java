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

import cn.digitalpublishing.po.design.DataField;
import cn.digitalpublishing.po.design.DataObject;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;

@Controller
@RequestMapping("/pages/field")
public class DataFieldController extends BaseController {
	
	@RequestMapping(value = "/fieldListManagerByObjectId", method = RequestMethod.GET)
    public ModelAndView accountGiveRole(HttpServletRequest request, DataField field) throws Exception {
    	String forward = "design/datafield/detailListField";
    	Map<String, Object> model = new HashMap<String, Object>();
    	String objectId = request.getParameter("objectId");
    	
    	try{
    		DataObject object = dataObjectService.findByObjectId(objectId);
    		object.setDbconn(deDBConnectService.getDBConnectById(object.getDbId()));
			if(DBUtil.tableIsexist(object)){
				object.setStatus(1);
				dataObjectService.update(object);
			}
    		model.put("object", object);
    		model.put("field", field);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    	}
    	return new ModelAndView(forward, model);
    }
	
  	@RequestMapping(value = "/dataGridByObjectId", method = RequestMethod.POST)
  	@ResponseBody
  	public Object dataGridByModuleId(HttpServletRequest request,DataField field,Integer page, Integer rows, String sort, String order) {
  		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
  		String objectId = request.getParameter("objectId");
  		try {
  			Map<String, Object> condition = new HashMap<String, Object>();
  			//当前 角色ID
  			if(!Strings.isNullOrEmpty(objectId)){
  				condition.put("tableId", objectId);
  			}
  			//查询条件：
  			if(!Strings.isNullOrEmpty(field.getCode())){
  				condition.put("code", field.getCode());
  			}
  			pageInfo.setCondition(condition);
  			dataFieldService.findDataGrid(pageInfo);
//	  			DicCache.match(pageInfo.getRows(), "DATA_STATUS:areaStatus");
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("查询列表出现异常，", e);
  		}
  		return pageInfo;
  	}
  	
  	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request, DataField field)
			throws Exception {
		String forward = "design/datafield/add";
		Map<String, Object> model = new HashMap<String, Object>();
		String objectId = request.getParameter("objectId");
		try {
			
			DataObject object = dataObjectService.findByObjectId(objectId);
			DeDBConnect db = deDBConnectService.getDBConnectById(object.getDbId());
			List<DesignTable> tableList = DBUtil.getTableList(db);
			model.put("tableList", tableList); 
			
			model.put("type", field.getType());
			model.put("tableId", objectId);
			model.put("dbId", object.getDbId());
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
  	
  	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,DesignTable table) throws Exception {
		String jsonarray = "";
		try {
			DeDBConnect db = deDBConnectService.getDBConnectById(table.getDbId());
			table.setDbconn(db);
			List<DesignTableColumn> pageList = DBUtil.getColumnList(table);
			jsonarray = JSONArray.toJSONString(pageList);
//			System.out.println(jsonarray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, DataField field) throws Exception {
		try {
			//如果，类型 选择是，外键关联，但是 关联表，和关联字段 没有值，报错提示。
			if(field.getType().equals("ref")&&(Strings.isNullOrEmpty(field.getRefField())||Strings.isNullOrEmpty(field.getRefTable()))){
				return renderError("如果当前字段属于关联类型，请 认真选择关联表，和关联字段。");
			}
			
			field.setCreateId((String)request.getSession().getAttribute("accountId"));
			field.setCreateDate(new Date());
			field.setStatus(0);
			dataFieldService.insert(field);
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
			dataFieldService.deleteListByIds(ids);
			
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String id)throws Exception {
		String forward = "design/datafield/edit";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DataField field = dataFieldService.findById(id);
			DataObject object = dataObjectService.findByObjectId(field.getTableId());
			DeDBConnect db = deDBConnectService.getDBConnectById(object.getDbId());
			List<DesignTable> tableList = DBUtil.getTableList(db);
			model.put("tableList", tableList); 
			model.put("field", field);
			model.put("dbId", db.getId());
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, DataField field) throws Exception {
		try {
			//如果，类型 选择是，外键关联，但是 关联表，和关联字段 没有值，报错提示。
			if(field.getType().equals("ref")&&(Strings.isNullOrEmpty(field.getRefField())||Strings.isNullOrEmpty(field.getRefTable()))){
				return renderError("如果当前字段属于关联类型，请 认真选择关联表，和关联字段。");
			}
			
			field.setUpdateId((String)request.getSession().getAttribute("accountId"));
			field.setUpdateDate(new Date());
			dataFieldService.update(field);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
}