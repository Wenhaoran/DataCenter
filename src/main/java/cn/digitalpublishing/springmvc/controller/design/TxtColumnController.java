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

import com.google.common.base.Strings;

import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/txtcolumn")
public class TxtColumnController extends BaseController {

	@RequestMapping(value = "/columnListByTxtId")
    public ModelAndView columnListByTxtId(HttpServletRequest request,String txtId) throws Exception {
        String forward = "design/text/detailColumnList";
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	TxtObject txt = txtObjectService.getTxtObjectById(txtId);
			String name = txt.getName();
			model.put("name", name);
			model.put("txt", txt);
        } catch(Exception e) {
    		logger.error("查询列表出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
	
  	@RequestMapping(value = "/dataGridByTxtId", method = RequestMethod.POST)
  	@ResponseBody
  	public Object dataGridByTxtId(HttpServletRequest request,TxtColumn column,Integer page, Integer rows, String sort, String order) {
  		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
  		String txtId = request.getParameter("txtId");
  		try {
  			Map<String, Object> condition = new HashMap<String, Object>();
  			//当前 text ID
  			if(!Strings.isNullOrEmpty(txtId)){
  				condition.put("txtId", txtId);
  			}
  			pageInfo.setCondition(condition);
  			txtColumnService.findDataGrid(pageInfo);
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("查询列表出现异常，", e);
  		}
  		return pageInfo;
  	}
  	
  	@RequestMapping(value = "/deleteColumn") 
  	@ResponseBody
  	public Object deleteAMRS(HttpServletRequest request,String[] ids,String txtId) throws Exception {//null
  		try {
  			txtColumnService.batchDelByTxtId(txtId, ids);
  		} catch (Exception e) {
  			logger.error("解除出现异常，", e);
  			e.printStackTrace();
  		}
  		return renderSuccess("解除成功！");
  	}
  	
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, TxtColumn column) throws Exception {
    	String forward = "design/txtcolumn/add";
    	String txtId = request.getParameter("txtId");
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		model.put("txtId", txtId);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }

	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,TxtColumn column) throws Exception {
    	try {
    		column.setType("0");//是否 映射数据库 表	未映射表
    		
    		column.setCreateId((String)request.getSession().getAttribute("accountId"));
    		column.setCreateDate(new Date());
    		column.setStatus(0);
    		txtColumnService.insertTxtColumn(column);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
}