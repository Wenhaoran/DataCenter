package com.design;
/////*package cn.digitalpublishing.controller;
////
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.IOException;
////import java.io.UnsupportedEncodingException;
////import java.util.ArrayList;
////import java.util.Date;
////import java.util.HashMap;
////import java.util.LinkedHashMap;
////import java.util.List;
////import java.util.Map;
////
////import javax.annotation.Resource;
////import javax.servlet.ServletOutputStream;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////
////import org.apache.commons.lang3.StringUtils;
////import org.apache.poi.hssf.usermodel.HSSFWorkbook;
////import org.apache.shiro.SecurityUtils;
////import org.apache.shiro.subject.Subject;
////import org.jeecgframework.poi.excel.ExcelExportUtil;
////import org.jeecgframework.poi.excel.entity.ExportParams;
////import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.core.io.DefaultResourceLoader;
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.util.FileCopyUtils;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestMethod;
////import org.springframework.web.bind.annotation.ResponseBody;
////import org.springframework.web.multipart.MultipartFile;
////import org.springframework.web.multipart.MultipartHttpServletRequest;
////
////import cn.digitalpublishing.commons.base.BaseController;
////import cn.digitalpublishing.commons.base.Constants;
////import cn.digitalpublishing.commons.cache.DicCache;
////import cn.digitalpublishing.commons.result.Result;
////import cn.digitalpublishing.commons.scan.SysLogAnnotation;
////import cn.digitalpublishing.commons.utils.AnnotationUtils;
////import cn.digitalpublishing.commons.utils.DateUtil;
////import cn.digitalpublishing.commons.utils.DateUtils;
////import cn.digitalpublishing.commons.utils.FileUtils;
////import cn.digitalpublishing.commons.utils.FreeMarkers;
////import cn.digitalpublishing.commons.utils.IDUtil;
////import cn.digitalpublishing.commons.utils.ImageHelper;
////import cn.digitalpublishing.commons.utils.MathUtil;
////import cn.digitalpublishing.commons.utils.MsgToolUtil;
////import cn.digitalpublishing.commons.utils.Reflections;
////import cn.digitalpublishing.commons.utils.RequestUtils;
////import cn.digitalpublishing.model.BDic;
////import cn.digitalpublishing.model.CnpicProduct;
////import cn.digitalpublishing.model.TBaseMailSendedQueue;
////import cn.digitalpublishing.model.TBusisProduct;
////import cn.digitalpublishing.model.TableColumnEntity;
////import cn.digitalpublishing.model.TableDescEntity;
////import cn.digitalpublishing.service.CommonService;
////import cn.digitalpublishing.service.TBaseMailSendedQueueService;
////import cn.digitalpublishing.service.TBusisProductCnpiecService;
////import cn.digitalpublishing.service.TBusisProductService;
////
////import com.google.common.base.Strings;
////import com.google.common.collect.Maps;
////
////import freemarker.template.Configuration;
////import freemarker.template.Template;
////
////*//**
//// * @description：公共的数据服务
//// * @author：shurui.chen
//// * @date：2015/10/1 14:51
//// *//*
////@Controller
////@RequestMapping("/common")
////public class CommonController extends BaseController {
////
////	
////	@Resource(name="commonService")
////    private CommonService commonService;
////	@Resource(name="tbusisProductService")
////    private TBusisProductService tbusisProductService;
////	@Autowired
////    private TBusisProductCnpiecService tbusisProductCnpiecService;   
////	
////	@Resource(name="tbaseMailSendedQueueService")
////	private TBaseMailSendedQueueService tbaseMailSendedQueueService;
////    *//**
////     * 获取数据字典值
////     *
////     * @return
////     *//*
////    @RequestMapping(value = "/getDic", method = RequestMethod.POST)
////    @ResponseBody
////    public Object dataGrid(String dicClass) {
////    	List<BDic> list = null;
////    	try{
////    		if(!Strings.isNullOrEmpty(dicClass)){
////    			list = DicCache.getData(dicClass, BDic.class);
////    		}
////    		if(list==null){
////    			list = new ArrayList<BDic>();
////    			BDic dic = new BDic();
////    			dic.setCode("99");
////    			dic.setName("没有定义数据字典["+dicClass+"],请联系");
////    			list.add(dic);
////    		}
////    		//加入一个请选择项
////    		BDic dic = new BDic();
////			dic.setCode("");
////			dic.setName("--请选择--");
////			list.add(0, dic);
////    	}catch(Exception e){
////    		logger.error("查询数据字典出现异常",e);
////    	}
////        return list;
////    }
////
////    *//**
////     * 页面跳转方法，跳转到列展示页面
////     * @param beanName
////     * @param model
////     * @return
////     *//*
////    @RequestMapping("/showColumnManager")
////    public String showColumnManager(String beanName,String ftlName, Model model) {    	
////    	try{    
////    		model.addAttribute("ftlName",ftlName);
////     		model.addAttribute("beanName", beanName);
////    	}catch(Exception e){
////    		logger.error("跳转展示列页面出现异常",e);
////    	}
////    	return "/common/showColumn";
////    }
////    
////    *//**
////     * 列展示页面 ajax方式获取展示列数据
////     * @param beanName
////     * @return
////     *//*
////    @RequestMapping(value = "/showColumnDataGrid", method = RequestMethod.POST)
////    @ResponseBody
////    public Object showColumnDataGrid(String beanName, String type) {
////    	List<TableColumnEntity> list = null;    	   		
////		if(!Strings.isNullOrEmpty(beanName)){
////			try{
////				list = AnnotationUtils.getEntityAnnotation(beanName, type);
////	    	 }catch(Exception e){
////	    		 logger.error("查询展示列出现异常",e);
////	    	 }
////		}    	
////    	return list;
////    }
////    
////    *//**
////     * 根据页面选择的展示列，生成用户的专有页面
////     * @param request
////     * @return
////     *//*
////    @SysLogAnnotation(moduleName = "设置选择列",option="选择")
////    @RequestMapping(value = "/setupSelect", method = RequestMethod.POST)
////    @ResponseBody
////    public Object setupSelect(HttpServletRequest request) {
////    	try{    		 
////			 Map<String,String> parms = RequestUtils.getParameterMap(request);
////		     logger.info("==>"+parms);	
////		     String beanName = parms.get("beanName");
////		     String fieldNames = parms.get("fns[]");
////		     String propNames = parms.get("pns[]");
////		     String propLengths = parms.get("pls[]");
////		     String dicFlag = parms.get("idc[]");
////		     String ftlName = parms.get("ftlName");
////		     
////		     //propNames = new String(propNames.getBytes("ISO-8859-1"),Constants.UTF8);
////		     StringBuffer strs = new StringBuffer();
////		     
////		     String[] fns = fieldNames.split(",");
////		     String[] pns = propNames.split(",");
////		     String[] pls = propLengths.split(",");
////		     String[] df = dicFlag.split(",");
////		     //列的数量
////		     int clsize = fns.length;
////		     	     
////		     String _field = null;
////		     for(int i=0;i< fns.length;i++){	   
////		    	 if(df[i].equals("Y")){
////		    		 _field = fns[i].trim();
////		    	 }else{
////		    		 _field = fns[i].trim();
////		    	 }
////				strs.append("{field:'").append(_field);
////				strs.append("',title:'").append(pns[i].trim());
////	    		//append("',width:'"+ MathUtil.multiply(Double.valueOf(pls[i].trim()), Double.valueOf(50))+ "%',sortable:true},");	
////				if(clsize>6){
////					strs.append("',width:'"+ MathUtil.multiply(Double.valueOf(pls[i].trim()), Double.valueOf(1))+ "%'");
////				}else{
////					//自动分配
////					strs.append("',width:'"+ Integer.parseInt((70/clsize)+"")+ "%'");
////				}
////				strs.append(",sortable:true},");
////		     }
////	    	strs.append("");
////	    	String columnJson = strs.toString();  
////	    	columnJson = columnJson.substring(0,columnJson.lastIndexOf(","));
////	        // 获取文件路径
////	 		String separator = File.separator;
////	 		String classPath = new DefaultResourceLoader().getResource("").getFile().getPath();
////	 		String templatePath = classPath.replace("classes",
////	 				"classes"+separator+"cn"+separator+"digitalpublishing"+separator+"ef"+separator+"modules");
////	 		String viewPath = classPath.replace(separator+"classes", separator+"views");
////	 		
////	 		// 代码模板配置
////	 		Configuration cfg = new Configuration();
////	 		cfg.setDefaultEncoding(Constants.UTF8);
////	 		String _templatePath = templatePath.replace("modules", "generate"+separator+"template");
////	 		cfg.setDirectoryForTemplateLoading(new File(_templatePath));
////
////	 		// 定义模板变量
////	 		Map<String, String> model = Maps.newHashMap();
////	 		model.put("columns", columnJson);
////	 		
////	 		if (StringUtils.isNotEmpty(ftlName)) {
////	 			beanName = ftlName;
////	 		}
////	 		Template template = cfg.getTemplate(beanName+"_list.ftl");
////	 		template.setEncoding(Constants.UTF8);
////	 		String content = FreeMarkers.renderTemplate(template, model);
////	 		Subject user = SecurityUtils.getSubject();
////	 		//命名规则，templateJsp/admin_bean_list.jsp
////	 		String filePath = viewPath+separator+"templateJsp"+separator+user.getPrincipal()+"_"+beanName+"_list.jsp";
////	 		FileUtils.writeFile(content, filePath);
////	 		logger.info(filePath);
////	 		logger.info("==>生成列表页面完毕......");  
////    	     
////    	}catch(Exception e){
////    		logger.error("添加出现异常，",e);
////    		return renderError(Constants.PARMS_MAP.get("client.show.msg"));
////    	}
////        return renderSuccess("添加成功！");
////    }
////    
////    *//**
////     * 跳转页面，跳转到导出列展示页面
////     * @param beanName
////     * @param ids
////     * @param model
////     * @return
////     *//*
////    @RequestMapping("/exportColumnManager")
////    public String exportColumnManager(Boolean isCommon, String beanName,String ids, Model model) {    	
////    	try{    	
////     		model.addAttribute("beanName", beanName);
////     		model.addAttribute("ids", ids);
////     		model.addAttribute("isCommon", null == isCommon ? true : isCommon);
////    	}catch(Exception e){
////    		logger.error("查询列表出现异常",e);
////    	}
////    	return "/common/exportColumn";
////    }
////    
//public class CommonController{
//    /**
//     * 
//     * ajax查询导出的列，并生成数据文件
//     */
//    @RequestMapping(value = "/exportSelectCls", method = RequestMethod.POST)
//    @ResponseBody
//    public Object exportSelectCls(HttpServletRequest request,HttpServletResponse response) {
//    	try{    		 
//			 Map<String,String> parms = RequestUtils.getParameterMap(request);
//		     Boolean isCommon = Boolean.valueOf(String.valueOf(parms.get("isCommon")));
//		     String beanName = parms.get("beanName");
//		     TableDescEntity tde = AnnotationUtils.getTableAnnotation(beanName);		    
//		     
//		     String fieldNames = parms.get("fns[]");
//		     String propNames = parms.get("pns[]");
//		     String[] fns = fieldNames.split(",");
//		     String[] pns = propNames.split(",");
//		     String _field = null;
//		     String[] field_code = new String[fns.length];
//		     List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
//		     for(int i = 0; i < fns.length; i++){	
//		    	 _field = fns[i].trim();
//		    	 field_code[i] = Reflections.properties2field(_field).toUpperCase();
//		    	 entity.add(new ExcelExportEntity(pns[i].trim(),field_code[i]));
//		     }      	
//	      	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//	      	String ids = parms.get("ids");
//	      	if(Strings.isNullOrEmpty(ids)){
//	      		list = commonService.getExportData(isCommon, beanName, tde.getTableName(), field_code, null);
//	      	}else{
//		      	String[] id = ids.split(",");
//		      	if(beanName.contains("TBusisProduct")){
//		      		List<Map<String, Object>> _list = new ArrayList<Map<String, Object>>();
//		      		for(int i=0;i<id.length;i++){
//		      			TBusisProduct product = tbusisProductService.findById(id[i]);
//		      			Map<String,Object> maps = MsgToolUtil.bean2map(product);		      			
//		      			_list.add(maps);
//			      	}
//		      		list.addAll(_list);
//		      	}else if(beanName.contains("TBaseMailSendedQueue")){
//		      		List<Map<String, Object>> _list = new ArrayList<Map<String, Object>>();
//		      		for(int i=0;i<id.length;i++){
//		      			TBaseMailSendedQueue queue  = tbaseMailSendedQueueService.findById(id[i]);
//		      			Map<String,Object> maps = beanToMap(queue);		      			
//		      			_list.add(maps);
//			      	}
//		      		list.addAll(_list);
//		      	}else{
//		      		//批量查询 用in实现
//		      		List<Map<String, Object>> _list = commonService.getExportData(isCommon, beanName, tde.getTableName(), field_code, id);
//		      		list.addAll(_list);
//		      	}	      		
//	      	}
//	      	//需要统一将字典值翻译为汉字
//	      	List<TableColumnEntity> cols = AnnotationUtils.getEntityAnnotation(beanName, "show");
//	      	for(TableColumnEntity tce : cols){
//	      		String isDic = tce.getDicFlag();
//	      		String fieldCode = tce.getFieldName();
//	      		if(isDic.equals("Y")){	      			
//	      			String dicclass = tce.getDicClassCode();
//	      			//logger.info(fieldCode+"==>"+dicclass);
//	      			//取出字典值
//      				Map<String, String> dic = DicCache.getDicDataByClassCode(dicclass);
//      				String _fieldCode  = Reflections.properties2field(fieldCode);
//      				_fieldCode = _fieldCode.toUpperCase();
//	      			for(Map<String, Object> map : list){	      				
//	      				String value = dic.get(String.valueOf(map.get(_fieldCode)));
//	      				map.put(_fieldCode, value);	      				
//	      			}
//	      		}
//	      		//时间字段进行格式化 yyyy-MM-dd HH:mm:ss
//	      		if("class java.util.Date".equals(tce.getDataType())){
//	      			
//	      			String _fieldCode  = Reflections.properties2field(fieldCode);
//	      			_fieldCode = _fieldCode.toUpperCase();
//	      			for(Map<String, Object> map : list){	      				
//	      				Date date = (Date)map.get(_fieldCode);
//	      				String value = DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss");
//	      				map.put(_fieldCode, value);	      				
//	      			}	      			
//	      		}
//	      	}
//	         HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(new ExportParams(
//	        		 tde.getTableDesc(), tde.getTableDesc()+"信息表"), entity, list); 
//	         FileOutputStream fos;
//	         String filename = tde.getTableDesc()+"_"+DateUtils.getDate("yyyyMMddHHmmss")+".xls";
//	        // String propath = File.separator+"config";         
//	    	 String downloadpath = Constants.PARMS_MAP.get("downloadpath");
//	    	 File dp = new File(downloadpath);
//	    	 if(!dp.exists()){
//	    		 FileUtils.createDirectory(downloadpath);
//	    	 }
//	    	 String filepath = downloadpath+File.separator+filename;
//	    	 
//			try {			
//				fos = new FileOutputStream(filepath);
//				workbook.write(fos);
//		        fos.close();
//			} catch (Exception e) {
//				logger.error("生成xls文件异常",e);
//			}
//			return renderSuccess(filename); 
//    	     
//    	}catch(Exception e){
//    		logger.error("导出xls出现异常",e);
//    		return renderError(Constants.PARMS_MAP.get("client.show.msg"));
//    	}
//    	
//    }
//    
//    
//    private Map<String,Object> beanToMap(TBaseMailSendedQueue queue){
//    	LinkedHashMap<String,Object> list_map = new LinkedHashMap<String,Object>();
//    	list_map.put("TO_MAIL".toUpperCase(), MsgToolUtil.Null2String(queue.getToMail()));
//    	list_map.put("TITLE".toUpperCase(), MsgToolUtil.Null2String(queue.getTitle()));
//    	list_map.put("BODY".toUpperCase(), MsgToolUtil.Null2String(queue.getBody()));
//    	list_map.put("COMEFORM_MAIL".toUpperCase(), MsgToolUtil.Null2String(queue.getComeformMail()));
//    	list_map.put("SEND_DATE".toUpperCase(), queue.getSendDate());
//    	list_map.put("STATUS".toUpperCase(), MsgToolUtil.Null2String(queue.getStatus()));
//    	list_map.put("ERROR_REASON_INFO".toUpperCase(), MsgToolUtil.Null2String(queue.getErrorReasonInfo()));
//    	return list_map;
//    }
//    
//    /**
//     * 下载
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/download")
//    public void download(HttpServletRequest request,HttpServletResponse response) {
//    	 response.setContentType("application/binary;charset=ISO8859_1"); 
//    	 File fileLoad = null;
//	     try{
//        	String filename = request.getParameter("filename");	        	     
//        	String downloadpath = Constants.PARMS_MAP.get("downloadpath");
//            //中文文件名需要转码
//        	String _filename = new String(filename.getBytes(), "ISO8859_1");        	
//            response.setHeader("Content-disposition", "attachment; filename=" + _filename);// 组装附件名称和格式  
//            fileLoad = new File(downloadpath+File.separator+filename); 
//            long fileLength = fileLoad.length(); 
//            String length = String.valueOf(fileLength); 
//            response.setHeader("Content_Length", length); 
//            FileInputStream input = null; 
//            ServletOutputStream output = null; 
//            try{
//               input = new FileInputStream(fileLoad); 
//               output = response.getOutputStream(); 
//               byte[] block = new byte[1024]; 
//               int len = 0; 
//               while ((len = input.read(block)) != -1) { 
//            	   output.write(block, 0, len); 
//               } 
//            } catch (Exception e) { 
//            	e.getMessage(); 
//            } finally { 
//               try { 
//	                input.close(); 
//	                output.flush(); 
//	                output.close(); 
//               } catch (Exception ex) { 
//            	   logger.error("关闭文件流异常",ex);
//               } 
//            }
//        }catch(Exception e){
//        	logger.error("导出文件异常",e);
//        }finally{
//        	if(fileLoad.exists()){
//        		FileUtils.deleteFile(fileLoad.getAbsolutePath());
//        	}
//        }
//	        
//    }
//    
//    
//    
//    
//    /**
//     * 下载模板文件
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/downloadTemplete")
//    public void downloadTemplete(HttpServletRequest request,HttpServletResponse response) {
//    	
//    	 response.setContentType("application/binary;charset=ISO8859_1"); 
//    	 File fileLoad = null;
//	     try{
//        	String filename = request.getParameter("filename");	        	     
//        	String downloadpath = Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"templete";
//            //中文文件名需要转码
//        	String _filename = new String(filename.getBytes(), "ISO8859_1");        	
//            response.setHeader("Content-disposition", "attachment; filename=" + _filename);// 组装附件名称和格式  
//            fileLoad = new File(downloadpath+File.separator+filename); 
//            long fileLength = fileLoad.length(); 
//            String length = String.valueOf(fileLength); 
//            response.setHeader("Content_Length", length); 
//            FileInputStream input = null; 
//            ServletOutputStream output = null; 
//            try{
//               input = new FileInputStream(fileLoad); 
//               output = response.getOutputStream(); 
//               byte[] block = new byte[1024]; 
//               int len = 0; 
//               while ((len = input.read(block)) != -1) { 
//            	   output.write(block, 0, len); 
//               } 
//            } catch (Exception e) { 
//            	e.getMessage(); 
//            } finally { 
//               try { 
//	                input.close(); 
//	                output.flush(); 
//	                output.close(); 
//               } catch (Exception ex) { 
//                ex.getMessage(); 
//               } 
//            }
//            
//        }catch(Exception e){
//        	logger.error("下载文件异常",e);
//        }
//	        
//    }
//    
//    /**
//     * 下载
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/commonDownload")
//    public void commonDownload(HttpServletRequest request,HttpServletResponse response) {
//    	
//    	 response.setContentType("application/binary;charset=ISO8859_1"); 
//    	 File fileLoad = null;
//	     try{
//	    	String filename = request.getParameter("filename");	
//        	String downtype = request.getParameter("downtype");	 
//        	String filesize = request.getParameter("filesize");	 
//        	if(Strings.isNullOrEmpty(downtype)){
//        		return ;
//        	}
//        	String downloadpath = "";
//        	if(downtype.equals("notice")){
//        		downloadpath = Constants.PARMS_MAP.get("notice.upload.path");
//        	}else if(downtype.equals("product")){
//        		downloadpath = Constants.PARMS_MAP.get("product.upload.path");
//        	}
//        	//没有上传图片的话，显示默认图片
//        	if(filename.equals("undefined")||filename.equals("null")||"".equals(filename)){
//        		filename = "default_120.jpg";
//        		String defaultPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"templete";
//        		downloadpath = defaultPath;
//        	}
//        	//中文文件名需要转码
//        	//String _filename = new String(filename.getBytes(), "ISO8859_1");   
//        	String _filename = filename;
//        	//前缀
//            String prefix = _filename.substring(_filename.lastIndexOf("."),_filename.length());
//            //logger.info("==>"+prefix);
//            //组合文件名称
//            String fn = _filename.substring(0,_filename.lastIndexOf("."));
//            if(filesize==null){
//            	filesize = "120";
//            }
//            fn = fn +"_" + filesize;
//            String newfilename = fn+prefix;
//            
//            response.setHeader("Content-disposition", "attachment; filename=" + newfilename);// 组装附件名称和格式  
//            fileLoad = new File(downloadpath+File.separator+newfilename); 
//            long fileLength = fileLoad.length(); 
//            String length = String.valueOf(fileLength); 
//            response.setHeader("Content_Length", length); 
//            FileInputStream input = null; 
//            ServletOutputStream output = null; 
//            try{
//               input = new FileInputStream(fileLoad); 
//               output = response.getOutputStream(); 
//               byte[] block = new byte[1024]; 
//               int len = 0; 
//               while ((len = input.read(block)) != -1) { 
//            	   output.write(block, 0, len); 
//               } 
//            } catch (Exception e) { 
//            	//e.getMessage(); 
//            } finally { 
//               try { 
//            	   if(input!=null){
//            		   input.close(); 
//            	   }
//            	   if(output!=null){
//            		   output.flush(); 
//            		   output.close(); 
//            	   }
//               } catch (Exception ex) { 
//            	   logger.error("关闭文件流异常",ex);
//               } 
//            }
//            
//        }catch(Exception e){
//        	logger.error("导出文件异常",e);
//        }finally{
//        	
//        }
//	     
//    }
//    
//       
//}
