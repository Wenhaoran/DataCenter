package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableConfig;
import cn.digitalpublishing.springmvc.controller.BaseController;

@Controller
@RequestMapping("/pages/config")
public class DesignConfigController extends BaseController {
	
	@RequestMapping(value = "/addConfigPage", method = RequestMethod.GET)
	public ModelAndView addConfigPage(HttpServletRequest request, String tableId) throws Exception {
		String forward = "design/designTable/addConfig";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DesignTable table = designTableService.findByTableId(tableId);
			model.put("table", table); 
			model.put("tableId", tableId);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/addConfig", method = RequestMethod.POST)
	@ResponseBody
	public Object addConfig(HttpServletRequest request, DesignTableConfig config)throws Exception {
		try {
			String tableId = config.getTableId();
			DesignTable table = designTableService.findByTableId(tableId);
			
			config.setCreateId((String) request.getSession().getAttribute("accountId"));
			config.setCreateDate(new Date());
			config.setStatus(1);
			designTableConfigService.insert(config);
			
			table.setType("3");
			table.setUpdateDate(new Date());
			table.setUpdateId((String) request.getSession().getAttribute("accountId"));
			designTableService.update(table);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
	
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String tableId) throws Exception {
		String forward = "design/designTable/editConfig";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DesignTableConfig config = designTableConfigService.getConfigByTableId(tableId);
			model.put("config", config);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, DesignTableConfig config)throws Exception {
		try {
			config.setUpdateId((String) request.getSession().getAttribute("accountId"));
			config.setUpdateDate(new Date());	
			designTableConfigService.update(config);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
}