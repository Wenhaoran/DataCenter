package cn.digitalpublishing.springmvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cn.digitalpublishing.service.base.BaseService;
import cn.digitalpublishing.service.design.DataFieldService;
import cn.digitalpublishing.service.design.DataObjectService;
import cn.digitalpublishing.service.design.DeDBConnectService;
import cn.digitalpublishing.service.design.DesignTableColumnService;
import cn.digitalpublishing.service.design.DesignTableConfigService;
import cn.digitalpublishing.service.design.DesignTableService;
import cn.digitalpublishing.service.design.FtpConnectService;
import cn.digitalpublishing.service.echarts.EchartColumnService;
import cn.digitalpublishing.service.echarts.EchartColumnValueService;
import cn.digitalpublishing.service.echarts.EchartExportRSService;
import cn.digitalpublishing.service.echarts.EchartJsonMapService;
import cn.digitalpublishing.service.echarts.EchartJsonService;
import cn.digitalpublishing.service.echarts.EchartsService;
import cn.digitalpublishing.service.excel.XlsColumnService;
import cn.digitalpublishing.service.excel.XlsObjectService;
import cn.digitalpublishing.service.export.DataExportService;
import cn.digitalpublishing.service.imp.ImportLogService;
import cn.digitalpublishing.service.imp.ImportRTMappedService;
import cn.digitalpublishing.service.imp.ImportRoleDetailMatchService;
import cn.digitalpublishing.service.imp.ImportRoleDetailService;
import cn.digitalpublishing.service.imp.ImportRoleService;
import cn.digitalpublishing.service.imp.ImportTimeTaskService;
import cn.digitalpublishing.service.txt.TxtColumnService;
import cn.digitalpublishing.service.txt.TxtObjectService;
import cn.digitalpublishing.springmvc.form.Result;
import cn.digitalpublishing.util.StringEscapeEditor;
import net.sf.json.JSONObject;
import quartz.cn.digitalpublishing.service.ScheduleJobService;

// 								 _ooOoo_
// 								o8888888o
//								88" . "88
//								(| -_- |)
// 								 O\ = /O
//							 ____/`---'\____
//						   .   ' \\| |// `.
//							/ \\||| : |||// \
//						  / _||||| -:- |||||- \
//                          | | \\\ - /// | |
//                        | \_| ''\---/'' | |
//                         \ .-\__ `-` ___/-. /
//                      ___`. .' /--.--\ `. . __
//                   ."" '< `.___\_<|>_/___.' >'"".
//                  | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                    \ \ `-. \_ __\ /__ _/ .-` / /
//            ======`-.____`-.___\_____/___.-`____.-'======
//                               `=---='
//
//       .............................................
//                    佛祖镇楼                  BUG辟易
//           		 佛曰:
//					写字楼里写字间，写字间里程序员；
//					程序人员写程序，又拿程序换酒钱。
//					酒醒只在网上坐，酒醉还来网下眠；
//					酒醉酒醒日复日，网上网下年复年。
//					但愿老死电脑间，不愿鞠躬老板前；
//					奔驰宝马贵者趣，公交自行程序员。
//					别人笑我忒疯癫，我笑自己命太贱；
//					不见满街漂亮妹，哪个归得程序员？


public class BaseController extends MultiActionController{
	
	public Logger log = Logger.getLogger(this.getClass());
	
	public static final String VALIDATE_CODE = "validateCode";
	
	public static final String GLOBAL_LOGIN_URL = "/pages/login.jsp";
	
	public static final String FAILURE = "false";
	
	public static final String SUCCESS = "true";
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	@Resource(name="deDBConnectService")
	protected DeDBConnectService deDBConnectService;
	
	@Resource(name="dataObjectService")
	protected DataObjectService dataObjectService;
	
	@Resource(name="dataFieldService")
	protected DataFieldService dataFieldService;
	
	@Resource(name="designTableService")
	protected DesignTableService designTableService;
	
	@Resource(name="designTableColumnService")
	protected DesignTableColumnService designTableColumnService;
	
	@Resource(name="designTableConfigService")
	protected DesignTableConfigService designTableConfigService;
	
	@Resource(name="ftpConnectService")
	protected FtpConnectService ftpConnectService;
	
	@Resource(name="xlsObjectService")
	protected XlsObjectService xlsObjectService;
	
	@Resource(name="xlsColumnService")
	protected XlsColumnService xlsColumnService;
	
	@Resource(name="txtObjectService")
	protected TxtObjectService txtObjectService;
	
	@Resource(name="txtColumnService")
	protected TxtColumnService txtColumnService;
	
	@Resource(name="importTimeTaskService")
	protected ImportTimeTaskService importTimeTaskService;
	
	@Resource(name="importLogService")
	protected ImportLogService importLogService;
	
	@Resource(name="importRoleService")
	protected ImportRoleService importRoleService;
	
	@Resource(name="importRoleDetailService")
	protected ImportRoleDetailService importRoleDetailService;
	
	@Resource(name="importRTMappedService")
	protected ImportRTMappedService importRTMappedService;
	
	@Resource(name="baseService")
	protected BaseService baseService;
	
	@Resource(name="dataExportService")
	protected DataExportService dataExportService;
	
	@Resource(name="scheduleJobService")
	protected ScheduleJobService scheduleJobService;
	
	@Resource(name="importRoleDetailMatchService")
	protected ImportRoleDetailMatchService importRoleDetailMatchService;
	
	
	@Resource(name="echartsService")
	protected EchartsService echartsService;
	
	@Resource(name="echartColumnService")
	protected EchartColumnService echartColumnService;
	
	@Resource(name="echartColumnValueService")
	protected EchartColumnValueService echartColumnValueService;
	
	@Resource(name="echartJsonService")
	protected EchartJsonService echartJsonService;
	
	@Resource(name="echartJsonMapService")
	protected EchartJsonMapService echartJsonMapService;
	
	@Resource(name="echartExportRSService")
	protected EchartExportRSService echartExportRSService;
	
	@InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }
	
	/**
	 * 返回ajax请求后的信息
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	public void ajaxReturn(HttpServletResponse response,String result) throws IOException{
		PrintWriter writer = null;
	    try {
	    	response.setContentType("text/html;charset=utf-8");
			//获取输出流
			writer = response.getWriter();
			writer.print(result);
		} catch (IOException e) {
			throw e;
		} finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	/**
	 * 返回ajax请求后的json信息
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	public void ajaxReturnJson(HttpServletResponse response,JSONObject json) throws IOException{
		PrintWriter writer = null;
	    try {
	    	response.setContentType("text/json;charset=UTF-8");
			//获取输出流
			writer = response.getWriter();
			writer.print(json.toString());
			writer.flush();
		} catch (IOException e) {
			throw e;
		} finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	/**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }
    
}
