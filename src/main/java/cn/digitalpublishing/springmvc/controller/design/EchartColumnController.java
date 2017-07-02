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

import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.po.echarts.EchartColumn;
import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/echartColumn")
public class EchartColumnController extends BaseController {
	
	@RequestMapping(value = "/columnListManagerByEchartId", method = RequestMethod.GET)
    public ModelAndView columnListManagerByEchartId(HttpServletRequest request, String echarId) throws Exception {
        String forward = "echarts/echartColumn/detailList";
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
			List<EchartColumn> echartsList = echartColumnService.findListByCondition(condition);
			pageInfo.setRows(echartsList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	
	@RequestMapping(value = "/addAttr", method = RequestMethod.GET)
	public ModelAndView addAttr(HttpServletRequest request, String echartId)throws Exception {
		String forward = "echarts/echartColumn/addAttr";
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
	public Object add(HttpServletRequest request, EchartColumn echartColumn)throws Exception {
		try {
			echartColumnService.insert(echartColumn);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,String[] ids,String tableId) throws Exception {
		try {
			echartColumnService.deleteListByIdsAndTableId(ids,tableId);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
	
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String id) throws Exception {
		String forward = "design/designColumn/edit";
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