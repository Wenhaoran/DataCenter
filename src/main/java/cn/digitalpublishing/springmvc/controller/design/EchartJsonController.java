package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.po.echarts.EchartJson;
import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/echartJson")
public class EchartJsonController extends BaseController {
	
	@RequestMapping(value = "/jsonListManagerByEchartId", method = RequestMethod.GET)
    public ModelAndView jsonListManagerByEchartId(HttpServletRequest request, String echarId) throws Exception {
        String forward = "echarts/echartJson/detailList";
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	Echarts echarts = echartsService.findByObjectId(echarId);
			model.put("echarts", echarts);
        } catch(Exception e) {
    		logger.error("查询列表出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
	
	//查询
	@RequestMapping(value = "/dataGridByEchartId", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGridByTableId(HttpServletRequest request, String echartId,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("echartId", echartId);
			List<EchartJson> jsonList = echartJsonService.findListByCondition(condition);
			
			pageInfo.setRows(jsonList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addAttr", method = RequestMethod.GET)
	public ModelAndView addAttr(HttpServletRequest request, String echartId)throws Exception {
		String forward = "echarts/echartJson/addAttr";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Echarts echarts = echartsService.findByObjectId(echartId);
			model.put("echarts", echarts);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, EchartJson echartJson)throws Exception {
		try {
			String jsonSource = echartJson.getSource();
			String jsonStr = jsonSource.replaceAll("DMXY", "\"");
			echartJson.setSource(jsonStr);
			String type = echartJson.getType();
			StringBuffer template = new StringBuffer();
			int mapNum = 0;
			if("oneArray".equals(type)){
				/*List<String> listString = JSON.parseArray(jsonStr, String.class); 
				if(listString.size() == 0){
					throw new Exception("数据错误，请认真填写。");
				}*/
				template.append("[");
				template.append("数据");
				template.append("]");
				mapNum = 1;
			}else if("manyArray".equals(type)){
				//List<String[]> listString = JSON.parseArray(jsonStr,  new TypeReference<String[]>(){}); 
				JSONArray array = JSON.parseArray(jsonStr);
				JSON json = (JSON) array.get(0);
				System.out.println(json);
				List<String> listString = JSON.parseArray(json.toString(), String.class); 
				template.append("[");
				for(int i = 1;i<listString.size()+1;i++){
					template.append("数据"+i);
					if(i != listString.size()){
						template.append(",");
					}
				}
				template.append("]");
				mapNum = listString.size();
			}else if("jsonValueString".equals(type)){
				//List<Map<String, Object>> listMap = JSON.parseObject(jsonStr, new TypeReference<List<Map<String,Object>>>(){});
				template.append("{");
				template.append("数据1(key)");
				template.append(":");
				template.append("数据2(value)");
				template.append("}");
				mapNum = 2;
			}else if("jsonValueArray".equals(type)){
				String str = jsonStr.replaceAll("DMXY", "\""); 
				JSONObject obj = JSON.parseObject(str);
				Set<String> keySet = obj.keySet();
				
				Iterator<String> i = keySet.iterator();
				String key = "";
				if(i.hasNext()){
					key = (String) i.next();
				}
				List<String> listString = JSON.parseArray(obj.get(key).toString(), String.class); 
				template.append("{");
				template.append("数据1(key)");
				template.append(":");
				template.append("[");
				for(int j=0;j<listString.size();j++){
					template.append("数据"+(j+2));
					if(j+1 != listString.size()){
						template.append(",");
					}
				}
				template.append("]");
				template.append("}");
				mapNum = 1+listString.size();
			}else if("jsonArray".equals(type)){
				String str = jsonStr.replaceAll("DMXY", "\""); 
				JSONArray arr = JSON.parseArray(str);
				JSONObject obj = arr.getJSONObject(0);
				Set<String> keySet = obj.keySet();
//				List<Map<String, Object>> listMap = JSON.parseObject(obj.toString(), new TypeReference<List<Map<String,Object>>>(){});
				template.append("{");
				int i=1;
				for(String key :keySet){
					template.append(key+":数据"+i);
					if(i!=keySet.size()){
						template.append(",");
					}
					i++;
				}
				template.append("}");
				mapNum = keySet.size();
			}
			echartJson.setMapNum(mapNum);
			echartJson.setTemp(template.toString());
			echartJsonService.insert(echartJson);
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,String[] ids,String tableId) throws Exception {
		try {
			echartJsonService.deleteListByIdsAndTableId(ids,tableId);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
	
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String id) throws Exception {
		String forward = "echarts/echartJson/edit";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DesignTableColumn column = designTableColumnService.findById(id);
			model.put("column", column);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, DesignTableColumn column)throws Exception {
		try {
			column.setUpdateId((String) request.getSession().getAttribute("accountId"));
			column.setUpdateDate(new Date());
			designTableColumnService.update(column);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
}