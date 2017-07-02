package cn.digitalpublishing.springmvc.controller.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.digitalpublishing.po.base.Tree;
import cn.digitalpublishing.po.design.DataObject;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.design.DataObjectForm;
import cn.digitalpublishing.util.sql.DBUtil;

import com.google.common.base.Strings;

@Controller
@RequestMapping("/pages/obj")
public class DataObjectController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, DataObjectForm form) throws Exception {

		// Map<String,String> accountInfoMap = (Map<String, String>)
		// request.getSession().getAttribute("pt_accountInfo");
		String forwardString = "design/dataobject/object";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("status", 1);
			List<DeDBConnect> dbconnList = deDBConnectService.getDBConnectList(condition);

			DataObject dataobject = new DataObject();
			if(dbconnList!=null&&dbconnList.size()>0){
				dataobject.setDbconn(dbconnList.get(0));
			}
			form.setObj(dataobject);

			model.put("form", form);
			model.put("dbconnList", dbconnList);
			form.setIsSuccess(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			form.setIsSuccess(FAILURE);
			form.setMsg("");
			forwardString = "msg";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Object tree(HttpServletRequest request,
			HttpServletResponse response, String dbId, Boolean islist) {
		List<Tree> tree = dataObjectService.findTreeByDBId(dbId, islist);
		return tree;
	}

	@RequestMapping(value = "/findObjectByDBId", method = RequestMethod.POST)
	@ResponseBody
	public Object findObjectByDBId(String dbId) throws Exception {
		HashMap<String, Object> condition = new HashMap<String, Object>();
		if (!Strings.isNullOrEmpty(dbId)) {
			condition.put("dbId", dbId);
		}
		List<DataObject> resourcesList = dataObjectService
				.findListByCondition(condition);
		return resourcesList;
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request, DataObject object)
			throws Exception {
		String forward = "design/dataobject/add";
		Map<String, Object> model = new HashMap<String, Object>();
		String dbId = request.getParameter("dbId");

		try {
			model.put("type", object.getType());
			model.put("dbId", dbId);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, DataObject object)throws Exception {
		try {
			// 如果 parentId 为 空，或者null ，设置父ID 为
			if (Strings.isNullOrEmpty(object.getpId())) {
				object.setpId("0");
			}
			if (object.getType() == 1) {
				object.setTableName("0");
			}
			object.setCreateId((String) request.getSession().getAttribute("accountId"));
			object.setCreateDate(new Date());
			object.setLeaf(object.getType());
			object.setDbconn(deDBConnectService.getDBConnectById(object.getDbId()));
			if(DBUtil.tableIsexist(object)){
				return renderError("当前表已存在，请重新命名数据库表名。");
			}
			
			if (object.getpId().equals("0") && object.getType() == 2) {
				return renderError("添加对象必须选择 父节点");
			}
			object.setStatus(0);
			dataObjectService.insert(object);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(HttpServletRequest request,HttpServletResponse response, String id) throws Exception {
		try {
			DataObject obj = dataObjectService.findByObjectId(id);
			String sql = dataObjectService.createSql(obj);
			String[] sqls = sql.split(";");
			DeDBConnect dbconn = deDBConnectService.getDBConnectById(obj.getDbId());
			DBUtil.executeSqls(sqls, dbconn);
			obj.setStatus(1);
			dataObjectService.update(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("创建表失败，错误信息为：" + e.getMessage());
		}
		return renderSuccess("创建表成功！");
	}

	@RequestMapping(value = "/exportSql")
	public void exportSql(HttpServletRequest request,HttpServletResponse response, String id) throws Exception {
		try {
			response.setContentType("application/binary;charset=UTF-8");
			
			// 根据传入的objId来查询 当前，以及所有子节点中，全部的 表，也就是全部的叶子节点
			DataObject obj = dataObjectService.findByObjectId(id);
			
			String sql = "";
			String fileName ="";
			if(obj.getType()==1){
				List<DataObject> objList = dataObjectService.findListByObjectId(id);
				sql = dataObjectService.createSql(objList);
				fileName=obj.getName()+".sql";
			}else{
				sql = dataObjectService.createSql(obj);
				fileName=obj.getTableName()+".sql";
			}
			
			String name = new String(fileName.getBytes(), "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="+ name);// 组装附件名称和格式
			File fileLoad = new File(name);
			FileWriter writer = new FileWriter(fileLoad);
			writer.write(sql);
			writer.flush();
            writer.close();
			long fileLength = fileLoad.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length);
			FileInputStream input = null;
			ServletOutputStream output = null;
			try {
				input = new FileInputStream(fileLoad);
				output = response.getOutputStream();
				byte[] block = new byte[1024];
				int len = 0;
				while ((len = input.read(block)) != -1) {
					output.write(block, 0, len);
				}
			} catch (Exception e) {
				e.getMessage();
			} finally {
				
				try {
					input.close();
					output.flush();
					output.close();
				} catch (Exception ex) {
					logger.error("关闭文件流异常", ex);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String id) throws Exception {
		String forward = "design/dataobject/edit";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DataObject object = dataObjectService.findByObjectId(id);
			model.put("object", object);
		} catch (Exception e) {
			logger.error("跳转编辑页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, DataObject object) throws Exception {
		try {
			object.setUpdateId((String) request.getSession().getAttribute("accountId"));
			object.setUpdateDate(new Date());
			dataObjectService.update(object);
		} catch (Exception e) {
			logger.error("编辑出现异常，", e);
			e.printStackTrace();
			return renderError("编辑出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("编辑成功！");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request, String id) throws Exception {
		try {
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("parentId", id);
			List<DataObject> ojbectList = dataObjectService.findListByCondition(condition);
			if(ojbectList.size()>0){
				return renderError("当前节点存在子节点，安全考虑，不允许直接删除父节点。");
			}
			dataObjectService.deleteById(id);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("删除成功！");
	}
}