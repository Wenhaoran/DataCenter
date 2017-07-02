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

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.echarts.EchartColumn;
import cn.digitalpublishing.po.echarts.EchartColumnValue;
import cn.digitalpublishing.po.echarts.EchartExportRS;
import cn.digitalpublishing.po.echarts.EchartJson;
import cn.digitalpublishing.po.echarts.EchartJsonMap;
import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.po.export.DataExport;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.echarts.EchartsForm;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

@Controller
@RequestMapping("/pages/echarts")
public class EchartsController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, EchartsForm form) throws Exception {
		String forwardString = "echarts/echarts/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Echarts echarts = new Echarts();
			form.setObj(echarts);
			model.put("form", form);
			form.setIsSuccess(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			form.setIsSuccess(FAILURE);
			form.setMsg("");
			forwardString = "msg";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGrid(HttpServletRequest request, Echarts echarts,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(echarts.getCode())){
				condition.put("code", echarts.getCode());
			}
			if(!Strings.isNullOrEmpty(echarts.getName())){
				condition.put("name", echarts.getName());
			}
			pageInfo.setCondition(condition);
			echartsService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request, Echarts echarts)throws Exception {
		String forward = "echarts/echarts/add";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, Echarts echarts)throws Exception {
		try {
			echarts.setStatus(0);
			echartsService.insert(echarts);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String id) throws Exception {
		String forward = "echarts/echarts/edit";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Echarts echarts = echartsService.findByObjectId(id);
			model.put("echarts", echarts);
		} catch (Exception e) {
			logger.error("跳转编辑页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, Echarts echarts) throws Exception {
		try {
			echarts.setUpdateId((String) request.getSession().getAttribute("accountId"));
			echarts.setUpdateDate(new Date());
			echartsService.update(echarts);
		} catch (Exception e) {
			logger.error("编辑出现异常，", e);
			e.printStackTrace();
			return renderError("编辑出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("编辑成功！");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Object delete(HttpServletRequest request, String id) throws Exception {
		try {
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("echartId", id);
			List<EchartExportRS> ojbectList = echartExportRSService.findListByCondition(condition);
			if(ojbectList.size()>0){
				return renderError("当前图表已 被使用，不能删除。");
			}
			echartsService.deleteById(id);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("删除成功！");
	}
	
	
	
	
	//报表查询中，选择的图表列表
	@RequestMapping(value = "/echartsListManagerByExportId", method = RequestMethod.GET)
    public ModelAndView echartsListManagerByExportId(HttpServletRequest request, String exportId) throws Exception {
        String forward = "echarts/echartExport/detailList";
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	DataExport export = dataExportService.getDataExportById(exportId);
			model.put("export", export);
        } catch(Exception e) {
    		logger.error("查询列表出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
	
	//查询
	@RequestMapping(value = "/dataGridByExportId", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGridByExportId(HttpServletRequest request, String exportId,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("exportId", exportId);
        	condition.put("type", "one");
			List<EchartExportRS> jsonList = echartExportRSService.findListByCondition(condition);
			
			pageInfo.setRows(jsonList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addAttr", method = RequestMethod.GET)
	public ModelAndView addAttr(HttpServletRequest request, String exportId,String type)throws Exception {
		String forward = "echarts/echartExport/addEchartExportRS";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DataExport export = dataExportService.getDataExportById(exportId);
			Map<String, Object> condition = new HashMap<String, Object>();
        	List<Echarts> echartsList = echartsService.findListByCondition(condition);
        	
			model.put("export", export);
			model.put("echartsList", echartsList);
			model.put("type", type);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	@RequestMapping(value = "/saveEchartExportRS", method = RequestMethod.POST)
    @ResponseBody
    public Object saveEchartExportRS(HttpServletRequest request,String exportId,String echartId,String type){
    	try {
    		String name = request.getParameter("name");
    		EchartExportRS rs = new EchartExportRS();
    		rs.setName(name);
    		rs.setType(type);
    		rs.setEchartId(echartId);
    		if(!Strings.isNullOrEmpty(exportId)){
    			rs.setExportId(exportId);
    		}else{
    			rs.setExportId("many");
    		}
    		rs.setStatus(0);
    		rs.setCreateId("createId");
    		echartExportRSService.insert(rs);
    	} catch(Exception e) {
			e.printStackTrace();
			return renderError("添加失败！");
		}
        return renderSuccess("添加成功！");
    }
	
	
	
	@RequestMapping(value = "/addMustColumn", method = RequestMethod.GET)
	public ModelAndView addMustColumn(HttpServletRequest request, String exportId,String rsId)throws Exception {
		String forward = "echarts/base/";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			EchartExportRS rs = echartExportRSService.findByObjectId(rsId);
			Echarts echart = echartsService.findByObjectId(rs.getEchartId());
			forward = forward+echart.getCode();
			
			DataExport export = dataExportService.getDataExportById(exportId);
			Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("exportId", exportId);
        	List<EchartExportRS> rsList = echartExportRSService.findListByCondition(condition);
			model.put("export", export);
			
			condition.clear();
			condition.put("", "");
			List<DataExport> dataExportList = dataExportService.getDataExportList(condition);
			model.put("dataExportList", dataExportList);
			
			model.put("echart", echart);
			model.put("rsList", rsList);
			model.put("rs", rs);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	@RequestMapping(value = "/saveMustColumn", method = RequestMethod.POST)
    @ResponseBody
    public Object saveMustColumn(HttpServletRequest request,String exportId,String echartId,String rsId){
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("rsId", rsId);
			List<EchartColumnValue> columnValue = echartColumnValueService.findListByCondition(condition);
			List<EchartColumn> echartColumnList = echartColumnService.findListByEchartId(echartId);
			
			if(columnValue!=null&&columnValue.size()>0){
				for(EchartColumn echartColumn:echartColumnList){
					Map<String, Object> oneCondition = new HashMap<String, Object>();
					oneCondition.put("code", echartColumn.getCode());
					oneCondition.put("rsId", rsId);
	    			EchartColumnValue echartColumnValue = echartColumnValueService.findListByCondition(oneCondition).get(0);
	    			
	    			String value = request.getParameter(echartColumn.getCode());
	    			echartColumnValue.setColVal(value);
	    			echartColumnValue.setUpdateId("dmxy");
	    			echartColumnValue.setUpdateDate(new Date());
	    			echartColumnValueService.update(echartColumnValue);
	    		}
			}else{
				for(EchartColumn echartColumn:echartColumnList){
	    			EchartColumnValue echartColumnValue = new EchartColumnValue();
	    			String value = request.getParameter(echartColumn.getCode());
	    			echartColumnValue.setColId(echartColumn.getId());
	    			echartColumnValue.setColVal(value);
	    			echartColumnValue.setEchartId(echartId);
	    			echartColumnValue.setExpId(exportId);
	    			echartColumnValue.setCode(echartColumn.getCode());
	    			echartColumnValue.setRsId(rsId);
	    			echartColumnValueService.insert(echartColumnValue);
	    		}
			}
    		
   		} catch(Exception e) {
			e.printStackTrace();
			return renderError("添加失败！");
		}
        return renderSuccess("添加成功！");
    }
	
	
	@RequestMapping(value = "/addJsonMap", method = RequestMethod.GET)
	public ModelAndView addJsonMap(HttpServletRequest request, String exportId,String rsId)throws Exception {
		String forward = "echarts/echartExport/jsonMap";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			EchartExportRS rs = echartExportRSService.findByObjectId(rsId);
			Echarts echart = echartsService.findByObjectId(rs.getEchartId());
			DataExport export = dataExportService.getDataExportById(exportId);
			Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("exportId", exportId);
        	List<EchartExportRS> rsList = echartExportRSService.findListByCondition(condition);
        	
			model.put("export", export);
			model.put("rsList", rsList);
			model.put("echart", echart);
			model.put("rsId", rsId);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	@RequestMapping(value = "/jsonMapListDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object jsonMapListDataGrid(HttpServletRequest request, String rsId,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("rsId", rsId);
			pageInfo.setCondition(condition);
			echartJsonMapService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addJsonMapValue", method = RequestMethod.GET)
	public ModelAndView addJsonMapValue(HttpServletRequest request, String rsId)throws Exception {
		String forward = "echarts/echartExport/jsonMapValue";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			EchartExportRS rs = echartExportRSService.findByObjectId(rsId);
			Echarts echart = echartsService.findByObjectId(rs.getEchartId());
			
			if(rs.getType().equals("one")){
				DataExport export = dataExportService.getDataExportById(rs.getExportId());
				DeDBConnect db = deDBConnectService.getDBConnectById(export.getDbId());
	        	List<String> columnList = DBUtil.executeSqlReturnColumnList(db,export.getSql());
	        	model.put("export", export);
				model.put("columnList", columnList);
			}
			
        	List<EchartJson> jsonList = echartJsonService.findListByEchartId(echart.getId());
        	
        	List<Map<String,String>> jsonMapList = new ArrayList<>();
        	for(EchartJson json:jsonList){
        		Map<String,String> jsonMap = new HashMap<String,String>();
        		jsonMap.put("id", json.getId());
        		jsonMap.put("temp", json.getTemp());
        		jsonMapList.add(jsonMap);
        	}
        	
        	String jsonMapString = JSONArray.toJSONString(jsonMapList);
        	Map<String, Object> condition = new HashMap<String, Object>();
        	List<DataExport> dataExportList = dataExportService.getDataExportList(condition);
        	model.put("dataExportList", dataExportList);
        	
        	model.put("jsonList", jsonList);
        	model.put("jsonMapString", jsonMapString);
			
			model.put("echart", echart);
			model.put("rs", rs);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	@RequestMapping(value = "/saveJsonMapValue", method = RequestMethod.POST)
    @ResponseBody
    public Object saveJsonMapValue(HttpServletRequest request,EchartJsonMap jsonmap){
		try {
			String rsId = jsonmap.getRsId();
			String jsonId = jsonmap.getJsonId();
			EchartJson json = echartJsonService.findByObjectId(jsonId);
			Integer jmapOrder = jsonmap.getJmapOrder();
			if(jmapOrder<1){
				return renderError("添加失败！映射数据只能从 1 开始。");
			}
			if(jmapOrder>json.getMapNum()){
				return renderError("添加失败！映射数据大于总个数。");
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("rsId", rsId);
			condition.put("jsonId", jsonId);
			condition.put("jmapOrder", jmapOrder);
			List<EchartJsonMap> jsonMapList = echartJsonMapService.findListByCondition(condition);
			if(jsonMapList!=null&&jsonMapList.size()>0){
				return renderError("添加失败！当前源数据，当前数据位置已存在映射，请不要重复添加。");
			}
			jsonmap.setCreateId("dmxy");
			jsonmap.setStatus(0);
			echartJsonMapService.insert(jsonmap);
   		} catch(Exception e) {
			e.printStackTrace();
			return renderError("添加失败！");
		}
        return renderSuccess("添加成功！");
    }
	
	@RequestMapping(value = "/integratedQue")
	public ModelAndView integratedQue(HttpServletRequest request,HttpServletResponse response, EchartsForm form) throws Exception {
		String forwardString = "echarts/echarts/integratedQue";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Echarts echarts = new Echarts();
			form.setObj(echarts);
			model.put("form", form);
			form.setIsSuccess(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			form.setIsSuccess(FAILURE);
			form.setMsg("");
			forwardString = "msg";
		}
		return new ModelAndView(forwardString, model);
	}
	
	//查询
	@RequestMapping(value = "/dataGridByTypeMany", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGridByTypeMany(HttpServletRequest request,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("type", "many");
			List<EchartExportRS> jsonList = echartExportRSService.findListByCondition(condition);
			
			pageInfo.setRows(jsonList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}

	
	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ModelAndView generate(HttpServletRequest request, String rsId)throws Exception {
		String forward = "echarts/echartExport/generate";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			EchartExportRS rs = echartExportRSService.findByObjectId(rsId);
			Echarts echarts = echartsService.findByObjectId(rs.getEchartId());
			
			List<EchartColumnValue> columnValueList = echartColumnValueService.findListByRSId(rsId);
			Map<String,String> columnValueMap = new HashMap<>();
			for(EchartColumnValue columnValue:columnValueList){
				columnValueMap.put(columnValue.getCode(), columnValue.getColVal());
			}
			String mustJson = JSONArray.toJSONString(columnValueMap);
			
			List<EchartJson> jsonList = echartJsonService.findListByEchartId(echarts.getId());
			List<Map<String,Object>> jsonDataList = new ArrayList<Map<String,Object>>();
			for(EchartJson json :jsonList){
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("rsId", rsId);
				condition.put("jsonId", json.getId());
				List<EchartJsonMap> jsonMapList = echartJsonMapService.findListByCondition(condition);
				String jsonType = json.getType();
				String template = json.getTemp();
				if(jsonType.equals("oneArray")){
					List<String> jsonDataOne = echartsService.getJsonString(jsonType,template,jsonMapList);
					//String jsonOne = JSONArray.toJSONString(jsonDataOne);
					map.put(json.getCode(), jsonDataOne);
				}
				if(jsonType.equals("manyArray")){
					List<String> jsonDataOne = echartsService.getJsonString(jsonType,template,jsonMapList);
					//String jsonOne = JSONArray.toJSONString(jsonDataOne);
					map.put(json.getCode(), jsonDataOne);
				}
				if(jsonType.equals("jsonValueString")){
					List<String> jsonDataOne = echartsService.getJsonString(jsonType,template,jsonMapList);
					//String jsonOne = JSONArray.toJSONString(jsonDataOne);
					map.put(json.getCode(), jsonDataOne);
				}
				if(jsonType.equals("jsonValueArray")){
					List<String> jsonDataOne = echartsService.getJsonString(jsonType,template,jsonMapList);
					//String jsonOne = JSONArray.toJSONString(jsonDataOne);
					map.put(json.getCode(), jsonDataOne);
				}
				if(jsonType.equals("jsonArray")){
					List<Map<String,Object>> jsonDataOne = echartsService.getjsonArray(jsonType,template,jsonMapList);
					//String jsonOne = JSONArray.toJSONString(jsonDataOne);
					map.put(json.getCode(), jsonDataOne);
				}
				jsonDataList.add(map);
			}
			
			String jsonData = JSONArray.toJSONString(jsonDataList);
			
        	model.put("mustJson", mustJson);
        	model.put("jsonData", jsonData);
        	model.put("echartCode", echarts.getCode());
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	
}